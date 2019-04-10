/*
* Archivo: ControladorRegla.java
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

import java.util.List;
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

import co.com.grupoasd.fae.motoreglas.entidades.Regla;
import co.com.grupoasd.fae.motoreglas.motor.MotorReglas;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioRegla;

/**
 * Servicios rest de la entidad regla.
 * 
 * @author Alvaro Cordoba Torres
 */

@RestController
@RequestMapping("motorRegla/regla")
public class ControladorRegla {

	/** Instancia de la clase que expone el CRUD de la entidad regla. */
	private final RepositorioRegla repositorioRegla;

	/**
	 * Instancia de la clase del motor de reglas de negocio.
	 */
	private final MotorReglas motorReglas;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioRegla
	 *            Repositorio de regla
	 * 
	 * @param motorReglas
	 *            Motor de reglas
	 */
	@Autowired
	public ControladorRegla(RepositorioRegla repositorioRegla, MotorReglas motorReglas) {
		super();
		this.repositorioRegla = repositorioRegla;
		this.motorReglas = motorReglas;
	}

	/**
	 * Endpoint que retorna todos los registro de la entidad regla.
	 *
	 * @return todos los registro de la entidad regla
	 */
	@GetMapping
	public Iterable<Regla> getReglas() {
		return repositorioRegla.findAll();
	}

	/**
	 * Endpoint que retorna todas las condiciones en texto para presentar al
	 * usuario.
	 *
	 * @return todas las condiciones en texto
	 */
	@PostMapping("/texto")
	public List<String> getReglaTexto(@RequestBody List<Long> idsRegla) {
		return motorReglas.getTextoReglas(idsRegla);
	}

	/**
	 * Endpoint que retorna la entidad regla con el id solicitado.
	 *
	 * @param idRegla
	 *            Id de la regla solicitada
	 * @return La regla con el id de búsqueda ingresado
	 */
	@GetMapping("/{idRegla}")
	public Optional<Regla> getRegla(@PathVariable("idRegla") Long idRegla) {
		return repositorioRegla.findById(idRegla);
	}

	/**
	 * Endpoint que retorna la entidad regla guardada o actualizada.
	 *
	 * @param regla
	 *            Regla a guardar o actualizar
	 * @return La regla guardada o actualizada.
	 */
	@PostMapping
	public Regla updateRegla(@RequestBody Regla regla) {
		return repositorioRegla.save(regla);
	}

	/**
	 * Endpoint que elimina la entidad regla con el id recibido.
	 *
	 * @param idRegla
	 *            Id de la regla a eliminar
	 * @return respuesta de la accion eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRegla(@PathVariable("id") Long idRegla) {
		repositorioRegla.deleteById(idRegla);
		return new ResponseEntity<>("Se eliminó registro.", HttpStatus.OK);
	}

}