/*
* Archivo: ControladorTipoUnion.java
* Fecha: 16/10/2018
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

import co.com.grupoasd.fae.motoreglas.entidades.TipoUnion;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioTipoUnion;

/**
 * Servicios rest de la entidad tipo union.
 * 
 * @author Alvaro Cordoba Torres
 */

@RestController
@RequestMapping("motorRegla/tipoUnion")
public class ControladorTipoUnion {

	/** Instancia de la clase que expone el CRUD de la entidad tipo union. */
	private final RepositorioTipoUnion repositorioTipoUnion;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioTipoUnion
	 *            Repositorio tipo union
	 */
	@Autowired
	public ControladorTipoUnion(RepositorioTipoUnion repositorioTipoUnion) {
		super();
		this.repositorioTipoUnion = repositorioTipoUnion;
	}

	/**
	 * Endpoint que retorna todos los registro de la entidad tipo union.
	 *
	 * @return todos los registro de la entidad tipo union
	 */
	@GetMapping
	public Iterable<TipoUnion> getTipoUniones() {
		return repositorioTipoUnion.findAll();
	}

	/**
	 * Endpoint que retorna la entidad tipo union con el id solicitado.
	 *
	 * @param idTipoUnion
	 *            Id del tipo union solicitada
	 * @return El tipo union con el id de búsqueda ingresado
	 */
	@GetMapping("/{id}")
	public Optional<TipoUnion> getTipoUnion(@PathVariable("id") Long idTipoUnion) {
		return repositorioTipoUnion.findById(idTipoUnion);
	}

	/**
	 * Endpoint que retorna la entidad tipo union guardada o actualizada.
	 *
	 * @param tipoUnion
	 *            tipo union a guardar o actualizar
	 * @return El tipo union guardada o actualizada.
	 */
	@PostMapping
	public TipoUnion updateTipoUnion(@RequestBody TipoUnion tipoUnion) {
		return repositorioTipoUnion.save(tipoUnion);
	}

	/**
	 * Endpoint que elimina la entidad tipo union con el id recibido.
	 *
	 * @param idTipoUnion
	 *            Id del tipo de union a eliminar
	 * @return respuesta de la accion eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTipoUnion(@PathVariable("id") Long idTipoUnion) {
		repositorioTipoUnion.deleteById(idTipoUnion);
		return new ResponseEntity<>("Se eliminó registro.", HttpStatus.OK);
	}

}