/*
* Archivo: AutenticarRs.java
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

import co.com.grupoasd.fae.model.entity.Menu;
import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;

/**
 * Mensaje de respuesta de autenticacion.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
@JsonInclude(Include.NON_NULL) 
public class AutenticarRs implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Estatus de respuesta.
     */
    @JsonView(View.MenuAcciones.class)
    private String status;
    /**
     * Token de autenticacion.
     */
    @JsonView(View.MenuAcciones.class)
    private String token;
    
    /**
     * Menu permitido al perfil del usuario
     */
    @JsonView(View.InternalAutenticarRs.class)
    private List<Menu> menu;

    public AutenticarRs() {
    }

    public AutenticarRs(String status) {
        this.status = status;
        this.token = null;
    }

    public AutenticarRs(String status, String token, List<Menu> menu) {
        this.status = status;
        this.token = token;
        this.menu = menu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
    
    
}
