/*
* Archivo: ControladorCondicion.java
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

import co.com.grupoasd.fae.motoreglas.entidades.Condicion;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioCondicion;

/**
 * Servicios rest de la entidad condicion.
 * 
 * @author Alvaro Cordoba Torres
 */

@RestController
@RequestMapping("motorRegla/condicion")
public class ControladorCondicion {

	/** Instancia de la clase que expone el CRUD de la entidad condicion. */
	private final RepositorioCondicion repositorioCondicion;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioCondicion
	 *            Repositorio condicion
	 */
	@Autowired
	public ControladorCondicion(RepositorioCondicion repositorioCondicion) {
		super();
		this.repositorioCondicion = repositorioCondicion;
	}

	/**
	 * Endpoint que retorna todos los registro de la entidad condicion.
	 *
	 * @return todos los registro de la entidad condicion
	 */
	@GetMapping
	public Iterable<Condicion> getCondiciones() {
		return repositorioCondicion.findAll();
	}

	/**
	 * Endpoint que retorna la entidad condicion con el id solicitado.
	 *
	 * @param idCondicion
	 *            Id de la condicion solicitada
	 * @return La condicion con el id de búsqueda ingresado
	 */
	@GetMapping("/{id}")
	public Optional<Condicion> getCondicion(@PathVariable("id") Long idCondicion) {
		return repositorioCondicion.findById(idCondicion);
	}

	/**
	 * Endpoint que retorna la entidad condicion guardada o actualizada.
	 *
	 * @param condicion
	 *            Condicion a guardar o actualizar
	 * @return La condicion guardada o actualizada.
	 */
	@PostMapping
	public Condicion updateCondicion(@RequestBody Condicion condicion) {
		return repositorioCondicion.save(condicion);
	}

	/**
	 * Endpoint que elimina la entidad condicion con el id recibido.
	 *
	 * @param idCondicion
	 *            Id de la condicion a eliminar
	 * @return respuesta de la accion eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCondicion(@PathVariable("id") Long idCondicion) {
		repositorioCondicion.deleteById(idCondicion);
		return new ResponseEntity<>("Se eliminó registro.", HttpStatus.OK);
	}

}