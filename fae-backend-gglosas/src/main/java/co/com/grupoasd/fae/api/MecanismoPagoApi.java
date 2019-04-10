/*
* Archivo: MecanismoPagoApi.java
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

import co.com.grupoasd.fae.model.entity.MecanismoPago;
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
@RequestMapping("/mecanismo-pago")
@Api(value = "mecanismo-pago", description = "Operaciones recurso Mecanismo de pago")
public interface MecanismoPagoApi {

    /**
     * Listar Mecanismo de pago
     *
     * @return Listado de MecanismoPago
     */
    @JsonView(View.MecanismoPago.class)
    @ApiOperation(value = "Listar Registros de Mecanismo de pago")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Mecanismo de pago."
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<MecanismoPago>> listar();

    /**
     * Obtiene un registro de Mecanismo de pago
     *
     * @param id Identificador de Mecanismo de pago
     * @return Registro de Mecanismo de pago
     */
    @JsonView(View.MecanismoPago.class)
    @ApiOperation(value = "Obtiene un solo registro de Mecanismo de pago")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Mecanismo de pago.")
        ,
        @ApiResponse(code = 400, message = "No existe un Mecanismo de pago perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<MecanismoPago> obtener(@PathVariable("id") Long id);

    /**
     * Crea un registro de Mecanismo de pago
     *
     * @param mecanismoPago Mecanismo de pago
     * @param req
     * @return Objeto Mecanismo de pago
     */
    @JsonView(View.MecanismoPago.class)
    @ApiOperation(value = "Crea un Mecanismo de pago", response = ServicioMecpago.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Mecanismo de pago creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<MecanismoPago> crear(@RequestBody MecanismoPago mecanismoPago, HttpServletRequest req);

    /**
     * cambia un registro de un MecanismoPago
     *
     * @param mecanismoPago Mecanismo de pago
     * @param req
     * @return Objeto MecanismoPago
     */
    @JsonView(View.MecanismoPago.class)
    @ApiOperation(value = "Cambia Mecanismo de pago", response = ServicioMecpago.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Mecanismo de pago cambiada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<MecanismoPago> cambiar(@RequestBody MecanismoPago mecanismoPago, HttpServletRequest req);
   
}
