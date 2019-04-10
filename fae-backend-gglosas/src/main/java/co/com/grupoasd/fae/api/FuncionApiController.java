/*
* Archivo: FuncionApiController.java
* Fecha: 30/10/2018
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

import co.com.grupoasd.fae.model.entity.Funcion;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.FuncionRepository;
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
 * Enpoint REST para gestion de Funcion.
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class FuncionApiController implements FuncionApi {

    /**
     * FuncionRepository.
     */
    private final FuncionRepository funcionRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase.
     *
     * @param menuRepository MenuRepository
     */
    @Autowired
    private FuncionApiController(final FuncionRepository funcionRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository) {
        this.funcionRepository = funcionRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Listar Funcion
     *
     * @return Listado de Funcion
     */
    @Override
    public ResponseEntity<List<Funcion>> listar() {
        Iterable<Funcion> iter = funcionRepository.findAll();
        List<Funcion> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de una Funcion
     *
     * @param id Identificador de la Funcion
     * @return Registro de Funcion
     */
    @Override
    public ResponseEntity<Funcion> obtener(final @PathVariable("id") String id) {
        Optional<Funcion> funcionOptional = funcionRepository.findById(id);
        if (funcionOptional.isPresent()) {
            return ResponseEntity.ok(funcionOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 400));
        }
    }

    /**
     * Crea un registro de Funcion
     *
     * @param funcion Código de la Funcion
     * @param req
     * @return Objeto Funcion
     */
    @Override
    public ResponseEntity<Funcion> crear(@RequestBody @Validated Funcion funcion, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        funcion.setCreadoPor(user.getId());
        funcion.setFechaCreacion(Date.from(Instant.now()));
        return ResponseEntity.ok(funcionRepository.save(funcion));
    }

    /**
     * cambia un registro de una Funcion
     *
     * @param funcion Código de la Funcion
     * @param req
     * @return Objeto Funcion
     */
    @Override
    public ResponseEntity<Funcion> cambiar(@RequestBody @Validated Funcion funcion, HttpServletRequest req) {
        if (!funcion.getFuncionCodigo().isEmpty()) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            funcion.setModificadoPor(user.getId());
            funcion.setFechaModificacion(Date.from(Instant.now()));
            return ResponseEntity.ok(funcionRepository.save(funcion));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a Cambiar", null, 400));
        }
    }
}
