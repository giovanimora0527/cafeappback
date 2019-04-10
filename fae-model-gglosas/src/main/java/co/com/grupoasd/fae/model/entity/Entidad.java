/*
* Archivo: Entidad.java
* Fecha: 22/10/2018
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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Entidad de negocio Entidad
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 * 
 */
@Entity
@Table(name = "adm_entidad")
public class Entidad implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del registro.
     */
    @JsonView(View.Entidad.class)
    @JsonProperty("id")
    @Id
    @Column(name = "entidad_id", nullable = false, length = 60)
    private String entidadId;
    
    /**
     * Nombre de la ERPS o IPS
     */
    @JsonView(View.Entidad.class)
    @JsonProperty("nombre")
    @NotBlank
    @Column(name = "nombre_entidad", nullable = false, length = 50)
    private String nombreEntidad;
    
    /**
     * Tipo de Identificación de la entidad CC O NIT
     */   
    @JsonView(View.Entidad.class)
    @Column(name = "tipo_documento_id", nullable = false, length = 11)
    private Integer tipoDocumentoId;
    
    /**
     * Número de identificación de la entidad
     */
    @JsonView(View.Entidad.class)
    @NotBlank
    @Column(name = "nit", nullable = false, length = 50)
    private String nit;
    
    /**
     * Dirección de la entidad
     */
    @JsonView(View.Entidad.class)
    @NotBlank
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;
    
    /**
     * Teléfono de la entidad
     */
    @JsonView(View.Entidad.class)
    @NotBlank
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;
    
    /**
     * Indica si corresponde a una ERPS o IPS (0: Administrador ejp. ASD, 1: EPS, 2 IPS)
     */
    @JsonView(View.Entidad.class)
    @NotBlank
    @Column(name = "tipo_entidad", nullable = false, length = 1)
    private String tipoEntidad;
    
    /**
     * Operador de facturación electrónica con el que trabaja la entidad
     */
    @JsonView(View.Entidad.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operador_fe_id", referencedColumnName = "operador_fe_id", nullable = false, insertable = false, updatable = false)    
    private OperadorFe operadorFe;
    
    /**
     * Id Operador de facturación electrónica con el que trabaja la entidad
     */
    @JsonView(View.Entidad.class)
    @Column(name = "operador_fe_id", nullable = false, length = 20)
    private Long operadorFeId;
    /**
     * Ruta del menú
     */
    @JsonView(View.Entidad.class)
    @Column(name = "dominio", nullable = false, length = 255)
    private String dominio;
    
    /**
     * Tiempo de inactividad de la sesión
     */
    @JsonView(View.Entidad.class)
    @Column(name = "tiempo_inactividad", nullable = false, length = 5)
    private Long tiempoInactividad;
    
    /**
     * Tiempo definido para cambiar la sesión
     */
    @JsonView(View.Entidad.class)
    @Column(name = "vigencia_password", nullable = false, length = 5)
    private Long vigenciaPassword;
    
    /**
     * Numero de intentos fallidos permitidos para iniciar sesión
     */
    @JsonView(View.Entidad.class)
    @Column(name = "intentos_fallidos", nullable = false, length = 5)
    private Long intentosFallidos;
    
    /**
     * Registra la capacidad de almacenamiento de archivos de la entidad
     */
    @JsonView(View.Entidad.class)
    @Column(name = "almacenamiento", nullable = false, length = 20)
    private Long almacenamiento;
    
    /**
     * Registra la unidad de almacenamiento en disco (Mg, Gb, Tb, etc)
     */
    @JsonView(View.Entidad.class)
    @Column(name = "unidad", nullable = false, length = 3)
    private String unidad;
    
    /**
     * Cantidad de Usuarios a Registrar
     */
    @JsonView(View.Entidad.class)
    @Column(name = "numero_usuarios", nullable = false, length = 5)
    private Long numeroUsuarios;
    
    /**
     * Costo por cada usuario
     */
    @JsonView(View.Entidad.class)
    @Column(name = "costo_usuarios", nullable = false, length = 12)
    private Long costoUsuarios;
    
    /**
     * Documentos a procesar por entidad
     */
    @JsonView(View.Entidad.class)
    @Column(name = "documentos_procesados", nullable = false, length = 12)
    private Long documentosProcesados;
    
    /**
     * Costo por documento procesado
     */
    @JsonView(View.Entidad.class)
    @Column(name = "costo_documento", nullable = false, length = 20)
    private Long costoDocumento;
    
    /**
     * Indica si el registro esta activo o inactivo, en el sistema soloo se utilizarán registros activos
     */   
    @JsonView(View.Entidad.class)
    @Column(name = "estado", nullable = false, length = 1)
    private int estado;
    
    /**
     * Id del perfil creado para la entidad
     */
    @JsonView(View.Entidad.class)
    @Transient
    private Perfil perfil;
        
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
    public String getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(String entidadId) {
        this.entidadId = entidadId;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public int getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(int tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public OperadorFe getOperadorFe() {
        return operadorFe;
    }

    public void setOperadorFe(OperadorFe operadorFe) {
        this.operadorFe = operadorFe;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
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

    public Long getOperadorFeId() {
        return operadorFeId;
    }

    public void setOperadorFeId(Long operadorFeId) {
        this.operadorFeId = operadorFeId;
    }

    public Long getTiempoInactividad() {
        return tiempoInactividad;
    }

    public void setTiempoInactividad(Long tiempoInactividad) {
        this.tiempoInactividad = tiempoInactividad;
    }

    public Long getVigenciaPassword() {
        return vigenciaPassword;
    }

    public void setVigenciaPassword(Long vigenciaPassword) {
        this.vigenciaPassword = vigenciaPassword;
    }

    public Long getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(Long intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public Long getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(Long almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Long getNumeroUsuarios() {
        return numeroUsuarios;
    }

    public void setNumeroUsuarios(Long numeroUsuarios) {
        this.numeroUsuarios = numeroUsuarios;
    }

    public Long getCostoUsuarios() {
        return costoUsuarios;
    }

    public void setCostoUsuarios(Long costoUsuarios) {
        this.costoUsuarios = costoUsuarios;
    }

    public Long getDocumentosProcesados() {
        return documentosProcesados;
    }

    public void setDocumentosProcesados(Long documentosProcesados) {
        this.documentosProcesados = documentosProcesados;
    }

    public Long getCostoDocumento() {
        return costoDocumento;
    }

    public void setCostoDocumento(Long costoDocumento) {
        this.costoDocumento = costoDocumento;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    /**
     * Define Equals y Hash 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.entidadId);
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
        final Entidad other = (Entidad) obj;
        if (!Objects.equals(this.entidadId, other.entidadId)) {
            return false;
        }
        return true;
    }
    
}
