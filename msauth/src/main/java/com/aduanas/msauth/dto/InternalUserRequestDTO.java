package com.aduanas.msauth.dto;

import lombok.Data;

@Data
public class InternalUserRequestDTO {

    private String rut;

    private String nombres;

    private String apellidos;

    private String email;

    private String rol;

}