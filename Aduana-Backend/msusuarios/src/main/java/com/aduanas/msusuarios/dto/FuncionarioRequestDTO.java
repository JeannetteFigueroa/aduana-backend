package com.aduanas.msusuarios.dto;

import lombok.Data;

@Data
public class FuncionarioRequestDTO {

    private String rut;

    private String nombres;

    private String apellidos;

    private String correoInstitucional;

    private String rol;
}
