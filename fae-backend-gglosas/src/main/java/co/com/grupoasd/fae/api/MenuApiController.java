/*
* Archivo: MenuApiController.java
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

import co.com.grupoasd.fae.model.entity.Menu;
import co.com.grupoasd.fae.model.entity.MenuFuncion;
import co.com.grupoasd.fae.model.entity.PerfilPermiso;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.MenuFuncionRepository;
import co.com.grupoasd.fae.model.repository.MenuRepository;
import co.com.grupoasd.fae.model.repository.PerfilPermisoRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.autenticacion.util.UtilMenu;
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
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestion de Menus.
 *
 * @author Javier Mosquera Díaz jmosquera@grupoasd.com.co
 */
@RestController
public class MenuApiController implements MenuApi {

    /**
     * MenúRepository.
     */
    private final MenuRepository menuRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final MenuFuncionRepository menuFuncionRepository;
    private final PerfilPermisoRepository perfilPermisoRepository;

    /**
     * Constructor de la clase.
     *
     * @param menuRepository MenuRepository
     */
    @Autowired
    private MenuApiController(final MenuRepository menuRepository,
            final JwtTokenProvider jwtTokenProvider,
            final UsuarioRepository usuarioRepository,
            final MenuFuncionRepository menuFuncionRepository,
            final PerfilPermisoRepository perfilPermisoRepository) {
        this.menuRepository = menuRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.menuFuncionRepository = menuFuncionRepository;
        this.perfilPermisoRepository = perfilPermisoRepository;
    }

    /**
     * Listar Registros de Menús
     *
     * @return Listado de Menús
     */
    @Override
    public ResponseEntity<List<Menu>> listar() {
        Iterable<Menu> iter = menuRepository.findAll();
        List<Menu> res = new ArrayList<>();
        iter.forEach(res::add);

        List<Menu> menus = new ArrayList<>();
        for (Menu menu : res) {
            List<MenuFuncion> menuFuncions = menuFuncionRepository.findByMenuCodigo(menu);
            if (menuFuncions != null && !menuFuncions.isEmpty()) {
                menu.setMenuFuncion(menuFuncions);
            }
            if (menu.getPadreMenu() != null) {
                UtilMenu.armarMenuRecursivo(menus, menu, res);
            } else {
                menus.add(menu);
            }
        }
        return ResponseEntity.ok(menus);
    }

    /**
     * Obtiene un registro de Menú
     *
     * @param id Identificador del registro
     * @return Registro de Menú
     */
    @Override
    public ResponseEntity<Menu> obtener(final @PathVariable("id") String id) {
        Optional<Menu> men = menuRepository.findById(id);
        if (men.isPresent()) {
            return ResponseEntity.ok(men.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 400));
        }
    }

    /**
     * Crea un registro de Menú
     *
     * @param menu Menu
     * @return Menú
     */
    @Override
    public ResponseEntity<Menu> crear(@RequestBody @Validated Menu menu, HttpServletRequest req) {
        Optional<Menu> men = menuRepository.findById(menu.getMenuCodigo());
        if (men.isPresent()) {
            throw new BadRequestException(ErrorRs.get("No se puede crear el menú seleccionado",
                    "El código ya existe, por favor verifique e intente de nuevo.", 400));
        }
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        menu.setCreadoPor(user.getId());
        menu.setFechaCreacion(Date.from(Instant.now()));
        return ResponseEntity.ok(menuRepository.save(menu));
    }

    /**
     * cambia un registro de Menú
     *
     * @param menu Menu
     * @return Menú
     */
    @Override
    public ResponseEntity<Menu> cambiar(@RequestBody @Validated Menu menu, HttpServletRequest req) {
        if (!menu.getMenuCodigo().isEmpty()) {
            String token = jwtTokenProvider.resolveToken(req);
            Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
            if (menu.getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
                resolverDependencias(menu, true, false, req);
            }
            menu.setModificadoPor(user.getId());
            menu.setFechaModificacion(Date.from(Instant.now()));
            return ResponseEntity.ok(menuRepository.save(menu));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a Cambiar", null, 400));
        }
    }

    /**
     * Elimina un registro de menú
     *
     * @param codigo
     * @param req
     * @return
     */
    @Override
    public ResponseEntity<GenericRs> eliminar(final @PathVariable("codigo") String codigo, HttpServletRequest req) {
        Optional<Menu> menuOpt = menuRepository.findById(codigo);
        if (menuOpt.isPresent()) {
            if (resolverDependencias(menuOpt.get(), false, true, req)) {
                throw new BadRequestException(ErrorRs.get("No se puede eliminar el menú seleccionado",
                        "No es posible eliminar el elemento seleccionado ya que cuenta con información asociada.", 400));
            } else {
                menuRepository.delete(menuOpt.get());
            }
            return ResponseEntity.ok(new GenericRs("200", "Eliminado"));
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe el Recurso a eliminar", null, 400));
        }
    }

    private boolean resolverDependencias(Menu menu, boolean actualizar,
            boolean eliminar, HttpServletRequest req) {
        boolean tienePermisos = false;
        boolean tieneAcciones = false;
        boolean tieneHijos = false;
        boolean puedeEliminar = true;
        String token = jwtTokenProvider.resolveToken(req);
        Usuario user = usuarioRepository.findByEmail(SeguridadUtil.getSubject(token));
        List<PerfilPermiso> perfilPermisos = perfilPermisoRepository.findByMenuCodigo(menu);
        if (perfilPermisos != null && !perfilPermisos.isEmpty()) {
            for (PerfilPermiso perfilPermiso : perfilPermisos) {
                if (perfilPermiso.getEstado() == VariablesEstaticas.ESTADO_ACTIVO
                        && !perfilPermiso.getMenuFuncion().equals(VariablesEstaticas.COD_MENU)) {
                    tienePermisos = true;
                    if (actualizar) {
                        perfilPermiso.setModificadoPor(user.getId());
                        perfilPermiso.setFechaModificacion(Date.from(Instant.now()));
                        perfilPermiso.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                        perfilPermisoRepository.save(perfilPermiso);
                    }
                }
            }
        }
        List<MenuFuncion> menuFuncions = menuFuncionRepository.findByMenuCodigo(menu);
        if (menuFuncions != null && !menuFuncions.isEmpty()) {
            for (MenuFuncion menuFuncion : menuFuncions) {
                if (menuFuncion.getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                    tieneAcciones = true;
                    if (actualizar) {
                        menuFuncion.setModificadoPor(user.getId());
                        menuFuncion.setFechaModificacion(Date.from(Instant.now()));
                        menuFuncion.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                        menuFuncionRepository.save(menuFuncion);
                    }
                }
            }
        }
        List<Menu> menusHijos = menuRepository.findByPadreMenu(menu.getMenuCodigo());
        if (menusHijos != null && !menusHijos.isEmpty()) {
            for (Menu menuHijo : menusHijos) {
                if (menuHijo.getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                    tieneHijos = true;
                    if (actualizar) {
                        menuHijo.setModificadoPor(user.getId());
                        menuHijo.setFechaModificacion(Date.from(Instant.now()));
                        menuHijo.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                        menuRepository.save(menuHijo);
                        resolverDependencias(menuHijo, actualizar, eliminar, req);
                    }
                }
            }
        }

        if (!tieneHijos && !tieneAcciones && !tienePermisos && eliminar) {
            puedeEliminar = true;
        }
        if (puedeEliminar) {
            if (!tieneHijos) {
                if (menusHijos != null && !menusHijos.isEmpty()) {
                    menusHijos.stream().forEachOrdered((menuHijo) -> {
                        resolverDependencias(menuHijo, actualizar, eliminar, req);
                        menuRepository.delete(menuHijo);
                    });
                }
            }
            if (!tieneAcciones) {
                if (menuFuncions != null && !menuFuncions.isEmpty()) {
                    menuFuncions.stream().forEachOrdered((menuFuncion) -> {
                        menuFuncionRepository.delete(menuFuncion);
                    });
                }
            }
            if (!tienePermisos) {
                if (perfilPermisos != null && !perfilPermisos.isEmpty()) {
                    perfilPermisos.stream().forEachOrdered((perfilPermiso) -> {
                        perfilPermisoRepository.delete(perfilPermiso);
                    });
                }
            }

        }

        return tienePermisos || tieneAcciones || tieneHijos;
    }
}
