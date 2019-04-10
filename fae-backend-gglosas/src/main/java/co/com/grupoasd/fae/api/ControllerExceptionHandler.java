/*
 * Archivo: ControllerExceptionHandler.java
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
package co.com.grupoasd.fae.api;

import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Interceptor de errores para presentarlos como respuesta de tipo JSON.
 *
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    /**
     * Codigo de error por defecto.
     */
    private static final int CODIGO_ERROR = 500;

    /**
     * Manejo de las excepciones de tipo NotFoundException.
     * @param e NotFoundException
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorRs> getNotFoundRquest(final NotFoundException e) {
        ResponseEntity<ErrorRs> responseEntity = new ResponseEntity<>(e.getErrorRs(), 
                HttpStatus.NOT_FOUND);
        log.info(String.format("%s: %s", e.getErrorRs().getError(), e.getErrorRs().getMessage()));
        return responseEntity;
    }

    /**
     * Manajo de las excepciones de tipo BadRequestException.
     * @param e BadRequestException
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorRs> getBadRequestException(final BadRequestException e) {
        ResponseEntity<ErrorRs> responseEntity = new ResponseEntity<>(e.getErrorRs(), 
                HttpStatus.BAD_REQUEST);
        log.info(String.format("%s: %s", e.getErrorRs().getError(), e.getErrorRs().getMessage()));
        return responseEntity;
    }

    /**
     * Manajo de las excepciones de tipo Exception.
     * @param e Exception
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorRs> getGeneralException(final Exception e) {
        ErrorRs error = ErrorRs.get("Error general", "Error general por favor intente mas tarde", 
                CODIGO_ERROR);
        ResponseEntity<ErrorRs> responseEntity = new ResponseEntity<>(error,
                HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(e.getMessage(), e);
        return responseEntity;
    }

    /**
     * Manajo de las excepciones de tipo NotFoundException.
     * @param e NotFoundException
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers, 
            final HttpStatus status, final WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();

        if (fieldErrors != null && fieldErrors.size() > 0) {
            errorMessage.append(fieldErrors.get(0).getDefaultMessage());
        } else {
            errorMessage.append("Ocurrio un error al procesar la solicitud. "
                    + "Por favor verifique e intente de nuevo.");
        }
        ErrorRs errorInfo = ErrorRs.get(HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                errorMessage.toString(), HttpStatus.BAD_REQUEST.value());
        log.info(ex.getMessage(), ex);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

}
