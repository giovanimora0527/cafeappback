/*
* Archivo: CondicionTipoCotejo.java
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

import co.com.grupoasd.fae.motoreglas.entidades.Condicion;
import co.com.grupoasd.fae.motoreglas.interfaces.CondicionTraducion;
import co.com.grupoasd.fae.motoreglas.motor.interprete.condicion.CondicionCampoDif;
import co.com.grupoasd.fae.motoreglas.motor.interprete.condicion.CondicionCampoIgu;
import co.com.grupoasd.fae.motoreglas.motor.interprete.condicion.CondicionValor;

/**
 * Implementa la selección del objeto de traducción condiciones del modelo de
 * datos al DSL del Drools es la que se debe aplicar.
 * 
 * @author Alvaro Cordoba Torres
 */

@Component
public class CondicionTipoCotejo {

	/**
	 * Instancia del objeto traduce de una condicion para el tipo de cotejamiento
	 * valor.
	 */
	private final CondicionValor condicionValor;

	/**
	 * Instancia del objeto traduce de una condicion para el tipo de cotejamiento
	 * campos mismo objeto de negocio.
	 */
	private final CondicionCampoIgu condicionCampoIgu;

	/**
	 * Instancia del objeto traduce de una accion para el tipo de cotejamiento
	 * campos diferente objeto de negocio.
	 */
	private final CondicionCampoDif condicionCampoDif;

	/**
	 * Constructor de la clase.
	 *
	 * @param condicionValor
	 *            Clase que realiza traducción de modelo de datos a DSL Drools para
	 *            tipo cotejamiento Valor.
	 * @param condicionCampoIgu
	 *            Clase que realiza traducción de modelo de datos a DSL Drools para
	 *            tipo cotejamiento Campo mismo objeto de negocio.
	 * @param condicionCampoDif
	 *            Clase que realiza traducción de modelo de datos a DSL Drools para
	 *            tipo cotejamiento Campo diferente objeto de negocio.
	 */
	@Autowired
	public CondicionTipoCotejo(CondicionValor condicionValor, CondicionCampoIgu condicionCampoIgu,
			CondicionCampoDif condicionCampoDif) {
		super();
		this.condicionValor = condicionValor;
		this.condicionCampoIgu = condicionCampoIgu;
		this.condicionCampoDif = condicionCampoDif;
	}

	/**
	 * Convierte una condicion del modelo de datos a DSL de Drools.
	 *
	 * @param condicion
	 *            Condicion a convertir a DSL de Drools
	 * @return Condicion en DSL Drools
	 */
	public String convertir(Condicion condicion) {

		CondicionTraducion condicionTraducion;

		switch (condicion.getTipoCotejo().getId().intValue()) {
		case 1:
			condicionTraducion = condicionValor;
			break;
		case 2:
			condicionTraducion = condicionCampoIgu;
			break;
		case 3:
			condicionTraducion = condicionCampoDif;
			break;
		default:
			return "Tipo cotejo sin implementación - Condicion.";
		}

		return condicionTraducion.getCondicion(condicion);
	}

}