/*
* Archivo: CupsApiController.java
* Fecha: 19/12/2018
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

import co.com.grupoasd.fae.administracion.service.CupsService;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.dto.CupsDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cups;
import co.com.grupoasd.fae.model.repository.CupsRepository;
import co.com.grupoasd.fae.util.SeguridadUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Enpoint REST para la gestión de CUPS
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class CupsApiController implements CupsApi {

    /**
     * CupsRepository.
     */
    private final CupsRepository cupsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final CupsService cupsService;

    /**
     * Constructor de la clase.
     *
     * @param jwtTokenProvider JwtTokenProvider
     * @param usuarioRepository UsuarioRepository
     * @param cupsRepository CupsRepository
     */
    @Autowired
    private CupsApiController(final CupsRepository cupsRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final CupsService cupsService) {
        this.cupsRepository = cupsRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.cupsService = cupsService;
    }

    /**
     * Listar CUPS
     *
     * @return Listado de CUPS
     */
    @Override
    public ResponseEntity<List<Cups>> listar() {
        Iterable<Cups> iter = cupsRepository.findAll();
        List<Cups> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }
    
    /**
     * Listar CUPS por norma
     * @param norma norma asociada al CUPS
     * @return Listado de CUPS
     */
    @Override
    public ResponseEntity<List<Cups>> listarXNorma(@PathVariable("norma") String norma) {
        Iterable<Cups> iter = cupsRepository.findByNorma(norma);
        List<Cups> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de CUPS
     *
     * @param id Identificador de CUPS
     * @return Registro de CUPS
     */
    @Override
    public ResponseEntity<Cups> obtener(final @PathVariable("id") Long id) {
        Optional<Cups> entiOptional = cupsRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    /**
     * Crea un registro de CUPS
     *
     * @param cups Código de CUPS
     * @param req
     * @return Objeto CUPS
     */
    @Override
    public ResponseEntity<Cups> crear(@RequestBody @Validated Cups cups, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        return ResponseEntity.ok(cupsService.crear(cups, user.getId(), false));
    }

    /**
     * cambia un registro de CUPS
     *
     * @param cups Código de CUPS
     * @param req
     * @return Objeto Servicios por Mecanismo de pago
     */
    @Override
    public ResponseEntity<Cups> cambiar(@RequestBody @Validated Cups cups, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        return ResponseEntity.ok(cupsService.editar(cups, user.getId(), false));
    }

    /**
     * Listar Archivos de CUPS no cargados
     *
     * @return Listado de archivos Cups no cargados
     */
    @Override
    public ResponseEntity<List<String>> listarArchivosNoCargados() {
        return ResponseEntity.ok(cupsService.getArchivosNoCargados());
    }

    /**
     * Carga un archivo de CUPS y los guarda en el sistema
     *
     * @return Respuesta con el nombre del archivo cargado
     */
    @Override
    public GenericRs subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("norma") String norma, 
            HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        return cupsService.cargarArchivo(file, user.getId(), norma);
    }

    /**
     * Descarga un archivo de CUPS no cargados
     *
     * @param fileName nombre del archivo
     * @return Archivo Cups no cargado por errores
     */
    @Override
    public ResponseEntity<Resource> descargar(final @PathVariable("nombreArchivo") String fileName) {
        return ResponseEntity.ok(cupsService.descargarArchivoNoCargado(fileName));
    }

    /**
     * Lista un archivo de errores de cargue de CUPS
     *
     * @param fileName nombre del archivo
     * @return Lista Archivo de errores
     */
    @Override
    public ResponseEntity<List<CupsDtoError>> listarDataNoCargados(final @PathVariable("nombreArchivo") String fileName) {
        return ResponseEntity.ok(cupsService.listarArchivosNoCargados(fileName));
    }
}
