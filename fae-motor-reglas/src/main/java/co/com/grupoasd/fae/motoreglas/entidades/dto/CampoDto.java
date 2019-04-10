/*
* Archivo: CampoDto.java
* Fecha: 12/10/2018
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

package co.com.grupoasd.fae.motoreglas.entidades.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Clase que es el Dto de los campos de las entidades.
 * 
 * @author Alvaro Cordoba Torres
 */

@Data
public class CampoDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6710428650682772289L;

	/** Atributo valores. */
	private String nombre;

	/** Atributo valores. */
	private String tipoCampo;

	/** Atributo valores. */
	private String regExp;

	/**
	 * Constructor de la clase.
	 *
	 * @param nombre
	 *            Nombre
	 * @param tipoCampo
	 *            Tipo de campo
	 * @param regExp
	 *            Expresión regular de validacion
	 */
	public CampoDto(String nombre, String tipoCampo, String regExp) {
		super();
		this.nombre = nombre;
		this.tipoCampo = tipoCampo.substring(tipoCampo.lastIndexOf('.') + 1);
		this.regExp = regExp;
	}

}