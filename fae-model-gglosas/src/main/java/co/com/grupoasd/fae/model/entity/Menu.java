/*
* Archivo: Menu.java
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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Entidad de negocio Menu
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@Entity
@Table(name = "auth_menu")
public class Menu implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del Menú.
     */
    @JsonView(View.MenuAcciones.class)
    @JsonProperty("codigo")
    @Id
    @Column(name = "menu_codigo", nullable = false, length = 5)
    private String menuCodigo;
    
    /**
     * Padre del menú
     */
    @JsonView(View.MenuAcciones.class)
    @Column(name = "padre", nullable = false, length = 5)
    private String padreMenu;
    
    /**
     * Orden del menú
     */   
    @JsonView(View.MenuAcciones.class)
    @Column(name = "orden", nullable = false, length = 11)
    private int ordenMenu;
    
    /**
     * Nombre del menú
     */
    @JsonView(View.MenuAcciones.class)
    @NotBlank
    @Column(name = "nombre_menu", nullable = false, length = 255)
    private String nombreMenu;
    
    /**
     * Ruta del menú
     */
    @JsonView(View.MenuAcciones.class)
    @NotBlank
    @Column(name = "ruta_menu", nullable = false, length = 500)
    private String rutaMenu;
    
    /**
     * Icono del menú
     */
    @JsonView(View.MenuAcciones.class)
    @NotBlank
    @Column(name = "icono", nullable = false, length = 500)
    private String iconoMenu;
    
    /**
     * Descripción del menú
     */
    @JsonView(View.MenuAcciones.class)
    @NotBlank
    @Column(name = "descripcion", nullable = false, length = 800)
    private String descripcionMenu;
    
    /**
     * Estado del registro 
     */   
    @JsonView(View.MenuAcciones.class)
    @NotNull
    @Column(name = "estado", nullable = false)
    private int estado;
        
    /**
     * Usuario que crea el registro 
     */
    @Column(name = "creado_por", nullable = false, insertable=true, updatable=false)
    private Long creadoPor;
    
    /**
     * Fecha de creación del registro
     */
    @Column(name = "fecha_creacion", nullable = false, insertable=true, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    /**
     * Usuario que modifica el registro
     */
    @Column(name = "modificado_por", nullable = true, insertable=false, updatable=true)
    private Long modificadoPor;
    
    /**
     * Fecha de modificación del registro 
     */
    @Column(name = "fecha_modificacion", nullable = true, insertable=false, updatable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    
    @JsonView(View.MenuAcciones.class)
    @Transient
    private List<Menu> menusHijos;
    
    @JsonView(View.MenuAcciones.class)
    @Transient
    private List<MenuFuncion> menuFuncion;
    
    /**
     * Define getter y Setter 
     */
    public String getMenuCodigo() {
        return menuCodigo;
    }

    public void setMenuCodigo(String menuCodigo) {
        this.menuCodigo = menuCodigo;
    }

    public String getPadreMenu() {
        return padreMenu;
    }

    public void setPadreMenu(String padreMenu) {
        this.padreMenu = padreMenu;
    }

    public int getOrdenMenu() {
        return ordenMenu;
    }

    public void setOrdenMenu(int ordenMenu) {
        this.ordenMenu = ordenMenu;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public String getRutaMenu() {
        return rutaMenu;
    }

    public void setRutaMenu(String rutaMenu) {
        this.rutaMenu = rutaMenu;
    }

    public String getIconoMenu() {
        return iconoMenu;
    }

    public void setIconoMenu(String iconoMenu) {
        this.iconoMenu = iconoMenu;
    }

    public String getDescripcionMenu() {
        return descripcionMenu;
    }

    public void setDescripcionMenu(String descripcionMenu) {
        this.descripcionMenu = descripcionMenu;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Long getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Long creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Long modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<Menu> getMenusHijos() {
        return menusHijos;
    }

    public void setMenusHijos(List<Menu> menusHijos) {
        this.menusHijos = menusHijos;
    }

    public List<MenuFuncion> getMenuFuncion() {
        return menuFuncion;
    }

    public void setMenuFuncion(List<MenuFuncion> menuFuncion) {
        this.menuFuncion = menuFuncion;
    }
    
    
    /**
     * Define Equals y Hash 
     */ 
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.menuCodigo);
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
        final Menu other = (Menu) obj;
        if (!Objects.equals(this.menuCodigo, other.menuCodigo)) {
            return false;
        }
        return true;
    }
}
