/*
* Archivo: MenuFuncionServiceImpl.java
* Fecha: 11/01/2019
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
package co.com.grupoasd.fae.autenticacion.service;

import co.com.grupoasd.fae.model.entity.Menu;
import co.com.grupoasd.fae.model.entity.MenuFuncion;
import co.com.grupoasd.fae.model.entity.PerfilPermiso;
import co.com.grupoasd.fae.model.repository.MenuFuncionRepository;
import co.com.grupoasd.fae.model.repository.PerfilPermisoRepository;
import co.com.grupoasd.fae.util.VariablesEstaticas;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Servicios de MenuFuncion
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Service
public class MenuFuncionServiceImpl implements MenuFuncionService{

    private final PerfilPermisoRepository perfilPermisoRepository;
    private final MenuFuncionRepository menuFuncionRepository;
    
    @Autowired
    public MenuFuncionServiceImpl(final PerfilPermisoRepository perfilPermisoRepository,
            final MenuFuncionRepository menuFuncionRepository) {
        this.perfilPermisoRepository = perfilPermisoRepository;
        this.menuFuncionRepository = menuFuncionRepository;
    }

    @Override
    public MenuFuncion crear(MenuFuncion menuFuncion, Long iduser) {
        Optional<MenuFuncion> funcionOptional = menuFuncionRepository.findById(menuFuncion.getMenuFuncionIdentity());
        if (funcionOptional.isPresent()) {
            if (menuFuncion.getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
                Menu menuCodigo = new Menu();
                menuCodigo.setMenuCodigo(menuFuncion.getMenuFuncionIdentity().getMenuCodigo());
                List<PerfilPermiso> perfilPermisos = perfilPermisoRepository.findByMenuCodigoAndMenuFuncion(menuCodigo,
                        menuFuncion.getMenuFuncionIdentity().getFuncionCodigo());
                if (perfilPermisos != null && !perfilPermisos.isEmpty()) {
                    perfilPermisos.forEach((perfilPermiso) -> {
                        perfilPermisoRepository.delete(perfilPermiso);
                    });
                }
                menuFuncionRepository.delete(funcionOptional.get());
                return menuFuncion;
            } else {
                menuFuncion.setModificadoPor(iduser);
                menuFuncion.setFechaModificacion(Date.from(Instant.now()));
                return menuFuncionRepository.save(menuFuncion);
            }
        } else {
            menuFuncion.setCreadoPor(iduser);
            menuFuncion.setFechaCreacion(Date.from(Instant.now()));
            return menuFuncionRepository.save(menuFuncion);
        }
    }
    
}
