/*
* Archivo: TipoDocumento.java
* Fecha: 24/09/2018
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
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Entidad de negocio Soportes servicio mecanismo de pago.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Entity
@Table(name = "adm_soporteserv_mecpago")
public class SoporteservMecpago implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del SoporteservMecpago.
     */
    @JsonView(View.SoporteservMecpago.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "soporteserv_mecpago_id", nullable = false)
    @ApiModelProperty(notes = "Identificador del tipo de documento.")
    private Long id;
    
    /**
     * Identificador del servicio mecanimos de pago.
     */
    @JsonView(View.SoporteservMecpago.class)
    @NotNull
    @Column(name = "serviciomec_pago_id", nullable = false)
    @ApiModelProperty(notes = "Identificador del servicio mecanismo de pago.")
    private Long serviciomecPagoId;
    
    /**
     * Identificador del mecanismo de pago.
     */
    @JsonView(View.SoporteservMecpago.class)
    @Column(name = "mecanismo_pago_id", nullable = false)
    @ApiModelProperty(notes = "Identificador del mecanismo de pago.")
    private Long mecanismoPagoId;
    
    /**
     * Identificador de la tipologia de documento.
     */
    @JsonView(View.SoporteservMecpago.class)
    @Column(name = "tipologia_documento_id", nullable = false)
    @ApiModelProperty(notes = "Identificador de la tipologia de documento.")
    private Long tipologiaDocumentoId;
    
    /**
     * Identificador de la tipologia de documento.
     * Objeto TipologiaDocumento.
     */
    @JsonView(View.SoporteservMecpago.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipologia_documento_id", referencedColumnName = "tipologia_documento_id", nullable = false, insertable = false, updatable = false)
    @ApiModelProperty(notes = "Identificador de la tipologia de documento.")
    private TipologiaDocumento tipologiaDocumento;
    
    /**
     * Indica si el registro esta activo o no, solo se trabajará con información activa
     */
    @JsonView(View.SoporteservMecpago.class)
    @Column(name = "estado", nullable = false)
    @ApiModelProperty(notes = "Indica si el registro esta activo o no, solo se trabajará con información activa.")
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
     * Identificador del tipo de documento.
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Identificador del tipo de documento.
     * @param id Long
     */
    public void setId(final Long id) {
        this.id = id;
    }

    public Long getServiciomecPagoId() {
        return serviciomecPagoId;
    }

    public void setServiciomecPagoId(Long serviciomecPagoId) {
        this.serviciomecPagoId = serviciomecPagoId;
    }

    public Long getMecanismoPagoId() {
        return mecanismoPagoId;
    }

    public void setMecanismoPagoId(Long mecanismoPagoId) {
        this.mecanismoPagoId = mecanismoPagoId;
    }

    public Long getTipologiaDocumentoId() {
        return tipologiaDocumentoId;
    }

    public void setTipologiaDocumentoId(Long tipologiaDocumentoId) {
        this.tipologiaDocumentoId = tipologiaDocumentoId;
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

    public TipologiaDocumento getTipologiaDocumento() {
        return tipologiaDocumento;
    }

    public void setTipologiaDocumento(TipologiaDocumento tipologiaDocumento) {
        this.tipologiaDocumento = tipologiaDocumento;
    }

    /**
     * HashCode.
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * equals.
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SoporteservMecpago other = (SoporteservMecpago) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }        
    
    
}
