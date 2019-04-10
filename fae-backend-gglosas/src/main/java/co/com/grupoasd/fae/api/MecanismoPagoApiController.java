/*
* Archivo: MecanismoPagoApiController.java
* Fecha: 13/12/2018
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
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.entity.MecanismoPago;
import co.com.grupoasd.fae.model.repository.MecanismoPagoRepository;
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
 * Enpoint REST para la gestión Mecanismo de pago
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class MecanismoPagoApiController implements MecanismoPagoApi {

    /**
     * MecanismoPagoRepository.
     */
    private final MecanismoPagoRepository mecanismoPagoRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase.
     * @param jwtTokenProvider JwtTokenProvider
     * @param usuarioRepository UsuarioRepository
     * @param mecanismoPagoRepository MecanismoPagoRepository
     */
    @Autowired
    private MecanismoPagoApiController(final MecanismoPagoRepository mecanismoPagoRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository) {
        this.mecanismoPagoRepository = mecanismoPagoRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Listar Mecanismo de pago
     *
     * @return Listado de Mecanismo de pago
     */
    @Override
    public ResponseEntity<List<MecanismoPago>> listar() {
        Iterable<MecanismoPago> iter = mecanismoPagoRepository.findAll();
        List<MecanismoPago> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de Mecanismo de pago
     *
     * @param id Identificador de Mecanismo de pago
     * @return Registro de Mecanismo de pago
     */
    @Override
    public ResponseEntity<MecanismoPago> obtener(final @PathVariable("id") Long id) {
        Optional<MecanismoPago> entiOptional = mecanismoPagoRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    /**
     * Crea un registro de Mecanismo de pago
     *
     * @param mecanismoPago Código de Mecanismo de pago
     * @param req
     * @return Objeto Mecanismo de pago
     */
    @Override
    public ResponseEntity<MecanismoPago> crear(@RequestBody @Validated MecanismoPago mecanismoPago, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        mecanismoPago.setCreadoPor(user.getId());
        mecanismoPago.setFechaCreacion(Date.from(Instant.now()));

        return ResponseEntity.ok(mecanismoPagoRepository.save(mecanismoPago));
    }

    /**
     * cambia un registro de Mecanismo de pago
     *
     * @param mecanismoPago Código de Mecanismo de pago
     * @param req
     * @return Objeto Mecanismo de pago
     */
    @Override
    public ResponseEntity<MecanismoPago> cambiar(@RequestBody @Validated MecanismoPago mecanismoPago, HttpServletRequest req) {
        if (mecanismoPago.getId() != null) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            mecanismoPago.setModificadoPor(user.getId());
            mecanismoPago.setFechaModificacion(Date.from(Instant.now()));
            return ResponseEntity.ok(mecanismoPagoRepository.save(mecanismoPago));
        } else {
            throw new NotFoundException(ErrorRs.get("No existe el mecanismo de pago a modificar", null, 404));
        }
    }
}
