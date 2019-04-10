/*
* Archivo: GenericRs.java
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
package co.com.grupoasd.fae.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Mensaje de respuesta generico.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
@JsonInclude(Include.NON_NULL) 
public class GenericRs implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /**
     * Estatus de respuesta.
     */
    private String status;
    /**
     * Mensaje de respuesta.
     */
    private String mensaje;

    public GenericRs() {
    }

    public GenericRs(String status) {
        this.status = status;
        this.mensaje = null;
    }

    public GenericRs(String status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }
    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
            
}
