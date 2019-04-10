/*
* Archivo: View.java
* Fecha: 20/02/2019
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
package co.com.grupoasd.fae.util;

/**
 *
 * @author jose
 * @author gmora
 */
public class View {

    public interface Menu {
    }

    public interface Perfil {
    }

    public interface Entidad extends OperadorFe {
    }

    public interface Usuario {
    }

    public interface UsuarioPerfil {
    }

    public interface MenuAcciones {
    }

    public interface InternalAutenticarRs extends MenuAcciones {
    }

    public interface TipoDocumento {
    }

    public interface TipologiaDocumento extends Entidad {
    }

    public interface SoporteservMecpago extends TipologiaDocumento{
    }

    public interface OperadorFe {
    }
    
    public interface ServicioMecpago {
    }
    
    public interface CodigoGlosa {
    }
    
    public interface CodigoGlosaEsp {
    }
    
    public interface MecanismoPago {
    }
    
    public interface Cups {
    }
    
    public interface Norma {
    }
    
    public interface Cie {
    }
    
    public interface Cum {
    }
}
