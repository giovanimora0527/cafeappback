/*
* Archivo: JwtTokenProvider.java
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
package co.com.grupoasd.fae.autenticacion.security;

import co.com.grupoasd.fae.model.entity.Perfil;
import co.com.grupoasd.fae.model.entity.Token;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.PerfilRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.service.TokenService;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.util.DateUtil;
import co.com.grupoasd.fae.util.SesionUsuario;
import co.com.grupoasd.fae.util.VariablesEstaticas;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Proveedor de autenticacion por token JWT.
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.issuer:fae}")
    private String ISSUER;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String SIGNING_KEY;

    private static final String LLAVE_TIMESTAMP = "tsp";
    private static final String LLAVE_ENTIDAD = "ent";
    private static final String LLAVE_NOMBRE = "nom";
    private static final String LLAVE_TIPO_PERFIL = "tip";
    private static final String LLAVE_ID = "id";
    private static final String LLAVE_PER_ID = "perid";

    private final UserDetailsImpl userDetailsImpl;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public JwtTokenProvider(UserDetailsImpl userDetailsImpl, TokenService tokenService,
            UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.userDetailsImpl = userDetailsImpl;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    /**
     * Genera un token de tipo JWT con Issuer el identificador del usuario y con
     * un claim personalizado llamado opt que contiene una cadena con todas las
     * opciones del sistema permitidas por el perfil del usuario separadas por
     * coma.
     *
     * @param usuario Usuario
     * @return Cadena con el token de tipo JWT.
     */
    public String createToken(Usuario usuario) {
        try {
            Optional<Perfil> perfil = perfilRepository.findById(usuario.getPerfilId());
            if (perfil.isPresent()) {
                Algorithm algorithm = Algorithm.HMAC256(SIGNING_KEY);
                String token = JWT.create()
                        .withIssuer(ISSUER)
                        .withSubject(usuario.getEmail())
                        .withClaim(LLAVE_TIMESTAMP, DateUtil.now().getTime())
                        .withClaim(LLAVE_ENTIDAD, usuario.getEntidadId())
                        .withClaim(LLAVE_NOMBRE, usuario.getNombreUsuario())
                        .withClaim(LLAVE_ID, usuario.getId())
                        .withClaim(LLAVE_PER_ID, perfil.get().getId())
                        .withClaim(LLAVE_TIPO_PERFIL, perfil.get().getEsSuper() != 0
                                ? "S" : "A")
                        .sign(algorithm);
                return token;
            } else {
                throw new NotFoundException(ErrorRs.get("No existe el perfil",
                        "El perfil no existe en el sistema, consulte con soporte", 404));
            }
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails us = userDetailsImpl.loadUserByUsername(getSubject(token));
        return new UsernamePasswordAuthenticationToken(us, "", us.getAuthorities());
    }

    private String getSubject(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token, SesionUsuario sessionUsuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SIGNING_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            verifier.verify(token);
            // Se valida que el token exista en BD para detectar si se hizo un ingreso 
            // desde otro dispositivo.
            Usuario us = usuarioRepository.findByEmail(getSubject(token));
            Optional<Token> optToken = tokenService.obtener(us.getId());
            if (optToken.isPresent()) {
                if (optToken.get().getToken().equals(token)) {
                    if (us.getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                        return true;
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        } catch (JWTVerificationException exception) {
            return false;
        }
        return false;
    }

    /**
     * Genera un token de autenticación para el usuario. Si ya existe un token
     * para el usuario, se elimina el token existente y se crea el nuevo.
     *
     * @param usuario Usuario del sistema.
     * @return Token generado.
     */
    @Transactional
    public String generar(Usuario usuario) {
        String strToken = createToken(usuario);
        Token token;
        Optional<Token> tokenActual = tokenService.obtener(usuario.getId());
        if (tokenActual.isPresent()) {
            // Si existia un token se le cambia el codigo para que el dispositivo
            // donde se encontraba logueado lo desloguee permitiendo solo que un usuario
            // este logueado al mismo tiempo en un dispositivo.
            token = tokenActual.get();
            token.setToken(strToken);
            token.setFechaCreacion(DateUtil.nowDateTime());
        } else {
            token = new Token(usuario.getId(), strToken, DateUtil.nowDateTime());
        }
        tokenService.guardar(token);
        return strToken;
    }

}
