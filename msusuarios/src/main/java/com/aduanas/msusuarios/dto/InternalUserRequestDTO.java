package com.aduanas.msusuarios.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InternalUserRequestDTO {

    private String rut;

    private String nombres;

    private String apellidos;

    private String email;

    private String password;

    private String rol;

}