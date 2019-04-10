/*
* Archivo: UtilMenu.java
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
package co.com.grupoasd.fae.autenticacion.util;

import co.com.grupoasd.fae.model.entity.Menu;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * Clase utilitaria para menus.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
public class UtilMenu {

    public static void armarMenuRecursivo(List<Menu> menuDtos, Menu menu, List<Menu> menus) {
        if (menuDtos != null && menuDtos.size() > 0) {
            Optional<Menu> existeMenuPadre = menuDtos.stream()
                    .filter(x -> x.getMenuCodigo().equals(menu.getPadreMenu())).findFirst();
            if (existeMenuPadre.isPresent()) {
                setMenusHijos(existeMenuPadre, menu);
            } else {
                existeMenuPadre = menus.stream()
                        .filter(x -> x.getMenuCodigo().equals(menu.getPadreMenu())).findFirst();
                if (existeMenuPadre.isPresent()) {
                    setMenusHijos(existeMenuPadre, menu);
                }
            }
        }
    }

    private static void setMenusHijos(Optional<Menu> existeMenuPadre, Menu menu) {
        List<Menu> menusPadre;
        /**
         * Si la lista de hijos tiene items ?
         */
        if (existeMenuPadre.get().getMenusHijos() != null && existeMenuPadre.get().getMenusHijos().size() > 0) {
            menusPadre = existeMenuPadre.get().getMenusHijos();
            menusPadre.add(menu);
            existeMenuPadre.get().setMenusHijos(menusPadre);
        } else {
            menusPadre = new ArrayList<>();
            menusPadre.add(menu);
            existeMenuPadre.get().setMenusHijos(menusPadre);
        }
    }
}
