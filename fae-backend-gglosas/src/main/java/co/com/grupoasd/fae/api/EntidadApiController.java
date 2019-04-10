/*
* Archivo: EntidadApiController.java
* Fecha: 22/10/2018
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

import co.com.grupoasd.fae.administracion.service.EntidadService;
import co.com.grupoasd.fae.model.entity.Entidad;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.EntidadRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.util.SeguridadUtil;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestion de Entidades.
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class EntidadApiController implements EntidadApi {

    /**
     * EntidadRepository.
     */
    private final EntidadRepository entidadRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final EntidadService entidadService;

    /**
     * Constructor de la clase.
     *
     * @param entidadRepository EntidadRepository
     */
    @Autowired
    private EntidadApiController(final EntidadRepository entidadRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final EntidadService entidadService) {
        this.entidadRepository = entidadRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.entidadService = entidadService;
    }

    /**
     * Listar entidades
     *
     * @return Listado de entidades
     */
    @Override
    public ResponseEntity<List<Entidad>> listar() {
        Iterable<Entidad> iter = entidadRepository.findAll();
        List<Entidad> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de una entidad
     *
     * @param id Identificador de la entidad
     * @return Registro de entidades
     */
    @Override
    public ResponseEntity<Entidad> obtener(final @PathVariable("id") String id) {
        Optional<Entidad> entiOptional = entidadRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    /**
     * Crea un registro de Entidad
     *
     * @param entidad Código de la entidad
     * @param req
     * @return Objeto Entidad
     */
    @Override
    public ResponseEntity<Entidad> crear(@RequestBody @Validated Entidad entidad, HttpServletRequest req) {
        Entidad entidadExistente = entidadRepository.findByTipoDocumentoIdAndNit(entidad.getTipoDocumentoId(), entidad.getNit());
        if (entidadExistente != null) {
            throw new BadRequestException(ErrorRs.get("Ya existe la entidad",
                    "La entidad que intenta crear la existe", 400));
        }
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        entidad.setEntidadId(UUID.randomUUID().toString());
        entidad.setCreadoPor(user.getId());
        entidad.setFechaCreacion(Date.from(Instant.now()));
        return ResponseEntity.ok(entidadService.crearEntidad(entidad, user.getId()));
    }

    /**
     * cambia un registro de una entidad
     *
     * @param entidad Código de la entidad
     * @param req
     * @return Objeto Entidad
     */
    @Override
    public ResponseEntity<Entidad> cambiar(@RequestBody @Validated Entidad entidad, HttpServletRequest req) {
        if (!entidad.getEntidadId().isEmpty()) {
            Optional<Entidad> entiOptional = entidadRepository.findById(entidad.getEntidadId());
            if (entiOptional.isPresent()) {
                String token = jwtTokenProvider.resolveToken(req);
                Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
                entidad.setModificadoPor(user.getId());
                entidad.setFechaModificacion(Date.from(Instant.now()));
                entidad.setCreadoPor(entiOptional.get().getCreadoPor());
                entidad.setFechaCreacion(entiOptional.get().getFechaCreacion());
                return ResponseEntity.ok(entidadRepository.save(entidad));
            }
        }
        throw new NotFoundException(ErrorRs.get("No existe la entidad a modificar", null, 404));
    }

    /**
     * Obtiene un registro de una entidad
     *
     * @param nombre Nombre de la entidad
     * @return Registro de entidades
     */
    @Override
    public ResponseEntity<Entidad> obtenerXNombre(final @PathVariable("nombre") String nombre) {
        return ResponseEntity.ok(entidadRepository.findByNombreEntidad(nombre));
    }
}
