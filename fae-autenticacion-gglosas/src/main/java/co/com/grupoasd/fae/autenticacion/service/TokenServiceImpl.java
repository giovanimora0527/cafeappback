/*
* Archivo: TokenServiceImpl.java
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
import co.com.grupoasd.fae.model.repository.TokenRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicios de negocio para gestion de token.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
@Service
@Transactional
public class TokenServiceImpl implements TokenService {
    
    private final TokenRepository tokenRepository;
    
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        
    }    
    
    @Override
    public Optional<Token> obtener(Long usuarioId) {
        return tokenRepository.findById(usuarioId);
    }
    
    @Override
    public void eliminar(Long usuarioId) {
        tokenRepository.deleteById(usuarioId);
    }
    
    @Override
    public Token guardar(Token token) {
         return tokenRepository.save(token);
    }
}
