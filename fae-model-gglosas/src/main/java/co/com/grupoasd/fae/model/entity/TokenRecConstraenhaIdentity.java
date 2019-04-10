/*
* Archivo: TokenRecConstraenhaIdentity.java
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
import java.util.Objects;

/**
 * Llave Token contraseña.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
public class TokenRecConstraenhaIdentity implements Serializable {   
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del usuario.    
     * No se hace uso de la referencia utilizando JPA para 
     * evitar joins innecesarios.
     */
    private Long usuarioId;
    /**
     * Tokens.
     */
    private String token;
   

    public TokenRecConstraenhaIdentity() {
        
    }        

    public TokenRecConstraenhaIdentity(Long usuarioId, String codigo, LocalDateTime creacion) {
        this.usuarioId = usuarioId;
        this.token = codigo;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.usuarioId);
        hash = 97 * hash + Objects.hashCode(this.token);
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
        final TokenRecConstraenhaIdentity other = (TokenRecConstraenhaIdentity) obj;
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        if (!Objects.equals(this.usuarioId, other.usuarioId)) {
            return false;
        }
        return true;
    }
    
    
    
}
