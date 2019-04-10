/*
* Archivo: Plantilla.java
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

package co.com.grupoasd.fae.motoreglas.motor.plantilla;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.com.grupoasd.fae.motoreglas.entidades.Regla;
import co.com.grupoasd.fae.motoreglas.motor.interprete.Interprete;
import co.com.grupoasd.fae.motoreglas.motor.interprete.valores.SustituirValores;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Coordina la creación del archivo con todas las regla en DSL Drools.
 * 
 * @author Alvaro Cordoba Torres
 */

@Component
public class Plantilla {

	/**
	 * Instancia de la clase de freemarker que configura administra las plantillas.
	 */
	private final Configuration configuracionPlantilla;

	/**
	 * Instancia de la clase que realiza la sustitucion de valores a las
	 * condiciones.
	 */
	private final SustituirValores sustituirValores;

	/**
	 * Instancia de la clase que realiza la conversión de información del modelo de
	 * datos al DSL de Drools.
	 */
	private final Interprete interprete;

	/** Constante con la ubicación de los objetos de negocio. */
	@Value("${motorReglas.paqueteObjetoNegocio}")
	private String paqueteObjetoNegocio;

	/**
	 * Constructor de la clase.
	 *
	 * @param configuracionPlantilla
	 *            Clase de freemarker que configura administra las plantillas
	 * @param asignarValores
	 *            Clase que realiza la sustitucion de valores a las condiciones
	 * @param interprete
	 *            Clase que realiza la conversión de información del modelo de datos
	 *            al DSL de Drools
	 */
	@Autowired
	public Plantilla(Configuration configuracionPlantilla, SustituirValores sustituirValores, Interprete interprete) {
		super();
		this.configuracionPlantilla = configuracionPlantilla;
		this.sustituirValores = sustituirValores;
		this.interprete = interprete;
	}

	/**
	 * Traduce las reglas del modelo de datos a DSL Drools un solo texto.
	 *
	 * @param reglas
	 *            Reglas a aplicar
	 * @return Las reglas en DSL Drools
	 * @throws TemplateException
	 *             Lanza excepción de plantilla.
	 * @throws IOException
	 *             Lanza excepción I/O
	 */
	public String traducirReglas(List<Regla> reglas) throws TemplateException, IOException {

		StringWriter stringCuerpo = new StringWriter();
		Map<String, Object> entradasCuerpo = new HashMap<>();

		entradasCuerpo.put("paqueteObjetoNegocio", paqueteObjetoNegocio);
		entradasCuerpo.put("reglas", getReglas(reglas));

		Template plantilla = configuracionPlantilla.getTemplate("plantillaCuerpo.ftl");
		plantilla.process(entradasCuerpo, stringCuerpo);

		return stringCuerpo.toString();

	}

	/**
	 * Traduce las reglas del modelo de datos a DSL Drools.
	 *
	 * @param reglas
	 *            Reglas a aplicar
	 * @return Las reglas en DSL Drools
	 * @throws TemplateException
	 *             lanza excepcion template
	 * @throws IOException
	 *             lanza excepcion I/O.
	 */
	private String getReglas(List<Regla> reglas) throws TemplateException, IOException {

		StringWriter stringReglas = new StringWriter();

		for (Regla regla : reglas) {

			StringWriter stringRegla = new StringWriter();
			Map<String, Object> camposRegla = new HashMap<>();

			camposRegla.put("ordenRegla", regla.getOrden());
			camposRegla.put("objetosNegocio", interprete.getObjetosNegocio(regla.getObjetoNegocioCondicion()));
			camposRegla.put("condiciones", interprete.getCondiciones(regla.getObjetoNegocio(), regla.getCondiciones()));
			camposRegla.put("acciones", interprete.getAcciones(regla.getAcciones()));

			Template plantilla = configuracionPlantilla.getTemplate("plantillaRegla.ftl");
			plantilla.process(camposRegla, stringRegla);

			// Agregar Valores
			if (!regla.getValoresRegla().isEmpty()) {
				sustituirValores.sustituir(regla, stringRegla);
			} else {
				String textoTemp = stringRegla.toString();
				textoTemp = textoTemp.replace("!{numeroRegla}", String.valueOf(regla.getId()));
				stringRegla.getBuffer().setLength(0);
				stringRegla.write(textoTemp);
			}

			stringReglas.append(stringRegla.toString());
		}

		return stringReglas.toString();

	}

	/**
	 * Obtiene todas las condiciones en formato texto para presentar al usuario a
	 * partir una lista de reglas.
	 *
	 * @param reglas
	 *            Reglas a obtener condiciones
	 * @return Condiciones en formato texto para presentar al usuario
	 */
	public List<String> getCondicionesTexto(List<Regla> reglas) {

		List<String> reglasTexto = new ArrayList<>();

		for (Regla regla : reglas) {
			String condicion = interprete.getCondiciones(regla.getCondiciones());
			if (!regla.getValoresRegla().isEmpty()) {
				reglasTexto.addAll(sustituirValores.sustituir(regla, condicion));
			} else {
				reglasTexto.add(condicion);
			}

		}

		return reglasTexto;
	}

}