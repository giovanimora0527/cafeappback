/*
* Archivo: TipologiaDocumentoApi.java
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

import co.com.grupoasd.fae.model.entity.TipologiaDocumento;
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
 * Enpoint REST para gestion de TipologiaDocumento.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/tipologia-documento")
@Api(value = "tipologia-documento",  description = "Operaciones recurso Tipologia Documento")
public interface TipologiaDocumentoApi {
    
    /**
     * Listar tipos de Tipologia.
     * @return Listado de Tipologias.
     */
    @JsonView(View.TipologiaDocumento.class)
    @ApiOperation(value = "Listar Tipologia de documento")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con las Tipologias de documento. "
                + "Si no existen retorna una lista vacia")        
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<TipologiaDocumento>> listar();
    
    /**
     * Obtener un Tipologia de documento.
     * @param id Identificador del Tipologia de documento.
     * @return Tipologia de documento.
     */
    @JsonView(View.TipologiaDocumento.class)
    @ApiOperation(value = "Obtener una Tipologia de documento", response = TipologiaDocumento.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Tipologia de documento."),
        @ApiResponse(code = 400, message = "No existe Tipologia de documento con el identificador.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<TipologiaDocumento> obtener(@PathVariable("id") Long id);
    
    /**
     * Crea un Tipologia de documento.
     * @param tipologiaDocumento tipologia documento    
     * @param req
     * @return Tipologia de documento.
     */
    @JsonView(View.TipologiaDocumento.class)
    @ApiOperation(value = "Crear Tipologia de documento", response = TipologiaDocumento.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Tipologia de documento creado satisfactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<TipologiaDocumento> crear(@RequestBody TipologiaDocumento tipologiaDocumento, HttpServletRequest req);
    
    /**
     * cambia un registro de TipologiaDocumento
     * @param tipologiaDocumento
     * @param req
     * @return Objeto TipologiaDocumento
     */
    @JsonView(View.TipoDocumento.class)
    @ApiOperation(value = "Cambia TipologiaDocumento", response = TipologiaDocumento.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "TipologiaDocumento cambiado satistactoriamente.")
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<TipologiaDocumento> cambiar(@RequestBody TipologiaDocumento tipologiaDocumento, HttpServletRequest req);
    
    /**
     * Elimina una TipologiaDocumento
     * @param id identificador del TipologiaDocumento
     * @param req
     * @return objeto de respuesta generico
     */
    @ApiOperation(value = "Elimina el TipologiaDocumento")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Eliminación de TipologiaDocumento."),
        @ApiResponse(code = 400, message = "No existe una TipologiaDocumento perteneciente a éste identificador.")
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<GenericRs> eliminar(Long id, HttpServletRequest req);
}
