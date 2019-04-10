/*
* Archivo: ValidarRs.java
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

/**
 * Mensaje de respuesta de validacion de puesto fronterizo.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public class ValidarRs implements Serializable {
    /**
     * Estatus de respuesta.
     */
    private String status;

    public ValidarRs() {
    }

    public ValidarRs(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
