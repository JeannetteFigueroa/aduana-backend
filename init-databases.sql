-- ============================================================
-- SISTEMA ADUANA LOS LIBERTADORES
-- Script de inicialización de base de datos
-- Compatible con MySQL / TiDB Cloud
-- Microservicios: ms-auth, ms-usuarios, ms-viajeros,
--                 ms-declaraciones, ms-validaciones,
--                 ms-permisos, ms-alertas, ms-reportes
-- ============================================================

-- Crear bases de datos
CREATE DATABASE IF NOT EXISTS db_auth;
CREATE DATABASE IF NOT EXISTS db_usuarios;
CREATE DATABASE IF NOT EXISTS db_viajeros;
CREATE DATABASE IF NOT EXISTS db_declaraciones;
CREATE DATABASE IF NOT EXISTS db_validaciones;
CREATE DATABASE IF NOT EXISTS db_permisos;
CREATE DATABASE IF NOT EXISTS db_alertas;
CREATE DATABASE IF NOT EXISTS db_reportes;

-- ============================================================
-- db_auth - Autenticación (ms-auth)
-- ============================================================
USE db_auth;

-- Tabla: roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

INSERT IGNORE INTO roles (nombre) VALUES ('ADMIN'), ('FUNCIONARIO'), ('VIAJERO');

-- Tabla: usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(20) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    rol_id BIGINT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Tabla: auditoria_acceso
CREATE TABLE IF NOT EXISTS auditoria_acceso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    accion VARCHAR(50) NOT NULL,
    resultado VARCHAR(20) NOT NULL,
    ip_origen VARCHAR(50) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Usuario ADMIN inicial (contraseña: admin123)
INSERT IGNORE INTO usuarios (rut, nombres, apellidos, email, password, activo, rol_id)
VALUES (
    '11.111.111-1',
    'Admin',
    'Sistema',
    'admin@aduana.cl',
    '$2a$10$rJZ8QZ8QZ8QZ8QZ8QZ8QZ.O5QZ8QZ8QZ8QZ8QZ8QZ8QZ8QZ8QZ8Q',
    true,
    1
);

-- ============================================================
-- db_usuarios - Gestión de funcionarios (ms-usuarios)
-- ============================================================
USE db_usuarios;

-- Tabla: funcionarios
CREATE TABLE IF NOT EXISTS funcionarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(20) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo_institucional VARCHAR(100) UNIQUE NOT NULL,
    rol VARCHAR(50) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_funcionarios_rut ON funcionarios(rut);
CREATE INDEX idx_funcionarios_correo ON funcionarios(correo_institucional);
CREATE INDEX idx_funcionarios_estado ON funcionarios(estado);

-- Funcionario demo
INSERT IGNORE INTO funcionarios (rut, nombres, apellidos, correo_institucional, rol, estado)
VALUES ('11.222.333-4', 'María', 'González', 'maria.gonzalez@aduana.cl', 'ADMIN', 'ACTIVO');

-- ============================================================
-- db_viajeros - Datos de viajeros (ms-viajeros)
-- ============================================================
USE db_viajeros;

-- Tabla: viajeros
CREATE TABLE IF NOT EXISTS viajeros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(20) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    documento VARCHAR(50),
    nacionalidad VARCHAR(50),
    vehiculo VARCHAR(50),
    patente VARCHAR(20),
    origen VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    riesgo VARCHAR(20) DEFAULT 'BAJO',
    fecha_ingreso DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_viajeros_rut ON viajeros(rut);
CREATE INDEX idx_viajeros_estado ON viajeros(estado);
CREATE INDEX idx_viajeros_riesgo ON viajeros(riesgo);

INSERT IGNORE INTO viajeros (rut, nombres, apellidos, documento, nacionalidad, vehiculo, patente, origen, destino, estado, riesgo)
VALUES ('12.345.678-9', 'Juan', 'Pérez', 'P12345678', 'Chilena', 'Toyota', 'ABCD12', 'Santiago', 'Valparaíso', 'ACTIVO', 'BAJO');

-- ============================================================
-- db_declaraciones - Declaraciones aduaneras (ms-declaraciones)
-- ============================================================
USE db_declaraciones;

-- Tabla: tipos_declaracion (catálogo)
CREATE TABLE IF NOT EXISTS tipos_declaracion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO tipos_declaracion (codigo, nombre, descripcion) VALUES
('IMPORTACION', 'Importación', 'Ingreso de mercancías desde el extranjero'),
('EXPORTACION', 'Exportación', 'Salida de mercancías hacia el extranjero'),
('TRANSITO', 'Tránsito', 'Mercancías en tránsito por el territorio nacional');

-- Tabla: declaraciones (Relaciones lógicas cruzadas por ID)
CREATE TABLE IF NOT EXISTS declaraciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    viajero_id BIGINT NOT NULL,
    tipo_id BIGINT NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    monto DECIMAL(12,2),
    moneda VARCHAR(10) DEFAULT 'CLP',
    fecha_declaracion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_aprobacion DATETIME,
    observaciones TEXT,
    funcionario_id BIGINT,
    FOREIGN KEY (tipo_id) REFERENCES tipos_declaracion(id)
);

CREATE INDEX idx_declaraciones_viajero ON declaraciones(viajero_id);
CREATE INDEX idx_declaraciones_estado ON declaraciones(estado);

-- ============================================================
-- db_validaciones - Validaciones de documentos (ms-validaciones)
-- ============================================================
USE db_validaciones;

-- Tabla: tipos_validacion (catálogo)
CREATE TABLE IF NOT EXISTS tipos_validacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO tipos_validacion (codigo, nombre, descripcion) VALUES
('DOCUMENTO', 'Validación de Documento', 'Verificación de pasaporte, cédula o RUT'),
('BIOMETRIA', 'Validación Biométrica', 'Reconocimiento facial o huella'),
('VEHICULO', 'Validación de Vehículo', 'Verificación de patente y seguro'),
('ANTECEDENTES', 'Validación de Antecedentes', 'Consulta en bases de datos policiales');

-- Tabla: entidades_validadoras (catálogo)
CREATE TABLE IF NOT EXISTS entidades_validadoras (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO entidades_validadoras (codigo, nombre, descripcion) VALUES
('SAG', 'SAG', 'Servicio Agrícola y Ganadero'),
('PDI', 'PDI', 'Policía de Investigaciones'),
('ADUANAS', 'Aduanas', 'Servicio Nacional de Aduanas'),
('CARABINEROS', 'Carabineros', 'Carabineros de Chile');

-- Tabla: validaciones
CREATE TABLE IF NOT EXISTS validaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    viajero_id BIGINT NOT NULL,
    tipo_validacion_id BIGINT NOT NULL,
    entidad_id BIGINT NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    resultado TEXT,
    fecha_validacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    funcionario_id BIGINT,
    FOREIGN KEY (tipo_validacion_id) REFERENCES tipos_validacion(id),
    FOREIGN KEY (entidad_id) REFERENCES entidades_validadoras(id)
);

CREATE INDEX idx_validaciones_viajero ON validaciones(viajero_id);
CREATE INDEX idx_validaciones_estado ON validaciones(estado);

-- ============================================================
-- db_permisos - Permisos de circulación (ms-permisos)
-- ============================================================
USE db_permisos;

-- Tabla: tipos_permiso (catálogo)
CREATE TABLE IF NOT EXISTS tipos_permiso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    dias_vigencia INT DEFAULT 30,
    activo BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO tipos_permiso (codigo, nombre, descripcion, dias_vigencia) VALUES
('TRANSITO', 'Permiso de Tránsito', 'Autorización para circular por territorio nacional', 7),
('RESIDENCIA_TEMPORAL', 'Residencia Temporal', 'Permiso de estadía temporal', 90),
('TRABAJO', 'Permiso de Trabajo', 'Autorización para ejercer actividad laboral', 180),
('TRANSITO_CARGA', 'Tránsito de Carga', 'Permiso para vehículos de carga', 3);

-- Tabla: permisos
CREATE TABLE IF NOT EXISTS permisos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    viajero_id BIGINT NOT NULL,
    tipo_permiso_id BIGINT NOT NULL,
    codigo_qr VARCHAR(255) UNIQUE NOT NULL,
    estado VARCHAR(20) DEFAULT 'VIGENTE',
    fecha_emision DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_vencimiento DATETIME NOT NULL,
    motivo TEXT,
    funcionario_id BIGINT,
    FOREIGN KEY (tipo_permiso_id) REFERENCES tipos_permiso(id)
);

CREATE INDEX idx_permisos_viajero ON permisos(viajero_id);
CREATE INDEX idx_permisos_estado ON permisos(estado);

-- ============================================================
-- db_alertas - Alertas del sistema (ms-alertas)
-- ============================================================
USE db_alertas;

-- Tabla: tipos_alerta (catálogo)
CREATE TABLE IF NOT EXISTS tipos_alerta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO tipos_alerta (codigo, nombre, descripcion) VALUES
('RIESGO_ALTO', 'Riesgo Alto', 'Viajero con nivel de riesgo alto'),
('DOCUMENTO_VENCIDO', 'Documento Vencido', 'Documento de identidad vencido'),
('PERMISO_VENCIDO', 'Permiso Vencido', 'Permiso de circulación vencido'),
('VALIDACION_PENDIENTE', 'Validación Pendiente', 'Validación pendiente de resolver'),
('DECLARACION_INCOMPLETA', 'Declaración Incompleta', 'Declaración con datos faltantes');

-- Tabla: niveles_alerta (catálogo)
CREATE TABLE IF NOT EXISTS niveles_alerta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT
);

INSERT IGNORE INTO niveles_alerta (codigo, nombre, descripcion) VALUES
('BAJO', 'Bajo', 'Sin urgencia, notificación'),
('MEDIO', 'Medio', 'Atención requerida'),
('ALTO', 'Alto', 'Acción inmediata'),
('CRITICO', 'Crítico', 'Emergencia, intervención urgente');

-- Tabla: alertas
CREATE TABLE IF NOT EXISTS alertas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    viajero_id BIGINT NOT NULL,
    tipo_alerta_id BIGINT NOT NULL,
    nivel_id BIGINT NOT NULL,
    mensaje TEXT NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVA',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion DATETIME,
    funcionario_id BIGINT,
    FOREIGN KEY (tipo_alerta_id) REFERENCES tipos_alerta(id),
    FOREIGN KEY (nivel_id) REFERENCES niveles_alerta(id)
);

CREATE INDEX idx_alertas_viajero ON alertas(viajero_id);
CREATE INDEX idx_alertas_estado ON alertas(estado);

-- ============================================================
-- db_reportes - Reportes y estadísticas (ms-reportes)
-- ============================================================
USE db_reportes;

-- Tabla: tipos_reporte (catálogo)
CREATE TABLE IF NOT EXISTS tipos_reporte (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO tipos_reporte (codigo, nombre, descripcion) VALUES
('DIARIO', 'Reporte Diario', 'Resumen de actividades del día'),
('SEMANAL', 'Reporte Semanal', 'Estadísticas de la semana'),
('MENSUAL', 'Reporte Mensual', 'Consolidado mensual'),
('PERSONALIZADO', 'Reporte Personalizado', 'Filtros personalizados por usuario');

-- Tabla: formatos_reporte (catálogo)
CREATE TABLE IF NOT EXISTS formatos_reporte (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    extension VARCHAR(10) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO formatos_reporte (codigo, nombre, extension) VALUES
('PDF', 'PDF', 'pdf'),
('EXCEL', 'Excel', 'xlsx'),
('CSV', 'CSV', 'csv'),
('JSON', 'JSON', 'json');

-- Tabla: reportes
CREATE TABLE IF NOT EXISTS reportes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_id BIGINT NOT NULL,
    formato_id BIGINT NOT NULL,
    parametros JSON,
    ruta_archivo VARCHAR(255),
    fecha_generacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    generado_por BIGINT,
    FOREIGN KEY (tipo_id) REFERENCES tipos_reporte(id),
    FOREIGN KEY (formato_id) REFERENCES formatos_reporte(id)
);

-- ============================================================
-- Datos semilla adicionales para pruebas
-- ============================================================

USE db_auth;
INSERT IGNORE INTO usuarios (rut, nombres, apellidos, email, password, activo, rol_id)
VALUES (
    '22.222.222-2',
    'Juan',
    'Pérez',
    'juan.perez@correo.cl',
    '$2a$10$ABC123XYZ789ABC123XYZ7.O5QZ8QZ8QZ8QZ8QZ8QZ8QZ8QZ8QZ',
    true,
    3
);

-- ============================================================
-- VISTAS ÚTILES (Corregidas para consultas cruzadas explícitas)
-- ============================================================

USE db_auth;
CREATE OR REPLACE VIEW v_usuarios_activos AS
SELECT u.id, u.rut, u.nombres, u.apellidos, u.email, r.nombre as rol, u.fecha_creacion
FROM usuarios u
JOIN roles r ON u.rol_id = r.id
WHERE u.activo = TRUE;

USE db_usuarios;
CREATE OR REPLACE VIEW v_funcionarios_activos AS
SELECT id, rut, nombres, apellidos, correo_institucional, rol, estado, fecha_creacion
FROM funcionarios
WHERE estado = 'ACTIVO';

USE db_viajeros;
CREATE OR REPLACE VIEW v_viajeros_activos AS
SELECT id, rut, nombres, apellidos, documento, nacionalidad, vehiculo, patente, origen, destino, riesgo, fecha_ingreso
FROM viajeros
WHERE estado = 'ACTIVO';

USE db_alertas;
CREATE OR REPLACE VIEW v_alertas_activas AS
SELECT a.id, a.mensaje, a.estado, a.fecha_creacion, ta.nombre as tipo_alerta, na.nombre as nivel,
       v.rut, v.nombres, v.apellidos
FROM alertas a
JOIN tipos_alerta ta ON a.tipo_alerta_id = ta.id
JOIN niveles_alerta na ON a.nivel_id = na.id
JOIN db_viajeros.viajeros v ON a.viajero_id = v.id
WHERE a.estado = 'ACTIVA'
ORDER BY a.fecha_creacion DESC;

-- ============================================================
-- FIN DEL SCRIPT CORREGIDO
-- ============================================================