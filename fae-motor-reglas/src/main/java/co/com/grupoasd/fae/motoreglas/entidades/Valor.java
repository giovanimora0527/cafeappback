/*
* Archivo: Valor.java
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

package co.com.grupoasd.fae.motoreglas.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import co.com.grupoasd.fae.motoreglas.entidades.dto.ValorDto;
import co.com.grupoasd.fae.motoreglas.excepciones.MotorException;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad valor.
 * 
 * @author Alvaro Cordoba Torres
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(schema = "fae_motor", catalog = "fae_motor")
@GenericGenerator(name = "generadorValor", strategy = "native")
public class Valor extends EntidadBase implements Serializable {

	/** Constante serialVersionUID. */
	@Transient
	private static final long serialVersionUID = -3844225342234943026L;

	/** Atributo id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generadorValor")
	private Long id;

	/** Atributo valores. */
	@Column(length = 700)
	private String valores;

	/** Atributo condicion. */
	@ManyToOne
	private Condicion condicion;

	/**
	 * transforma valor del front al modelo de datos del proyecto.
	 *
	 * @param valorDto
	 *            valorDto que envía el front
	 * @throws MotorException
	 *             lanza la excepción motor
	 */
	public void setValor(ValorDto valorDto) throws MotorException {
		this.id = valorDto.getId();
		valores = valorDto.getValoresDB();
		this.condicion = valorDto.getCondicion();
	}

}