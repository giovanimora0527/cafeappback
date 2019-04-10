/*
* Archivo: AccionTraducion.java
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

import co.com.grupoasd.fae.motoreglas.entidades.Accion;

/**
 * Interfaz que desacopla los tipos de acciones en su conversión a DSL del motor
 * de reglas.
 * 
 * @author Alvaro Cordoba Torres
 */

public interface AccionTraducion {

	/**
	 * retorna la acción en texto para el uso en el motor.
	 *
	 * @param accion
	 *            accion de la regla
	 * @return la accion en texto
	 */
	public String getAccion(Accion accion);

}