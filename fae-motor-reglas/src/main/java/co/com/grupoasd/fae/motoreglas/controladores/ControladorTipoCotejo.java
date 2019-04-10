/*
* Archivo: ControladorTipoCotejo.java
* Fecha: 16/11/2018
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

import co.com.grupoasd.fae.motoreglas.entidades.TipoCotejo;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioTipoCotejo;

/**
 * Servicios rest de la entidad tipo cotejo.
 * 
 * @author Alvaro Cordoba Torres
 */

@RestController
@RequestMapping("motorRegla/tipoCotejo")
public class ControladorTipoCotejo {

	/** Instancia de la clase que expone el CRUD de la entidad tipo cotejo. */
	private final RepositorioTipoCotejo repositorioTipoCotejo;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioTipoCotejo
	 *            Repositorio tipo cotejo
	 */
	@Autowired
	public ControladorTipoCotejo(RepositorioTipoCotejo repositorioTipoCotejo) {
		super();
		this.repositorioTipoCotejo = repositorioTipoCotejo;
	}

	/**
	 * Endpoint que retorna todos los registro de la entidad tipo cotejo.
	 *
	 * @return todos los registro de la entidad tipo cotejo
	 */
	@GetMapping
	public Iterable<TipoCotejo> getTipoCotejo() {
		return repositorioTipoCotejo.findAll();
	}

	/**
	 * Endpoint que retorna la entidad tipo cotejo con el id solicitado.
	 *
	 * @param idTipoCotejo
	 *            Id del tipo de cotejo solicitado
	 * @return El tipo de cotejo con el id de búsqueda ingresado
	 */
	@GetMapping("/{id}")
	public Optional<TipoCotejo> getTipoCotejo(@PathVariable("id") Long idTipoCotejo) {
		return repositorioTipoCotejo.findById(idTipoCotejo);
	}

	/**
	 * Endpoint que retorna la entidad tipo cotejo guardada o actualizada.
	 *
	 * @param tipoCotejo
	 *            Tipo de cotejo
	 * @return El tipo de cotejo guardada o actualizada.
	 */
	@PostMapping
	public TipoCotejo updateTipoCotejo(@RequestBody TipoCotejo tipoCotejo) {
		return repositorioTipoCotejo.save(tipoCotejo);
	}

	/**
	 * Endpoint que elimina la entidad tipo cotejo con el id recibido.
	 *
	 * @param idTipoCotejo
	 *            Id del tipo de cotejo a eliminar
	 * @return respuesta de la accion eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTipoCotejo(@PathVariable("id") Long idTipoCotejo) {
		repositorioTipoCotejo.deleteById(idTipoCotejo);
		return new ResponseEntity<>("Se eliminó registro.", HttpStatus.OK);
	}

}