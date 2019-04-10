/*
* Archivo: TipoDocumentoApiController.java
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

import co.com.grupoasd.fae.model.entity.TipoDocumento;
import co.com.grupoasd.fae.model.repository.TipoDocumentoRepository;
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
public class TipoDocumentoApiController implements TipoDocumentoApi {

    /**
     * TipoDocumentoRepository.
     */
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final EntidadRepository entidadRepository;

    /**
     * Constructor de clase.
     *
     * @param departamentoRepository TipoDocumentoRepository
     */
    @Autowired
    private TipoDocumentoApiController(final TipoDocumentoRepository departamentoRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final EntidadRepository entidadRepository) {
        this.tipoDocumentoRepository = departamentoRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.entidadRepository = entidadRepository;
    }

    /**
     * Lista los tipos de documentos
     *
     * @return lista de TipoDocumento
     */
    @Override
    public ResponseEntity<List<TipoDocumento>> listar() {
        Iterable<TipoDocumento> iter = tipoDocumentoRepository.findAll();
        List<TipoDocumento> res = new ArrayList<>();
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
    public ResponseEntity<TipoDocumento> obtener(final @PathVariable("id") Long id) {
        Optional<TipoDocumento> tipo = tipoDocumentoRepository.findById(id.intValue());
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
     * @param tipoDocumento Objeto tipo documento
     * @param req
     * @return objeto TipoDocumento
     */
    @Override
    public ResponseEntity<TipoDocumento> crear(@RequestBody @Validated TipoDocumento tipoDocumento, HttpServletRequest req) {
        TipoDocumento tipoDoc = tipoDocumentoRepository.findByCodigoTipoDocumento(tipoDocumento.getCodigoTipoDocumento());
        if (tipoDoc == null) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            tipoDocumento.setCreadoPor(user.getId());
            tipoDocumento.setFechaCreacion(Date.from(Instant.now()));
            return ResponseEntity.ok(tipoDocumentoRepository.save(tipoDocumento));
        }
        throw new BadRequestException(ErrorRs.get("No se puede crear el tipo documento",
                "El codigo del tipo de documento ya existe, por favor verifique e intente de nuevo", 400));
    }

    /**
     * Modifica un TipoDocumento
     *
     * @param tipoDocumento objeto TipoDocumento
     * @param req
     * @return objeto TipoDocumento
     */
    @Override
    public ResponseEntity<TipoDocumento> cambiar(@RequestBody @Validated TipoDocumento tipoDocumento, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        if (tipoDocumento.getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
            inactivarDatos(tipoDocumento, true, req);
        }
        tipoDocumento.setModificadoPor(user.getId());
        tipoDocumento.setFechaModificacion(Date.from(Instant.now()));
        return ResponseEntity.ok(tipoDocumentoRepository.save(tipoDocumento));
    }

    @Override
    public ResponseEntity<GenericRs> eliminar(final @PathVariable("id") Long id, HttpServletRequest req) {
        Optional<TipoDocumento> optionalTipDoc = tipoDocumentoRepository.findById(id.intValue());
        if (optionalTipDoc.isPresent()) {
            if (inactivarDatos(optionalTipDoc.get(), false, req)) {
                throw new BadRequestException(ErrorRs.get("No se puede eliminar el tipo de documento seleccionado",
                        "No es posible eliminar el elemento seleccionado ya que cuenta con información asociada.", 400));
            } else {
                tipoDocumentoRepository.delete(optionalTipDoc.get());
            }
            return ResponseEntity.ok(new GenericRs("200", "Eliminado"));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a eliminar", null, 404));
        }
    }

    private boolean inactivarDatos(TipoDocumento tipoDocumento, boolean actualizar,
            HttpServletRequest req) {
        boolean tieneDatos = false;
        List<Entidad> entidadList = entidadRepository.findByTipoDocumentoId(tipoDocumento.getId());
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        if (entidadList != null && !entidadList.isEmpty()) {
            for (Entidad entidad : entidadList) {
                if (entidad.getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                    tieneDatos = true;
                    if (actualizar) {
                        entidad.setModificadoPor(user.getId());
                        entidad.setFechaModificacion(Date.from(Instant.now()));
                        entidad.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                        entidadRepository.save(entidad);
                    } else {
                        throw new BadRequestException(ErrorRs.get("No se puede eliminar el tipo de documento seleccionado",
                                "No es posible eliminar el elemento seleccionado ya que cuenta con información asociada.", 400));
                    }
                }
            }
        }

        List<Usuario> usuarios = usuarioRepository.findByTipoDocumentoId(tipoDocumento.getId());
        if (usuarios != null && !usuarios.isEmpty()) {
            tieneDatos = true;
            for (Usuario usuario : usuarios) {
                if (usuario.getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                    if (actualizar) {
                        usuario.setModificadoPor(user.getId());
                        usuario.setFechaModificacion(Date.from(Instant.now()));
                        usuario.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                        usuarioRepository.save(usuario);
                    } else {
                        throw new BadRequestException(ErrorRs.get("No se puede eliminar el tipo de documento seleccionado",
                                "No es posible eliminar el elemento seleccionado ya que cuenta con información asociada.", 400));
                    }
                }
            }
        }

        return tieneDatos;
    }

}
