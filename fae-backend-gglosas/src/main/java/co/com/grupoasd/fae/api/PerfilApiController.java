/*
* Archivo: PerfilApiController.java
* Fecha: 26/09/2018
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
package co.com.grupoasd.fae.api;

import co.com.grupoasd.fae.model.entity.Menu;
import co.com.grupoasd.fae.model.entity.Perfil;
import co.com.grupoasd.fae.model.entity.PerfilPermiso;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.PerfilPermisoRepository;
import co.com.grupoasd.fae.model.repository.PerfilRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.autenticacion.service.PerfilService;
import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.util.SeguridadUtil;
import co.com.grupoasd.fae.util.VariablesEstaticas;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestion de Perfiles.
 *
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@RestController
public class PerfilApiController implements PerfilApi {

    /**
     * MenúRepository.
     */
    private final PerfilRepository perfilRepository;
    private final PerfilService perfilService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final PerfilPermisoRepository perfilPermisoRepository;

    /**
     * Constructor de la clase.
     *
     * @param perfilRepository PerfilRepository
     */
    @Autowired
    private PerfilApiController(final PerfilRepository perfilRepository,
            final PerfilService perfilService,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final PerfilPermisoRepository perfilPermisoRepository) {
        this.perfilRepository = perfilRepository;
        this.perfilService = perfilService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.perfilPermisoRepository = perfilPermisoRepository;
    }

    /**
     * Listar Registros de Perfiles
     *
     * @return Listado de Perfiles
     */
    @Override
    public ResponseEntity<List<Perfil>> listar() {
        Iterable<Perfil> iter = perfilRepository.findAll();
        List<Perfil> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de Perfil
     *
     * @param id Identificador del registro
     * @return Registro de Perfil
     */
    @Override
    public ResponseEntity<Perfil> obtener(final @PathVariable("id") Long id) {
        Optional<Perfil> perf = perfilRepository.findById(id);
        if (perf.isPresent()) {
            return ResponseEntity.ok(perf.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 400));
        }
    }

    /**
     * Crea un registro de Perfil
     *
     * @param perfil Perfil
     * @return perfil
     */
    @Override
    public ResponseEntity<Perfil> crear(@RequestBody @Validated Perfil perfil, HttpServletRequest req) {
        Perfil perf = perfilRepository.findByNombrePerfil(perfil.getNombrePerfil());
        if (perf != null) {
            throw new BadRequestException(ErrorRs.get("No se puede crear el perfil",
                    String.format("El perfil %s ya existe, por favor verifique e intente de nuevo.",
                            perf.getNombrePerfil()), 400));
        }
        if (perfil.getEsBase() == VariablesEstaticas.ESTADO_ACTIVO) {
            Iterable<Perfil> iter = perfilRepository.findAll();
            List<Perfil> res = new ArrayList<>();
            iter.forEach(res::add);
            for (Perfil per : res) {
                if (per.getEsBase() == VariablesEstaticas.ESTADO_ACTIVO) {
                    if (per.getTipoBase().equals(perfil.getTipoBase())) {
                        throw new BadRequestException(ErrorRs.get("No se puede crear el perfil",
                                String.format("El perfil %s no se puede crear, solo puede existir un tipo base en el sistema, "
                                        + "por favor verifique e intente de nuevo.", perfil.getNombrePerfil()), 400));
                    }
                }
            }
        }

        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        perfil.setCreadoPor(user.getId());
        perfil.setFechaCreacion(Date.from(Instant.now()));
        return ResponseEntity.ok(perfilRepository.save(perfil));
    }

    /**
     * cambia un registro de Perfil
     *
     * @param perfil Perfil
     * @return Perfil
     */
    @Override
    public ResponseEntity<Perfil> cambiar(@RequestBody @Validated Perfil perfil, HttpServletRequest req) {
        //if(perfil.getId().isEmpty()){
        Perfil perf = perfilRepository.findByNombrePerfil(perfil.getNombrePerfil());
        if (perf != null && !perf.getId().equals(perfil.getId())) {
            throw new BadRequestException(ErrorRs.get("No se puede modificar el perfil",
                    String.format("El perfil %s ya existe, por favor verifique e intente de nuevo.",
                            perf.getNombrePerfil()), 400));
        }
        if (perfil.getEsBase() == VariablesEstaticas.ESTADO_ACTIVO) {
            Iterable<Perfil> iter = perfilRepository.findAll();
            List<Perfil> res = new ArrayList<>();
            iter.forEach(res::add);
            for (Perfil per : res) {
                if (per.getEsBase() == VariablesEstaticas.ESTADO_ACTIVO) {
                    if (per.getTipoBase().equals(perfil.getTipoBase())) {
                        throw new BadRequestException(ErrorRs.get("No se puede modificar el perfil",
                                String.format("El perfil %s no se puede modificar, solo puede existir un tipo base en el sistema, "
                                        + "por favor verifique e intente de nuevo.", perfil.getNombrePerfil()), 400));
                    }
                }
            }
        }
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        if (perfil.getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
            inactivarPermisosAcciones(perfil, true, false, req);
        }
        perfil.setModificadoPor(user.getId());
        perfil.setFechaModificacion(Date.from(Instant.now()));
        return ResponseEntity.ok(perfilRepository.save(perfil));
        /*}
        else{
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a Cambiar",null, 400));
        }  */
    }

    @Override
    public ResponseEntity<List<Menu>> obtenerMenuPerfil(final @PathVariable("id") Long id) {
        return ResponseEntity.ok(perfilService.obtenerMenuPerfil(id, false));
    }

    @Override
    public ResponseEntity<GenericRs> eliminar(final @PathVariable("id") Long id, HttpServletRequest req) {
        Optional<Perfil> optionalPerf = perfilRepository.findById(id);
        if (optionalPerf.isPresent()) {
            if (!inactivarPermisosAcciones(optionalPerf.get(), false, true, req)) {
                perfilRepository.delete(optionalPerf.get());
            }
            return ResponseEntity.ok(new GenericRs("200", "Eliminado"));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a eliminar", null, 404));
        }
    }

    private boolean inactivarPermisosAcciones(Perfil perfil, boolean actualizar,
            boolean eliminar, HttpServletRequest req) {
        boolean tienePermisosAcciones = false;
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        List<PerfilPermiso> perfilPermisos = perfilPermisoRepository.findByPerfilId(perfil);
        if (perfilPermisos != null && !perfilPermisos.isEmpty()) {
            for (PerfilPermiso perfilPermiso : perfilPermisos) {
                if (perfilPermiso.getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                    if (actualizar) {
                        perfilPermiso.setModificadoPor(user.getId());
                        perfilPermiso.setFechaModificacion(Date.from(Instant.now()));
                        perfilPermiso.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                        perfilPermisoRepository.save(perfilPermiso);
                    }
                }
            }
        }

        List<Usuario> usuarios = usuarioRepository.findByPerfilId(perfil.getId());
        if (usuarios != null && !usuarios.isEmpty()) {
            tienePermisosAcciones = true;
            for (Usuario usuario : usuarios) {
                if (usuario.getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                    if (actualizar) {
                        usuario.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                        usuario.setModificadoPor(user.getId());
                        usuario.setFechaModificacion(Date.from(Instant.now()));
                        usuarioRepository.save(usuario);
                    } else {
                        throw new BadRequestException(ErrorRs.get("No se puede eliminar el perfil seleccionado",
                                "No es posible eliminar el elemento seleccionado ya que cuenta con información asociada.", 400));
                    }
                }
            }
        }

        if (perfilPermisos != null && !perfilPermisos.isEmpty()) {
            if (eliminar) {
                perfilPermisos.stream().forEachOrdered((perfilPermiso) -> {
                    perfilPermisoRepository.delete(perfilPermiso);
                });
            }
        }

        return tienePermisosAcciones;
    }

}
