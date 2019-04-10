/*
* Archivo: AccionValor.java
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

package co.com.grupoasd.fae.motoreglas.motor.interprete.accion;

import org.springframework.stereotype.Component;

import co.com.grupoasd.fae.motoreglas.entidades.Accion;
import co.com.grupoasd.fae.motoreglas.interfaces.AccionTraducion;

/**
 * Implementacion de la traducción de una accion para el tipo de cotejamiento
 * valor.
 * 
 * @author Alvaro Cordoba Torres
 */

@Component
public class AccionValor implements AccionTraducion {

	@Override
	public String getAccion(Accion accion) {
		StringBuilder stringAccion = new StringBuilder();

		stringAccion.append("$" + accion.getObjDestino().getNombre().toLowerCase() + ".");
		stringAccion.append(accion.getCampoDestino() + " = \"");
		stringAccion.append("${valorResultado}\";");

		return stringAccion.toString();
	}

}