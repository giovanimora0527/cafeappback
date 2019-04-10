/*
* Archivo: PerfilPermisoIdentity.java
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

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Identity entidad de negocio PerfilPermiso
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@Embeddable
public class PerfilPermisoIdentity implements Serializable{
    /**
     * Identificador del perfil.
     */
    @NotNull
    @Column(name = "perfil_id", insertable = false, updatable = false)
    private Long perfilId;
    
    /**
     * Identificador de Menú Función.
     */
    @NotBlank
    @Size(max = 5)
    @Column(name = "menu_funcion_codigo", insertable = false, updatable = false)
    private String menuFuncionCodigo;
    
    /**
     * Identificador del menú
     */
    @NotBlank
    @Size(max = 5)
    @Column(name = "menu_codigo", insertable = false, updatable = false)
    private String menuCodigo;
    
    /**
     * Constructor
     */
    public PerfilPermisoIdentity() {
    }
    
    /**
     * Constructor
     */
    public PerfilPermisoIdentity(Long perfilId, String funcionCodigo, String menuCodigo) {
        this.perfilId = perfilId;
        this.menuFuncionCodigo = funcionCodigo;
        this.menuCodigo = menuCodigo;
    }
    
    /**
     * Setters y Getters
     */
    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public String getMenuFuncionCodigo() {
        return menuFuncionCodigo;
    }

    public void setMenuFuncionCodigo(String menuFuncionCodigo) {
        this.menuFuncionCodigo = menuFuncionCodigo;
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
        hash = 83 * hash + Objects.hashCode(this.perfilId);
        hash = 83 * hash + Objects.hashCode(this.menuFuncionCodigo);
        hash = 83 * hash + Objects.hashCode(this.menuCodigo);
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
        final PerfilPermisoIdentity other = (PerfilPermisoIdentity) obj;
        if (!Objects.equals(this.menuFuncionCodigo, other.menuFuncionCodigo)) {
            return false;
        }
        if (!Objects.equals(this.menuCodigo, other.menuCodigo)) {
            return false;
        }
        if (!Objects.equals(this.perfilId, other.perfilId)) {
            return false;
        }
        return true;
    }
}
