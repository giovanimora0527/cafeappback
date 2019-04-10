/*
* Archivo: ControladorTipoCondicion.java
* Fecha: 04/10/2018
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

import co.com.grupoasd.fae.motoreglas.entidades.TipoCondicion;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioTipoCondicion;

/**
 * Servicios rest de la entidad tipo de condicion.
 * 
 * @author Alvaro Cordoba Torres
 */

@RestController
@RequestMapping("motorRegla/tipoCondicion")
public class ControladorTipoCondicion {

	/** Instancia de la clase que expone el CRUD de la entidad tipo de condicion. */
	private final RepositorioTipoCondicion repositorioTipoCondicion;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioTipoCondicion
	 *            Repositorio tipo condicion
	 */
	@Autowired
	public ControladorTipoCondicion(RepositorioTipoCondicion repositorioTipoCondicion) {
		super();
		this.repositorioTipoCondicion = repositorioTipoCondicion;
	}

	/**
	 * Endpoint que retorna todos los registro de la entidad tipo de condicion.
	 *
	 * @return todos los registro de la entidad tipo de condicion
	 */
	@GetMapping
	public Iterable<TipoCondicion> getTipoCondiciones() {
		return repositorioTipoCondicion.findAll();
	}

	/**
	 * Endpoint que retorna la entidad tipo de condicion con el id solicitado.
	 *
	 * @param idTipoCondicion
	 *            Id del tipo de condicion solicitada
	 * @return El tipo de condicion con el id de búsqueda ingresado
	 */
	@GetMapping("/{id}")
	public Optional<TipoCondicion> getTipoCondicion(@PathVariable("id") Long idTipoCondicion) {
		return repositorioTipoCondicion.findById(idTipoCondicion);
	}

	/**
	 * Endpoint que retorna la entidad tipo de condicion guardada o actualizada.
	 *
	 * @param tipoCondicion
	 *            Tipo de condicion a guardar o actualizar
	 * @return El tipo de condicion guardada o actualizada.
	 */
	@PostMapping
	public TipoCondicion updateTipoCondicion(@RequestBody TipoCondicion tipoCondicion) {
		return repositorioTipoCondicion.save(tipoCondicion);
	}

	/**
	 * Endpoint que elimina la entidad tipo de condicion con el id recibido.
	 *
	 * @param idTipoCondicion
	 *            Id del tipo de condicion a eliminar
	 * @return respuesta de la accion eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTipoCondicion(@PathVariable("id") Long idTipoCondicion) {
		repositorioTipoCondicion.deleteById(idTipoCondicion);
		return new ResponseEntity<>("Se eliminó registro.", HttpStatus.OK);
	}

}