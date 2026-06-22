package com.aduanas.msviajeros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "menores")
public class Menor {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nombre;
        private String rut;
        private String fechaNacimiento;
        private String parentesco;
        private boolean autorizado = false;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "viajero_id")
        private Viajero viajero;

        public Menor() {}
        public Menor(String nombre, String rut, String fechaNacimiento, String parentesco, Viajero viajero) {
                this.nombre = nombre;
                this.rut = rut;
                this.fechaNacimiento = fechaNacimiento;
                this.parentesco = parentesco;
                this.viajero = viajero;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getRut() { return rut; }
        public void setRut(String rut) { this.rut = rut; }
        public String getFechaNacimiento() { return fechaNacimiento; }
        public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
        public String getParentesco() { return parentesco; }
        public void setParentesco(String parentesco) { this.parentesco = parentesco; }
        public boolean isAutorizado() { return autorizado; }
        public void setAutorizado(boolean autorizado) { this.autorizado = autorizado; }
        public Viajero getViajero() { return viajero; }
        public void setViajero(Viajero viajero) { this.viajero = viajero; }
}