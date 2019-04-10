/*
* Archivo: CupsApi.java
* Fecha: 19/12/2018
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

import co.com.grupoasd.fae.model.dto.CupsDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cups;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Enpoint REST para la gestión de CUPS
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/cups")
@Api(value = "cups", description = "Operaciones recurso CUPS")
public interface CupsApi {

    /**
     * Listar CUPS
     *
     * @return Listado de CUPS
     */
    @JsonView(View.Cups.class)
    @ApiOperation(value = "Listar Registros de CUPS")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de CUPS. "
                + "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/")
    ResponseEntity<List<Cups>> listar();
    
    /**
     * Listar CUPS por norma
     *
     * @param norma norma asociada al CUPS
     * @return Listado de CUPS
     */
    @JsonView(View.Cups.class)
    @ApiOperation(value = "Listar Registros de CUPS por norma")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con los registros de CUPS. "
                + "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/norma/{norma}")
    ResponseEntity<List<Cups>> listarXNorma(@PathVariable("norma") String norma);

    /**
     * Obtiene un registro de CUPS
     *
     * @param id Identificador de CUPS
     * @return Registro de CUPS
     */
    @JsonView(View.Cups.class)
    @ApiOperation(value = "Obtiene un solo registro de CUPS")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de CUPS.")
        ,
        @ApiResponse(code = 400, message = "No existe un CUPS perteneciente a éste identificador.")
    })
    @GetMapping(value = "/{id}")
    ResponseEntity<Cups> obtener(@PathVariable("id") Long id);

    /**
     * Crea un registro de CUPS
     *
     * @param cups objeto CUPS
     * @param req
     * @return Objeto CUPS
     */
    @JsonView(View.Cups.class)
    @ApiOperation(value = "Crea un CUPS", response = Cups.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "CUPS creado satistactoriamente.")
    })
    @PostMapping(value = "/")
    ResponseEntity<Cups> crear(@RequestBody Cups cups, HttpServletRequest req);

    /**
     * cambia un registro de un CUPS
     *
     * @param cups Objeto CUPS
     * @param req
     * @return Objeto Cups
     */
    @JsonView(View.Cups.class)
    @ApiOperation(value = "Cambia un CUPS", response = Cups.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "CUPS cambiado satistactoriamente.")
    })
    @PutMapping(value = "/")
    ResponseEntity<Cups> cambiar(@RequestBody Cups cups, HttpServletRequest req);

    /**
     * Listar Archivos de CUPS no cargados
     *
     * @return Listado de archivos Cups no cargados
     */
    @ApiOperation(value = "Listar Archivos de CUPS no cargados")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con archivos de CUPS no cargados. "
                + "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/archivos")
    ResponseEntity<List<String>> listarArchivosNoCargados();

    /**
     * Recibe un archivo de cargue de CUPS
     *
     * @param file
     * @param norma
     * @param req
     * @return Respuesta con el nombre del archivo cargado
     */
    @ApiOperation(value = "Recibe un archivo de cargue de CUPS y las registra")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Registro de CUPS masivo.")
        ,
        @ApiResponse(code = 400, message = "No existe un CUPS perteneciente a éste identificador.")
    })
    @PostMapping(value = "/upload-file")
    GenericRs subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("norma") String norma, HttpServletRequest req);

    /**
     * Descarga un archivo de errores de cargue de CUPS
     *
     * @param fileName
     * @return Archivo de errores 
     */
    @ApiOperation(value = "Recibe un nombre de archivo a descargar")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Descarga de archivo de errores de cargue CUPS .")
        ,
        @ApiResponse(code = 400, message = "No existe un CUPS perteneciente a éste identificador.")
    })
    @GetMapping(value = "/descargar/{nombreArchivo}")
    ResponseEntity<Resource> descargar(@PathVariable String fileName);
    
    /**
     * Lista un archivo de errores de cargue de CUPS
     *
     * @param fileName
     * @return Lista Archivo de errores 
     */
    @ApiOperation(value = "Recibe un nombre de archivo a listar")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de archivo de errores de cargue CUPS .")
        ,
        @ApiResponse(code = 400, message = "No existe un CUPS perteneciente a éste identificador.")
    })
    @GetMapping(value = "/listar/{nombreArchivo}")
    ResponseEntity<List<CupsDtoError>> listarDataNoCargados(@PathVariable String fileName);
}
