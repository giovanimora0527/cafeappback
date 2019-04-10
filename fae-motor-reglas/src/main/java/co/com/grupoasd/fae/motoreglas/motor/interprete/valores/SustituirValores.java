/*
* Archivo: SustituirValores.java
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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.grupoasd.fae.motoreglas.entidades.Regla;
import co.com.grupoasd.fae.motoreglas.entidades.ValoresRegla;

/**
 * Realiza la sustitucion de valores a las condiciones.
 * 
 * @author Alvaro Cordoba Torres
 */

@Component
public class SustituirValores {

	/** Constante expresion regular para operador en. */
	private static final String REG_EXP_EN = "in \\(\\$\\{valor_\\d+\\}\\)";

	/** Constante expresion regular para operador entre. */
	private static final String REG_EXP_ENTRE = "\\$\\{valor_\\d+\\} < valor && valor < \\$\\{valor_\\d+\\}";

	/** Constante expresion regular para operadores estandar. */
	private static final String REG_EXP_UN_PARAMETRO = "\\$\\{valor_\\d+\\}";

	/** Variable para identificar reglas. */
	private long indiceRegla;

	/**
	 * Instancia de la clase que implementa casos de como remplazar valores sobre
	 * condiciones.
	 */
	private final MapearValores mapearValores;

	/**
	 * Constructor de la clase.
	 *
	 * @param mapearValores
	 *            Clase que implementa casos de como remplazar valores sobre
	 *            condiciones
	 */
	@Autowired
	public SustituirValores(MapearValores mapearValores) {
		super();
		this.indiceRegla = 0;
		this.mapearValores = mapearValores;
	}

	/**
	 * Sustituye texto por los valores asignado en el modelo de datos.
	 *
	 * @param regla
	 *            Regla a sustituir valores
	 * @param condicion
	 *            Condicion a sustituir valores
	 * @return Las condiciones con valores asignados en texto para presentar al
	 *         usuario
	 */
	public List<String> sustituir(Regla regla, String condicion) {

		List<ValoresRegla> valores = regla.getValoresRegla();
		List<String> condicionesTexto = new ArrayList<>();

		for (ValoresRegla valor : valores) {
			condicionesTexto.add(mapear(condicion, valor));
		}

		return condicionesTexto;
	}

	/**
	 * Sustituye texto por los valores asignado en el modelo de datos.
	 *
	 * @param regla
	 *            Regla a sustituir valores
	 * @param stringRegla
	 *            Texto de la regla en DSL Drools
	 */
	public void sustituir(Regla regla, StringWriter stringRegla) {

		StringBuilder stringValores = new StringBuilder();
		indiceRegla = 0;
		long idRegla = regla.getId();

		regla.getValoresRegla().forEach(valores -> {

			String textoReglaTemp = stringRegla.toString();

			// Asingnar valores
			textoReglaTemp = mapear(textoReglaTemp, valores);

			// Valor resultado
			if (textoReglaTemp.contains("${valorResultado}")) {
				textoReglaTemp = textoReglaTemp.replace("${valorResultado}", valores.getValorResultado().getValores());
			}

			textoReglaTemp = textoReglaTemp.replace("!{numeroRegla}", String.valueOf((idRegla * 200) + indiceRegla++));

			stringValores.append(textoReglaTemp);

		});

		// limpia el buffer
		stringRegla.getBuffer().setLength(0);
		stringRegla.write(stringValores.toString());
	}

	/**
	 * Sustituye el valor en la condición basado en el tipo de condición.
	 *
	 * @param condicion
	 *            La condición a asignar valor.
	 * @param valores
	 *            El valor a sustituir
	 * @return La condición con el valor asignado
	 */
	private String mapear(String condicion, ValoresRegla valores) {

		Pattern patron = Pattern.compile(REG_EXP_EN);
		Matcher matcher = patron.matcher(condicion);

		while (matcher.find()) {
			condicion = mapearValores.mapearEn(condicion, valores, matcher.group());
		}

		patron = Pattern.compile(REG_EXP_ENTRE);
		matcher = patron.matcher(condicion);

		while (matcher.find()) {
			condicion = mapearValores.mapearEntre(condicion, valores, matcher.group());
		}

		patron = Pattern.compile(REG_EXP_UN_PARAMETRO);
		matcher = patron.matcher(condicion);

		while (matcher.find()) {
			condicion = mapearValores.mapearValor(condicion, valores, matcher.group());
		}

		return condicion;
	}

}