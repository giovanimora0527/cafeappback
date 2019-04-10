/*
* Archivo: AccionesTipoCotejo.java
* Fecha: 19/11/2018
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

package co.com.grupoasd.fae.motoreglas.motor.interprete.seleccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.grupoasd.fae.motoreglas.entidades.Accion;
import co.com.grupoasd.fae.motoreglas.interfaces.AccionTraducion;
import co.com.grupoasd.fae.motoreglas.motor.interprete.accion.AccionCampo;
import co.com.grupoasd.fae.motoreglas.motor.interprete.accion.AccionValor;

/**
 * Implementa la selección del objeto de traducción acciones del modelo de datos
 * al DSL del Drools es la que se debe aplicar.
 * 
 * @author Alvaro Cordoba Torres
 */

@Component
public class AccionesTipoCotejo {

	/**
	 * Instancia del objeto traduce de una accion para el tipo de cotejamiento
	 * campos.
	 */
	private final AccionCampo accionCampo;

	/**
	 * Instancia del objeto traduce de una accion para el tipo de cotejamiento
	 * valor.
	 */
	private final AccionValor accionValor;

	/**
	 * Constructor de la clase
	 *
	 * @param accionCampo
	 *            Clase que realiza traducción de modelo de datos a DSL Drools para
	 *            tipo cotejamiento Campo.
	 * @param accionValor
	 *            Clase que realiza traducción de modelo de datos a DSL Drools para
	 *            tipo cotejamiento Valor.
	 */
	@Autowired
	public AccionesTipoCotejo(AccionCampo accionCampo, AccionValor accionValor) {
		super();
		this.accionCampo = accionCampo;
		this.accionValor = accionValor;
	}

	/**
	 * Convierte una accion del modelo de datos a DSL de Drools.
	 *
	 * @param accion
	 *            Accion a convertir a DSL de Drools
	 * @return Accion en DSL Drools
	 */
	public String convertir(Accion accion) {

		AccionTraducion accionTraducion;

		switch (accion.getTipoCotejo().getId().intValue()) {
		case 1:
			accionTraducion = accionValor;
			break;
		case 2:
		case 3:
			accionTraducion = accionCampo;
			break;
		default:
			return "Tipo cotejo sin implementación - Accion.";
		}

		return accionTraducion.getAccion(accion);
	}

}