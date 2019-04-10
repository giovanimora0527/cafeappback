/*
* Archivo: CodigoGlosaEspApi.java
* Fecha: 12/12/2018
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
import co.com.grupoasd.fae.model.entity.CodigoGlosaEspecifica;
import co.com.grupoasd.fae.util.View;
import co.com.grupoasd.fae.util.View.CodigoGlosaEsp;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de Codigos de Glosa Esp
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/codigo-glosa-esp")
@Api(value = "codigo-glosa-esp", description = "Operaciones recurso Gestión de Códigos de Glosas Esp")
public interface CodigoGlosaEspApi {
    /**
     * Listar Códigos de Glosa Esp
     * @return Listado de Códigos de Glosa Esp
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @ApiOperation(value = "Listar Códigos de Glosa Esp")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los códigos de glosa Esp. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<CodigoGlosaEspecifica>> listar();
    
    /**
     * Obtiene un registro de código de glosa Esp
     * @param id Identificador del registro
     * @return Registro de Codigo de Glosa Esp
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @ApiOperation(value = "Obtiene un solo registro de código de documento")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Código de Glosa Esp."),
        @ApiResponse(code = 400, message = "No existe un código de glosa Esp perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<CodigoGlosaEspecifica> obtener(@PathVariable("id") Long id);
    
    /**
     * Crea un registro de Código de Glosa Esp
     * @param codigoGlosaEspecifica Código de Glosa Esp
     * @param req
     * @return Código de Glosa Esp
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @ApiOperation(value = "Crea Código de Glosa Esp", response = CodigoGlosaEsp.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Código de Glosa Esp creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<CodigoGlosaEspecifica> crear(@RequestBody CodigoGlosaEspecifica codigoGlosaEspecifica, HttpServletRequest req);
    
    /**
     * Modifica un registro de Código de Glosa
     * @param codigoGlosaEspecifica Código de Glosa
     * @param req
     * @return Código de Glosa
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @ApiOperation(value = "Modifica Código de Glosa Esp", response = CodigoGlosaEsp.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Código de Glosa Esp Modificado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<CodigoGlosaEspecifica> cambiar(@RequestBody CodigoGlosaEspecifica codigoGlosaEspecifica, HttpServletRequest req);
    
    /**
     * Listar Códigos de Glosa Esp por codigo fe glosa
     * @param codigoGlosaId
     * @return Listado de Códigos de Glosa Esp por codigo de glosa
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @ApiOperation(value = "Listar Códigos de Glosa Esp")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los códigos de glosa Esp. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/codigo-glosa/{codigoGlosaId}", method = RequestMethod.GET)
    ResponseEntity<List<CodigoGlosaEspecifica>> listarXCodigoGlosa(@PathVariable("codigoGlosaId") Long codigoGlosaId);
    
    /**
     * Elimina un registro de Códigos de Glosa Esp
     * @param codigoGlosaEspId id de Códigos de Glosa Esp
     * @param req
     * @return objeto de respuesta generico
     */
    @ApiOperation(value = "Eliminar Códigos de Glosa Esp", response = CodigoGlosaEspecifica.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Códigos de Glosa Esp eliminado satistactoriamente.")
    })
    @DeleteMapping(value = "/{codigoGlosaEspId}")
    ResponseEntity<GenericRs> eliminar(@PathVariable("codigoGlosaEspId") Long codigoGlosaEspId, HttpServletRequest req);
}
