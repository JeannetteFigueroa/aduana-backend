package com.aduanas.msauth.controller;

import com.aduanas.msauth.dto.AuthResponseDTO;
import com.aduanas.msauth.dto.ChangePasswordDTO;
import com.aduanas.msauth.dto.InternalUserRequestDTO;
import com.aduanas.msauth.dto.InternalUserResponseDTO;
import com.aduanas.msauth.dto.LoginRequestDTO;
import com.aduanas.msauth.dto.RegisterRequestDTO;
import com.aduanas.msauth.dto.UserProfileDTO;
import com.aduanas.msauth.service.AuthService;
import com.aduanas.msauth.dto.InternalUserRequestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @PostMapping("/register")
        public ResponseEntity<AuthResponseDTO> register(
                        @Valid @RequestBody RegisterRequestDTO request,
                        HttpServletRequest httpRequest) {

                String ip = httpRequest.getRemoteAddr();

                AuthResponseDTO response = authService.register(request, ip);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @PostMapping("/internal/register")
        public InternalUserResponseDTO registerInternal(
                        @RequestBody InternalUserRequestDTO request) {

                return authService.registerInternal(request);

        }

        @PostMapping("/login")
        public AuthResponseDTO login(
                        @Valid @RequestBody LoginRequestDTO request,
                        HttpServletRequest httpRequest) {

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
                        @RequestBody ChangePasswordDTO request,
                        HttpServletRequest httpRequest) {

                Authentication auth = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                String ip = httpRequest.getRemoteAddr();

                authService.changePassword(
                                auth.getName(),
                                request,
                                ip);

                return ResponseEntity.ok(
                                "Contraseña actualizada");
        }

        @GetMapping("/protected")
        public ResponseEntity<?> protectedEndpoint() {

                return ResponseEntity.ok(
                                "JWT válido");
        }

        @GetMapping("/admin")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> adminOnly() {

                return ResponseEntity.ok(
                                "Acceso autorizado para ADMIN");
        }

        @GetMapping("/funcionario")
        @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
        public ResponseEntity<?> funcionarioOnly() {

                return ResponseEntity.ok(
                                "Acceso autorizado para funcionario");
        }
}
