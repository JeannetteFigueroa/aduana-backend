package com.aduanas.msauth.service;

import com.aduanas.msauth.dto.AuthResponseDTO;
import com.aduanas.msauth.dto.ChangePasswordDTO;
import com.aduanas.msauth.dto.LoginRequestDTO;
import com.aduanas.msauth.dto.RegisterRequestDTO;
import com.aduanas.msauth.dto.UserProfileDTO;
import com.aduanas.msauth.exception.*;
import com.aduanas.msauth.model.AuditoriaAcceso;
import com.aduanas.msauth.model.Rol;
import com.aduanas.msauth.model.Usuario;
import com.aduanas.msauth.repository.AuditoriaRepository;
import com.aduanas.msauth.repository.RolRepository;
import com.aduanas.msauth.repository.UsuarioRepository;
import com.aduanas.msauth.security.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UsuarioRepository usuarioRepository;
        private final RolRepository rolRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuditoriaRepository auditoriaRepository;

        public AuthResponseDTO register(
                        RegisterRequestDTO request,
                        String ip) {

                if (usuarioRepository.existsByEmail(
                                request.getEmail())) {

                        throw new EmailDuplicadoException(
                                        "Email ya registrado");
                }

                if (usuarioRepository.existsByRut(
                                request.getRut())) {

                        throw new RutDuplicadoException(
                                        "Rut ya registrado");
                }

                Rol rol = rolRepository
                                .findByNombre("VIAJERO")
                                .orElseThrow(() -> new RuntimeException(
                                                "Rol por defecto VIAJERO no existe"));

                Usuario usuario = Usuario.builder()
                                .rut(request.getRut())
                                .nombres(request.getNombres())
                                .apellidos(request.getApellidos())
                                .email(request.getEmail())
                                .password(
                                                passwordEncoder.encode(
                                                                request.getPassword()))
                                .activo(true)
                                .rol(rol)
                                .build();

                usuarioRepository.save(usuario);

                registrarAuditoria(
                                usuario,
                                "REGISTER",
                                "EXITOSO",
                                ip);

                String token = jwtService.generateToken(
                                usuario);

                return AuthResponseDTO.builder()
                                .token(token)
                                .email(usuario.getEmail())
                                .rol(rol.getNombre())
                                .build();
        }

        public AuthResponseDTO login(
                        LoginRequestDTO request,
                        String ip) {

                Optional<Usuario> usuarioOpt = usuarioRepository
                                .findByEmail(request.getEmail());

                Usuario usuario = usuarioOpt.orElse(null);

                if (usuario == null || !passwordEncoder.matches(
                                request.getPassword(),
                                usuario.getPassword())) {

                        if (usuario != null) {
                                registrarAuditoria(
                                                usuario,
                                                "LOGIN",
                                                "FALLIDO",
                                                ip);
                        }

                        throw new CredencialesInvalidasException(
                                        "Credenciales inválidas");
                }

                String token = jwtService.generateToken(
                                usuario);

                registrarAuditoria(
                                usuario,
                                "LOGIN",
                                "EXITOSO",
                                ip);

                return AuthResponseDTO.builder()
                                .token(token)
                                .email(usuario.getEmail())
                                .rol(usuario.getRol().getNombre())
                                .build();
        }

        public UserProfileDTO getProfile(
                        String email) {

                Usuario usuario = usuarioRepository
                                .findByEmail(email)
                                .orElseThrow(() -> new UsuarioNoEncontradoException(
                                                "Usuario no encontrado"));

                return UserProfileDTO.builder()
                                .email(usuario.getEmail())
                                .rol(usuario.getRol().getNombre())
                                .nombres(usuario.getNombres())
                                .apellidos(usuario.getApellidos())
                                .build();
        }

        public void changePassword(
                        String email,
                        ChangePasswordDTO request,
                        String ip) {

                Usuario usuario = usuarioRepository
                                .findByEmail(email)
                                .orElseThrow(() -> new UsuarioNoEncontradoException(
                                                "Usuario no encontrado"));

                if (!passwordEncoder.matches(
                                request.getOldPassword(),
                                usuario.getPassword())) {

                        throw new ContrasenaIncorrectaException(
                                        "Contraseña incorrecta");
                }

                usuario.setPassword(
                                passwordEncoder.encode(
                                                request.getNewPassword()));

                usuarioRepository.save(usuario);

                registrarAuditoria(
                                usuario,
                                "CHANGE_PASSWORD",
                                "EXITOSO",
                                ip);
        }

        private void registrarAuditoria(
                        Usuario usuario,
                        String accion,
                        String resultado,
                        String ip) {

                AuditoriaAcceso auditoria = new AuditoriaAcceso();

                auditoria.setUsuario(usuario);

                auditoria.setAccion(accion);

                auditoria.setResultado(resultado);

                auditoria.setFecha(
                                java.time.LocalDateTime.now());

                auditoria.setIpOrigen(ip);

                auditoriaRepository.save(auditoria);

        }
}
