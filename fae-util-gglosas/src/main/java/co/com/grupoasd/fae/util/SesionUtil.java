package co.com.grupoasd.fae.util;

import javax.servlet.ServletRequest;

/**
 * Clase utilitaria para menejo de sesion de usuario
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public class SesionUtil {
    /**
     * Constructor privado. Patrón singleton.
     */
    private SesionUtil() {
        
    }
    /**
     * Obtiene la sesion del usuario.
     * @param req HttpServletRequest
     * @param identificacion
     * @param puesto Puesto fronterizo.
     * @return Sesion del usuario.
     */
    public static SesionUsuario obtenerSesion(ServletRequest req, String identificacion, Short puesto) {
        return new SesionUsuario(identificacion, puesto, req.getRemoteAddr());
    }
    /**
     * Obtiene la sesion del usuario.
     * @param req HttpServletRequest
     * @return Sesion del usuario.
     */
    public static SesionUsuario obtenerSesion(ServletRequest req) {
        return new SesionUsuario(req.getRemoteAddr());
    }
}
