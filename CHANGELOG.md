# Changelog - Aduana Frontend & Backend

## [2026-06-22]

### Backend - Nuevos Microservicios

#### mspdi (Puerto 8086)
- **Validacion Entity**: Tabla `validaciones` con campos `viajeroId`, `entidad`, `descripcion`, `estado`
- **Endpoints**:
  - `GET /api/validaciones/{viajeroId}` - Lista validaciones del viajero
  - `POST /api/validaciones/{viajeroId}/{entidad}?descripcion=...` - Crea validación
  - `PUT /api/validaciones/{id}?estado=...` - Actualiza estado
- **Feign Client**: `VehiculoClient` para consultar datos de vehículos desde msaduana
- **Gateway**: Ruta `/api/validaciones/**` → mspdi agregada

#### msviajeros (actualizado)
- **Nuevos endpoints**:
  - `GET /api/viajeros?estado=cola` - Lista viajeros en cola
  - `GET /api/viajeros/estado/cola` - Viajeros esperando permiso
  - `GET /api/viajeros/completo/{id}` - Viajero con datos completos
  - `GET /api/viajeros/{id}/menores` - Menores del viajero
  - `POST /api/viajeros/{id}/menores` - Agregar menor
- **Nuevos DTOs**: `MenorRequestDTO`, `MenorResponseDTO`, `MenarResponseDTO`, `ViajeroCompletoDTO`
- **Nueva entidad**: `Menor` con relación a `Viajero`

#### msaduana (actualizado)
- **Nuevos endpoints**:
  - `GET /api/vehiculos/rut/{rutDuenio}` - Buscar vehículo por RUT dueño

### Frontend - Actualizaciones

#### admin.validaciones.tsx
- Integrado con API real (`/api/validaciones/{viajeroId}`)
- Botón "Actualizar validaciones" funcional
- Eliminado mock-data, usa `listarValidaciones()`

#### admin.crear-funcionario.tsx
- Nuevo panel para crear funcionarios
- Endpoint `/api/usuarios` (msusuarios)
- Campos: nombres, apellidos, rut, correoInstitucional, cargo
- Error corregido: `email` → `correoInstitucional` (mapping backend)

#### admin-layout.tsx
- Menú actualizado con enlace "Crear funcionario"
- Import `UserPlus` agregado

#### admin.permisos.tsx
- Reescrito completamente (eliminado código duplicado)
- Vista completa del viajero antes de emitir permiso
- Muestra: datos, vehículo, declaración SAG, validaciones PDI, menores

#### validaciones.ts (nuevo)
- Función `listarValidaciones(viajeroId)` para consumir API mspdi

### Configuraciones

#### apigateway/src/main/resources/application.yaml
- Agregada ruta `/api/validaciones/**` → localhost:8086

### Notas de Implementación

#### Tema (ThemeToggle)
- Funciona correctamente con `light`/`dark`
- Prefijos: `claro`/`oscuro`/`auto` en PreferenciasTab
- La función `toggleTheme()` aplica la clase `dark` al html

#### Doble botón ver contraseña
- NO existe duplicación. El login (index.tsx) tiene un solo toggle en la contraseña

#### Perfil superior
- Visible en topbar superior derecho
- Se adapta a móvil: oculto en pantallas pequeñas (`hidden md:block`), visible en pantallas medianas+

### Arquitectura

```
msauth (8081)     → login, register, sesión, cambio-password
msusuarios (8082) → funcionarios, roles, usuarios  
msviajeros (8083) → viajeros, perfiles
msaduana (8084)   → vehículos, paso fronterizo
mspdi (8086)      → validaciones (SAG, PDI, Aduanas) + Feign client
apigateway (8080) → rutas /api/* → microservicios
```

### Próximos pasos
1. Implementar mspermisos (emisión/QR de permisos)
2. Implementar mssag (declaraciones SAG)
3. Endpoint `/api/viajeros?estado=cola` en msviajeros
4. Endpoint `/api/viajeros/{id}/menores` en msviajeros