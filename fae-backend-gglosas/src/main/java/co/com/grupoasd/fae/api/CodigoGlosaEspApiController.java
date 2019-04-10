/*
* Archivo: CodigoGlosaEspApiController.java
* Fecha: 12/12/2018
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
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.CodigoGlosaEspecifica;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.CodigoGlosaEspRepository;
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
 *
 * @author josem.moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class CodigoGlosaEspApiController implements CodigoGlosaEspApi {

    /**
     * CodigoGlosaRepository.
     */
    private final CodigoGlosaEspRepository codigoGlosaEspRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase.
     *
     * @param codigoGlosaRepository CodigoGlosaRepository
     */
    @Autowired
    private CodigoGlosaEspApiController(final CodigoGlosaEspRepository codigoGlosaEspRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository) {
        this.codigoGlosaEspRepository = codigoGlosaEspRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Listar Códigos de Glosa esp
     *
     * @return Listado de Códigos de Glosa esp
     */
    @Override
    public ResponseEntity<List<CodigoGlosaEspecifica>> listar() {
        Iterable<CodigoGlosaEspecifica> iter = codigoGlosaEspRepository.findAll();
        List<CodigoGlosaEspecifica> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de código de glosa esp
     *
     * @param id Identificador del registro
     * @return Registro de Codigo de Glosa esp
     */
    @Override
    public ResponseEntity<CodigoGlosaEspecifica> obtener(final @PathVariable("id") Long id) {
        Optional<CodigoGlosaEspecifica> tipo = codigoGlosaEspRepository.findById(id);
        if (tipo.isPresent()) {
            return ResponseEntity.ok(tipo.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 400));
        }
    }

    /**
     * Crea un registro de Código de Glosa esp
     *
     * @param codigoGlosaEspecifica Código de Glosa esp
     * @param req
     * @return Código de Glosa esp
     */
    @Override
    public ResponseEntity<CodigoGlosaEspecifica> crear(@RequestBody @Validated CodigoGlosaEspecifica codigoGlosaEspecifica, HttpServletRequest req) {
        CodigoGlosaEspecifica glosa = codigoGlosaEspRepository.findByCodigoGlosaesp(codigoGlosaEspecifica.getCodigoGlosaesp());
        if (glosa != null) {
            throw new BadRequestException(ErrorRs.get("Ya existe la Glosa",
                    "El código de glosa ya existe, por favor verifique e intente de nuevo.", 400));
        }
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        codigoGlosaEspecifica.setCreadoPor(user.getId());
        codigoGlosaEspecifica.setFechaCreacion(Date.from(Instant.now()));
        return ResponseEntity.ok(codigoGlosaEspRepository.save(codigoGlosaEspecifica));
    }

    /**
     * Cambia un registro de Código de Glosa esp
     *
     * @param codigoGlosaEspecifica Código de Glosa esp
     * @param req
     * @return Código de Glosa esp
     */
    @Override
    public ResponseEntity<CodigoGlosaEspecifica> cambiar(@RequestBody @Validated CodigoGlosaEspecifica codigoGlosaEspecifica, HttpServletRequest req) {
        if (codigoGlosaEspecifica.getId() != null) {
            Optional<CodigoGlosaEspecifica> entiOptional = codigoGlosaEspRepository.findById(codigoGlosaEspecifica.getId());
            if (entiOptional.isPresent()) {
                CodigoGlosaEspecifica glosa = codigoGlosaEspRepository.findByCodigoGlosaesp(codigoGlosaEspecifica.getCodigoGlosaesp());
                if (glosa != null) {
                    if (entiOptional.get().getCodigoGlosaesp().equals(glosa.getCodigoGlosaesp())
                            && !glosa.getId().equals(entiOptional.get().getId())) {
                        throw new BadRequestException(ErrorRs.get("Ya existe la Glosa",
                                "El código de glosa ya existe, por favor verifique e intente de nuevo.", 400));
                    }
                }
                String token = jwtTokenProvider.resolveToken(req);
                Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
                codigoGlosaEspecifica.setModificadoPor(user.getId());
                codigoGlosaEspecifica.setFechaModificado(Date.from(Instant.now()));
                codigoGlosaEspecifica.setCreadoPor(entiOptional.get().getCreadoPor());
                codigoGlosaEspecifica.setFechaCreacion(entiOptional.get().getFechaCreacion());
                return ResponseEntity.ok(codigoGlosaEspRepository.save(codigoGlosaEspecifica));
            }
        }
        throw new NotFoundException(ErrorRs.get("No existe el Código de Glosa a modificar", null, 404));
    }

    /**
     * Listar Códigos de Glosa esp por codigo glosa
     *
     * @return Listado de Códigos de Glosa esp por codigoGlosaId
     */
    @Override
    public ResponseEntity<List<CodigoGlosaEspecifica>> listarXCodigoGlosa(final @PathVariable("codigoGlosaId") Long codigoGlosaId) {
        Iterable<CodigoGlosaEspecifica> iter = codigoGlosaEspRepository.findByCodigoGlosaId(codigoGlosaId);
        List<CodigoGlosaEspecifica> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<GenericRs> eliminar(final @PathVariable("codigoGlosaEspId") Long codigoGlosaEspId, HttpServletRequest req) {
        Optional<CodigoGlosaEspecifica> codigoGloEspOpt = codigoGlosaEspRepository.findById(codigoGlosaEspId);
        if (codigoGloEspOpt.isPresent()) {
            /*if (resolverDependencias(menuOpt.get(), false, true, req)) {
                throw new BadRequestException(ErrorRs.get("No se puede eliminar la glosa seleccionada",
                        "No se puede eliminar la glosa seleccionada, 
                        ya que cuenta con información asociada, 
                        por favor inactive el elemento desde la opción Editar.", 400));
            } else {*/
            codigoGlosaEspRepository.delete(codigoGloEspOpt.get());
            //}
            return ResponseEntity.ok(new GenericRs("200",
                    String.format("La glosa %s se eliminó satisfactoriamente", codigoGloEspOpt.get().getDetalleGlosaEspecifica())));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a eliminar", null, 400));
        }
    }
}
