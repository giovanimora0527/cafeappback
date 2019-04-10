/*
* Archivo: EntidadApi.java
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

import co.com.grupoasd.fae.model.entity.Entidad;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de Entidades
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/entidad")
@Api(value = "entidad", description = "Operaciones recurso Entidad")
public interface EntidadApi {

    /**
     * Listar entidades
     *
     * @return Listado de entidades
     */
    @JsonView(View.Entidad.class)
    @ApiOperation(value = "Listar Registros de Entidad")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Entidad. "
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<Entidad>> listar();

    /**
     * Obtiene un registro de una entidad
     *
     * @param id Identificador de la entidad
     * @return Registro de entidades
     */
    @JsonView(View.Entidad.class)
    @ApiOperation(value = "Obtiene un solo registro de Entidad")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Entidad.")
        ,
        @ApiResponse(code = 400, message = "No existe una entidad perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Entidad> obtener(@PathVariable("id") String id);

    /**
     * Crea un registro de Entidad
     *
     * @param entidad Código de la entidad
     * @return Objeto Entidad
     */
    @ApiOperation(value = "Crea Entidad", response = Entidad.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Entidad creada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<Entidad> crear(@RequestBody Entidad entidad, HttpServletRequest req);

    /**
     * cambia un registro de una entidad
     *
     * @param entidad Código de la entidad
     * @param req
     * @return Objeto Entidad
     */
    @ApiOperation(value = "Cambia Entidad", response = Entidad.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Entidad cambiada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<Entidad> cambiar(@RequestBody Entidad entidad, HttpServletRequest req);
    
    /**
     * Obtiene un registro de una entidad
     *
     * @param nombre Nombre de la entidad
     * @return Registro de entidades
     */
    @JsonView(View.Entidad.class)
    @ApiOperation(value = "Obtiene un solo registro de Entidad")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Entidad.")
        ,
        @ApiResponse(code = 400, message = "No existe una entidad perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/nombre/{nombre}", method = RequestMethod.GET)
    ResponseEntity<Entidad> obtenerXNombre(@PathVariable("nombre") String nombre);
}
