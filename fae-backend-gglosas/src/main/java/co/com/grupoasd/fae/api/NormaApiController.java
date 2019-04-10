/*
* Archivo: OperadorApiController.java
* Fecha: 22/10/2018
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
package co.com.grupoasd.fae.api;

import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.entity.Norma;
import co.com.grupoasd.fae.model.repository.NormaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Enpoint REST para gestion de Operadores de facturacion electrónica.
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@RestController
public class NormaApiController implements NormaApi {

    /**
     * OperadorFeRepository.
     */
    private final NormaRepository normaRepository;

    /**
     * Constructor de la clase.
     * @param jwtTokenProvider JwtTokenProvider
     * @param usuarioRepository UsuarioRepository
     * @param operadorFeRepository1 OperadorFeRepository
     */
    @Autowired
    private NormaApiController(final NormaRepository normaRepository) {
        this.normaRepository = normaRepository;
    }

    /**
     * Listar normas
     *
     * @return Listado de normas
     */
    @Override
    public ResponseEntity<List<Norma>> listar() {
        Iterable<Norma> iter = normaRepository.findAll();
        List<Norma> res = new ArrayList<>();
        iter.forEach(res::add);
        return ResponseEntity.ok(res);
    }

    /**
     * Obtiene un registro de una norma
     *
     * @param id Identificador de la Norma
     * @return Registro de Norma
     */
    @Override
    public ResponseEntity<Norma> obtener(final @PathVariable("id") Long id) {
        Optional<Norma> entiOptional = normaRepository.findById(id);
        if (entiOptional.isPresent()) {
            return ResponseEntity.ok(entiOptional.get());
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", id), 404));
        }
    }

    @Override
    public ResponseEntity<List<Norma>> listarXTipo(final @PathVariable("tipo") String tipo) {
        List<Norma> normas = normaRepository.findByTipoOrderByFechaInicioDesc(tipo);
        if (normas != null && !normas.isEmpty()) {
            return ResponseEntity.ok(normas);
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", tipo), 404));
        }
    }

}
