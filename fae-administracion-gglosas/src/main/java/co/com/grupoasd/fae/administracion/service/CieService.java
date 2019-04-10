/*
* Archivo: CieService.java
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
package co.com.grupoasd.fae.administracion.service;

import co.com.grupoasd.fae.model.dto.CieDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cie;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GMORA
 */
public interface CieService {
    
    /**
     * Crea un registro de CIE
     * @param cie
     * @param userId
     * @return objeto CIE creado
     */
    Cie crear(Cie cie, Long userId);
    
    
    /**
     * Modifica un registro de CIE
     * @param cie
     * @param userId id usuario que realiza la modificacion
     * @return objeto CUPS modificado
     */
    Cie editar(Cie cie, Long userId);
    
    
    /**
     * Obtiene una lista de nombres de archivos no cargados
     * @return una lista de nombres de los archivos
     */
    List<String> getArchivosNoCargados();
    
    /**
     * Hace el cargue de un archivo csv y guarda en base los registros que estan correctos y los que no genera un archivo de error
     * y lo guarda en una ruta parametrizable
     * @param archivo archivo a cargar
     * @param idUser id usuario que realiza el cargue
     * @param norma norma que rige sobre el cups
     * @return Respuesta generica confirmando la transacción
     */
    GenericRs cargarArchivo(MultipartFile archivo, Long idUser, String norma);
    
    /**
     * Descarga un archivo de errores de cargue de CUPS
     *
     * @param nombre
     * @return Descarga Archivo de errores 
     */
    Resource descargarArchivoNoCargado(String nombre);
    
    /**
     * Lista un archivo de errores de cargue de CUPS
     *
     * @param nombre
     * @return Lista Archivo de errores 
     */
    List<CieDtoError> listarArchivosNoCargados(String nombre);
    
}
