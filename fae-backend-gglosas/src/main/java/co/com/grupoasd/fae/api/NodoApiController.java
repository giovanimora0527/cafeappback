/*
* Archivo: NodoApiController.java
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
package co.com.grupoasd.fae.api;

import co.com.grupoasd.fae.autenticacion.model.Nodo;
import com.jcabi.manifests.Manifests;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @inheritDoc
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
@RestController
public class NodoApiController implements NodoApi {
    
    /**
     * Version.
     */
    @Value("${core.version}")
    private String version;
    
    /**
     * Identificador del nodo.
     */
    @Value("${core.nodo}")
    private String nodoId;
    
    /**
     * DataSource
     */
    @Autowired
    private DataSource dataSource;
    
    private static final String LLAVE_BUILD = "Implementation-Build";
    
    @Override
    public ResponseEntity nodo() {        
        Nodo nodo = new Nodo();
        nodo.setVersion(version);
        nodo.setNodo(nodoId);
        nodo.setBuild("");
        if (Manifests.exists(LLAVE_BUILD)) {
            nodo.setBuild(Manifests.read(LLAVE_BUILD));
        }
        try (Connection con = dataSource.getConnection()) {
           try (PreparedStatement st = con.prepareStatement("show variables like 'server_id'")) {
                try (ResultSet rs = st.executeQuery()) {
                    rs.next();
                    nodo.setDbms(rs.getString("Value"));
                }
           }            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return ResponseEntity.ok(nodo);
    }
}
