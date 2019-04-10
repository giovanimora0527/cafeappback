/*
* Archivo: UsuarioApi.java
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

import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de Usuarios
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/usuario")
@Api(value = "usuario", description = "Operaciones recurso Gestión de Usuarios")
public interface UsuarioApi {
    /**
     * Listar Usuarios
     * @return Listado de usuarios
     */
    @JsonView(View.UsuarioPerfil.class)
    @ApiOperation(value = "Listar Registros de Usuarios")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de Usuarios. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listar();
    
    /**
     * Obtiene un registro de Usuario
     * @param id Identificador del usuario
     * @return Registro de Usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @ApiOperation(value = "Obtiene un solo registro de Usuario")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Usuario."),
        @ApiResponse(code = 400, message = "No existe un Usuario perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Usuario> obtener(@PathVariable("id") Long id);
    
    /**
     * Crea un registro de Usuario
     * @param usuario Usuario
     * @param req
     * @return Objeto Usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @ApiOperation(value = "Crea Usuario", response = Usuario.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Usuario creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<Usuario> crear(@RequestBody Usuario usuario, HttpServletRequest req);
    
    /**
     * cambia un registro de Usuario
     * @param usuario Usuario
     * @param req
     * @return Objeto Usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @ApiOperation(value = "Cambia Usuario", response = Usuario.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Usuario cambiado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<Usuario> cambiar(@RequestBody Usuario usuario, HttpServletRequest req);
    
    /**
     * Obtiene un registro de Usuario
     * @param email Email del usuario
     * @return Registro de Usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @ApiOperation(value = "Obtiene un solo registro de Usuario por email")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de Usuario."),
        @ApiResponse(code = 400, message = "No existe un Usuario perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    ResponseEntity<Usuario> obtenerXEmail(@PathVariable("email") String email);
}
