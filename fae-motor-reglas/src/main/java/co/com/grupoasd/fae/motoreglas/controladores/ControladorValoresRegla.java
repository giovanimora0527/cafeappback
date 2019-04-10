/*
* Archivo: ControladorValoresRegla.java
* Fecha: 29/10/2018
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

import co.com.grupoasd.fae.motoreglas.entidades.ValoresRegla;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioValoresRegla;

/**
 * Servicios rest de la entidad valores regla.
 * 
 * @author Alvaro Cordoba Torres
 */

@RestController
@RequestMapping("motorRegla/valoresRegla")
public class ControladorValoresRegla {

	/** Instancia de la clase que expone el CRUD de la entidad valores regla. */
	private final RepositorioValoresRegla repositorioValoresRegla;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioValoresRegla
	 *            Repositorio valores regla
	 */
	@Autowired
	public ControladorValoresRegla(RepositorioValoresRegla repositorioValoresRegla) {
		super();
		this.repositorioValoresRegla = repositorioValoresRegla;
	}

	/**
	 * Endpoint que retorna todos los registro de la entidad valores regla.
	 *
	 * @return todos los registro de la entidad valor
	 */
	@GetMapping
	public Iterable<ValoresRegla> getValores() {
		return repositorioValoresRegla.findAll();
	}

	/**
	 * Endpoint que retorna la entidad valores regla con el id solicitado.
	 *
	 * @param idValorRegla
	 *            Id de la valores regla solicitado
	 * @return La valor con el id de búsqueda ingresado
	 */
	@GetMapping("/{id}")
	public Optional<ValoresRegla> getValor(@PathVariable("id") Long idValorRegla) {
		return repositorioValoresRegla.findById(idValorRegla);
	}

	/**
	 * Endpoint que retorna la entidad valores regla guardada o actualizada.
	 *
	 * @param valoresRegla
	 *            El valores regla
	 * @return La valor guardada o actualizada.
	 */
	@PostMapping
	public ValoresRegla updateValorRegla(@RequestBody ValoresRegla valoresRegla) {
		return repositorioValoresRegla.save(valoresRegla);
	}

	/**
	 * Endpoint que elimina la entidad valor regla con el id recibido.
	 *
	 * @param idValorRegla
	 *            Id del valor regla a eliminar
	 * @return respuesta de la accion eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteValorRegla(@PathVariable("id") Long idValorRegla) {
		repositorioValoresRegla.deleteById(idValorRegla);
		return new ResponseEntity<>("Se eliminó registro.", HttpStatus.OK);
	}

}