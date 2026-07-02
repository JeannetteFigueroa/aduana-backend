package com.aduanas.msusuarios.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private String token;

    private String email;

    private String rol;
}