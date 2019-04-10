/*
* Archivo: RepositorioObjetoNegocio.java
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

package co.com.grupoasd.fae.motoreglas.respositorios;

import org.springframework.data.repository.CrudRepository;

import co.com.grupoasd.fae.motoreglas.entidades.ObjetoNegocio;

/**
 * Interfaz que provee el CRUD de la entidad objeto negocio.
 * 
 * @author Alvaro Cordoba Torres
 */

public interface RepositorioObjetoNegocio extends CrudRepository<ObjetoNegocio, Long> {

	/**
	 * Método que busca una entidad basado en su nombre.
	 *
	 * @param nombre
	 *            nombre de la entidad a buscar
	 * @return Entidad que tiene el nombre ingresado
	 */
	public ObjetoNegocio findByNombre(String nombre);

}