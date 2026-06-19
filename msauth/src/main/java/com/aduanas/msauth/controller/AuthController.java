package com.aduanas.msauth.controller;

import com.aduanas.msauth.dto.*;

import com.aduanas.msauth.service.AuthService;

import jakarta.validation.Valid;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDTO register(
            @Valid @RequestBody RegisterRequestDTO request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(
            @Valid @RequestBody LoginRequestDTO request) {

        return authService.login(request);
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
}