/*
* Archivo: CambioClaveRq.java
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
import javax.validation.constraints.NotNull;

/**
 * Mensaje de peticion cambio de clave.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
public class CambioClaveRq implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Clave actual del usuario
     */
    @NotNull
    private String claveActual;
    /**
     * Nueva clave.
     */
    @NotNull
    private String claveNueva;
    
    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }
    
}
