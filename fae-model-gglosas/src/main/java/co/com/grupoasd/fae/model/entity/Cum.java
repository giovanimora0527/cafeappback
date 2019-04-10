/*
* Archivo: Cum.java
* Fecha: 19/02/2019
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
 * Entidad de negocio Cum
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Entity
@Table(name = "adm_cum")
public class Cum implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del cum.
     */
    @JsonView(View.Cum.class)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "cum_id", nullable = false)
    private Long id;
    
    /**
     * Código cum
     */
    @JsonView(View.Cum.class)
    @NotBlank
    @Column(name = "codigo", nullable = false, length = 6)
    private String codigo;
    
    /**
     * Nombre del producto
     */
    @JsonView(View.Cum.class)
    @NotBlank
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    
    /**
     * Descripción comercial de lo que representa el código CUM
     */
    @JsonView(View.Cum.class)
    @NotBlank
    @Column(name = "nombre_comercial", nullable = false, length = 255)
    private String nombreComercial;
    
    /**
     * Concentración cum
     */
    @JsonView(View.Cum.class)
    @NotBlank
    @Column(name = "concentracion", nullable = false, length = 6)
    private String concentracion;
    
     /**
     * Presentación cum
     */
    @JsonView(View.Cum.class)
    @NotBlank
    @Column(name = "presentacion", nullable = false, length = 6)
    private String presentacion;
    
    /**
     * Indica la fecha de vigencia del CUM
     */
    @JsonView(View.Cum.class)
    @Column(name = "fecha_inactivo", nullable = true, insertable=true, updatable=true)
    @Temporal(TemporalType.DATE)
    private Date fechaInactivo;
    
    /**
     * Estado del registro 
     */   
    @JsonView(View.Cum.class)
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

    
    public Long getId() {
        return id;
    }

    /**
     * Define setters and Getters 
     * @param id
     */
    public void setId(Long id) {    
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public Date getFechaInactivo() {
        return fechaInactivo;
    }

    public void setFechaInactivo(Date fechaInactivo) {
        this.fechaInactivo = fechaInactivo;
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
        final Cum other = (Cum) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
