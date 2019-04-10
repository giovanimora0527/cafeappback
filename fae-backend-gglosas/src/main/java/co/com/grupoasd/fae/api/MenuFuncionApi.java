/*
* Archivo: MenuFuncionApi.java
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

import co.com.grupoasd.fae.model.entity.MenuFuncion;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de MenuFuncion
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/menu-accion")
@Api(value = "menu-accion", description = "Operaciones recurso MenuFuncion")
public interface MenuFuncionApi {

    /**
     * Listar MenuFuncion
     *
     * @return Listado de MenuFuncion
     */
    @ApiOperation(value = "Listar Registros de MenuFuncion")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de MenuFuncion. "
                + "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<MenuFuncion>> listar();

    /**
     * Obtiene un registro de una MenuFuncion
     *
     * @param menuCodigo Identificador de la MenuFuncion
     * @param menuFuncionCodigo Identificador de la MenuFuncion
     * @return Registro de MenuFuncion
     */
    @ApiOperation(value = "Obtiene un solo registro de MenuFuncion")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de MenuFuncion.")
        ,
        @ApiResponse(code = 400, message = "No existe una entidad perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{menuCodigo}/{menuFuncionCodigo}", method = RequestMethod.GET)
    ResponseEntity<MenuFuncion> obtener(@PathVariable("menuCodigo") String menuCodigo, 
            @PathVariable("menuFuncionCodigo") String menuFuncionCodigo);

    /**
     * Crea un registro de MenuFuncion
     *
     * @param menuFuncion Código de la MenuFuncion
     * @param req
     * @return Objeto MenuFuncion
     */
    @ApiOperation(value = "Crea MenuFuncion", response = MenuFuncion.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "MenuFuncion creada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<MenuFuncion> crear(@RequestBody MenuFuncion menuFuncion, HttpServletRequest req);

    /**
     * cambia un registro de una MenuFuncion
     *
     * @param menuFuncion Código de la MenuFuncion
     * @param req
     * @return Objeto MenuFuncion
     */
    @ApiOperation(value = "Cambia MenuFuncion", response = MenuFuncion.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "MenuFuncion cambiada satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<MenuFuncion> cambiar(@RequestBody MenuFuncion menuFuncion, HttpServletRequest req);
    
    /**
     * Obtiene una lista de una FuncionMenu
     *
     * @param menuCodigo Identificador del menu
     * @return lista de FuncionMenu
     */
    @ApiOperation(value = "Obtiene un solo registro de MenuFuncion")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de MenuFuncion.")
        ,
        @ApiResponse(code = 400, message = "No existe una entidad perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/menu/{menuCodigo}", method = RequestMethod.GET)
    ResponseEntity<List<MenuFuncion>> obtenerFuncionMenu(@PathVariable("menuCodigo") String menuCodigo);
}
