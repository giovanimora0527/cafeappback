/*
* Archivo: Regla.java
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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad regla.
 * 
 * @author Alvaro Cordoba Torres
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(schema = "fae_motor", catalog = "fae_motor")
@GenericGenerator(name = "generadorRegla", strategy = "native")
public class Regla extends EntidadBase implements Serializable {

	/** Constante serialVersionUID. */
	@Transient
	private static final long serialVersionUID = -4861254833627227139L;

	/** Atributo id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generadorRegla")
	private Long id;

	/** Atributo nombre. */
	@NotNull
	@Column(unique = true)
	private String nombre;

	/** Atributo orden. */
	@NotNull
	private Integer orden;

	/** Atributo acciones. */
	@OneToMany
	@JoinColumn(name = "regla_fk")
	private List<Accion> acciones;

	/** Atributo condiciones. */
	@OneToMany
	@JoinColumn(name = "regla_fk")
	private List<Condicion> condiciones;

	/** Atributo valoresRegla. */
	@OneToMany
	@JoinColumn(name = "regla_fk")
	private List<ValoresRegla> valoresRegla;

	/** Atributo objetoNegocio. */
	@ManyToOne
	private ObjetoNegocio objetoNegocio;

	/**
	 * Busca todos los objetos de negocios de las condiciones asociados a la regla.
	 *
	 * @return lista de objetos de negocios
	 */
	@JsonIgnore
	public List<ObjetoNegocio> getObjetoNegocioCondicion() {

		List<ObjetoNegocio> objetos = new ArrayList<>();

		condiciones.forEach(condicion -> {
			ObjetoNegocio objeto = condicion.getObjetoNegocio();
			if (objeto != null && !objeto.equals(objetoNegocio)) {
				objetos.add(objeto);
			}

		});

		return objetos;
	}

}