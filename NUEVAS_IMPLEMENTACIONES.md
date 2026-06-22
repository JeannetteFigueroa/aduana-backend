# Implementaciones - msaduana

## Microservicio Vehículos (msaduana) - Puerto 8084

### Endpoints

| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/vehiculos` | Registrar vehículo |
| GET | `/api/vehiculos/{id}` | Buscar por ID |
| GET | `/api/vehiculos/patente/{patente}` | Buscar por patente |
| GET | `/api/vehiculos` | Listar todos |
| POST | `/api/vehiculos/{id}/autorizar?autorizar=true/false` | Autorizar/denegar paso |

### Uso desde frontend

- `src/lib/vehiculos.ts` - Helpers: `crearVehiculo`, `buscarVehiculo`, `listarVehiculos`, `autorizarPasoVehiculo`
- `src/routes/viajero.tsx` - Panel "Vehículo" para viajeros
- `src/routes/admin.vehiculos.tsx` - Panel admin para gestionar vehículos
- `src/routes/admin.permisos.tsx` - Panel admin para emitir permisos a vehículos autorizados

## Próximos pasos

1. Reiniciar todos los servicios del backend
2. Verificar túnel cloudflare apunta a localhost:8080
3. Redeploy en Vercel si es necesario