/*
* Archivo: ObjetoNegocio.java
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

package co.com.grupoasd.fae.motoreglas.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad ObjetoNegocio.
 * 
 * @author Alvaro Cordoba Torres
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(schema = "fae_motor", catalog = "fae_motor")
@GenericGenerator(name = "generadorObjetoNegocio", strategy = "native")
public class ObjetoNegocio extends EntidadBase implements Serializable {

	/** Constante serialVersionUID. */
	@Transient
	private static final long serialVersionUID = -5010040573242073687L;

	/** Atributo id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generadorObjetoNegocio")
	protected Long id;

	/** Atributo nombre. */
	@NotNull
	@Column(unique = true)
	private String nombre;

	/**
	 * Constructor con el parámetro nombre.
	 *
	 * @param nombre
	 *            nombre de la entidad
	 */
	public ObjetoNegocio(String nombre) {
		super();
		this.nombre = nombre;
	}

	/**
	 * Constructor de la clase.
	 */
	public ObjetoNegocio() {
		super();
	}

}