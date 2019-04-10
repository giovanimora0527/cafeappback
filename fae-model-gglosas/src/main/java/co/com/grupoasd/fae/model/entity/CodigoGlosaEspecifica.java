/*
* Archivo: CodigoGlosaEspecifica.java
* Fecha: 12/12/2018
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Entidad de negocio CodigoGlosa
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Entity
@Table(name = "adm_codigoesp_glosa")
public class CodigoGlosaEspecifica implements Serializable{
    private static final Long serialVersionUID = 1L;
    
    /**
     * Identificador del tipo de documento.
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigoesp_glosa_id", nullable = false)
    private Long id;
    
    /**
     * Identificador del código de la glosa al que pertenece (padre del registro).
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @NotNull
    @Column(name = "codigo_glosa_id", nullable = false)
    private Long codigoGlosaId;
    
    /**
     * Detalle de la glosa
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @NotBlank
    @Column(name = "detalle_glosaesp", nullable = false, length = 255)
    private String detalleGlosaEspecifica;
    
    /**
     * Codigo de la glosa esp
     */
    @JsonView(View.CodigoGlosaEsp.class)
    @NotBlank
    @Column(name = "codigo_glosaesp", nullable = false, length = 50)
    private String codigoGlosaesp;
    
    /**
     * Estado del registro 
     */   
    @JsonView(View.CodigoGlosaEsp.class)
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

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * Define setters and Getters
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Long getCodigoGlosaId() {
        return codigoGlosaId;
    }

    public void setCodigoGlosaId(Long codigoGlosaId) {
        this.codigoGlosaId = codigoGlosaId;
    }

    public String getDetalleGlosaEspecifica() {
        return detalleGlosaEspecifica;
    }
    
    public void setDetalleGlosaEspecifica(String detalleGlosaEspecifica) {    
        this.detalleGlosaEspecifica = detalleGlosaEspecifica;
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

    public String getCodigoGlosaesp() {
        return codigoGlosaesp;
    }

    public void setCodigoGlosaesp(String codigoGlosaesp) {
        this.codigoGlosaesp = codigoGlosaesp;
    }
    
    /**
     * Define Equals y Hash 
     */
    @Override
    public int hashCode() {
        int hash = 3;
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
        final CodigoGlosaEspecifica other = (CodigoGlosaEspecifica) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
