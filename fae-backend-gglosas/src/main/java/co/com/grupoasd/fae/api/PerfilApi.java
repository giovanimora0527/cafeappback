/*
* Archivo: PerfilApi.java
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
import co.com.grupoasd.fae.model.entity.Perfil;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de Perfiles
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/perfil")
@Api(value = "perfil", description = "Operaciones recurso Gestión de Perfiles")
public interface PerfilApi {
    /**
     * Listar Perfiles
     * @return Listado de menús
     */
    @JsonView(View.UsuarioPerfil.class)
    @ApiOperation(value = "Listar Registros de Perfil")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Perfiles. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<Perfil>> listar();
    
    /**
     * Obtiene un registro de Perfil
     * @param id Identificador del perfil
     * @return Registro de Perfil
     */
    @JsonView(View.UsuarioPerfil.class)
    @ApiOperation(value = "Obtiene un solo registro de Perfil")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Perfil."),
        @ApiResponse(code = 400, message = "No existe un Perfil perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Perfil> obtener(@PathVariable("id") Long id);
    
    /**
     * Crea un registro de Perfil
     * @param perfil Perfil
     * @param req
     * @return Objeto Perfil
     */
    @ApiOperation(value = "Crea Perfil", response = Perfil.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Perfil creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<Perfil> crear(@RequestBody Perfil perfil, HttpServletRequest req);
    
    /**
     * cambia un registro de Perfil
     * @param perfil Perfil
     * @param req
     * @return Objeto Perfil
     */
    @ApiOperation(value = "Cambia Perfil", response = Perfil.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Perfil cambiado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<Perfil> cambiar(@RequestBody Perfil perfil, HttpServletRequest req);
    
    
    /**
     * Obtiene el menú asociado a un perfil
     * @param id identificador del perfil
     * @return lista de menus asociados al perfil
     */
    @JsonView(View.MenuAcciones.class)
    @ApiOperation(value = "Obtiene el menú asociado a un Perfil")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Perfil."),
        @ApiResponse(code = 400, message = "No existe un Perfil perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
    ResponseEntity<List<Menu>> obtenerMenuPerfil(Long id);
    
    /**
     * Elimina un perfil
     * @param id identificador del perfil
     * @param req
     * @return objeto de respuesta generico
     */
    @ApiOperation(value = "Elimina el Perfil")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Eliminación de Perfil."),
        @ApiResponse(code = 400, message = "No existe un Perfil perteneciente a éste identificador.")
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<GenericRs> eliminar(Long id, HttpServletRequest req);
}
