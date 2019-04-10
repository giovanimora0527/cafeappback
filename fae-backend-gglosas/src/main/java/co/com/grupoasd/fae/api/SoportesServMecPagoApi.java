/*
* Archivo: SoportesServMecPagoApi.java
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
import co.com.grupoasd.fae.model.entity.SoporteservMecpago;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para la gestión del Soportes por servicios de Mecanismo de pago
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/soportes-serv-mec-pago")
@Api(value = "soportes-serv-mec-pago", description = "Operaciones recurso Soportes por servicios de Mecanismo de pago")
public interface SoportesServMecPagoApi {

    /**
     * Listar Soportes por servicios de Mecanismo de pago
     *
     * @return Listado de Soportes por servicios de Mecanismo de pago
     */
    @JsonView(View.SoporteservMecpago.class)
    @ApiOperation(value = "Listar Registros de Soportes por servicios de Mecanismo de pago")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Soportes por servicios de Mecanismo de pago. "
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<SoporteservMecpago>> listar();

    /**
     * Obtiene un registro de Soportes por servicios de Mecanismo de pago
     *
     * @param id Identificador de Soportes por servicios de Mecanismo de pago
     * @return Registro de Soportes por servicios de Mecanismo de pago
     */
    @JsonView(View.SoporteservMecpago.class)
    @ApiOperation(value = "Obtiene un solo registro de Soportes por servicios de Mecanismo de pago")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Soportes por servicios de Mecanismo de pago.")
        ,
        @ApiResponse(code = 400, message = "No existe un Soportes por servicios de Mecanismo de pago perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<SoporteservMecpago> obtener(@PathVariable("id") Long id);

    /**
     * Crea un registro de Soportes por servicios de Mecanismo de pago
     *
     * @param soporteservMecpago Código del Soportes por servicios de Mecanismo de pago
     * @param req
     * @return Objeto Soportes por servicios de Mecanismo de pago
     */
    @JsonView(View.SoporteservMecpago.class)
    @ApiOperation(value = "Crea un Soportes por servicios de Mecanismo de pago", response = SoporteservMecpago.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Soportes por servicios de Mecanismo de pago creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<SoporteservMecpago> crear(@RequestBody SoporteservMecpago soporteservMecpago, HttpServletRequest req);

    /**
     * cambia un registro de una Soportes por servicios de Mecanismo de pago
     *
     * @param soporteservMecpago Código del Soportes por servicios de Mecanismo de pago
     * @param req
     * @return Objeto Soportes por servicios de Mecanismo de pago
     */
    @JsonView(View.SoporteservMecpago.class)
    @ApiOperation(value = "Cambia Soportes por servicios de Mecanismo de pago", response = SoporteservMecpago.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Soportes por servicios de Mecanismo de pago cambiada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<SoporteservMecpago> cambiar(@RequestBody SoporteservMecpago soporteservMecpago, HttpServletRequest req);
    
    /**
     * Listar Soportes por servicios de Mecanismo de pago por servicio
     *
     * @param serviciomecPagoId
     * @return Listado de Soportes por servicios de Mecanismo de pago
     */
    @JsonView(View.SoporteservMecpago.class)
    @ApiOperation(value = "Listar Registros de Soportes por servicios de Mecanismo de pago por servicio")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Soportes por servicios de Mecanismo de pago por servicio."
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/servicio/{serviciomecPagoId}", method = RequestMethod.GET)
    ResponseEntity<List<SoporteservMecpago>> listarXServicio(@PathVariable("serviciomecPagoId") Long serviciomecPagoId);
    
    /**
     * Elimina un registro de Soportes por Servicios por Mecanismo de pago
     * @param id id de Soportes por Servicios por Mecanismo de pago
     * @param req
     * @return objeto de respuesta generico
     */
    @ApiOperation(value = "Eliminar Soportes por Servicios por Mecanismo de pago", response = SoporteservMecpago.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Soportes por Servicios por Mecanismo de pago eliminado satistactoriamente.")
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<GenericRs> eliminar(@PathVariable("id") Long id, HttpServletRequest req);
}
