/*
* Archivo: TipoDocumentoRepository.java
* Fecha: 24/09/2018
* Todos los derechos de propiedad intelectual e industrial sobre esta
* aplicacion son de propiedad exclusiva del GRUPO ASESORIA EN
* SISTEMATIZACION DE DATOS SOCIEDAD POR ACCIONES SIMPLIFICADA GRUPO ASD
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
package co.com.grupoasd.fae.model.repository;

import co.com.grupoasd.fae.model.entity.TipologiaDocumento;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repositorio de la entidad TipoDocumento.
 *
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public interface TipologiaDocumentoRepository extends PagingAndSortingRepository<TipologiaDocumento, Long> {

    /**
     * Busca una tipologia de documento por status.
     *
     * @param status Status a buscar.
     * @param pageable Pageable
     * @return Page<TipologiaDocumento>
     */
    Page<TipologiaDocumento> findByEstado(Boolean status, Pageable pageable);

    List<TipologiaDocumento> findByEntidadId(String entidadId);
}
