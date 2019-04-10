/*
* Archivo: AccesosAutenticarApi.java
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

import co.com.grupoasd.fae.autenticacion.model.AutenticarRq;
import co.com.grupoasd.fae.autenticacion.model.AutenticarRs;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Endpoint REST para el control de accesos.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@CrossOrigin("*")
@RequestMapping("/accesos")
public interface AccesosAutenticarApi {
    
    /**
     * Autentica un usuario en el sistema.
     * @param req HttpServletRequest.
     * @param autenticarRq Mensaje de peticion de autenticacion.
     * @return Mensaje de respuesta de autenticacion.
     */
    @JsonView({View.InternalAutenticarRs.class})
    @PostMapping(value = "/autenticar",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" })
    ResponseEntity<AutenticarRs> autenticar(HttpServletRequest req, AutenticarRq autenticarRq);
    
    /**
     * Finaliza la sesion de un usuario autenticado.
     * @param req HttpServletRequest
     * @return ResponseEntity
     */
    @PostMapping(value = "/cerrar-sesion",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" })
    ResponseEntity cerrarSesion(HttpServletRequest req);
    
}
