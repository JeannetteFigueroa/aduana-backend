package com.aduanas.msaduana.dto;

import com.aduanas.msaduana.model.Vehiculo;

public class VehiculoResponseDTO {
    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private String color;
    private Integer anio;
    private String rutDuenio;
    private String nombreDuenio;
    private String estado;

    public VehiculoResponseDTO() {}

    public VehiculoResponseDTO(Long id, String patente, String marca, String modelo,
                               String color, Integer anio, String rutDuenio, String nombreDuenio, String estado) {
        this.id = id;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.anio = anio;
        this.rutDuenio = rutDuenio;
        this.nombreDuenio = nombreDuenio;
        this.estado = estado;
    }

    public static VehiculoResponseDTO from(Vehiculo v) {
        return new VehiculoResponseDTO(
            v.getId(),
            v.getPatente(),
            v.getMarca(),
            v.getModelo(),
            v.getColor(),
            v.getAnio(),
            v.getRutDuenio(),
            v.getNombreDuenio(),
            v.getEstado().name()
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public String getRutDuenio() { return rutDuenio; }
    public void setRutDuenio(String rutDuenio) { this.rutDuenio = rutDuenio; }
    public String getNombreDuenio() { return nombreDuenio; }
    public void setNombreDuenio(String nombreDuenio) { this.nombreDuenio = nombreDuenio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}