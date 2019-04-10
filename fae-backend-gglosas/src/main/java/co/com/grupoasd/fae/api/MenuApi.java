/*
* Archivo: MenuApi.java
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

import co.com.grupoasd.fae.model.entity.Menu;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de Menús
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/menu")
@Api(value = "menu", description = "Operaciones recurso Gestión de Menús")
public interface MenuApi {
    /**
     * Listar menús
     * @return Listado de menús
     */
    @JsonView(View.MenuAcciones.class)
    @ApiOperation(value = "Listar Registros de Menús")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Menús. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/")
    ResponseEntity<List<Menu>> listar();
    
    /**
     * Obtiene un registro de menú
     * @param id Identificador del menú
     * @return Registro de menú
     */
    @ApiOperation(value = "Obtiene un solo registro de Menú")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Menú."),
        @ApiResponse(code = 400, message = "No existe un Menú perteneciente a éste identificador.")
    })
    @GetMapping(value = "/{id}")
    ResponseEntity<Menu> obtener(@PathVariable("id") String id);
    
    /**
     * Crea un registro de Menú
     * @param menu Código de Menú
     * @param req
     * @return Objeto Menú
     */
    @ApiOperation(value = "Crea Menú", response = Menu.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Menú creado satistactoriamente.")
    })
    @PostMapping(value = "/")
    ResponseEntity<Menu> crear(@RequestBody Menu menu, HttpServletRequest req);
    
    /**
     * cambia un registro de Menú
     * @param menu Código de Menú
     * @param req
     * @return Objeto Menú
     */
    @ApiOperation(value = "Cambia Menú", response = Menu.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Menú cambiado satistactoriamente.")
    })
    @PutMapping(value = "/")
    ResponseEntity<Menu> cambiar(@RequestBody Menu menu, HttpServletRequest req);
    
    /**
     * Elimina un registro de Menú
     * @param codigo codigo del menu
     * @param req
     * @return objeto de respuesta generico
     */
    @ApiOperation(value = "Eliminar Menú", response = Menu.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Menú eliminado satistactoriamente.")
    })
    @DeleteMapping(value = "/{codigo}")
    ResponseEntity<GenericRs> eliminar(@PathVariable("codigo") String codigo, HttpServletRequest req);
}
