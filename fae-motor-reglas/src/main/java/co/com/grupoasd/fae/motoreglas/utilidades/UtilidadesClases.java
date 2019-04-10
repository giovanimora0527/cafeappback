/*
* Archivo: UtilidadesClases.java
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

package co.com.grupoasd.fae.motoreglas.utilidades;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import co.com.grupoasd.fae.motoreglas.entidades.dto.CampoDto;
import co.com.grupoasd.fae.motoreglas.interfaces.FrontValidacion;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Utilidades de reflexión de clases java.
 * 
 * @author Alvaro Cordoba Torres
 */
@Controller
@AllArgsConstructor
public class UtilidadesClases {

	/** Constante ATRIBUTO_ADICIONAL. */
	private static final String ATRIBUTO_ADICIONAL = "serialVersionUID";

	/** Instancia de la clase que retorna la validación del campo. */
        @Autowired
	private FrontValidacion frontValidacion;

	/**
	 * Método que toma una clase y retorna sus atributos en formato lista.
	 *
	 * @param clase
	 *            Clase a la que se le van a obtener los atributos
	 * @return Los atributos en formato de lista
	 */
	public List<CampoDto> getAtributos(Class<?> clase) {
		return getStream(clase).collect(Collectors.toList());
	}

	/**
	 * Método que crea un Stream con los atributos de una clase.
	 *
	 * @param clase
	 *            Clase a la que se le van a obtener los atributos
	 * @return Stream con los atributos de la clase
	 */
	private Stream<CampoDto> getStream(Class<?> clase) {

		Stream<Field> campoSuper = Stream.of(clase.getSuperclass().getDeclaredFields());
		Stream<Field> campos = Stream.of(clase.getDeclaredFields());

		return Stream.concat(campoSuper, campos).filter(campo -> !campo.getName().equals(ATRIBUTO_ADICIONAL))
				.map(campo -> new CampoDto(campo.getName(), campo.getType().getTypeName(),
						frontValidacion.getExpReg(campo.getName())));

	}

}