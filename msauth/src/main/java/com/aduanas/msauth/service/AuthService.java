package com.aduanas.msauth.service;

import com.aduanas.msauth.dto.*;
import com.aduanas.msauth.model.*;

import com.aduanas.msauth.repository.*;

import com.aduanas.msauth.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UsuarioRepository usuarioRepository;
        private final RolRepository rolRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuditoriaRepository auditoriaRepository;

        public AuthResponseDTO register(
                        RegisterRequestDTO request) {

                if (usuarioRepository.existsByEmail(
                                request.getEmail())) {

                        throw new RuntimeException(
                                        "Email ya registrado");
                }

                if (usuarioRepository.existsByRut(
                                request.getRut())) {

                        throw new RuntimeException(
                                        "Rut ya registrado");
                }

                Rol rol = rolRepository
                                .findByNombre("VIAJERO")
                                .orElseThrow();

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
                                "127.0.0.1");

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

                Usuario usuario = usuarioRepository
                                .findByEmail(request.getEmail())
                                .orElse(null);

                if (usuario == null) {

                        throw new RuntimeException(
                                        "Usuario no encontrado");
                }

                if (!passwordEncoder.matches(
                                request.getPassword(),
                                usuario.getPassword())) {

                        registrarAuditoria(
                                        usuario,
                                        "LOGIN",
                                        "FALLIDO",
                                        ip);

                        throw new RuntimeException(
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
                                .orElseThrow();

                return UserProfileDTO.builder()
                                .email(usuario.getEmail())
                                .rol(usuario.getRol().getNombre())
                                .nombres(usuario.getNombres())
                                .apellidos(usuario.getApellidos())
                                .build();
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

        public void changePassword(
                        String email,
                        ChangePasswordDTO request) {

                Usuario usuario = usuarioRepository
                                .findByEmail(email)
                                .orElseThrow();

                if (!passwordEncoder.matches(
                                request.getOldPassword(),
                                usuario.getPassword())) {

                        throw new RuntimeException(
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
                                "127.0.0.1");
        }
/*              METODOS PARA MSUSUARIOS!!!!
        public List<UsuarioResponseDTO> getAllUsers() {

                return usuarioRepository.findAll()
                                .stream()
                                .map(usuario -> UsuarioResponseDTO.builder()
                                                .id(usuario.getId())
                                                .rut(usuario.getRut())
                                                .nombres(usuario.getNombres())
                                                .apellidos(usuario.getApellidos())
                                                .email(usuario.getEmail())
                                                .rol(usuario.getRol().getNombre())
                                                .activo(usuario.getActivo())
                                                .build())
                                .toList();
        }

        public UsuarioResponseDTO getUserById(Long id) {

                Usuario usuario = usuarioRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                return UsuarioResponseDTO.builder()
                                .id(usuario.getId())
                                .rut(usuario.getRut())
                                .nombres(usuario.getNombres())
                                .apellidos(usuario.getApellidos())
                                .email(usuario.getEmail())
                                .rol(usuario.getRol().getNombre())
                                .activo(usuario.getActivo())
                                .build();
        }

        public void desactivarUsuario(Long id) {

                Usuario usuario = usuarioRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                usuario.setActivo(false);

                usuarioRepository.save(usuario);
        }

        public void activarUsuario(Long id) {

                Usuario usuario = usuarioRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                usuario.setActivo(true);

                usuarioRepository.save(usuario);
        }*/
}
