/*
* Archivo: CieApi.java
* Fecha: 20/02/2019
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

import co.com.grupoasd.fae.model.dto.CieDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cie;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Enpoint REST para la gestión de CIE
 *
 * @author GMORA lmora@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/cie")
@Api(value = "cie", description = "Operaciones recurso CIE")
public interface CieApi {
    
    /**
     * Listar CIE
     *
     * @return Listado de CIE
     */
    @JsonView(View.Cie.class)
    @ApiOperation(value = "Listar Registros de CIE")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de CIE. "
                + "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/")
    ResponseEntity<List<Cie>> listar();
    
    
    /**
     * Listar CIE por norma
     *
     * @param norma norma asociada al CIE
     * @return Listado de CIE
     */
    @JsonView(View.Cie.class)
    @ApiOperation(value = "Listar Registros de CIE por norma")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de CIE. "
                + "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/norma/{norma}")
    ResponseEntity<List<Cie>> listarXNorma(@PathVariable("norma") String norma);
    
    
    
    /**
     * Obtiene un registro de CIE
     *
     * @param id Identificador de CIE
     * @return Registro de CUPS
     */
    @JsonView(View.Cie.class)
    @ApiOperation(value = "Obtiene un solo registro de CIE")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de CIE.")
        ,
        @ApiResponse(code = 400, message = "No existe un CIE perteneciente a éste identificador.")
    })
    @GetMapping(value = "/{id}")
    ResponseEntity<Cie> obtener(@PathVariable("id") Long id);
    
    /**
     * Listar Archivos de CIE no cargados
     *
     * @return Listado de archivos CIE no cargados
     */
    @ApiOperation(value = "Listar Archivos de CIE no cargados")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con archivos de CIE no cargados. "
                + "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/archivos")
    ResponseEntity<List<String>> listarArchivosNoCargados();
    
    
    /**
     * Recibe un archivo de cargue de CIE
     *
     * @param file
     * @param norma
     * @param req
     * @return Respuesta con el nombre del archivo cargado
     */
    @ApiOperation(value = "Recibe un archivo de cargue de CIE y las registra")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de CIE masivo.")
        ,
        @ApiResponse(code = 400, message = "No existe un CIE perteneciente a éste identificador.")
    })
    @PostMapping(value = "/upload-file")
    GenericRs subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("norma") String norma, HttpServletRequest req);
    
    
    /**
     * Descarga un archivo de errores de cargue de CIE
     *
     * @param filename
     * @return Archivo de errores 
     */
    @ApiOperation(value = "Recibe un nombre de archivo a descargar")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Descarga de archivo de errores de cargue CIE .")
        ,
        @ApiResponse(code = 400, message = "No existe un CIE perteneciente a éste identificador.")
    })
    @GetMapping(value = "/descargar/{nombreArchivo}")
    ResponseEntity<Resource> descargar(@PathVariable String filename);
    
    
    /**
     * Lista un archivo de errores de cargue de CIE
     *
     * @param fileName
     * @return Lista Archivo de errores 
     */
    @ApiOperation(value = "Recibe un nombre de archivo a listar")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de archivo de errores de cargue CIE .")
        ,
        @ApiResponse(code = 400, message = "No existe un CIE perteneciente a éste identificador.")
    })
    @GetMapping(value = "/listar/{nombreArchivo}")
    ResponseEntity<List<CieDtoError>> listarDataNoCargados(@PathVariable String fileName);
}
