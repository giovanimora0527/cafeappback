/*
* Archivo: ServicioMecpago.java
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

import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.ServicioMecpago;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para la gestión del Servicios por Mecanismo de pago
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/servicio-mec-pago")
@Api(value = "servicio-mec-pago", description = "Operaciones recurso Servicios por Mecanismo de pago")
public interface ServiciosMecPagoApi {

    /**
     * Listar Servicios por Mecanismo de pago
     *
     * @return Listado de ServicioMecpago
     */
    @JsonView(View.ServicioMecpago.class)
    @ApiOperation(value = "Listar Registros de Servicios por Mecanismo de pago")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Servicios por Mecanismo de pago. "
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<ServicioMecpago>> listar();

    /**
     * Obtiene un registro de Servicios por Mecanismo de pago
     *
     * @param id Identificador de Servicios por Mecanismo de pago
     * @return Registro de Servicios por Mecanismo de pago
     */
    @JsonView(View.ServicioMecpago.class)
    @ApiOperation(value = "Obtiene un solo registro de Servicios por Mecanismo de pago")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Servicios por Mecanismo de pago.")
        ,
        @ApiResponse(code = 400, message = "No existe un Servicios por Mecanismo de pago perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<ServicioMecpago> obtener(@PathVariable("id") Long id);

    /**
     * Crea un registro de Servicios por Mecanismo de pago
     *
     * @param servicioMecpago Código del Servicios por Mecanismo de pago
     * @param req
     * @return Objeto Servicios por Mecanismo de pago
     */
    @JsonView(View.ServicioMecpago.class)
    @ApiOperation(value = "Crea un Servicios por Mecanismo de pago", response = ServicioMecpago.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Servicios por Mecanismo de pago creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<ServicioMecpago> crear(@RequestBody ServicioMecpago servicioMecpago, HttpServletRequest req);

    /**
     * cambia un registro de una ServicioMecpago
     *
     * @param servicioMecpago Código del Servicios por Mecanismo de pago
     * @param req
     * @return Objeto ServicioMecpago
     */
    @JsonView(View.ServicioMecpago.class)
    @ApiOperation(value = "Cambia Servicios por Mecanismo de pago", response = ServicioMecpago.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Servicios por Mecanismo de pago cambiada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<ServicioMecpago> cambiar(@RequestBody ServicioMecpago servicioMecpago, HttpServletRequest req);
    
    /**
     * Listar Servicios por id de Mecanismo de pago
     *
     * @param mecanismoPagoId
     * @return Listado de ServicioMecpago
     */
    @JsonView(View.ServicioMecpago.class)
    @ApiOperation(value = "Listar Registros de Servicios por id de Mecanismo de pago")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Servicios por id de Mecanismo de pago. "
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/mecanismo-pago/{mecanismoPagoId}", method = RequestMethod.GET)
    ResponseEntity<List<ServicioMecpago>> listarXMecanismo(@PathVariable("mecanismoPagoId") Long mecanismoPagoId);
    
    /**
     * Elimina un registro de Servicios por Mecanismo de pago
     * @param id id de Servicios por Mecanismo de pago
     * @param req
     * @return objeto de respuesta generico
     */
    @ApiOperation(value = "Eliminar Servicios por Mecanismo de pago", response = ServicioMecpago.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Servicios por Mecanismo de pago eliminado satistactoriamente.")
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<GenericRs> eliminar(@PathVariable("id") Long id, HttpServletRequest req);
}
