/*
* Archivo: ControladorValor.java
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

import co.com.grupoasd.fae.motoreglas.entidades.Valor;
import co.com.grupoasd.fae.motoreglas.entidades.dto.ValorDto;
import co.com.grupoasd.fae.motoreglas.excepciones.MotorException;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioValor;

/**
 * Servicios rest de la entidad valor.
 * 
 * @author Alvaro Cordoba Torres
 */

@RestController
@RequestMapping("motorRegla/valor")
public class ControladorValor {

	/** Instancia de la clase que expone el CRUD de la entidad valor. */
	private final RepositorioValor repositorioValor;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioValor
	 *            Repositorio valor
	 */
	@Autowired
	public ControladorValor(RepositorioValor repositorioValor) {
		super();
		this.repositorioValor = repositorioValor;
	}

	/**
	 * Endpoint que retorna todos los registro de la entidad valor.
	 *
	 * @return todos los registro de la entidad valor
	 */
	@GetMapping
	public Iterable<Valor> getValores() {
		return repositorioValor.findAll();
	}

	/**
	 * Endpoint que retorna la entidad valor con el id solicitado.
	 *
	 * @param idValor
	 *            Id de la valor solicitada
	 * @return La valor con el id de búsqueda ingresado
	 */
	@GetMapping("/{id}")
	public Optional<Valor> getValor(@PathVariable("id") Long idValor) {
		return repositorioValor.findById(idValor);
	}

	/**
	 * Endpoint que retorna la entidad valor guardada o actualizada.
	 *
	 * @param valor
	 *            Valor a guardar o actualizar
	 * @return El valor guardada o actualizada.
	 * @throws MotorException
	 *             the motor exception
	 */
	@PostMapping
	public Valor updateValorUno(@RequestBody ValorDto valor) throws MotorException {
		Valor valorTemp = new Valor();
		valorTemp.setValor(valor);
		return repositorioValor.save(valorTemp);
	}

	/**
	 * Endpoint que elimina la entidad valor con el id recibido.
	 *
	 * @param idValor
	 *            Id del valor a eliminar
	 * @return respuesta de la accion eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteValor(@PathVariable("id") Long idValor) {
		repositorioValor.deleteById(idValor);
		return new ResponseEntity<>("Se eliminó registro.", HttpStatus.OK);
	}

}