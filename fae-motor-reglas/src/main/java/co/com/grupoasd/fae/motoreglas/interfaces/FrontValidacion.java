/*
* Archivo: FrontValidacion.java
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

package co.com.grupoasd.fae.motoreglas.interfaces;

/**
 * Interfaz que desacopla la validación de los campos del front con la
 * implemntacion del motor de reglas.
 *
 * @author Alvaro Cordoba Torres
 */

public interface FrontValidacion {

	/**
	 * retorna la expresión regular que valida el campo en el front
	 *
	 * @param campoFront
	 *            Campo del front
	 * @return la expresión regular que valida el compo en el front
	 */
	public String getExpReg(String campoFront);

}