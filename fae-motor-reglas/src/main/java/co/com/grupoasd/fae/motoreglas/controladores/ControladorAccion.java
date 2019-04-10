/*
* Archivo: ControladorAccion.java
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.grupoasd.fae.motoreglas.entidades.Accion;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioAccion;

/**
 * Servicios rest de la entidad accion.
 * 
 * @author Alvaro Cordoba Torres
 */

@RestController
@RequestMapping("motorRegla/accion")
public class ControladorAccion {

	/** Instancia de la clase que expone el CRUD de la entidad accion. */
	private final RepositorioAccion repositorioAccion;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioAccion
	 *            Repositorio accion
	 */
	@Autowired
	public ControladorAccion(RepositorioAccion repositorioAccion) {
		super();
		this.repositorioAccion = repositorioAccion;
	}

	/**
	 * Endpoint que retorna todos los registro de la entidad accion.
	 *
	 * @return todos los registro de la entidad accion
	 */
	@GetMapping
	public Iterable<Accion> getAcciones() {
		return repositorioAccion.findAll();
	}

	/**
	 * Endpoint que retorna la entidad accion con el id solicitado.
	 *
	 * @param idAccion
	 *            Id de la accion solicitada
	 * @return La accion con el id de búsqueda ingresado
	 */
	@GetMapping("/{id}")
	public Optional<Accion> getAccion(@PathVariable("id") Long idAccion) {
		return repositorioAccion.findById(idAccion);
	}

	/**
	 * Endpoint que retorna la entidad accion guardada o actualizada.
	 *
	 * @param accion
	 *            Accion a guardar o actualizar
	 * @return La accion guardada o actualizada.
	 */
	@PostMapping
	public Accion updateAccion(@RequestBody Accion accion) {
		return repositorioAccion.save(accion);
	}

	/**
	 * Endpoint que elimina la entidad accion con el id recibido.
	 *
	 * @param idAccion
	 *            Id de la accion a eliminar
	 * @return respuesta de la accion eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccion(@PathVariable("id") Long idAccion) {
		repositorioAccion.deleteById(idAccion);
		return new ResponseEntity<>("Se eliminó registro.", HttpStatus.OK);
	}

}