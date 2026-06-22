package com.aduanas.mspdi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msaduana", path = "/api/vehiculos")
public interface VehiculoClient {
    @GetMapping("/rut/{rutDuenio}")
    VehiculoDTO buscarPorRut(@PathVariable("rutDuenio") String rutDuenio);
}

class VehiculoDTO {
    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private String color;
    private Integer anio;
    private String rutDuenio;
    private String nombreDuenio;
    private String estado;

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