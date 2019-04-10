/*
* Archivo: CondicionValor.java
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

package co.com.grupoasd.fae.motoreglas.motor.interprete.condicion;

import org.springframework.stereotype.Component;

import co.com.grupoasd.fae.motoreglas.entidades.Condicion;
import co.com.grupoasd.fae.motoreglas.interfaces.CondicionTraducion;

/**
 * Implementacion de la traducción de una condicion para el tipo de cotejamiento
 * valor.
 * 
 * @author Alvaro Cordoba Torres
 */

@Component
public class CondicionValor implements CondicionTraducion {

	@Override
	public String getCondicion(Condicion condicion) {
		StringBuilder stringCondicion = new StringBuilder();

		switch (condicion.getTipoCondicion().getId().intValue()) {
		case 7:
			stringCondicion.append("${valor_" + condicion.getId() + "}");
			stringCondicion.append(" < ");
			stringCondicion.append(condicion.getCampo());
			stringCondicion.append(" && ");
			stringCondicion.append(condicion.getCampo());
			stringCondicion.append(" < ");
			stringCondicion.append("${valor_" + condicion.getId() + "}");
			break;
		case 8:
			stringCondicion.append(condicion.getCampo());
			stringCondicion.append(" in (${valor_" + condicion.getId() + "})");
			break;
		default:
			stringCondicion.append(condicion.getCampo() + " ");
			stringCondicion.append(condicion.getTipoCondicion().getOperador() + " ");
			stringCondicion.append("${valor_" + condicion.getId() + "}");
		}

		return stringCondicion.toString();
	}

}