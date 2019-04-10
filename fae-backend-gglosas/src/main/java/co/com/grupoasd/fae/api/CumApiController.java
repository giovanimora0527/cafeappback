/*
* Archivo: CumApi.java
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
package co.com.grupoasd.fae.api;

import co.com.grupoasd.fae.administracion.service.CumService;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.dto.CumDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cum;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.CumRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.util.SeguridadUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementacion de los servicios REST para CUM
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class CumApiController implements CumApi {

    /**
     * CumRepository.
     */
    private final CumRepository cumRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final CumService cumService;

    /**
     * Constructor de la clase.
     *
     * @param jwtTokenProvider JwtTokenProvider
     * @param usuarioRepository UsuarioRepository
     * @param cumRepository CupsRepository
     */
    @Autowired
    private CumApiController(final CumRepository cumRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final CumService cumService) {
        this.cumRepository = cumRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.cumService = cumService;
    }

    @Override
    public ResponseEntity<List<Cum>> listar() {
        Iterable<Cum> iter = cumRepository.findAll();
        List<Cum> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<Cum> obtener(final @PathVariable("id") Long id) {
        Optional<Cum> entiOptional = cumRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    @Override
    public ResponseEntity<List<String>> listarArchivosNoCargados() {
        return ResponseEntity.ok(cumService.getArchivosNoCargados());
    }

    @Override
    public GenericRs subirArchivo(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        return cumService.cargarArchivo(file, user.getId());
    }

    @Override
    public ResponseEntity<Resource> descargar(final @PathVariable("nombreArchivo") String fileName) {
        return ResponseEntity.ok(cumService.descargarArchivoNoCargado(fileName));
    }

    @Override
    public ResponseEntity<List<CumDtoError>> listarDataNoCargados(final @PathVariable("nombreArchivo") String fileName) {
        return ResponseEntity.ok(cumService.listarArchivosNoCargados(fileName));
    }

}
