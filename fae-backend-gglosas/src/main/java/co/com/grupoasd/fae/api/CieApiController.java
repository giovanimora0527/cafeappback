/*
* Archivo: CieApiController.java
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

import co.com.grupoasd.fae.administracion.service.CieService;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.dto.CieDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cie;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.CieRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.util.SeguridadUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementacion de los servicios REST para CIE
 * @author GMORA
 */
@RestController
public class CieApiController implements CieApi  {
    
    /**
     * CupsRepository.
     */
    private final CieRepository cieRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final CieService cieService;
    
    /**
     * Constructor de la clase.
     *
     * @param jwtTokenProvider JwtTokenProvider
     * @param usuarioRepository UsuarioRepository
     * @param cieRepository CupsRepository
     */
    @Autowired
    private CieApiController(final CieRepository cieRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final CieService cieService) {
        this.cieRepository = cieRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.cieService = cieService;
    }

    @Override
    public ResponseEntity<List<Cie>> listar() {
        Iterable<Cie> iter = cieRepository.findAll();
        List<Cie> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<List<Cie>> listarXNorma(@PathVariable("norma") String norma) {
        Iterable<Cie> iter = cieRepository.findByNorma(Long.parseLong(norma));
        List<Cie> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<Cie> obtener(final @PathVariable("id") Long id) {
        Optional<Cie> entiOptional = cieRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    @Override
    public ResponseEntity<List<String>> listarArchivosNoCargados() {
        return ResponseEntity.ok(cieService.getArchivosNoCargados());
    }

    @Override
    public GenericRs subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("norma") String norma, 
            HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        return cieService.cargarArchivo(file, user.getId(), norma);
    }

    @Override
    public ResponseEntity<Resource> descargar(final @PathVariable("nombreArchivo") String fileName) {        
        return ResponseEntity.ok(cieService.descargarArchivoNoCargado(fileName));
    }

    @Override
    public ResponseEntity<List<CieDtoError>> listarDataNoCargados(final @PathVariable("nombreArchivo") String fileName) {
        return ResponseEntity.ok(cieService.listarArchivosNoCargados(fileName));
    }

    
}
