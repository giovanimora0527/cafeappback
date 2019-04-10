/*
* Archivo: SoportesServMecPagoApiController.java
* Fecha: 11/12/2018
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
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.SoporteservMecpago;
import co.com.grupoasd.fae.model.repository.SoporteservMecpagoRepository;
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
 * Enpoint REST para la gestión del Soportes por servicios de Mecanismo de pago
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class SoportesServMecPagoApiController implements SoportesServMecPagoApi {

    /**
     * SoporteservMecpagoRepository.
     */
    private final SoporteservMecpagoRepository soporteservMecpagoRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase.
     *
     * @param jwtTokenProvider JwtTokenProvider
     * @param usuarioRepository UsuarioRepository
     * @param servicioMecpagoRepository ServicioMecpagoRepository
     */
    @Autowired
    private SoportesServMecPagoApiController(final SoporteservMecpagoRepository soporteservMecpagoRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository) {
        this.soporteservMecpagoRepository = soporteservMecpagoRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Listar Soportes por servicios de Mecanismo de pago
     *
     * @return Listado de Soportes por servicios de Mecanismo de pago
     */
    @Override
    public ResponseEntity<List<SoporteservMecpago>> listar() {
        Iterable<SoporteservMecpago> iter = soporteservMecpagoRepository.findAll();
        List<SoporteservMecpago> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de Soportes por servicios de Mecanismo de pago
     *
     * @param id Identificador de Soportes por servicios de Mecanismo de pago
     * @return Registro de Soportes por servicios de Mecanismo de pago
     */
    @Override
    public ResponseEntity<SoporteservMecpago> obtener(final @PathVariable("id") Long id) {
        Optional<SoporteservMecpago> entiOptional = soporteservMecpagoRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    /**
     * Crea un registro de Soportes por servicios de Mecanismo de pago
     *
     * @param soporteservMecpago Código de Soportes por servicios de Mecanismo
     * de pago
     * @param req
     * @return Objeto Soportes por servicios de Mecanismo de pago
     */
    @Override
    public ResponseEntity<SoporteservMecpago> crear(@RequestBody @Validated SoporteservMecpago soporteservMecpago, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        soporteservMecpago.setCreadoPor(user.getId());
        soporteservMecpago.setFechaCreacion(Date.from(Instant.now()));

        return ResponseEntity.ok(soporteservMecpagoRepository.save(soporteservMecpago));
    }

    /**
     * cambia un registro de Soportes por servicios de Mecanismo de pago
     *
     * @param soporteservMecpago Código de Soportes por servicios de Mecanismo
     * de pago
     * @param req
     * @return Objeto Soportes por servicios de Mecanismo de pago
     */
    @Override
    public ResponseEntity<SoporteservMecpago> cambiar(@RequestBody @Validated SoporteservMecpago soporteservMecpago, HttpServletRequest req) {
        if (soporteservMecpago.getId() != null) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            soporteservMecpago.setModificadoPor(user.getId());
            soporteservMecpago.setFechaModificacion(Date.from(Instant.now()));
            return ResponseEntity.ok(soporteservMecpagoRepository.save(soporteservMecpago));
        } else {
            throw new NotFoundException(ErrorRs.get("No existe el operador a modificar", null, 404));
        }
    }

    /**
     * Listar Soportes por servicios de Mecanismo de pago por servicio
     *
     * @return Listado de Soportes por servicios de Mecanismo de pago
     */
    @Override
    public ResponseEntity<List<SoporteservMecpago>> listarXServicio(final @PathVariable("serviciomecPagoId") Long serviciomecPagoId) {
        Iterable<SoporteservMecpago> iter = soporteservMecpagoRepository.findByServiciomecPagoId(serviciomecPagoId);
        List<SoporteservMecpago> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<GenericRs> eliminar(final @PathVariable("id") Long id, HttpServletRequest req) {
        Optional<SoporteservMecpago> optional = soporteservMecpagoRepository.findById(id);
        if (optional.isPresent()) {
            soporteservMecpagoRepository.delete(optional.get());
            return ResponseEntity.ok(new GenericRs("200",
                    String.format("La tipología %s asociada al servicio se eliminó satisfactoriamente",
                            optional.get().getTipologiaDocumento().getDetalleTipologia())));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a eliminar", null, 400));
        }
    }
}
