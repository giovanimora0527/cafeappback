/*
* Archivo: AccesosCambioClaveApi.java
* Fecha: 19/10/2018
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

import co.com.grupoasd.fae.autenticacion.model.CambioClaveRq;
import co.com.grupoasd.fae.autenticacion.model.CambioClaveRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Endpoint REST para cambio de clave.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/accesos")
public interface AccesosCambioClaveApi {
    
    /**
     * Cambia la clave de un usuario.
     * @param cambioClaveRq Mensaje de peticion de cambio de clave.
     * @return Mensaje de respuesta de cambio de clave.
     */
    @RequestMapping(value = "/cambio-clave",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    ResponseEntity<CambioClaveRs> cambioClave(CambioClaveRq cambioClaveRq);
}
