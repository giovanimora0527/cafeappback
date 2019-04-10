/*
* Archivo: Interprete.java
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

package co.com.grupoasd.fae.motoreglas.motor.interprete;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.grupoasd.fae.motoreglas.entidades.Accion;
import co.com.grupoasd.fae.motoreglas.entidades.Condicion;
import co.com.grupoasd.fae.motoreglas.entidades.ObjetoNegocio;
import co.com.grupoasd.fae.motoreglas.motor.interprete.seleccion.AccionesTipoCotejo;
import co.com.grupoasd.fae.motoreglas.motor.interprete.seleccion.CondicionTipoCotejo;

/**
 * Implementan la transformacion del modelo de datos al DSL de Drools.
 *
 * @author Alvaro Cordoba Torres
 */

@Component
public class Interprete {

	/** Instancia del objeto que retorna las acciones del modelo de datos. */
	private AccionesTipoCotejo accionesTipoCotejo;

	/** Instancia del objeto que retorna las condiciones del modelo de datos. */
	private CondicionTipoCotejo condicionTipoCotejo;

	/**
	 * Constructor de la clase.
	 *
	 * @param accionesTipoCotejo
	 *            Acciones por tipo cotejo
	 * @param condicionTipoCotejo
	 *            Condicion por tipo cotejo
	 */
	@Autowired
	public Interprete(AccionesTipoCotejo accionesTipoCotejo, CondicionTipoCotejo condicionTipoCotejo) {
		super();
		this.accionesTipoCotejo = accionesTipoCotejo;
		this.condicionTipoCotejo = condicionTipoCotejo;
	}

	/**
	 * Convierte las condiciones del modelo de datos al DSL de Drools.
	 *
	 * @param objetoNegocio
	 *            Objeto negocio de la regla
	 * @param condiciones
	 *            Las condiciones de la regla
	 * @return Las reglas en DSL de Drools
	 */
	public String getCondiciones(ObjetoNegocio objetoNegocio, List<Condicion> condiciones) {
		StringBuilder stringCondicion = new StringBuilder();

		stringCondicion.append("$" + objetoNegocio.getNombre().toLowerCase() + " : ");
		stringCondicion.append(objetoNegocio.getNombre() + "( ");
		stringCondicion.append(getCondiciones(condiciones));
		stringCondicion.append(" )");

		return stringCondicion.toString();
	}

	/**
	 * Transforma las condiciones del modelo de datos al DSL de Drools.
	 *
	 * @param condiciones
	 *            Las condiciones a transformar
	 * @return Las condiciones en DSL de Drools
	 */
	public String getCondiciones(List<Condicion> condiciones) {
		StringBuilder stringCondicion = new StringBuilder();

		for (Condicion condicion : condiciones) {

			if (condicion.getTipoUnion() != null) {
				stringCondicion.append(" " + condicion.getTipoUnion().getOperador() + " ");
			}

			stringCondicion.append(condicionTipoCotejo.convertir(condicion));
		}

		return stringCondicion.toString();
	}

	/**
	 * Transforma las acciones del modelo de datos a DSL de Drools.
	 *
	 * @param acciones
	 *            Las acciones a transformar
	 * @return Las acciones en DSL de Drools
	 */
	public String getAcciones(List<Accion> acciones) {
		StringBuilder stringAcciones = new StringBuilder();

		for (Accion accion : acciones) {
			stringAcciones.append(accionesTipoCotejo.convertir(accion));
			stringAcciones.append("\n");
		}

		return stringAcciones.toString();
	}

	/**
	 * Transforma todos los objetos de negocio al DSL de Drools.
	 *
	 * @param objetos
	 *            Lista de objetos de negocio a transformar
	 * @return los objetos de negocio en DSL de Drools
	 */
	public String getObjetosNegocio(List<ObjetoNegocio> objetos) {

		StringBuilder respuesta = new StringBuilder();

		objetos.forEach(objeto -> {
			String nombre = objeto.getNombre();

			respuesta.append("$" + nombre.toLowerCase());
			respuesta.append(" : ");
			respuesta.append(nombre);
			respuesta.append("();");
			respuesta.append("\n");
		});

		return respuesta.toString();
	}

}