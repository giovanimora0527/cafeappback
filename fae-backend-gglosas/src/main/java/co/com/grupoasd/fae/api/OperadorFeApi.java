/*
* Archivo: OperadorFeApi.java
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
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para la gestión de operador de Facturacion electronica
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/operador")
@Api(value = "operador", description = "Operaciones recurso operador")
public interface OperadorFeApi {

    /**
     * Listar operador
     *
     * @return Listado de OperadorFe
     */
    @JsonView(View.OperadorFe.class)
    @ApiOperation(value = "Listar Registros de operador")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de operador. "
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<OperadorFe>> listar();

    /**
     * Obtiene un registro de un operador
     *
     * @param id Identificador del operador
     * @return Registro de operador
     */
    @JsonView(View.OperadorFe.class)
    @ApiOperation(value = "Obtiene un solo registro de operador")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de operador.")
        ,
        @ApiResponse(code = 400, message = "No existe una entidad perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<OperadorFe> obtener(@PathVariable("id") Long id);

    /**
     * Crea un registro de operador
     *
     * @param operadorFe Código de la operador
     * @param req
     * @return Objeto operador
     */
    @JsonView(View.OperadorFe.class)
    @ApiOperation(value = "Crea operador", response = OperadorFe.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "operador creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<OperadorFe> crear(@RequestBody OperadorFe operadorFe, HttpServletRequest req);

    /**
     * cambia un registro de una operador
     *
     * @param operadorFe Código de la operador
     * @param req
     * @return Objeto operador
     */
    @JsonView(View.OperadorFe.class)
    @ApiOperation(value = "Cambia operador", response = OperadorFe.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "operador cambiada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<OperadorFe> cambiar(@RequestBody OperadorFe operadorFe, HttpServletRequest req);
}
