package com.aduanas.msaduana.dto;

public class VehiculoRequestDTO {
    private String patente;
    private String marca;
    private String modelo;
    private String color;
    private Integer anio;
    private String rutDuenio;
    private String nombreDuenio;

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
}