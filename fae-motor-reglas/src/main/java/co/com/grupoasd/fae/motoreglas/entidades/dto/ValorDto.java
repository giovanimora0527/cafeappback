/*
* Archivo: ValorDto.java
* Fecha: 18/10/2018
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
import java.util.List;

import co.com.grupoasd.fae.motoreglas.entidades.Condicion;
import co.com.grupoasd.fae.motoreglas.excepciones.MotorException;

import lombok.Data;

/**
 * Clase que es el Dto de los valores de las reglas.
 * 
 * @author Alvaro Cordoba Torres
 */

@Data
public class ValorDto implements Serializable {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 7121909033061993083L;

	/** Atributo id. */
	private Long id;

	/** Atributo valores. */
	private transient List<Object> valores;

	/** Atributo condicion. */
	private Condicion condicion;

	/**
	 * Transforma los valores del front a formato del modelo de datos.
	 *
	 * @return Los valores en formato del modelo de datos.
	 * @throws MotorException
	 *             lanza la excepcion motor de reglas
	 */
	public String getValoresDB() throws MotorException {

		StringBuilder stringBuilder = new StringBuilder();
		boolean esNumerico = esNumerico(valores.get(0));

		for (Object valor : valores) {

			String textoTemp = String.valueOf(valor);

			// Valida el numero ingresado
			if (esNumerico) {
				try {
					Double.valueOf(textoTemp);
				} catch (NumberFormatException e) {
					throw new MotorException("los datos ingresados deben ser solo numericos");
				}
			}

			stringBuilder.append(String.valueOf(textoTemp));
			stringBuilder.append("#");
		}

		return stringBuilder.toString().substring(0, stringBuilder.length() - 1);
	}

	/**
	 * Valida que el objeto ingresado se numerico.
	 *
	 * @param objeto
	 *            Objeto a validar si es numerico
	 * @return verdadero, si el objeto es numerico
	 */
	private boolean esNumerico(Object objeto) {

		String textoTemp = String.valueOf(objeto);

		try {
			Double.valueOf(textoTemp);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}