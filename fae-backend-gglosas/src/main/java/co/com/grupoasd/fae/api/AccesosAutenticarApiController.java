/*
* Archivo: AccesosAutenticarApiController.java
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
import co.com.grupoasd.fae.autenticacion.model.ValidarRs;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.autenticacion.service.AutenticarService;
import co.com.grupoasd.fae.util.SesionUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint REST para el control de accesos.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class AccesosAutenticarApiController implements AccesosAutenticarApi {

    private final AutenticarService autenticarService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AccesosAutenticarApiController(AutenticarService autenticarService, 
            JwtTokenProvider jwtTokenProvider) {
        this.autenticarService = autenticarService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Override
    public ResponseEntity<AutenticarRs> autenticar(HttpServletRequest req, 
            @RequestBody @Validated AutenticarRq autenticarRq) {
        return ResponseEntity.ok(autenticarService.autenticar(autenticarRq, 
                SesionUtil.obtenerSesion(req)));
    }
    
    @Override
    public ResponseEntity cerrarSesion(HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        autenticarService.cerrarSesion(token, SesionUtil.obtenerSesion(req));
        return ResponseEntity.ok(new ValidarRs("200"));
    }
    
}
