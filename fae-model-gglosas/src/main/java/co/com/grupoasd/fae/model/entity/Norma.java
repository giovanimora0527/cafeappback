/*
* Archivo: Norma.java
* Fecha: 13/02/2019
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
 * Entidad de negocio Norma
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Entity
@Table(name = "adm_norma")
public class Norma implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del registro.
     */
    @JsonView(View.Norma.class)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "norma_id", nullable = false)
    private Long id;
    
    /**
     * Nombre o código de la norma
     */
    @JsonView(View.Norma.class)
    @NotBlank
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    
    /**
     * Tipo, indica si será usado para CUPS=1 o para CUM=2 u otro negocio
     */
    @JsonView(View.Norma.class)
    @NotBlank
    @Column(name = "tipo", nullable = false, length = 1)
    private String tipo;
    
    /**
     * Estado del registro 
     */   
    @JsonView(View.Norma.class)
    @Column(name = "estado", nullable = false)
    private int estado;
    
    /**
     * Fecha de inicio de vigencia de la norma
     */
    @Column(name = "fecha_inicio", nullable = false, insertable=true, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
        
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
     * Define setters and Getters 
     */
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {    
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public Date getFechaModificado() {
        return fechaModificacion;
    }

    public void setFechaModificado(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    
    /**
     * Define Equals y Hash 
     */   
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Norma other = (Norma) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
