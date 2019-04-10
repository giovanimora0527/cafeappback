/*
* Archivo: AutenticarServiceImpl.java
* Fecha: 19/12/2018
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
package co.com.grupoasd.fae.autenticacion.service;

import co.com.grupoasd.fae.model.entity.Token;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.autenticacion.model.AutenticarRq;
import co.com.grupoasd.fae.autenticacion.model.AutenticarRs;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.autenticacion.security.JwtTokenProvider;
import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.model.entity.Entidad;
import co.com.grupoasd.fae.model.repository.EntidadRepository;
import co.com.grupoasd.fae.util.SeguridadUtil;
import co.com.grupoasd.fae.util.SesionUsuario;
import co.com.grupoasd.fae.util.VariablesEstaticas;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicios de negocio para autenticacion de usuarios.
 *
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
@Service
@Transactional
public class AutenticarServiceImpl implements AutenticarService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PerfilService perfilService;
    private final EntidadRepository entidadRepository;

    public AutenticarServiceImpl(UsuarioRepository usuarioRepository, TokenService tokenService,
            JwtTokenProvider jwtTokenProvider, PerfilService perfilService,
            EntidadRepository entidadRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.perfilService = perfilService;
        this.entidadRepository = entidadRepository;
    }

    @Override
    public AutenticarRs autenticar(AutenticarRq autenticarRq, SesionUsuario sesionUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(
                StringUtils.trimToEmpty(StringUtils.lowerCase(autenticarRq.getUsuario())));
        if (usuario == null) {
            // Usuario invalido
            throw new BadRequestException(ErrorRs.get("Credenciales no válidas",
                    "Credenciales no válidas, por favor verifique.", 400));
        }
        if (!usuario.getPassword().equals(SeguridadUtil.codificarClave(usuario.getIdentificacion(), autenticarRq.getClave()))) {
            // Clave invalida
            throw new BadRequestException(ErrorRs.get("Credenciales no válidas",
                    "Credenciales no válidas, por favor verifique.", 400));
        } else if (usuario.getEstado() == 0) {
            // Usuario y clave validas pero inactivo
            throw new BadRequestException(ErrorRs.get("Usuario inactivo",
                    "Usuario se encuentra inactívo, por favor contacte al administrador de su entidad.", 400));
        } else {
            // Usuario y clave validas
            Optional<Entidad> entidad = entidadRepository.findById(usuario.getEntidadId());
            if (entidad.isPresent()) {
                if (entidad.get().getEstado() == VariablesEstaticas.ESTADO_ACTIVO) {
                    String strToken = jwtTokenProvider.generar(usuario);

                    return new AutenticarRs(VariablesEstaticas.STATUS_AUTENTICADO, strToken,
                            perfilService.obtenerMenuPerfil(usuario.getPerfilId(), true));
                } else {
                    throw new BadRequestException(ErrorRs.get("Usuario inactivo",
                            "Entidad inactíva, por favor contacte al administrador de su entidad.", 400));
                }
            }
            throw new BadRequestException(ErrorRs.get("Entidad no existe",
                    "Usuario no se encuentra asociado a una entidad, por favor contacte al administrador de su entidad.", 400));
        }
    }

    @Override
    public void cerrarSesion(String token, SesionUsuario sesionUsuario) {
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        if (auth != null) {
            UserDetails user = (UserDetails) auth.getPrincipal();
            Usuario us = usuarioRepository.findByEmail(user.getUsername());
            if (us != null) {
                Optional<Token> optToken = tokenService.obtener(us.getId());
                if (optToken.isPresent()) {
                    tokenService.eliminar(us.getId());
                }
            }
        }
    }

}
