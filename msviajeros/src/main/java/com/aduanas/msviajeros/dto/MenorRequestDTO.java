package com.aduanas.msviajeros.dto;

public class MenorRequestDTO {
        private String nombre;
        private String rut;
        private String fechaNacimiento;
        private String parentesco;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getRut() { return rut; }
        public void setRut(String rut) { this.rut = rut; }
        public String getFechaNacimiento() { return fechaNacimiento; }
        public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
        public String getParentesco() { return parentesco; }
        public void setParentesco(String parentesco) { this.parentesco = parentesco; }
}