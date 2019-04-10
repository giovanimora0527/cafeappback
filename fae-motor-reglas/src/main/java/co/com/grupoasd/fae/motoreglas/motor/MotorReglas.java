/*
* Archivo: MotorReglas.java
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

package co.com.grupoasd.fae.motoreglas.motor;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.grupoasd.fae.motoreglas.entidades.Regla;
import co.com.grupoasd.fae.motoreglas.excepciones.MotorException;
import co.com.grupoasd.fae.motoreglas.motor.plantilla.Plantilla;
import co.com.grupoasd.fae.motoreglas.respositorios.RepositorioRegla;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementan el motor de reglas de negocio.
 * 
 * @author Alvaro Cordoba Torres
 */

@Slf4j
@Component
public class MotorReglas {

	/**
	 * Instancia de la clase que expone el CRUD de la entidad regla.
	 */
	private final RepositorioRegla repositorioRegla;

	/**
	 * Instancia de la clase que a partir del modelo de datos crea las reglas a
	 * aplicar.
	 */
	private Plantilla plantilla;

	/**
	 * Constructor de la clase.
	 *
	 * @param repositorioRegla
	 *            Repositorio regla
	 * @param plantilla
	 *            Plantilla de reglas
	 */
	@Autowired
	public MotorReglas(RepositorioRegla repositorioRegla, Plantilla plantilla) {
		super();
		this.repositorioRegla = repositorioRegla;
		this.plantilla = plantilla;
	}

	/**
	 * retorna las condiciones para presentar al usuario.
	 *
	 * @param idsRegla
	 *            Reglas a las que se requieren obtener las condiciones
	 * @return Condiciones en formato texto para el usuario
	 */
	public List<String> getTextoReglas(List<Long> idsRegla) {
		return plantilla.getCondicionesTexto((List<Regla>) repositorioRegla.findByIdIn(idsRegla));
	}

	/**
	 * Aplicar reglas ingresadas a los objetos ingresados.
	 *
	 * @param idsRegla
	 *            id de las reglas a aplicar
	 * @param objetos
	 *            Objetos a los que se le van aplicar las reglas
	 * @throws TemplateException
	 *             lanza excepcion template
	 * @throws IOException
	 *             lanza excepcion I/O.
	 * @throws MotorException
	 *             lanza excepcion motor
	 */
	public void aplicarReglas(List<Long> idsRegla, List<?> objetos)
			throws TemplateException, IOException, MotorException {

		List<Regla> reglas = (List<Regla>) repositorioRegla.findByIdIn(idsRegla);

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		InternalKnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();

		String dslDrools = plantilla.traducirReglas(reglas);
		kbuilder.add(ResourceFactory.newByteArrayResource(dslDrools.getBytes()), ResourceType.DRL);

		if (kbuilder.hasErrors()) {
			log.error(kbuilder.getErrors().toString());
			log.error("---- DSL Error : Inicio ----");
			log.error(dslDrools);
			log.error("---- DSL Error : Fin ----");
			throw new MotorException("Se presentaron errores al cargar las reglas");
		}

		Collection<KiePackage> pkgs = kbuilder.getKnowledgePackages();
		knowledgeBase.addPackages(pkgs);

		StatelessKieSession kieSession = knowledgeBase.newStatelessKieSession();

		// Se Aplican reglas
		kieSession.execute(objetos);
	}

}