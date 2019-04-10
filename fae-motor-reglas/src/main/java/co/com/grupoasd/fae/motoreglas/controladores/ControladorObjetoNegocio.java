/*
* Archivo: ControladorObjetoNegocio.java
* Fecha: 25/09/2018
* Todos los derechos de propiedad intelectual e industrial sobre esta
* aplicacion son de propiedad exclusiva del GRUPO ASESORIA EN
* SISTEMATIZACION DE DATOS SOCIEDAD POR ACCIONES SIMPLIFICADA – GRUPO ASD
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
package co.com.grupoasd.fae.motoreglas.controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.grupoasd.fae.motoreglas.entidades.ObjetoNegocio;
import co.com.grupoasd.fae.motoreglas.entidades.dto.CampoDto;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioObjetoNegocio;
import co.com.grupoasd.fae.motoreglas.utilidades.UtilidadesClases;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

/**
 * Servicios rest de la entidad objeto negocio.
 *
 * @author Alvaro Cordoba Torres
 */
@RestController
@RequestMapping("motorRegla/objetoNegocio")
public class ControladorObjetoNegocio {

    /**
     * Instancia de la clase que expone el CRUD de la entidad objeto negocio.
     */
    private final UtilidadesClases utilidadesClases;
    private final RepositorioObjetoNegocio repositorioObjetoNegocio;
    @Value("${motorReglas.paqueteObjetoNegocio}")
    private String paqueteObjetoNegocio;

    /**
     * Constructor de la clase.
     *
     * @param repositorioObjetoNegocio Repositorio objeto negocio
     * @param utilidadesClases Clase utilitaria
     */
    @Autowired
    public ControladorObjetoNegocio(RepositorioObjetoNegocio repositorioObjetoNegocio,
            UtilidadesClases utilidadesClases) {
        super();
        this.repositorioObjetoNegocio = repositorioObjetoNegocio;
        this.utilidadesClases = utilidadesClases;
    }

    /**
     * Endpoint que retorna todos los registro de la entidad objeto negocio.
     *
     * @return todos los registro de la entidad objeto negocio
     */
    @GetMapping
    public Iterable<ObjetoNegocio> getObjectoNegocios() {
        return repositorioObjetoNegocio.findAll();
    }

    /**
     * Endpoint que retorna la entidad objeto negocio con el id solicitado.
     *
     * @param idObjetoNegocio Id del objeto negocio solicitado
     * @return El objecto negocio con el id de búsqueda ingresado
     */
    @GetMapping("/{id}")
    public Optional<ObjetoNegocio> getObjectoNegocio(@PathVariable("id") Long idObjetoNegocio) {
        return repositorioObjetoNegocio.findById(idObjetoNegocio);
    }

    /**
     * Endpoint que retorna la entidad objeto negocio con el id solicitado.
     *
     * @param nameObjetoNegocio Nombre del objeto negocio solicitado
     * @return El objecto negocio con el id de búsqueda ingresado
     */
    @GetMapping("/campos/{nameObjetoNegocio}")
    public ResponseEntity<List<CampoDto>> getCamposObjectoNegocio(@PathVariable("nameObjetoNegocio") String nameObjetoNegocio) {
        try {
            Class clase = Class.forName(paqueteObjetoNegocio.concat(".").concat(nameObjetoNegocio));
            return ResponseEntity.ok(utilidadesClases.getAtributos(clase));
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

}
