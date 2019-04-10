/*
* Archivo: MapearValores.java
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

package co.com.grupoasd.fae.motoreglas.motor.interprete.valores;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import co.com.grupoasd.fae.motoreglas.entidades.Valor;
import co.com.grupoasd.fae.motoreglas.entidades.ValoresRegla;

/**
 * Casos de remplazar valores sobre condiciones.
 * 
 * @author Alvaro Cordoba Torres
 */

@Component
public class MapearValores {

	/** Constante expresion regular numeros. */
	private static final String REG_EXP_NUMEROS = "\\d+";

	/** Constante expresión regular valor a insertar modelo de datos. */
	private static final String REG_EXP_UN_PARAMETRO = "\\$\\{valor_\\d+\\}";

	/** Instancia de la clase que aplica expresiones reglares. */
	private Pattern patron;

	/** Instancia de la clase que aplica expresiones reglares. */
	private Matcher matcher;

	/**
	 * Mapea el valor sobre una condición para operaciones estandar.
	 *
	 * @param condicion
	 *            Condicion a la cual se va a sustituir el valor del modelo de
	 *            datos.
	 * @param valores
	 *            Valores a sustituir
	 * @param campoRemplazar
	 *            Campos que se van a remplazar en la condicion
	 * @return Condicion con valores sustituidos
	 */
	public String mapearValor(String condicion, ValoresRegla valores, String campoRemplazar) {
		patron = Pattern.compile(REG_EXP_NUMEROS);
		matcher = patron.matcher(campoRemplazar);

		if (matcher.find()) {
			Long idCondicion = Long.valueOf(matcher.group(0));

			Optional<Valor> valorRemplazar = valores.getValores().stream()
					.filter(valor -> valor.getCondicion().getId() == idCondicion).findFirst();

			if (valorRemplazar.isPresent()) {
				condicion = condicion.replace(campoRemplazar, String.valueOf(valorRemplazar.get().getValores()));
			}
		}

		return condicion;
	}

	/**
	 * Mapea el valor sobre una condición para operacion entre.
	 *
	 * @param condicion
	 *            Condicion a la cual se va a sustituir el valor del modelo de
	 *            datos.
	 * @param valores
	 *            Valores a sustituir
	 * @param campoRemplazar
	 *            Campos que se van a remplazar en la condicion
	 * @return Condicion con valores sustituidos
	 */
	public String mapearEntre(String condicion, ValoresRegla valores, String campoRemplazar) {

		patron = Pattern.compile(REG_EXP_NUMEROS);
		matcher = patron.matcher(campoRemplazar);

		matcher.find();
		Long idCondicion = Long.valueOf(matcher.group());

		Optional<Valor> valorRemplazar = valores.getValores().stream()
				.filter(valor -> valor.getCondicion().getId() == idCondicion).findFirst();

		if (valorRemplazar.isPresent()) {
			String[] valoresCampos = valorRemplazar.get().getValores().split("#");
			matcher = patron.matcher(campoRemplazar);
			int indiceValor = 0;

			while (matcher.find()) {
				condicion = condicion.replaceFirst("\\$\\{valor_" + idCondicion + "\\}", valoresCampos[indiceValor++]);
			}
		}

		return condicion;
	}

	/**
	 * Mapea el valor sobre una condición para operacion en.
	 *
	 * @param condicion
	 *            Condicion a la cual se va a sustituir el valor del modelo de
	 *            datos.
	 * @param valores
	 *            Valores a sustituir
	 * @param campoRemplazar
	 *            Campos que se van a remplazar en la condicion
	 * @return Condicion con valores sustituidos
	 */
	public String mapearEn(String condicion, ValoresRegla valores, String campoRemplazar) {
		patron = Pattern.compile(REG_EXP_NUMEROS);
		matcher = patron.matcher(campoRemplazar);

		if (matcher.find()) {
			Long idCondicion = Long.valueOf(matcher.group(0));

			Optional<Valor> valorRemplazar = valores.getValores().stream()
					.filter(valor -> valor.getCondicion().getId() == idCondicion).findFirst();

			if (valorRemplazar.isPresent()) {

				patron = Pattern.compile(REG_EXP_UN_PARAMETRO);
				matcher = patron.matcher(campoRemplazar);
				matcher.find();
				condicion = condicion.replace(matcher.group(0), valorRemplazar.get().getValores().replace("#", ","));
			}
		}

		return condicion;
	}

}