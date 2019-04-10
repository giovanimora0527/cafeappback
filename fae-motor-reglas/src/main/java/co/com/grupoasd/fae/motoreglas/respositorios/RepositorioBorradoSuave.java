/*
* Archivo: RepositorioBorradoSuave.java
* Fecha: 02/11/2018
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

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import co.com.grupoasd.fae.motoreglas.entidades.EntidadBase;

/**
 * Interfaz que implementa borrado suave.
 *
 * @param <T>
 *            Entidad del repositorio
 * @param <ID>
 *            Tipo de dato del id de la entidad
 */

@Transactional
@NoRepositoryBean
public interface RepositorioBorradoSuave<T extends EntidadBase, ID extends Long> extends CrudRepository<T, ID> {

	@Override
	@Transactional(readOnly = true)
	@Query("select e from #{#entityName} e where e.estaEliminado = false")
	Iterable<T> findAll();

	@Transactional(readOnly = true)
	@Query("select e from #{#entityName} e where e.id in ?1 and e.estaEliminado = false")
	Iterable<T> findAll(Iterable<ID> ids);

	@Override
	@Transactional(readOnly = true)
	@Query("select e from #{#entityName} e where e.id = ?1 and e.estaEliminado = false")
	Optional<T> findById(ID id);

	/**
	 * método que retorna todos las entidades sin eliminar.
	 *
	 * @return las entidades sin eliminar
	 */
	@Query("select e from #{#entityName} e where e.estaEliminado = true")
	@Transactional(readOnly = true)
	List<T> findInactive();

	@Override
	@Transactional(readOnly = true)
	@Query("select count(e) from #{#entityName} e where e.estaEliminado = false")
	long count();

	@Transactional(readOnly = true)
	default boolean exists(ID id) {
		return findById(id).isPresent();
	}

	@Override
	@Query("update #{#entityName} e set e.estaEliminado = true where e.id = ?1")
	@Transactional
	@Modifying
	void deleteById(Long id);

	@Override
	@Transactional
	default void delete(T entity) {
		deleteById(entity.getId());
	}

	@Override
	@Transactional
	default void deleteAll(Iterable<? extends T> entities) {
		entities.forEach(entitiy -> deleteById(entitiy.getId()));
	}

	@Override
	@Query("update #{#entityName} e set e.estaEliminado = true")
	@Transactional
	@Modifying
	void deleteAll();
}