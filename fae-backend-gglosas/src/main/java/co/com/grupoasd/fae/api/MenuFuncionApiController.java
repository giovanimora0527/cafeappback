/*
* Archivo: FuncionApiController.java
* Fecha: 30/10/2018
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

import co.com.grupoasd.fae.model.entity.Menu;
import co.com.grupoasd.fae.model.entity.MenuFuncion;
import co.com.grupoasd.fae.model.entity.MenuFuncionIdentity;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.MenuFuncionRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.autenticacion.service.MenuFuncionService;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
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
 * Enpoint REST para gestion de Funcion.
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class MenuFuncionApiController implements MenuFuncionApi {

    /**
     * MenuFuncionRepository.
     */
    private final MenuFuncionRepository menuFuncionRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final MenuFuncionService menuFuncionService;

    /**
     * Constructor de la clase.
     *
     * @param menuRepository MenuFuncionRepository
     */
    @Autowired
    private MenuFuncionApiController(final MenuFuncionRepository menuFuncionRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final MenuFuncionService menuFuncionService) {
        this.menuFuncionRepository = menuFuncionRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.menuFuncionService = menuFuncionService;
    }

    /**
     * Listar Funcion
     *
     * @return Listado de Funcion
     */
    @Override
    public ResponseEntity<List<MenuFuncion>> listar() {
        Iterable<MenuFuncion> iter = menuFuncionRepository.findAll();
        List<MenuFuncion> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de una Funcion
     *
     * @param menuCodigo Identificador de la MenuFuncion
     * @param funcionCodigo Identificador de la MenuFuncion
     * @return Registro de Funcion
     */
    @Override
    public ResponseEntity<MenuFuncion> obtener(final @PathVariable("menuCodigo") String menuCodigo,
            final @PathVariable("funcionCodigo") String funcionCodigo) {
        MenuFuncionIdentity funcionIdentity = new MenuFuncionIdentity();
        funcionIdentity.setMenuCodigo(menuCodigo);
        funcionIdentity.setFuncionCodigo(funcionCodigo);
        Optional<MenuFuncion> funcionOptional = menuFuncionRepository.findById(funcionIdentity);
        if (funcionOptional.isPresent()) {
            return ResponseEntity.ok(funcionOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", funcionIdentity), 400));
        }
    }

    /**
     * Crea un registro de MenuFuncion
     *
     * @param menuFuncion Código de la MenuFuncion
     * @return Objeto MenuFuncion
     */
    @Override
    public ResponseEntity<MenuFuncion> crear(@RequestBody @Validated MenuFuncion menuFuncion, HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        return ResponseEntity.ok(menuFuncionService.crear(menuFuncion, user.getId()));
    }

    /**
     * cambia un registro de una MenuFuncion
     *
     * @param menuFuncion Código de la MenuFuncion
     * @return Objeto MenuFuncion
     */
    @Override
    public ResponseEntity<MenuFuncion> cambiar(@RequestBody @Validated MenuFuncion menuFuncion, HttpServletRequest req) {
        if (menuFuncion.getMenuFuncionIdentity() != null) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            menuFuncion.setModificadoPor(user.getId());
            menuFuncion.setFechaModificacion(Date.from(Instant.now()));
            return ResponseEntity.ok(menuFuncionRepository.save(menuFuncion));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a Cambiar", null, 400));
        }
    }

    /**
     * Obtiene una lista de acciones asociadas a un menú
     *
     * @param menuCodigo identificador del menu
     * @return lista de Funciones(acciones)
     */
    @Override
    public ResponseEntity<List<MenuFuncion>> obtenerFuncionMenu(final @PathVariable("menuCodigo") String menuCodigo) {
        Menu menu = new Menu();
        menu.setMenuCodigo(menuCodigo);
        List<MenuFuncion> menuFuncions = menuFuncionRepository.findByMenuCodigo(menu);
        if (menuFuncions != null && !menuFuncions.isEmpty()) {
            return ResponseEntity.ok(menuFuncions);
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", menuCodigo), 400));
        }
    }
}
