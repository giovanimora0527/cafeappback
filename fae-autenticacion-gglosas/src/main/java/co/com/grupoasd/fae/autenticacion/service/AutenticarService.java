/*
* Archivo: AutenticarService.java
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

import co.com.grupoasd.fae.autenticacion.model.AutenticarRq;
import co.com.grupoasd.fae.autenticacion.model.AutenticarRs;
import co.com.grupoasd.fae.util.SesionUsuario;

/**
 * Servicios de negocio para autenticacion de usuarios.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public interface AutenticarService {
    /**
     * Autentica un usuario en el sistema.
     * @param autenticarRq Mensaje de autenticacion de usuario.
     * @param sesionUsuario Sesion del usuario.
     * @return Respuesta de autenticacion de usuario.
     */
    AutenticarRs autenticar(AutenticarRq autenticarRq, SesionUsuario sesionUsuario);
    /**
     * Invalida la sesion.
     * @param token Token de sesion.
     * @param sesionUsuario Sesion del usuario.
     */
    void cerrarSesion(String token, SesionUsuario sesionUsuario);
    
}
