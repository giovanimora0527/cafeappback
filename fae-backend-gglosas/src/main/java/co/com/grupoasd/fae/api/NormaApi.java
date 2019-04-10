/*
* Archivo: NormaApi.java
* Fecha: 13/02/2019
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

import co.com.grupoasd.fae.model.entity.CodigoGlosa;
import co.com.grupoasd.fae.model.entity.Norma;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Enpoint REST para gestión de Normas
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/norma")
@Api(value = "norma", description = "Operaciones recurso Norma")
public interface NormaApi {
    /**
     * Listar Normas
     * @return Listado de Normas
     */
    @JsonView(View.Norma.class)
    @ApiOperation(value = "Listar Normas")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con las Normas. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/")
    ResponseEntity<List<Norma>> listar();
    
    /**
     * Obtiene un registro de una norma
     * @param id Identificador del registro
     * @return Registro de la norma
     */
    @JsonView(View.Norma.class)
    @ApiOperation(value = "Obtiene un solo registro de Norma")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Norma."),
        @ApiResponse(code = 400, message = "No existe un código de glosa perteneciente a éste identificador.")
    })
    @GetMapping(value = "/{id}")
    ResponseEntity<Norma> obtener(@PathVariable("id") Long id);
    
    /**
     * Listar Normas por tipo
     * @param tipo Tipo de norma
     * @return Listado de Normas
     */
    @JsonView(View.Norma.class)
    @ApiOperation(value = "Listar Normas por tipo")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista con las Normas. " + 
                    "Si no existe retorna una lista vacía.")
    })
    @GetMapping(value = "/tipo/{tipo}")
    ResponseEntity<List<Norma>> listarXTipo(@PathVariable("tipo") String tipo);
}
