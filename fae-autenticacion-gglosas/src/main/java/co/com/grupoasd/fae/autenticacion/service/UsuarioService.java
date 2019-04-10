/*
* Archivo: UsuarioService.java
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
package co.com.grupoasd.fae.autenticacion.service;

import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.autenticacion.model.CambioClaveRs;
import co.com.grupoasd.fae.autenticacion.model.RestaurarRq;
import co.com.grupoasd.fae.autenticacion.model.RestaurarRs;
import org.springframework.http.ResponseEntity;

/**
 *
 * Servicios de negocio para gestion de usuarios.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
public interface UsuarioService {
    ResponseEntity<Usuario> crearUsuario(Usuario usuario);
    
    RestaurarRs restablecerContrasena(RestaurarRq restaurarRq);
    
    CambioClaveRs cambioClave(String usuario, String claveActual, String claveNueva);
    
    RestaurarRs validar(String token);
    
    RestaurarRs restablecerClave(String usuario, String claveNueva);
}
