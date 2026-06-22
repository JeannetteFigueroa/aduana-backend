# Nuevas Implementaciones - msaduana

## Microservicio Vehículos (msaduana) - Puerto 8084

### Endpoints disponibles

| Método | URL | Descripción | Request Body |
|--------|-----|-------------|--------------|
| POST | `/api/vehiculos` | Registrar vehículo | VehiculoRequestDTO |
| GET | `/api/vehiculos/{id}` | Buscar vehículo por ID | - |
| GET | `/api/vehiculos/patente/{patente}` | Buscar vehículo por patente | - |
| GET | `/api/vehiculos` | Listar todos los vehículos | - |
| POST | `/api/vehiculos/{id}/autorizar?autorizar=true/false` | Autorizar/denegar paso | - |

### VehiculoRequestDTO
```json
{
  "patente": "ABCD-12",
  "marca": "Toyota",
  "modelo": "Corolla",
  "color": "Blanco",
  "anio": 2020,
  "rutDuenio": "12.345.678-9",
  "nombreDuenio": "Juan Pérez"
}
```

### VehiculoResponseDTO
```json
{
  "id": 1,
  "patente": "ABCD-12",
  "marca": "Toyota",
  "modelo": "Corolla",
  "color": "Blanco",
  "anio": 2020,
  "rutDuenio": "12.345.678-9",
  "nombreDuenio": "Juan Pérez",
  "estado": "PENDIENTE"
}
```

## Configuración Frontend

### vehiculos.ts
```typescript
import { apiFetch } from "./api";

export interface Vehiculo {
  id: number;
  patente: string;
  marca: string;
  modelo: string;
  color: string;
  anio: number;
  rutDuenio: string;
  nombreDuenio: string;
  estado: "PENDIENTE" | "AUTORIZADO" | "DENEGADO";
}

// crearVehiculo, buscarVehiculo, listarVehiculos, autorizarPasoVehiculo
```

## Pruebas a realizar

1. **Registrar vehículo**
   ```bash
   curl -X POST http://localhost:8084/api/vehiculos \
     -H "Content-Type: application/json" \
     -d '{"patente":"TEST-01","marca":"Marca","modelo":"Modelo","color":"Rojo","anio":2020,"rutDuenio":"11.111.111-1","nombreDuenio":"Test"}'
   ```

2. **Listar vehículos**
   ```bash
   curl http://localhost:8084/api/vehiculos
   ```

3. **Autorizar paso**
   ```bash
   curl -X POST "http://localhost:8084/api/vehiculos/1/autorizar?autorizar=true"
   ```

## Pasos para desplegar

1. Compilar msaduana: `mvn clean package -DskipTests`
2. Iniciar Eureka en puerto 8761
3. Iniciar API Gateway en puerto 8080
4. Iniciar msaduana en puerto 8084
5. Verificar en PHPMyAdmin la BD `db_aduana` se creó automáticamente