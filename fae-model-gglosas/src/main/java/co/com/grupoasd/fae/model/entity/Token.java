/*
* Archivo: Token.java
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

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad de negocio Token.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
@Entity
@Table(name = "auth_token")
public class Token implements Serializable {   
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del usuario.    
     * No se hace uso de la referencia utilizando JPA para 
     * evitar joins innecesarios.
     */
    @Id
    @Column(name = "usuario_id", nullable = false, length = 36)
    private Long usuarioId;
    /**
     * Token JWT.
     */
    @Column(name = "token", nullable = false, columnDefinition = "TEXT")
    private String token;
    /**
     * Fecha de fechaCreacion del token.
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    public Token() {
        
    }        

    public Token(Long usuarioId, String codigo, LocalDateTime creacion) {
        this.usuarioId = usuarioId;
        this.token = codigo;
        this.fechaCreacion = creacion;
    }    

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
        
}
