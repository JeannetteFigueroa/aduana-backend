package com.aduanas.msauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    private Long id;

    private String rut;

    private String nombres;

    private String apellidos;

    private String email;

    private String rol;

    private Boolean activo;
}