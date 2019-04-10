/*
* Archivo: UsuarioApiController.java
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

import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.autenticacion.service.UsuarioService;
import co.com.grupoasd.fae.exception.BadRequestException;
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
 * Enpoint REST para gestion de Usuarios.
 *
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@RestController
public class UsuarioApiController implements UsuarioApi {

    /**
     * MenúRepository.
     */
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructor de la clase.
     *
     * @param perfilRepository PerfilRepository
     */
    @Autowired
    private UsuarioApiController(final UsuarioRepository usuarioRepository,
            final UsuarioService usuarioService,
            final JwtTokenProvider jwtTokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Listar Registros de Usuarios
     *
     * @return Listado de Usuarios
     */
    @Override
    public ResponseEntity<List<Usuario>> listar() {
        Iterable<Usuario> iter = usuarioRepository.findAll();
        List<Usuario> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de Usuarios
     *
     * @param id Identificador del registro
     * @return Registro de Usuarios
     */
    @Override
    public ResponseEntity<Usuario> obtener(final @PathVariable("id") Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 400));
        }
    }

    /**
     * Crea un registro de Usuario
     *
     * @param usuario Usuario
     * @return usuario
     */
    @Override
    public ResponseEntity<Usuario> crear(@RequestBody @Validated Usuario usuario, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        usuario.setCreadoPor(user.getId());
        usuario.setFechaCreacion(Date.from(Instant.now()));
        return usuarioService.crearUsuario(usuario);
    }

    /**
     * cambia un registro de Usuario
     *
     * @param usuario Usuario
     * @return Usuario
     */
    @Override
    public ResponseEntity<Usuario> cambiar(@RequestBody @Validated Usuario usuario, HttpServletRequest req) {
        Optional<Usuario> user = usuarioRepository.findById(usuario.getId());
        if (user.isPresent()) {
            Usuario usuarioActual = usuarioRepository.findByEmail(usuario.getEmail());
            if (usuarioActual != null && !usuarioActual.getId().equals(usuario.getId())) {
                throw new BadRequestException(ErrorRs.get("Cuenta de correo existente",
                        "La cuenta de correo ya está siendo usada por otro usuario", 400));
            }
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user1 = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            usuario.setPassword(user.get().getPassword());
            usuario.setCreadoPor(user.get().getCreadoPor());
            usuario.setFechaCreacion(user.get().getFechaCreacion());
            usuario.setModificadoPor(user1.getId());
            usuario.setFechaModificacion(Date.from(Instant.now()));
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a Cambiar", null, 400));
        }
    }

    /**
     * Obtiene un registro de Usuario
     * @param email Email del usuario
     * @return Registro de Usuario
     */
    @Override
    public ResponseEntity<Usuario> obtenerXEmail(final @PathVariable("email") String email) {
        return ResponseEntity.ok(usuarioRepository.findByEmail(email));
    }
}
