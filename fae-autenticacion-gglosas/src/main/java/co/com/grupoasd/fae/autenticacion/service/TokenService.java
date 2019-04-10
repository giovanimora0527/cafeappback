/*
* Archivo: TokenService.java
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
package co.com.grupoasd.fae.autenticacion.service;

import co.com.grupoasd.fae.model.entity.Token;
import java.util.Optional;

/**
 * Servicios de negocio para gestion de token.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public interface TokenService {
        
    /**
     * Obtiene un token registrado para el usuario.
     * @param usuarioId Identificador del usuario.
     * @return Token registrado. Si no existe retorna un Optional vacio.
     */
    Optional<Token> obtener(Long usuarioId);
    /**
     * Eliminar un token.
     * @param usuarioId Identificador del usuario.
     */
    void eliminar(Long usuarioId);
    /**
     * Almacena un token.
     * @param token Token
     * @return Token almacenado.
     */
    Token guardar(Token token);
}
