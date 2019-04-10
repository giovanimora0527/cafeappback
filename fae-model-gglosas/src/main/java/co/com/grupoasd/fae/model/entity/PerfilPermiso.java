/*
* Archivo: PerfilPermiso.java
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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad de negocio Menu
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@Entity
@Table(name = "auth_perfil_permiso")
public class PerfilPermiso implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de PerfilPermiso.
     */
    @EmbeddedId
    private PerfilPermisoIdentity perfilPermisoIdentity;
    
    /**
     * Identificador del perfil
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id", referencedColumnName = "perfil_id", nullable = false, insertable = false, updatable = false)
    private Perfil perfilId;
    
    /**
     * Indentificador del menu
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_codigo", referencedColumnName = "menu_codigo", nullable = false, insertable = false, updatable = false)
    private Menu menuCodigo;
    
    /**
     * Identificador de Menú Función.
     */
    @Column(name = "menu_funcion_codigo", insertable = false, updatable = false)
    private String menuFuncion;
    
    /**
     * Estado del registro 
     */
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

    /**
     * Define getter y Setter 
     * @return Objeto PerfilPermisoIdentity
     */
    public PerfilPermisoIdentity getPerfilPermisoIdentity() {
        return perfilPermisoIdentity;
    }

    public void setPerfilPermisoIdentity(PerfilPermisoIdentity perfilPermisoIdentity) {
        this.perfilPermisoIdentity = perfilPermisoIdentity;
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

    public Perfil getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Perfil perfilId) {
        this.perfilId = perfilId;
    }

    public Menu getMenuCodigo() {
        return menuCodigo;
    }

    public void setMenuCodigo(Menu menuCodigo) {
        this.menuCodigo = menuCodigo;
    }

    public String getMenuFuncion() {
        return menuFuncion;
    }

    public void setMenuFuncion(String menuFuncion) {
        this.menuFuncion = menuFuncion;
    }

    
}
