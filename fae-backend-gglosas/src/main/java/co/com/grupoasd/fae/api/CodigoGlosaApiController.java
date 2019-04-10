/*
* Archivo: CodigoGlosaApiController.java
* Fecha: 26/09/2018
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

import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.model.entity.CodigoGlosa;
import co.com.grupoasd.fae.model.repository.CodigoGlosaRepository;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.util.SeguridadUtil;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @inheritDoc
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@RestController
public class CodigoGlosaApiController implements CodigoGlosaApi {

    /**
     * CodigoGlosaRepository.
     */
    private final CodigoGlosaRepository codigoGlosaRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase.
     *
     * @param codigoGlosaRepository CodigoGlosaRepository
     */
    @Autowired
    private CodigoGlosaApiController(final CodigoGlosaRepository codigoGlosaRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository) {
        this.codigoGlosaRepository = codigoGlosaRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Listar Códigos de Glosa
     *
     * @return Listado de Códigos de Glosa
     */
    @Override
    public ResponseEntity<List<CodigoGlosa>> listar() {
        Iterable<CodigoGlosa> iter = codigoGlosaRepository.findAll();
        List<CodigoGlosa> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de código de glosa
     *
     * @param id Identificador del registro
     * @return Registro de Codigo de Glosa
     */
    @Override
    public ResponseEntity<CodigoGlosa> obtener(final @PathVariable("id") Long id) {
        Optional<CodigoGlosa> tipo = codigoGlosaRepository.findById(id);
        if (tipo.isPresent()) {
            return ResponseEntity.ok(tipo.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 400));
        }
    }

    /**
     * Crea un registro de Código de Glosa
     *
     * @param codigoGlosa Código de Glosa
     * @param req
     * @return Código de Glosa
     */
    @Override
    public ResponseEntity<CodigoGlosa> crear(@RequestBody @Validated CodigoGlosa codigoGlosa, HttpServletRequest req) {
        CodigoGlosa glosa = codigoGlosaRepository.findByCodigoGlosa(codigoGlosa.getCodigoGlosa());
        if (glosa != null) {
            throw new BadRequestException(ErrorRs.get("Ya existe la Glosa",
                    "El código de glosa ya existe, por favor verifique e intente de nuevo.", 400));
        }
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        codigoGlosa.setCreadoPor(user.getId());
        codigoGlosa.setFechaCreacion(Date.from(Instant.now()));
        return ResponseEntity.ok(codigoGlosaRepository.save(codigoGlosa));
    }

    /**
     * Cambia un registro de Código de Glosa
     *
     * @param codigoGlosa Código de Glosa
     * @param req
     * @return Código de Glosa
     */
    @Override
    public ResponseEntity<CodigoGlosa> cambiar(@RequestBody @Validated CodigoGlosa codigoGlosa, HttpServletRequest req) {
        if (!codigoGlosa.getCodigoGlosa().isEmpty()) {
            Optional<CodigoGlosa> entiOptional = codigoGlosaRepository.findById(codigoGlosa.getId());
            if (entiOptional.isPresent()) {
                CodigoGlosa glosa = codigoGlosaRepository.findByCodigoGlosa(codigoGlosa.getCodigoGlosa());
                if (glosa != null) {
                    if (entiOptional.get().getCodigoGlosa().equals(glosa.getCodigoGlosa())
                            && !glosa.getId().equals(entiOptional.get().getId())) {
                        throw new BadRequestException(ErrorRs.get("Ya existe la Glosa",
                                "El código de glosa ya existe, por favor verifique e intente de nuevo.", 400));
                    }
                }
                String token = jwtTokenProvider.resolveToken(req);
                Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
                codigoGlosa.setModificadoPor(user.getId());
                codigoGlosa.setFechaModificado(Date.from(Instant.now()));
                codigoGlosa.setCreadoPor(entiOptional.get().getCreadoPor());
                codigoGlosa.setFechaCreacion(entiOptional.get().getFechaCreacion());
                return ResponseEntity.ok(codigoGlosaRepository.save(codigoGlosa));
            }
        }
        throw new NotFoundException(ErrorRs.get("No existe el Código de Glosa a modificar", null, 404));
    }
}
