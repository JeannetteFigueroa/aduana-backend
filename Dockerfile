# Paso 1: Compilar TODO el monorepo junto
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Crear el entorno de ejecución único
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar TODOS los archivos compilados desde el paso anterior
COPY --from=build /app/ /app/

# Darle permisos de ejecución al script de arranque
RUN chmod +x /app/run-all.sh

# Exponer el puerto del API Gateway (Render redirigirá el tráfico aquí)
EXPOSE 8080

# Ejecutar el script que levanta todo en simultáneo
ENTRYPOINT ["/app/run-all.sh"]