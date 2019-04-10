/*
* Archivo: EntidadBase.java
* Fecha: 02/11/2018
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

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Entidad EntidadBase.
 * 
 * @author Alvaro Cordoba Torres
 */

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class EntidadBase {

	/** Atributo esta eliminado. */
	@JsonIgnore
	private boolean estaEliminado = false;

	/** Atributo usuario crea. */
	@CreatedBy
	@JsonIgnore
	private Long usuarioCreacion;

	/** Atributo fecha creacion. */
	@Column(nullable = false)
	@CreatedDate
	@JsonIgnore
	private Instant fechaCreacion;

	/** Atributo usuario modifica. */
	@LastModifiedBy
	@JsonIgnore
	private Long usuarioModificacion;

	/** Atributo fecha modifica. */
	@Column(nullable = false)
	@LastModifiedDate
	@JsonIgnore
	private Instant fechaModificacion;

	/**
	 * retorna el id del objeto.
	 *
	 * @return el id del objeto
	 */
	public abstract Long getId();

}