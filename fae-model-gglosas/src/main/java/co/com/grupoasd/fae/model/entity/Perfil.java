/*
* Archivo: Perfil.java
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
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Entidad de negocio Perfil
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@Entity
@Table(name = "auth_perfil")
public class Perfil implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del perfil.
     */
    @JsonView(View.UsuarioPerfil.class)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "perfil_id", nullable = false)
    private Long id;
    
    /**
     * Nombre del perfil
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotBlank
    @Column(name = "nombre_perfil", nullable = false, length = 255)
    private String nombrePerfil;
    
    /**
     * Entidad a la que pertenece el perfil
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotNull
    @Column(name = "entidad_id", nullable = false, length = 60)
    private String entidadId;
    
    /**
     * Indicador de SuperUsuario
     */   
    @JsonView(View.UsuarioPerfil.class)
    @Column(name = "es_super", nullable = true)
    private int esSuper;
    
    /**
     * Indicador de Administrador
     */   
    @JsonView(View.UsuarioPerfil.class)
    @Column(name = "es_admin", nullable = true)
    private int esAdmin;
    
    /**
     * Indicador de Perfil Base
     */   
    @JsonView(View.UsuarioPerfil.class)
    @Column(name = "es_base", nullable = true)
    private int esBase;
    
    /**
     * Estado del registro 
     */   
    @JsonView(View.UsuarioPerfil.class)
    @NotNull
    @Column(name = "estado", nullable = false)
    private int estado;
    
    /**
     * Entidad a la que pertenece el perfil
     */
    @JsonView(View.UsuarioPerfil.class)
    @Column(name = "tipo_base", nullable = false, length = 1)
    private String tipoBase;
        
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
     * Define Setters y Getters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public String getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(String entidadId) {
        this.entidadId = entidadId;
    }

    public int getEsSuper() {
        return esSuper;
    }

    public void setEsSuper(int esSuper) {
        this.esSuper = esSuper;
    }

    public int getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(int esAdmin) {
        this.esAdmin = esAdmin;
    }

    public int getEsBase() {
        return esBase;
    }

    public void setEsBase(int esBase) {
        this.esBase = esBase;
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

    public String getTipoBase() {
        return tipoBase;
    }

    public void setTipoBase(String tipoBase) {
        this.tipoBase = tipoBase;
    }
    
    
    
    /**
     * Define Equals y Hash 
     */
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Perfil other = (Perfil) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
