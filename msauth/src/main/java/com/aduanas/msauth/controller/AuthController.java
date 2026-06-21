package com.aduanas.msauth.controller;

import com.aduanas.msauth.dto.*;

import com.aduanas.msauth.service.AuthService;

import jakarta.validation.Valid;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @PostMapping("/register")
        public AuthResponseDTO register(
                        @Valid @RequestBody RegisterRequestDTO request) {

                return authService.register(request); /* cambiar por httpstatus */
        }

        @PostMapping("/login")
        public AuthResponseDTO login(
                        @Valid @RequestBody LoginRequestDTO request,
                        jakarta.servlet.http.HttpServletRequest httpRequest) {

                String ip = httpRequest.getRemoteAddr();

                return authService.login(
                                request,
                                ip);
        }

        @GetMapping("/me")
        public UserProfileDTO me(
                        Principal principal) {

                return authService.getProfile(
                                principal.getName());
        }

        @GetMapping("/validate")
        public ResponseEntity<?> validate() {

                Authentication auth = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                return ResponseEntity.ok(
                                Map.of(
                                                "valid", true,
                                                "email", auth.getName()));
        }

        @PostMapping("/change-password")
        public ResponseEntity<?> changePassword(
                        @RequestBody ChangePasswordDTO request) {

                Authentication auth = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                authService.changePassword(
                                auth.getName(),
                                request);

                return ResponseEntity.ok(
                                "Contraseña actualizada");
        }

        /*      MIGRAN A OTRO MICROSERVICIO
        @GetMapping("/protected")
        public String protectedEndpoint() {

                return "JWT válido";
        }

        @GetMapping("/admin")
        @PreAuthorize("hasRole('ADMIN')")
        public String adminOnly() {

                return "Acceso autorizado para ADMIN";
        }

        @GetMapping("/funcionario")
        @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
        public String funcionarioOnly() {

                return "Acceso autorizado para funcionario";
        }

        @GetMapping("/admin/test")
        @PreAuthorize("hasRole('ADMIN')")
        public String adminTest() {

                return "Acceso ADMIN correcto";
        }

        @GetMapping("/funcionario/test")
        @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
        public String funcionarioTest() {

                return "Acceso FUNCIONARIO correcto";
        }

        @GetMapping("/viajero/test")
        @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO','VIAJERO')")
        public String viajeroTest() {

                return "Acceso VIAJERO correcto";
        }
*/
/* 
        METODOS ADMINISTRATIVOS PARA MSUSUARIOS!!
        @GetMapping("/users")
        public List<UsuarioResponseDTO> getAllUsers() {

                return authService.getAllUsers();
        }

        @GetMapping("/users/{id}")
        public UsuarioResponseDTO getUserById(
                        @PathVariable Long id) {

                return authService.getUserById(id);
        }

        @PutMapping("/users/{id}/disable")
        public ResponseEntity<?> disableUser(
                        @PathVariable Long id) {

                authService.desactivarUsuario(id);

                return ResponseEntity.ok("Usuario desactivado");
        }

        @PutMapping("/users/{id}/enable")
        public ResponseEntity<?> enableUser(
                        @PathVariable Long id) {

                authService.activarUsuario(id);

                return ResponseEntity.ok("Usuario activado");
        }
*/
        
}