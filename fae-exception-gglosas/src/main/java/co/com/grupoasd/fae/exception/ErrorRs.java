/*
* Archivo: ErrorRs.java
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

import java.time.LocalDateTime;

/**
 * Respuesta de error de tipo generica.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public final class ErrorRs {
    /**
     * Mensaje de error.
     */
    private final String error;
    /**
     * Descripcion del error.
     */
    private final String message;
    /**
     * Codigo de status del error.
     */
    private final int status;
    /**
     * Fecha del error.
     */
    private final LocalDateTime date;

    /**
     * Constructor de clase.
     * @param error Mensaje de error.
     * @param message Descripcion del error.
     * @param status Status de error.
     * @param date Fecha del error.
     */
    public ErrorRs(final String error, final String message, final int status, 
            final LocalDateTime date) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.date = date;
    }

    
    
    /**
     * Obtiene nuevo error.
     * @param error String Nombre error HTTP
     * @param message String Mensaje personalizado del error HTTP
     * @param status int Codigo error HTTP
     * @return ErrorRs
     */
    public static ErrorRs get(final String error, final String message, final int status) {
        ErrorRs errorRs = new ErrorRs(error, message, status, LocalDateTime.now());        
        return errorRs;
    }

    /**
     * Retorna el mensaje de error.
     * @return Mensaje de error
     */
    public String getError() {
        return error;
    }

    /**
     * Retorna la descripcion del error.
     * @return Descripcion del error
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retorna el status del error.
     * @return Status de error.
     */
    public int getStatus() {
        return status;
    }
   
    /**
     * Retorna fecha del error.
     * @return Fecha del error.
     */
    public LocalDateTime getDate() {
        return date;
    }
    
}
