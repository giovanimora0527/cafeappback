/*
* Archivo: PerfilServiceImpl.java
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
package co.com.grupoasd.fae.autenticacion.service;

import co.com.grupoasd.fae.model.entity.Menu;
import co.com.grupoasd.fae.model.entity.MenuFuncion;
import co.com.grupoasd.fae.model.entity.Perfil;
import co.com.grupoasd.fae.model.entity.PerfilPermiso;
import co.com.grupoasd.fae.model.repository.MenuFuncionRepository;
import co.com.grupoasd.fae.model.repository.MenuRepository;
import co.com.grupoasd.fae.model.repository.PerfilPermisoRepository;
import co.com.grupoasd.fae.autenticacion.util.UtilMenu;
import co.com.grupoasd.fae.util.VariablesEstaticas;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Servicios de perfiles
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Service
public class PerfilServiceImpl implements PerfilService{

    private final PerfilPermisoRepository perfilPermisoRepository;
    private final MenuRepository menuRepository;
    private final MenuFuncionRepository menuFuncionRepository;
    
    @Autowired
    public PerfilServiceImpl(final MenuRepository menuRepository, 
            final PerfilPermisoRepository perfilPermisoRepository,
            final MenuFuncionRepository menuFuncionRepository) {
        this.menuRepository = menuRepository;
        this.perfilPermisoRepository = perfilPermisoRepository;
        this.menuFuncionRepository = menuFuncionRepository;
    }
    
    @Override
    public List<Menu> obtenerMenuPerfil(Long id, boolean sideBar) {
        Perfil perfil = new Perfil();
        perfil.setId(id);
        List<PerfilPermiso> perfilPermisos = perfilPermisoRepository.findByPerfilId(perfil);
        List<Menu> menusPermisos = new LinkedList<>();
        for (PerfilPermiso perfilPermiso : perfilPermisos) {
            if (perfilPermiso.getPerfilPermisoIdentity().getMenuFuncionCodigo().equals(VariablesEstaticas.COD_MENU)) {
                if(sideBar && perfilPermiso.getEstado() == VariablesEstaticas.ESTADO_INACTIVO){
                    continue;
                }
                Optional<Menu> optionalMenu = menuRepository.findById(perfilPermiso.getPerfilPermisoIdentity().getMenuCodigo());
                if (optionalMenu.isPresent()) {
                    menusPermisos.add(optionalMenu.get());
                }
            }
        }

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
                if (!menusPermisos.contains(menu)) {
                    continue;
                }
                UtilMenu.armarMenuRecursivo(menus, menu, res);
            } else {
                menus.add(menu);
            }
        }
        return menus;
    }
    
}
