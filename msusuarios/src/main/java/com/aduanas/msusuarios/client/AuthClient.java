package com.aduanas.msusuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.aduanas.msusuarios.dto.AuthResponseDTO;
import com.aduanas.msusuarios.dto.InternalUserRequestDTO;
import com.aduanas.msusuarios.dto.InternalUserResponseDTO;

@FeignClient(name = "ms-auth")
public interface AuthClient {

        @PostMapping("/api/auth/internal/register")
        InternalUserResponseDTO crearUsuario(
                        @RequestBody InternalUserRequestDTO request);

}