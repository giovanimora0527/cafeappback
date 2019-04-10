/*
* Archivo: UsuarioServiceImpl.java
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

import co.com.grupoasd.fae.model.entity.TokenRecConstraenha;
import co.com.grupoasd.fae.autenticacion.model.CambioClaveRs;
import co.com.grupoasd.fae.autenticacion.model.RestaurarRq;
import co.com.grupoasd.fae.autenticacion.model.RestaurarRs;
import co.com.grupoasd.fae.model.repository.TokenRecRepository;
import co.com.grupoasd.fae.model.repository.UsuarioRepository;
import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.entity.Entidad;
import co.com.grupoasd.fae.model.entity.Usuario;
import co.com.grupoasd.fae.model.repository.EntidadRepository;
import co.com.grupoasd.fae.util.DateUtil;
import co.com.grupoasd.fae.util.EnvioCorreo;
import co.com.grupoasd.fae.util.SeguridadUtil;
import co.com.grupoasd.fae.util.VariablesEstaticas;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * Servicios de negocio para gestion de usuarios.
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final EntidadRepository entidadRepository;
    private final TokenRecRepository tokenRecRepository;

    @Value("${email.user}")
    private String user;
    @Value("${email.password}")
    private String password;
    @Value("${email.asunto}")
    private String asunto;
    @Value("${email.body}")
    private String cuerpo;
    @Value("${restore.url}")
    private String urlRestore;
    @Value("${restore.asunto}")
    private String restoreAsunto;
    @Value("${restore.body}")
    private String urlBody;

    @Autowired
    public UsuarioServiceImpl(final UsuarioRepository usuarioRepository,
            final EntidadRepository entidadRepository,
            final TokenRecRepository tokenRecRepository) {
        this.usuarioRepository = usuarioRepository;
        this.entidadRepository = entidadRepository;
        this.tokenRecRepository = tokenRecRepository;
    }

    @Override
    public ResponseEntity<Usuario> crearUsuario(Usuario usuario) {
        String clave = SeguridadUtil.generarClave();
        usuario.setPassword(SeguridadUtil.codificarClave(usuario.getIdentificacion(), clave));
        usuario.setFechaCreacion(Date.from(Instant.now()));
        Optional<Entidad> entidadOpt = entidadRepository.findById(usuario.getEntidadId());
        if (entidadOpt.isPresent()) {
            Usuario usuarioActual = usuarioRepository.findByEmail(usuario.getEmail());
            if (usuarioActual != null) {
                throw new BadRequestException(ErrorRs.get("Usuario ya existe",
                        "El usuario ya existe con la misma cuenta de correo", 400));
            }
            ResponseEntity<Usuario> resp = ResponseEntity.ok(usuarioRepository.save(usuario));
            String body = String.format(cuerpo, usuario.getNombreUsuario(), usuario.getEmail(), clave, entidadOpt.get().getNombreEntidad());
            EnvioCorreo.enviar(usuario.getEmail(), asunto, body, user, password);
            return resp;
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    String.format("El recurso con Id = %s no existe.", usuario.getEntidadId()), 400));
        }
    }

    @Override
    public RestaurarRs restablecerContrasena(RestaurarRq usuario) {
        Usuario usuarioActual = usuarioRepository.findByEmailAndIdentificacion(
                StringUtils.trimToEmpty(StringUtils.lowerCase(usuario.getUsuario())),
                usuario.getIdentificacion());
        if (usuarioActual != null) {
            if (usuarioActual.getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
                throw new BadRequestException(ErrorRs.get("Usuario inactivo",
                        "Usuario se encuentra inactivo, por favor contacte al administrador de su entidad.", 400));
            }
            TokenRecConstraenha tokenRec = tokenRecRepository.findByUsuarioIdAndEstado(usuarioActual.getId(), 1);
            String token = SeguridadUtil.codificarClave(LocalDateTime.now().toString(), usuarioActual.getEmail());
            if (tokenRec != null) {
                tokenRec.setEstado(0);
                tokenRecRepository.save(tokenRec);
                guardarTokenRecContrasenha(usuarioActual, token);
            } else {
                guardarTokenRecContrasenha(usuarioActual, token);
            }
            String body = String.format(urlBody, String.format(urlRestore, token));
            EnvioCorreo.enviar(usuarioActual.getEmail(), restoreAsunto, body, user, password);
        } else {
            throw new NotFoundException(ErrorRs.get("Datos inválidos", "Datos incorrectos, por favor verifique", 404));
        }

        return new RestaurarRs("200", "Se ha enviado a su correo un enlace para restablecer");
    }

    @Override
    public CambioClaveRs cambioClave(String usuario, String claveActual, String claveNueva) {
        Usuario usuarioActual = usuarioRepository.findByEmail(
                StringUtils.trimToEmpty(StringUtils.lowerCase(usuario)));
        if (usuarioActual != null) {
            if (claveNueva.contains(usuario)) {
                throw new BadRequestException(ErrorRs.get("Clave contiene correo",
                        "La clave no puede contener al usuario, por favor verifique.", 400));
            }
            String claveActualCodif = SeguridadUtil.codificarClave(usuarioActual.getIdentificacion(), claveActual);
            Matcher matcher = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!-\\/:-@])[a-zA-Z\\d!-\\/:-@]{8,}$").matcher(claveNueva);

            if (!usuarioActual.getPassword().equals(claveActualCodif)) {
                // Si clave recibida no coincide con la actual
                throw new BadRequestException(ErrorRs.get("Clave no coincide con la actual",
                        "La clave ingresada no coincide con la clave actual, por favor verifique.", 400));
            }
            if (!matcher.find()) {
                // Si clave no cumple con las validaciones requeridas
                throw new BadRequestException(ErrorRs.get("Clave no cumple",
                        "Debe contener al menos un número, una letra minúscula, una mayúscula y un simbolo, por favor verifique.", 400));
            }
            if (claveActual.equals(claveNueva)) {
                throw new BadRequestException(ErrorRs.get("Clave igual",
                        "La clave ingresada es igual a la actual, por favor verifique.", 400));
                // Si clave es igual a la anterior
            }

            if (usuarioActual.getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
                throw new BadRequestException(ErrorRs.get("Usuario inactivo",
                        "Usuario se encuentra inactivo, por favor contacte al administrador de su entidad.", 400));
            }

            usuarioActual.setPassword(SeguridadUtil.codificarClave(usuarioActual.getIdentificacion(), claveNueva));
            usuarioRepository.save(usuarioActual);
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso", "Usuario no existe, por favor verifique", 400));
        }
        return new CambioClaveRs(VariablesEstaticas.STATUS_CAMBIO);
    }

    private void guardarTokenRecContrasenha(Usuario usuarioActual, String tokenStr) {
        TokenRecConstraenha token = new TokenRecConstraenha();
        token.setUsuarioId(usuarioActual.getId());
        token.setEstado(1);
        token.setFechaCreacion(DateUtil.nowDateTime());
        token.setToken(tokenStr);
        tokenRecRepository.save(token);
    }

    @Override
    public RestaurarRs validar(String token) {
        TokenRecConstraenha tokenRec = tokenRecRepository.findByToken(token);
        if (tokenRec != null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            //Period period = DateUtil.getPeriod(tokenRec.getFechaCreacion(), localDateTime);
            long time[] = DateUtil.getTime(tokenRec.getFechaCreacion(), localDateTime);
            if (time[0] >= 24) {
                throw new BadRequestException(ErrorRs.get("Enlace caducado",
                        "El recurso al que intenta acceder ya superó el periodo de vida", 400));
            }
            if (tokenRec.getEstado() == 0) {
                throw new BadRequestException(ErrorRs.get("Enlace utilizado",
                        "El recurso al que intenta acceder ya fue utilizado", 400));
            }
            Optional<Usuario> usuario = usuarioRepository.findById(tokenRec.getUsuarioId());
            if (usuario.isPresent()) {
                if (usuario.get().getEstado() == 0) {
                    throw new BadRequestException(ErrorRs.get("Usuario inactivo",
                            "Usuario se encuentra inactivo, por favor contacte al administrador de su entidad.", 400));
                }
            }
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    "El recurso no existe.", 400));
        }
        return new RestaurarRs("200", "Exitoso");
    }

    @Override
    public RestaurarRs restablecerClave(String token, String claveNueva) {
        TokenRecConstraenha tokenRec = tokenRecRepository.findByToken(token);
        if (tokenRec != null) {
            if (tokenRec.getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
                throw new BadRequestException(ErrorRs.get("Enlace utilizado",
                        "El recurso al que intenta acceder ya fue utilizado", 400));
            }
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(tokenRec.getUsuarioId());
            if (usuarioOpt.isPresent()) {
                if (usuarioOpt.get().getEstado() == VariablesEstaticas.ESTADO_INACTIVO) {
                    throw new BadRequestException(ErrorRs.get("Usuario inactivo",
                            "Usuario se encuentra inactivo, por favor contacte al administrador de su entidad.", 400));
                }
                if (claveNueva.contains(usuarioOpt.get().getEmail())) {
                    throw new BadRequestException(ErrorRs.get("Clave contiene correo",
                            "La clave no puede contener al usuario, por favor verifique.", 400));
                }
                Matcher matcher = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!-\\/:-@])[a-zA-Z\\d!-\\/:-@]{8,}$").matcher(claveNueva);
                if (!matcher.find()) {
                    // Si clave no cumple con las validaciones requeridas
                    throw new BadRequestException(ErrorRs.get("Clave no cumple",
                            "Debe contener al menos un número, una letra minúscula, una mayúscula y un simbolo, por favor verifique.", 400));
                }
                Usuario usuarioActual = usuarioOpt.get();
                tokenRec.setEstado(VariablesEstaticas.ESTADO_INACTIVO);
                tokenRecRepository.save(tokenRec);
                usuarioActual.setPassword(SeguridadUtil.codificarClave(usuarioActual.getIdentificacion(), claveNueva));
                usuarioActual.setFechaModificacion(Date.from(Instant.now()));
                usuarioActual.setModificadoPor(usuarioActual.getId());
                usuarioRepository.save(usuarioActual);
            } else {
                throw new NotFoundException(ErrorRs.get("No Existe Recurso", "Usuario no existe, por favor verifique", 404));
            }
        } else {
            throw new NotFoundException(ErrorRs.get("No Existe Recurso",
                    "El recurso no existe.", 404));
        }

        return new RestaurarRs(VariablesEstaticas.STATUS_CAMBIO);
    }

}
