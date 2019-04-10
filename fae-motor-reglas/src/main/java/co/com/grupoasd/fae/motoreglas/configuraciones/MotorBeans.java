/*
* Archivo: MotorBeans.java
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

package co.com.grupoasd.fae.motoreglas.configuraciones;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import co.com.grupoasd.fae.motoreglas.entidades.ObjetoNegocio;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioObjetoNegocio;

/**
 * Clase donde se configuran y se agregan componentes al contexto de Spring.
 *
 * @author Alvaro Cordoba Torres
 */

@Configuration
@EnableJpaAuditing
public class MotorBeans {

	/**
	 * Instancia de la clase que expone el CRUD de la entidad objeto de negocio.
	 */
	private final RepositorioObjetoNegocio repositorioObjetoNegocio;

	/** ruta de los objeto de negocio. */
	@Value("${motorReglas.paqueteObjetoNegocio}")
	private String paqueteNegocio;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioObjetoNegocio
	 *            Repositorio objeto negocio
	 */
	@Autowired
	public MotorBeans(RepositorioObjetoNegocio repositorioObjetoNegocio) {
		super();
		this.repositorioObjetoNegocio = repositorioObjetoNegocio;

	}

	/**
	 * Método que carga en el primer cargue de la aplicación las entidades de
	 * negocio disponibles para aplicar reglas.
	 */
	@PostConstruct
	private void init() {
		Reflections reflections = new Reflections(paqueteNegocio, new SubTypesScanner(false));
		Set<Class<? extends Serializable>> clases = reflections.getSubTypesOf(Serializable.class);
		Set<ObjetoNegocio> entidades = new LinkedHashSet<>();

		for (Class<? extends Serializable> clase : clases) {
			String nombreClase = clase.getSimpleName();
			if (repositorioObjetoNegocio.findByNombre(nombreClase) == null) {
				entidades.add(new ObjetoNegocio(nombreClase));
			}
		}

		if (!entidades.isEmpty()) {
			repositorioObjetoNegocio.saveAll(entidades);
		}
	}

}