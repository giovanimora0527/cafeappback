/*
* Archivo: Cups.java
* Fecha: 19/12/2018
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
 * Entidad de negocio Cups
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Entity
@Table(name = "adm_cups")
public class Cups implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del cups.
     */
    @JsonView(View.Cups.class)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "cups_id", nullable = false)
    private Long id;
    
    /**
     * Código cups
     */
    @JsonView(View.Cups.class)
    @NotBlank
    @Column(name = "codigo", nullable = false, length = 6)
    private String codigo;
    
    /**
     * Descripción de lo que representa el código CUPS
     */
    @JsonView(View.Cups.class)
    @NotBlank
    @Column(name = "detalle", nullable = false, length = 255)
    private String detalle;
    
    /**
     * Indica la fecha de vigencia del CUPS de acuerdo a la resolución con la que fue agregado
     */
    @JsonView(View.Cups.class)
    @Column(name = "fecha_inicio_vigencia", nullable = false, insertable=true, updatable=true)
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    
    /**
     * Indica la fecha fin de vigencia de acuerdo a la resolución con la que se cambia el CUPS.
     */
    @JsonView(View.Cups.class)
    @Column(name = "fecha_fin_vigencia", nullable = false, insertable=true, updatable=true)
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    
    /**
     * Código cups
     */
    @JsonView(View.Cups.class)    
    @Column(name = "cod_homologar", nullable = false, length = 6)
    private String codHomologar;
    
    /**
     * Norma a la que aplica el CUPS
     */
    @JsonView(View.Cups.class)
    @NotBlank
    @Column(name = "norma", nullable = false, length = 60)
    private String norma;
    
    /**
     * Estado del registro 
     */   
    @JsonView(View.Cups.class)
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Date getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getCodHomologar() {
        return codHomologar;
    }

    public void setCodHomologar(String codHomologar) {
        this.codHomologar = codHomologar;
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

    public String getNorma() {
        return norma;
    }

    public void setNorma(String norma) {
        this.norma = norma;
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
        final Cups other = (Cups) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
