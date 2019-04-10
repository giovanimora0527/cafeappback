/*
* Archivo: Funcion.java
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
 * Entidad de negocio Funcion
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@Entity
@Table(name = "auth_funcion")
public class Funcion implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de Función.
     */
    @JsonView(View.MenuAcciones.class)
    @Id
    @Column(name = "funcion_codigo", nullable = false, length = 5)
    private String funcionCodigo;
    
    /**
     * Detalle de la función de menú
     */
    @JsonView(View.MenuAcciones.class)
    @NotBlank
    @Column(name = "detalle_funcion", nullable = false, length = 255)
    private String detalleMenuFuncion;
    
    /**
     * Estado del registro 
     */   
    @JsonView(View.MenuAcciones.class)
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

    public String getFuncionCodigo() {
        return funcionCodigo;
    }
    
    /**
     * Define getter y Setter 
     */
    public void setFuncionCodigo(String funcionCodigo) {
        this.funcionCodigo = funcionCodigo;
    }

    public String getDetalleMenuFuncion() {
        return detalleMenuFuncion;
    }

    public void setDetalleMenuFuncion(String detalleMenuFuncion) {
        this.detalleMenuFuncion = detalleMenuFuncion;
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
    
    /**
     * Define Equals y Hash 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.funcionCodigo);
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
        final Funcion other = (Funcion) obj;
        if (!Objects.equals(this.funcionCodigo, other.funcionCodigo)) {
            return false;
        }
        return true;
    }   
}
