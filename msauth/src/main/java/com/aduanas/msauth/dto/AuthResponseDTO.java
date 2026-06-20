package com.aduanas.msauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {

    private String token;

    private String email;

    private String rol;
}