/*
* Archivo: AccesosCambioClaveApiController.java
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
import co.com.grupoasd.fae.autenticacion.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @inheritDoc
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class AccesosCambioClaveApiController implements AccesosCambioClaveApi {
    
    private final UsuarioService usuarioService;
    
    public AccesosCambioClaveApiController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }
    
    @Override
    public ResponseEntity<CambioClaveRs> cambioClave(@RequestBody @Validated CambioClaveRq cambioClaveRq) {
        // Sacara el usuario de spring security 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String usuarioToken = userDetails.getUsername();
        
        return ResponseEntity.ok(usuarioService.cambioClave(usuarioToken, cambioClaveRq.getClaveActual(), cambioClaveRq.getClaveNueva()));
    }
}
