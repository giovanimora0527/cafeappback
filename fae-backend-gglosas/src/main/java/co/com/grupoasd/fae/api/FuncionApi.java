/*
* Archivo: FuncionApi.java
* Fecha: 30/10/2018
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

import co.com.grupoasd.fae.model.entity.Funcion;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de Funcion
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/accion")
@Api(value = "accion", description = "Operaciones recurso Funcion")
public interface FuncionApi {

    /**
     * Listar Funcion
     *
     * @return Listado de Funcion
     */
    @JsonView(View.MenuAcciones.class)
    @ApiOperation(value = "Listar Registros de Funcion")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Entidad. "
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<Funcion>> listar();

    /**
     * Obtiene un registro de una Funcion
     *
     * @param id Identificador de la Funcion
     * @return Registro de Funcion
     */
    @JsonView(View.MenuAcciones.class)
    @ApiOperation(value = "Obtiene un solo registro de Funcion")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Funcion.")
        ,
        @ApiResponse(code = 400, message = "No existe una entidad perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Funcion> obtener(@PathVariable("id") String id);

    /**
     * Crea un registro de Funcion
     *
     * @param funcion Código de la Funcion
     * @return Objeto Funcion
     */
    @JsonView(View.MenuAcciones.class)
    @ApiOperation(value = "Crea Funcion", response = Funcion.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Funcion creada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<Funcion> crear(@RequestBody Funcion funcion, HttpServletRequest req);

    /**
     * cambia un registro de una Funcion
     *
     * @param funcion Código de la Funcion
     * @return Objeto Funcion
     */
    @JsonView(View.MenuAcciones.class)
    @ApiOperation(value = "Cambia Funcion", response = Funcion.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Funcion cambiada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<Funcion> cambiar(@RequestBody Funcion funcion, HttpServletRequest req);
    
}
