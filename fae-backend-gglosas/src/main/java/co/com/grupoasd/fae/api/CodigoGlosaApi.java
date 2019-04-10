/*
* Archivo: CodigoGlosaApi.java
* Fecha: 24/09/2018
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

import co.com.grupoasd.fae.model.entity.CodigoGlosa;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de Codigos de Glosa
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/codigo-glosa")
@Api(value = "codigo-glosa", description = "Operaciones recurso Gestión de Códigos de Glosas")
public interface CodigoGlosaApi {
    /**
     * Listar Códigos de Glosa
     * @return Listado de Códigos de Glosa
     */
    @JsonView(View.CodigoGlosa.class)
    @ApiOperation(value = "Listar Códigos de Glosa")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los códigos de glosa. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<CodigoGlosa>> listar();
    
    /**
     * Obtiene un registro de código de  glosa
     * @param id Identificador del registro
     * @return Registro de Codigo de Glosa
     */
    @JsonView(View.CodigoGlosa.class)
    @ApiOperation(value = "Obtiene un solo registro de código de documento")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Código de Glosa."),
        @ApiResponse(code = 400, message = "No existe un código de glosa perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<CodigoGlosa> obtener(@PathVariable("id") Long id);
    
    /**
     * Crea un registro de Código de Glosa
     * @param codigoGlosa Código de Glosa
     * @param req
     * @return Código de Glosa
     */
    @JsonView(View.CodigoGlosa.class)
    @ApiOperation(value = "Crea Código de Glosa", response = CodigoGlosa.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Código de Glosa creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<CodigoGlosa> crear(@RequestBody CodigoGlosa codigoGlosa, HttpServletRequest req);
    
    /**
     * Modifica un registro de Código de Glosa
     * @param codigoGlosa Código de Glosa
     * @param req
     * @return Código de Glosa
     */
    @JsonView(View.CodigoGlosa.class)
    @ApiOperation(value = "Modifica Código de Glosa", response = CodigoGlosa.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Código de Glosa Modificado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<CodigoGlosa> cambiar(@RequestBody CodigoGlosa codigoGlosa, HttpServletRequest req);
}
