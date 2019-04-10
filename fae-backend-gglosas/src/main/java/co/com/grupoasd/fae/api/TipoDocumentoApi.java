/*
* Archivo: TipoDocumentoApi.java
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

import co.com.grupoasd.fae.model.entity.TipoDocumento;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Enpoint REST para gestion de TipoDocumento.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/tipo-documento")
@Api(value = "tipo-documento",  description = "Operaciones recurso Tipo Documento")
public interface TipoDocumentoApi {
    
    /**
     * Listar tipos de documento.
     * @return Listado de departamentos.
     */
    @JsonView(View.TipoDocumento.class)
    @ApiOperation(value = "Listar tipos de documento")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los tipos de document. "
                + "Si no existen retorna una lista vacia")        
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<TipoDocumento>> listar();
    
    /**
     * Obtener un tipo de documento.
     * @param id Identificador del tipo de documento.
     * @return Tipo de documento.
     */
    @JsonView(View.TipoDocumento.class)
    @ApiOperation(value = "Obtener un tipo de documento", response = TipoDocumento.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Tipo de documento."),
        @ApiResponse(code = 400, message = "No existe tipo de documento con el identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<TipoDocumento> obtener(@PathVariable("id") Long id);
    
    /**
     * Crea un tipo de documento.
     * @param tipoDocumento Tipo de documento.
     * @param req
     * @return Tipo de documento.
     */
    @JsonView(View.TipoDocumento.class)
    @ApiOperation(value = "Crear tipo de documento", response = TipoDocumento.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Tipo de documento creado satisfactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<TipoDocumento> crear(@RequestBody TipoDocumento tipoDocumento, HttpServletRequest req);
    
    /**
     * cambia un registro de TipoDocumento
     * @param tipoDocumento
     * @param req
     * @return Objeto TipoDocumento
     */
    @JsonView(View.TipoDocumento.class)
    @ApiOperation(value = "Cambia TipoDocumento", response = TipoDocumento.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "TipoDocumento cambiado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<TipoDocumento> cambiar(@RequestBody TipoDocumento tipoDocumento, HttpServletRequest req);
    
    /**
     * Elimina un TipoDocumento
     * @param id identificador del TipoDocumento
     * @param req
     * @return objeto de respuesta generico
     */
    @ApiOperation(value = "Elimina el TipoDocumento")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Eliminación de TipoDocumento."),
        @ApiResponse(code = 400, message = "No existe un TipoDocumento perteneciente a éste identificador.")
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<GenericRs> eliminar(Long id, HttpServletRequest req);
}
