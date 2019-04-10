/*
* Archivo: EntidadServiceImpl.java
* Fecha: 19/12/2018
* Todos los derechos de propiedad intelectual e industrial sobre esta
* aplicacion son de propiedad exclusiva del GRUPO ASESORIA EN
* SISTEMATIZACION DE DATOS SOCIEDAD POR ACCIONES SIMPLIFICADA GRUPO ASD
  S.A.S.
* Su uso, alteracion, reproduccion o modificacion sin la debida
* consentimiento por escrito de GRUPO ASD S.A.S.
* autorizacion por parte de su autor quedan totalmente prohibidos.
*
* Este programa se encuentra protegido por las disposiciones de la
* Ley 23 de 1982 y demas normas concordantes sobre derechos de autor y
* propiedad intelectual. Su uso no autorizado dara lugar a las sanciones
* previstas en la Ley.
*/
package co.com.grupoasd.fae.administracion.service;

import co.com.grupoasd.fae.model.entity.Entidad;
import co.com.grupoasd.fae.model.entity.Perfil;
import co.com.grupoasd.fae.model.entity.PerfilPermiso;
import co.com.grupoasd.fae.model.entity.PerfilPermisoIdentity;
import co.com.grupoasd.fae.model.repository.EntidadRepository;
import co.com.grupoasd.fae.model.repository.PerfilPermisoRepository;
import co.com.grupoasd.fae.model.repository.PerfilRepository;
import co.com.grupoasd.fae.util.VariablesEstaticas;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicios para Entidad
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Service
public class EntidadServiceImpl implements EntidadService {

    private final EntidadRepository entidadRepository;
    private final PerfilRepository perfilRepository;
    private final PerfilPermisoRepository perfilPermisoRepository;

    @Autowired
    public EntidadServiceImpl(final EntidadRepository entidadRepository,
            final PerfilRepository perfilRepository,
            final PerfilPermisoRepository perfilPermisoRepository) {
        this.entidadRepository = entidadRepository;
        this.perfilRepository = perfilRepository;
        this.perfilPermisoRepository = perfilPermisoRepository;
    }

    @Override
    public Entidad crearEntidad(Entidad entidad, Long userId) {
        Entidad newEntidad = entidadRepository.save(entidad);
        if (newEntidad.getTipoEntidad() != null && !newEntidad.getTipoEntidad().equals(VariablesEstaticas.BASE_ADMIN)) {
            List<Perfil> perfiles = perfilRepository.findByTipoBase(newEntidad.getTipoEntidad());
            if (perfiles != null && !perfiles.isEmpty()) {
                Perfil perfil = copiarPerfilAndPermisos(perfiles, userId, entidad);
                newEntidad.setPerfil(perfil);
            }
        }
        return newEntidad;
    }

    private Perfil copiarPerfilAndPermisos(List<Perfil> perfiles, Long userId, Entidad entidad) {
        Perfil perfil = new Perfil();
        perfil.setCreadoPor(userId);
        perfil.setFechaCreacion(Date.from(Instant.now()));        
        perfil.setModificadoPor(null);
        perfil.setFechaModificacion(null);
        perfil.setEntidadId(entidad.getEntidadId());
        perfil.setNombrePerfil(VariablesEstaticas.NOMBRE_BASE.concat(" - ").concat(entidad.getNombreEntidad()));
        perfil.setEsAdmin(perfiles.get(0).getEsAdmin());
        perfil.setEsBase(0);
        perfil.setEsSuper(perfiles.get(0).getEsSuper());
        perfil.setEstado(perfiles.get(0).getEstado());
        perfil = perfilRepository.save(perfil);
        List<PerfilPermiso> perfilPermisos = perfilPermisoRepository.findByPerfilId(perfiles.get(0));
        if (perfilPermisos != null && !perfilPermisos.isEmpty()) {
            for (PerfilPermiso perfilPermiso : perfilPermisos) {
                PerfilPermiso newPerfilPermiso = new PerfilPermiso();
                PerfilPermisoIdentity identity = new PerfilPermisoIdentity();
                identity.setMenuCodigo(perfilPermiso.getPerfilPermisoIdentity().getMenuCodigo());
                identity.setPerfilId(perfil.getId());
                identity.setMenuFuncionCodigo(perfilPermiso.getPerfilPermisoIdentity().getMenuFuncionCodigo());
                newPerfilPermiso.setPerfilPermisoIdentity(identity);
                newPerfilPermiso.setCreadoPor(userId);
                newPerfilPermiso.setFechaCreacion(Date.from(Instant.now()));
                newPerfilPermiso.setEstado(perfilPermiso.getEstado());
                perfilPermisoRepository.save(newPerfilPermiso);
            }
        }
        return perfil;
    }

}
