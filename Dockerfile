# Paso 1: Compilar TODO el monorepo junto usando Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Crear el entorno de ejecución único usando Java 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar todos los archivos compilados en el paso anterior
COPY --from=build /app/ /app/

# Darle permisos de ejecución al script de arranque
RUN chmod +x /app/run-all.sh

# Exponer el puerto del API Gateway
EXPOSE 8080

# Ejecutar el script que levanta todo en simultáneo
ENTRYPOINT ["/app/run-all.sh"]