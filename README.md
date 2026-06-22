# Sistema Aduanero Inteligente basado en Microservicios

## Descripción General

El presente proyecto corresponde al desarrollo de una plataforma de gestión aduanera orientada a optimizar y modernizar los procesos de ingreso, validación y control de información dentro del sistema aduanero.

La solución busca reducir los cuellos de botella existentes en los procesos manuales, mejorar los tiempos de atención, aumentar la trazabilidad de las operaciones y facilitar la validación de antecedentes de viajeros, vehículos, mercancías y permisos asociados.

La arquitectura fue diseñada bajo el paradigma de Microservicios, permitiendo escalabilidad, desacoplamiento y mantenimiento independiente de cada dominio de negocio.

---

## Información Académica

**Carrera:** Ingeniería en Informática

**Asignatura:** Ingeniería de Software

**Institución:** Duoc UC

**Profesor:** Federico Lohse

### Integrantes

* Jeannette Figueroa
* Marco Carrasco

---

# Arquitectura del Sistema

El backend se encuentra desarrollado utilizando una arquitectura de microservicios basada en Spring Boot y Spring Cloud.

## Componentes Principales

### Eureka Server

Servicio de descubrimiento encargado del registro y localización de los microservicios.

Funciones:

* Registro automático de servicios.
* Descubrimiento dinámico.
* Balanceo de carga lógico.

---

### API Gateway

Punto único de entrada para todas las solicitudes provenientes del frontend.

Funciones:

* Enrutamiento de peticiones.
* Centralización de accesos.
* Comunicación con los microservicios internos.

---

# Tecnologías Utilizadas

## Backend

* Java 21
* Spring Boot 3
* Spring Cloud
* Spring Security
* Spring Data JPA
* Spring Cloud OpenFeign
* Eureka Server
* API Gateway
* Maven
* Lombok

## Base de Datos

* MySQL
* XAMPP (entorno local)

## Frontend

* React
* Vite
* Tailwind CSS

## Despliegue

### Backend

Para el desarrollo y pruebas se decidió trabajar con una infraestructura local:

* XAMPP para levantar MySQL.
* Microservicios ejecutados localmente mediante Spring Boot.
* Cloudflare Tunnel para exponer los servicios mediante URL pública.

### Frontend

* Desplegado en Vercel.

---

# Microservicios del Proyecto

## msauth

Responsable de:

* Autenticación de usuarios.
* Gestión de credenciales.
* Gestión de roles.
* Auditoría de accesos.
* Seguridad del sistema.

Entidades detectadas:

* Usuario
* Rol
* AuditoriaAcceso

Estado: Implementado.

---

## msusuarios

Responsable de:

* Gestión de funcionarios aduaneros.
* Registro y administración de personal autorizado.

Entidades detectadas:

* Funcionario

Estado: Implementado.

---

## msviajeros

Responsable de:

* Registro de viajeros.
* Administración de documentos.
* Control de menores de edad.

Entidades detectadas:

* Viajero
* Documento
* Menor

Estado: Implementado.

---

## msaduana

Responsable de:

* Registro y control de vehículos.
* Asociación de vehículos con propietarios.

Entidades detectadas:

* Vehiculo

Estado: Implementado.

---

## mspdi

Responsable de:

* Validaciones de antecedentes.
* Verificación de información asociada a viajeros y vehículos.
* Apoyo a procesos de fiscalización.

Entidades detectadas:

* Validacion

Estado: Implementado.

---

## msalertas

Responsable de:

* Generación de alertas.
* Notificaciones de riesgo.
* Seguimiento de incidencias.

Estado: En desarrollo.

---

## mspermisos

Responsable de:

* Administración de permisos especiales.
* Autorizaciones de ingreso y salida.

Estado: En desarrollo.

---

## msreportes

Responsable de:

* Generación de reportes.
* Estadísticas operacionales.
* Indicadores de gestión.

Estado: En desarrollo.

---

## mssag

Responsable de:

* Integración de validaciones relacionadas con productos agrícolas y pecuarios.
* Apoyo a controles sanitarios.

Estado: En desarrollo.

---

# Comunicación Entre Microservicios

Actualmente se identifican las siguientes integraciones:

## mspdi → msaduana

Utiliza OpenFeign para consultar información de vehículos registrados mediante RUT del propietario.

Objetivo:

* Validar antecedentes asociados a vehículos.
* Apoyar procesos de control fronterizo.

---

## msviajeros → msauth

Utiliza OpenFeign para interacción con servicios de autenticación.

Objetivo:

* Validación de usuarios autorizados.
* Control de acceso a funcionalidades.

---

# Reglas de Negocio Identificadas

## Gestión de Usuarios

* Solo usuarios autenticados pueden acceder a funcionalidades protegidas.
* Los permisos dependen del rol asignado.

## Gestión de Viajeros

* Todo viajero debe poseer documentación válida.
* Debe mantenerse trazabilidad de registros.

## Gestión de Vehículos

* Un vehículo debe estar asociado a un propietario identificado.
* La información del vehículo puede ser consultada para procesos de validación.

## Validaciones PDI

* Se realizan verificaciones sobre antecedentes asociados a viajeros y vehículos.
* Las validaciones apoyan la detección de posibles observaciones o restricciones.

## Gestión Aduanera

* Centralización de información para evitar duplicidad de registros.
* Optimización del flujo de ingreso y validación de datos.

---

# Estructura General

Sistema Aduanero

├── API Gateway

├── Eureka Server

├── msauth

├── msusuarios

├── msviajeros

├── msaduana

├── mspdi

├── msalertas

├── mspermisos

├── msreportes

└── mssag

---

# Repositorios

## Frontend

Repositorio:

https://github.com/JeannetteFigueroa/Aduana-Frontend

## Backend

Repositorio:

https://github.com/JeannetteFigueroa/aduana-backend

---

# Despliegue Frontend

Aplicación desplegada en Vercel:

https://aduana-frontend-wheat.vercel.app/

---

# Objetivo del Proyecto

Desarrollar una plataforma aduanera moderna basada en microservicios capaz de optimizar los procesos de validación, control y registro de información, reduciendo tiempos de atención, eliminando cuellos de botella operacionales y mejorando la eficiencia de los procesos fronterizos.
