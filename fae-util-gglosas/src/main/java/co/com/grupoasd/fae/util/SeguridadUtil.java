package co.com.grupoasd.fae.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Clase utilitaria con funciones de seguridad.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
public class SeguridadUtil {
    
    /**
     * Constructor privado. Patrón singleton.
     */
    private SeguridadUtil() {
        
    }
    
    /**
     * Codifica la clave en SHA256 agregandole un salt para mayor seguridad.
     * @param identificacion
     * @param clave Clave
     * @return Clave codificada.
     */
    public static String codificarClave(String identificacion, String clave) {
        // Se utiliza el identificador del usuario como salt
        String salt = DigestUtils.sha256Hex(identificacion);
        clave = DigestUtils.sha256Hex(clave);
        return DigestUtils.sha256Hex(salt + clave);
    }
    
    public static String generarClave() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
    }
        
    public static String getSubject(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }
}
