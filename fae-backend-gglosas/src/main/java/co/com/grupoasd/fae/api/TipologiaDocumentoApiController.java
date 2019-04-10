/*
* Archivo: TipologiaDocumentoApiController.java
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

import co.com.grupoasd.fae.model.entity.SoporteservMecpago;
import co.com.grupoasd.fae.model.entity.TipologiaDocumento;
import co.com.grupoasd.fae.model.repository.SoporteservMecpagoRepository;
import co.com.grupoasd.fae.model.repository.TipologiaDocumentoRepository;
import co.com.grupoasd.fae.model.entity.Entidad;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.EntidadRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.util.SeguridadUtil;
import co.com.grupoasd.fae.util.VariablesEstaticas;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @inheritDoc
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class TipologiaDocumentoApiController implements TipologiaDocumentoApi {

    /**
     * TipoDocumentoRepository.
     */
    private final TipologiaDocumentoRepository tipologiaDocumentoRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final SoporteservMecpagoRepository soporteservMecpagoRepository;
    private final EntidadRepository entidadRepository;

    /**
     * Constructor de clase.
     *
     * @param tipologiaDocumentoRepository TipologiaDocumentoRepository
     */
    @Autowired
    private TipologiaDocumentoApiController(final TipologiaDocumentoRepository tipologiaDocumentoRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final SoporteservMecpagoRepository soporteservMecpagoRepository,
            final EntidadRepository entidadRepository) {
        this.tipologiaDocumentoRepository = tipologiaDocumentoRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.soporteservMecpagoRepository = soporteservMecpagoRepository;
        this.entidadRepository = entidadRepository;
    }

    /**
     * Lista los tipos de documentos
     *
     * @return lista de TipoDocumento
     */
    @Override
    public ResponseEntity<List<TipologiaDocumento>> listar() {
        Iterable<TipologiaDocumento> iter = tipologiaDocumentoRepository.findAll();
        List<TipologiaDocumento> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un objeto TipoDocumento
     *
     * @param id del tipo de documento
     * @return objeto TipoDocumento
     */
    @Override
    public ResponseEntity<TipologiaDocumento> obtener(final @PathVariable("id") Long id) {
        Optional<TipologiaDocumento> tipo = tipologiaDocumentoRepository.findById(id);
        if (tipo.isPresent()) {
            return ResponseEntity.ok(tipo.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No existe recurso",
                    String.format("El recurso con id = %s no existe", id), 400));
        }
    }

    /**
     * Crea un tipo documento
     *
     * @param tipologiaDocumento Objeto tipo documento
     * @param req
     * @return objeto TipoDocumento
     */
    @Override
    public ResponseEntity<TipologiaDocumento> crear(@RequestBody @Validated TipologiaDocumento tipologiaDocumento, HttpServletRequest req) {
        Optional<Entidad> entidadOpt = entidadRepository.findById(tipologiaDocumento.getEntidadId());
        if (entidadOpt.isPresent()) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            tipologiaDocumento.setCreadoPor(user.getId());
            tipologiaDocumento.setFechaCreacion(Date.from(Instant.now()));
            return ResponseEntity.ok(tipologiaDocumentoRepository.save(tipologiaDocumento));
        }
        throw new NotFoundException(ErrorRs.get("No existe recurso",
                String.format("La entidad con el id %s no existe", tipologiaDocumento.getEntidadId()), 404));

    }

    /**
     * Modifica un TipoDocumento
     *
     * @param tipologiaDocumento objeto TipoDocumento
     * @param req
     * @return objeto TipoDocumento
     */
    @Override
    public ResponseEntity<TipologiaDocumento> cambiar(@RequestBody @Validated TipologiaDocumento tipologiaDocumento, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        if (tipologiaDocumento.getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
            inactivarDatos(tipologiaDocumento, true, req);
        }
        tipologiaDocumento.setModificadoPor(user.getId());
        tipologiaDocumento.setFechaModificacion(Date.from(Instant.now()));
        return ResponseEntity.ok(tipologiaDocumentoRepository.save(tipologiaDocumento));
    }

    @Override
    public ResponseEntity<GenericRs> eliminar(final @PathVariable("id") Long id, HttpServletRequest req) {
        Optional<TipologiaDocumento> optionalTipDoc = tipologiaDocumentoRepository.findById(id);
        if (optionalTipDoc.isPresent()) {
            if (inactivarDatos(optionalTipDoc.get(), false, req)) {
                throw new BadRequestException(ErrorRs.get("No se puede eliminar la tipologia de documento seleccionado",
                        "No es posible eliminar el elemento seleccionado ya que cuenta con información asociada.", 400));
            } else {
                tipologiaDocumentoRepository.delete(optionalTipDoc.get());
            }
            return ResponseEntity.ok(new GenericRs("200", "Eliminado"));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a eliminar", null, 404));
        }
    }

    private boolean inactivarDatos(TipologiaDocumento tipologiaDocumento, boolean actualizar,
            HttpServletRequest req) {
        boolean tieneDatos = false;
        List<SoporteservMecpago> soporteservMecpagos = soporteservMecpagoRepository.findByTipologiaDocumentoId(tipologiaDocumento.getId());
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        if (soporteservMecpagos != null && !soporteservMecpagos.isEmpty()) {
            for (SoporteservMecpago soporteservMecpago : soporteservMecpagos) {
                if (soporteservMecpago.getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                    tieneDatos = true;
                    if (actualizar) {
                        soporteservMecpago.setModificadoPor(user.getId());
                        soporteservMecpago.setFechaModificacion(Date.from(Instant.now()));
                        soporteservMecpago.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                        soporteservMecpagoRepository.save(soporteservMecpago);
                    } else {
                        throw new BadRequestException(ErrorRs.get("No se puede eliminar la tipologia de documento seleccionado",
                                "No es posible eliminar el elemento seleccionado ya que cuenta con información asociada.", 400));
                    }
                }
            }
        }

        return tieneDatos;
    }

}
