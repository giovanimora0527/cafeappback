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

import co.com.grupoasd.fae.model.entity.PerfilPermiso;
import co.com.grupoasd.fae.model.entity.PerfilPermisoIdentity;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.PerfilPermisoRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.util.SeguridadUtil;
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
 * Enpoint REST para gestion de PerfilPermiso.
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class PerfilPermisoApiController implements PerfilPermisoApi {

    /**
     * PerfilPermisoRepository.
     */
    private final PerfilPermisoRepository perfilPermisoRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase.
     *
     * @param perfilPermisoRepository PerfilPermisoRepository
     */
    @Autowired
    private PerfilPermisoApiController(final PerfilPermisoRepository perfilPermisoRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository) {
        this.perfilPermisoRepository = perfilPermisoRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Listar Registros de PerfilPermiso
     *
     * @return Listado de PerfilPermiso
     */
    @Override
    public ResponseEntity<List<PerfilPermiso>> listar() {
        Iterable<PerfilPermiso> iter = perfilPermisoRepository.findAll();
        List<PerfilPermiso> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de Perfil
     *
     * @param menuCodigo Identificador del registro
     * @param perfilId Identificador del registro
     * @param menuFuncionCodigo Identificador del registro
     * @return Registro de PerfilPermiso
     */
    @Override
    public ResponseEntity<PerfilPermiso> obtener(final @PathVariable("menuCodigo") String menuCodigo,
            final @PathVariable("perfilId") Long perfilId,
            final @PathVariable("menuFuncionCodigo") String menuFuncionCodigo) {
        PerfilPermisoIdentity identity = new PerfilPermisoIdentity();
        identity.setMenuCodigo(menuCodigo);
        identity.setPerfilId(perfilId);
        identity.setMenuFuncionCodigo(menuFuncionCodigo);
        Optional<PerfilPermiso> perf = perfilPermisoRepository.findById(identity);
        if (perf.isPresent()) {
            return ResponseEntity.ok(perf.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", identity), 400));
        }
    }

    /**
     * Crea un registro de PerfilPermiso
     *
     * @param perfilPermiso PerfilPermiso
     * @return objeto PerfilPermiso
     */
    @Override
    public ResponseEntity<PerfilPermiso> crear(@RequestBody @Validated PerfilPermiso perfilPermiso, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        Optional<PerfilPermiso> perf = perfilPermisoRepository.findById(perfilPermiso.getPerfilPermisoIdentity());
        if (perf.isPresent()) {
            perfilPermiso.setModificadoPor(user.getId());
            perfilPermiso.setFechaModificacion(Date.from(Instant.now()));
        } else {
            perfilPermiso.setCreadoPor(user.getId());
            perfilPermiso.setFechaCreacion(Date.from(Instant.now()));
        }
        return ResponseEntity.ok(perfilPermisoRepository.save(perfilPermiso));
    }

    /**
     * cambia un registro de PerfilPermiso
     *
     * @param perfilPermiso PerfilPermiso
     * @return PerfilPermiso
     */
    @Override
    public ResponseEntity<PerfilPermiso> cambiar(@RequestBody @Validated PerfilPermiso perfilPermiso, HttpServletRequest req) {
        //if(perfil.getId().isEmpty()){
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        perfilPermiso.setModificadoPor(user.getId());
        perfilPermiso.setFechaModificacion(Date.from(Instant.now()));
        return ResponseEntity.ok(perfilPermisoRepository.save(perfilPermiso));
        /*}
        else{
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a Cambiar",null, 400));
        }  */
    }

}
