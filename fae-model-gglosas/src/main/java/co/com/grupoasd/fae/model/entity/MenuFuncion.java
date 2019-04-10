/*
* Archivo: MenuFuncion.java
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
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Entidad de negocio Menu
 *
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@Entity
@Table(name = "auth_menu_funcion")
public class MenuFuncion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de Menú Función.
     */
    @JsonView(View.MenuAcciones.class)
    @EmbeddedId
    private MenuFuncionIdentity menuFuncionIdentity;

    /**
     * Indentificador de la funcion
     */
    @JsonView(View.MenuAcciones.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcion_codigo", referencedColumnName = "funcion_codigo", nullable = false, insertable = false, updatable = false)
    private Funcion funcionCodigo;

    /**
     * Indentificador del menu
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_codigo", referencedColumnName = "menu_codigo", nullable = false, insertable = false, updatable = false)
    private Menu menuCodigo;

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
    @Column(name = "creado_por", nullable = false, insertable = true, updatable = false)
    private Long creadoPor;

    /**
     * Fecha de creación del registro
     */
    @Column(name = "fecha_creacion", nullable = false, insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    /**
     * Usuario que modifica el registro
     */
    @Column(name = "modificado_por", nullable = true, insertable = false, updatable = true)
    private Long modificadoPor;

    /**
     * Fecha de modificación del registro
     */
    @Column(name = "fecha_modificacion", nullable = true, insertable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    /**
     * Define getter y Setter
     */
    public MenuFuncionIdentity getMenuFuncionIdentity() {
        return menuFuncionIdentity;
    }

    public void setMenuFuncionIdentity(MenuFuncionIdentity menuFuncionIdentity) {
        this.menuFuncionIdentity = menuFuncionIdentity;
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

    public Menu getMenuCodigo() {
        return menuCodigo;
    }

    public void setMenuCodigo(Menu menuCodigo) {
        this.menuCodigo = menuCodigo;
    }

    public Funcion getFuncionCodigo() {
        return funcionCodigo;
    }

    public void setFuncionCodigo(Funcion funcionCodigo) {
        this.funcionCodigo = funcionCodigo;
    }

    
}
