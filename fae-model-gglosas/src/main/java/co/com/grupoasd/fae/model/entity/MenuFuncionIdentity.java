/*
* Archivo: MenuFuncionIdentity.java
* Fecha: 25/09/2018
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
package co.com.grupoasd.fae.model.entity;

import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Identity entidad de negocio MenuFuncion
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@Embeddable
public class MenuFuncionIdentity implements Serializable{
    /**
     * Identificador de Menú Función.
     */
    @JsonView(View.MenuAcciones.class)
    @NotBlank
    @Size(max = 5)
    @Column(name = "funcion_codigo", insertable = false, updatable = false)
    private String funcionCodigo;
    
    /**
     * Identificador del menú
     */
    @JsonView(View.MenuAcciones.class)
    @NotBlank
    @Size(max = 5)
    @Column(name = "menu_codigo", insertable = false, updatable = false)
    private String menuCodigo;
    
    /**
     * Constructor
     */
    public MenuFuncionIdentity() {
    }
    
    /**
     * Constructor
     * @param funcionCodigo
     * @param menuCodigo
     */
    public MenuFuncionIdentity(String funcionCodigo, String menuCodigo) {
        this.funcionCodigo = funcionCodigo;
        this.menuCodigo = menuCodigo;
    }

    /**
     * Setters y Getters
     */
    public void setFuncionCodigo(String funcionCodigo) {
        this.funcionCodigo = funcionCodigo;
    }
    
    public String getFuncionCodigo() {
        return funcionCodigo;
    }

    public String getMenuCodigo() {
        return menuCodigo;
    }

    public void setMenuCodigo(String menuCodigo) {
        this.menuCodigo = menuCodigo;
    }
    
    /**
     * define hash y equals
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.funcionCodigo);
        hash = 89 * hash + Objects.hashCode(this.menuCodigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuFuncionIdentity other = (MenuFuncionIdentity) obj;
        if (!Objects.equals(this.funcionCodigo, other.funcionCodigo)) {
            return false;
        }
        if (!Objects.equals(this.menuCodigo, other.menuCodigo)) {
            return false;
        }
        return true;
    }
}
