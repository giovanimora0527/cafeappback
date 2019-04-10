/*
* Archivo: ServiciosMecPagoApiController.java
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
import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.ServicioMecpago;
import co.com.grupoasd.fae.model.entity.SoporteservMecpago;
import co.com.grupoasd.fae.model.repository.ServicioMecpagoRepository;
import co.com.grupoasd.fae.model.repository.SoporteservMecpagoRepository;
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
 * Enpoint REST para la gestión del Servicios por Mecanismo de pago
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class ServiciosMecPagoApiController implements ServiciosMecPagoApi {

    /**
     * ServicioMecpagoRepository.
     */
    private final ServicioMecpagoRepository servicioMecpagoRepository;
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
    private ServiciosMecPagoApiController(final ServicioMecpagoRepository servicioMecpagoRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final SoporteservMecpagoRepository soporteservMecpagoRepository) {
        this.servicioMecpagoRepository = servicioMecpagoRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.soporteservMecpagoRepository = soporteservMecpagoRepository;
    }

    /**
     * Listar Servicios por Mecanismo de pago
     *
     * @return Listado de Servicios por Mecanismo de pago
     */
    @Override
    public ResponseEntity<List<ServicioMecpago>> listar() {
        Iterable<ServicioMecpago> iter = servicioMecpagoRepository.findAll();
        List<ServicioMecpago> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de Servicios por Mecanismo de pago
     *
     * @param id Identificador de Servicios por Mecanismo de pago
     * @return Registro de Servicios por Mecanismo de pago
     */
    @Override
    public ResponseEntity<ServicioMecpago> obtener(final @PathVariable("id") Long id) {
        Optional<ServicioMecpago> entiOptional = servicioMecpagoRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    /**
     * Crea un registro de Servicios por Mecanismo de pago
     *
     * @param servicioMecpago Código de Servicios por Mecanismo de pago
     * @param req
     * @return Objeto Servicios por Mecanismo de pago
     */
    @Override
    public ResponseEntity<ServicioMecpago> crear(@RequestBody @Validated ServicioMecpago servicioMecpago, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        servicioMecpago.setCreadoPor(user.getId());
        servicioMecpago.setFechaCreacion(Date.from(Instant.now()));

        return ResponseEntity.ok(servicioMecpagoRepository.save(servicioMecpago));
    }

    /**
     * cambia un registro de Servicios por Mecanismo de pago
     *
     * @param servicioMecpago Código de Servicios por Mecanismo de pago
     * @param req
     * @return Objeto Servicios por Mecanismo de pago
     */
    @Override
    public ResponseEntity<ServicioMecpago> cambiar(@RequestBody @Validated ServicioMecpago servicioMecpago, HttpServletRequest req) {
        if (servicioMecpago.getId() != null) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            servicioMecpago.setModificadoPor(user.getId());
            servicioMecpago.setFechaModificacion(Date.from(Instant.now()));
            return ResponseEntity.ok(servicioMecpagoRepository.save(servicioMecpago));
        } else {
            throw new NotFoundException(ErrorRs.get("No existe el Servicio por mecanismo de pago a modificar", null, 404));
        }
    }

    /**
     * Listar Servicios por Mecanismo de pago por id mecanismo de pago
     *
     * @return Listado de Servicios por Mecanismo de pago por id mecanismo de
     * pago
     */
    @Override
    public ResponseEntity<List<ServicioMecpago>> listarXMecanismo(final @PathVariable("mecanismoPagoId") Long mecanismoPagoId) {
        Iterable<ServicioMecpago> iter = servicioMecpagoRepository.findByMecanismoPagoId(mecanismoPagoId);
        List<ServicioMecpago> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<GenericRs> eliminar(final @PathVariable("id") Long id, HttpServletRequest req) {
        Optional<ServicioMecpago> optional = servicioMecpagoRepository.findById(id);
        if (optional.isPresent()) {
            List<SoporteservMecpago> list = soporteservMecpagoRepository.findByServiciomecPagoId(id);
            if (list != null && !list.isEmpty()) {
                list.stream().filter((soporteservMecpago) -> (soporteservMecpago.getEstado() == VariablesEstaticas.ESTADO_ACTIVO)).forEachOrdered((_item) -> {
                    throw new BadRequestException(ErrorRs.get("No se puede eliminar el servicio seleccionado",
                            "No es posible eliminar el elemento seleccionado ya que cuenta con información asociada.", 400));
                });
                list.forEach((soporteservMecpago) -> {
                    soporteservMecpagoRepository.delete(soporteservMecpago);
                });
            }
            servicioMecpagoRepository.delete(optional.get());
            return ResponseEntity.ok(new GenericRs("200",
                    String.format("Servicio %s se eliminó satisfactoriamente", optional.get().getDetalleServiciomec())));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a eliminar", null, 400));
        }
    }
}
