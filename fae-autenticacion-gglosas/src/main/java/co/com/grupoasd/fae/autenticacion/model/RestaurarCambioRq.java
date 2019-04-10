/*
* Archivo: RestaurarCambioRq.java
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
package co.com.grupoasd.fae.autenticacion.model;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * Mensaje de peticion de restauracion de clave de usuario.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public class RestaurarCambioRq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Nombre de usuario.
     */
    @NotBlank
    private String key;
    @NotBlank
    private String clave;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    
    
}
