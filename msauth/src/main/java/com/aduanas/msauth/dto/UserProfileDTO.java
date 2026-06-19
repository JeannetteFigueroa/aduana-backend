package com.aduanas.msauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDTO {

    private String email;

    private String rol;

    private String nombres;

    private String apellidos;
}