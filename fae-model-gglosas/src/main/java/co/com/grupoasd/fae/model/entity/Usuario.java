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

import co.com.grupoasd.fae.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Entidad de negocio Usuario
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@Entity
@Table(name = "auth_usuario")
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del usuario.
     */
    @JsonView(View.UsuarioPerfil.class)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "usuario_id", nullable = false)
    private Long id;
    
    /**
     * Entidad a la que pertenece el usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotNull
    @Column(name = "entidad_id", nullable = false)
    private String entidadId;
    
    /**
     * Perfil al que pertenece el usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", referencedColumnName = "perfil_id", nullable = false, insertable = false, updatable = false)
    private Perfil perfil;
    
    /**
     * Perfil al que pertenece el usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotNull
    @Column(name = "perfil_id", nullable = false)
    private Long perfilId;
    
    /**
     * Nombre del usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotBlank
    @Column(name = "nombre_usuario", nullable = false, length = 255)
    private String nombreUsuario;
    
    
    /**
     * Tipo documento del usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotNull
    @Column(name = "tipo_documento_id", nullable = false)
    private Integer tipoDocumentoId;
    
    /**
     * numero documento del usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotBlank
    @Column(name = "identificacion", nullable = false,  length = 50)
    private String identificacion;
    
    /**
     * Dirección del usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotBlank
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;
    
    /**
     * numero telefono del usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotBlank
    @Column(name = "telefono", nullable = false,  length = 20)
    private String telefono;
    
    /**
     * email del usuario
     */
    @JsonView(View.UsuarioPerfil.class)
    @NotBlank
    @Column(name = "email", nullable = false,  length = 255)
    private String email;
    
    /**
     * cláve del usuario
     */
    @Column(name = "password", nullable = false,  length = 255)
    private String password;
    
    /**
     * Estado del registro 
     */   
    @JsonView(View.UsuarioPerfil.class)
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
    
    /**
     * Define getter y Setter 
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(String entidadId) {
        this.entidadId = entidadId;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }
    
    
    
    /**
     * Define Equals y Hash 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
