package co.com.grupoasd.fae.util;

import java.io.Serializable;

/**
 * Modelo que representa los datos de la sesion de un usuario
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public class SesionUsuario implements Serializable {
    
    /**
     * Identificador del usuario.
     */
    private String usuarioId;
    /**
     * Puesto fronterizo seleccionado por el usuario en la sesion de trabajo.
     */
    private Short puestoId;
    /**
     * IP del cliente.
     */
    private String ip;

    public SesionUsuario(String usuarioId, Short puestoId, String ip) {
        this.usuarioId = usuarioId;
        this.puestoId = puestoId;
        this.ip = ip;
    }
    
    public SesionUsuario(String ip) {        
        this.ip = ip;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Short getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(Short puestoId) {
        this.puestoId = puestoId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    
}
