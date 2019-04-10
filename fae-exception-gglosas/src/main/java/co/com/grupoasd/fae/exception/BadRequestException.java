/*
* Archivo: BadRequestException.java
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
package co.com.grupoasd.fae.exception;

/**
 * Exception que representa un error de validacion en el mensaje de peticion.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public class BadRequestException extends AbstractException {
    
    /**
     * Constructor de clase.
     * @param errorRs 
     */
    public BadRequestException(final ErrorRs errorRs) {
        super(errorRs);
    }
}
