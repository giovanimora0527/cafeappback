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

import co.com.grupoasd.fae.model.entity.Perfil;
import co.com.grupoasd.fae.model.entity.PerfilPermiso;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestión de Perfiles
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/perfil-permiso")
@Api(value = "perfil-permiso", description = "Operaciones recurso Gestión de Permiso en Perfiles")
public interface PerfilPermisoApi {
    /**
     * Listar PerfilPermiso
     * @return Listado de PerfilPermiso
     */
    @ApiOperation(value = "Listar Registros de PerfilPermiso")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de PerfilPermiso. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<PerfilPermiso>> listar();
    
    /**
     * Obtiene un registro de Perfil
     * @param menuCodigo Identificador del perfil
     * @param perfilId Identificador del perfil
     * @param menuFuncionCodigo Identificador del perfil
     * @return Registro de Perfil
     */
    @ApiOperation(value = "Obtiene un solo registro de PerfilPermiso")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de PerfilPermiso."),
        @ApiResponse(code = 400, message = "No existe un PerfilPermiso perteneciente a éste identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<PerfilPermiso> obtener(@PathVariable("menuCodigo") String menuCodigo, 
            @PathVariable("perfilId") Long perfilId, 
            @PathVariable("menuFuncionCodigo") String menuFuncionCodigo);
    
    /**
     * Crea un registro de PerfilPermiso
     * @param perfilPermiso PerfilPermiso
     * @param req
     * @return Objeto PerfilPermiso
     */
    @ApiOperation(value = "Crea PerfilPermiso", response = PerfilPermiso.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "PerfilPermiso creado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<PerfilPermiso> crear(@RequestBody PerfilPermiso perfilPermiso, HttpServletRequest req);
    
    /**
     * cambia un registro de PerfilPermiso
     * @param perfilPermiso
     * @param req
     * @return Objeto PerfilPermiso
     */
    @ApiOperation(value = "Cambia PerfilPermiso", response = Perfil.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "PerfilPermiso cambiado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<PerfilPermiso> cambiar(@RequestBody PerfilPermiso perfilPermiso, HttpServletRequest req);
    
}
