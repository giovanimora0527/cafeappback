/*
* Archivo: CumApi.java
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

import co.com.grupoasd.fae.model.dto.CumDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cum;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Enpoint REST para la gestión de CUM
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/cum")
@Api(value = "cum", description = "Operaciones recurso CUM")
public interface CumApi {
    
    /**
     * Listar CUM
     *
     * @return Listado de CUM
     */
    @JsonView(View.Cum.class)
    @ApiOperation(value = "Listar Registros de CUM")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de CUM. "
                + "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/")
    ResponseEntity<List<Cum>> listar();
    
    
    
    /**
     * Obtiene un registro de CUM
     *
     * @param id Identificador de CUM
     * @return Registro de CUM
     */
    @JsonView(View.Cie.class)
    @ApiOperation(value = "Obtiene un solo registro de CUM")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de CUM.")
        ,
        @ApiResponse(code = 400, message = "No existe un CUM perteneciente a éste identificador.")
    })
    @GetMapping(value = "/{id}")
    ResponseEntity<Cum> obtener(@PathVariable("id") Long id);
    
    /**
     * Listar Archivos de CUM no cargados
     *
     * @return Listado de archivos CUM no cargados
     */
    @ApiOperation(value = "Listar Archivos de CUM no cargados")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con archivos de CUM no cargados. "
                + "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/archivos")
    ResponseEntity<List<String>> listarArchivosNoCargados();
    
    
    /**
     * Recibe un archivo de cargue de CUM
     *
     * @param file
     * @param req
     * @return Respuesta con el nombre del archivo cargado
     */
    @ApiOperation(value = "Recibe un archivo de cargue de CUM y las registra")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de CUM masivo.")
        ,
        @ApiResponse(code = 400, message = "No existe un CUM perteneciente a éste identificador.")
    })
    @PostMapping(value = "/upload-file")
    GenericRs subirArchivo(@RequestParam("file") MultipartFile file, HttpServletRequest req);
    
    
    /**
     * Descarga un archivo de errores de cargue de CUM
     *
     * @param filename nombre del archivo de errores
     * @return Archivo de errores 
     */
    @ApiOperation(value = "Recibe un nombre de archivo a descargar")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Descarga de archivo de errores de cargue CUM .")
        ,
        @ApiResponse(code = 400, message = "No existe un archivo de errores CUM perteneciente a éste identificador.")
    })
    @GetMapping(value = "/descargar/{nombreArchivo}")
    ResponseEntity<Resource> descargar(@PathVariable String filename);
    
    
    /**
     * Lista un archivo de errores de cargue de CUM
     *
     * @param fileName nombre del archivo de errores
     * @return Lista Archivo de errores 
     */
    @ApiOperation(value = "Recibe un nombre de archivo a listar")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de archivo de errores de cargue CUM .")
        ,
        @ApiResponse(code = 400, message = "No existe un CUM perteneciente a éste identificador.")
    })
    @GetMapping(value = "/listar/{nombreArchivo}")
    ResponseEntity<List<CumDtoError>> listarDataNoCargados(@PathVariable String fileName);
}
