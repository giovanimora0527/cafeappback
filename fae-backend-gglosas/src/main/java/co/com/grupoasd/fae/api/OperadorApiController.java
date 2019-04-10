/*
* Archivo: OperadorApiController.java
* Fecha: 22/10/2018
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

import co.com.grupoasd.fae.model.entity.OperadorFe;
import co.com.grupoasd.fae.model.repository.OperadorFeRepository;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
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
 * Enpoint REST para gestion de Operadores de facturacion electrónica.
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class OperadorApiController implements OperadorFeApi {

    /**
     * OperadorFeRepository.
     */
    private final OperadorFeRepository operadorFeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase.
     * @param jwtTokenProvider JwtTokenProvider
     * @param usuarioRepository UsuarioRepository
     * @param operadorFeRepository1 OperadorFeRepository
     */
    @Autowired
    private OperadorApiController(final OperadorFeRepository operadorFeRepository1,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository) {
        this.operadorFeRepository = operadorFeRepository1;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Listar OperadorFe
     *
     * @return Listado de OperadorFe
     */
    @Override
    public ResponseEntity<List<OperadorFe>> listar() {
        Iterable<OperadorFe> iter = operadorFeRepository.findAll();
        List<OperadorFe> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de una OperadorFe
     *
     * @param id Identificador de la OperadorFe
     * @return Registro de OperadorFe
     */
    @Override
    public ResponseEntity<OperadorFe> obtener(final @PathVariable("id") Long id) {
        Optional<OperadorFe> entiOptional = operadorFeRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    /**
     * Crea un registro de OperadorFe
     *
     * @param operadorFe Código de la OperadorFe
     * @param req
     * @return Objeto OperadorFe
     */
    @Override
    public ResponseEntity<OperadorFe> crear(@RequestBody @Validated OperadorFe operadorFe, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        operadorFe.setCreadoPor(user.getId());
        operadorFe.setFechaCreacion(Date.from(Instant.now()));

        return ResponseEntity.ok(operadorFeRepository.save(operadorFe));
    }

    /**
     * cambia un registro de una OperadorFe
     *
     * @param operadorFe Código de la OperadorFe
     * @param req
     * @return Objeto OperadorFe
     */
    @Override
    public ResponseEntity<OperadorFe> cambiar(@RequestBody @Validated OperadorFe operadorFe, HttpServletRequest req) {
        if (operadorFe.getId() != null) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            operadorFe.setModificadoPor(user.getId());
            operadorFe.setFechaModificacion(Date.from(Instant.now()));
            return ResponseEntity.ok(operadorFeRepository.save(operadorFe));
        } else {
            throw new NotFoundException(ErrorRs.get("No existe el operador a modificar", null, 404));
        }
    }
}
