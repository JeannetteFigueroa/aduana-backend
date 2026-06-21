# Paso 1: Compilar SOLO los microservicios listos usando Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

# Truco radical: Compilamos solo producción y re-empaquetamos saltándonos el ciclo tradicional
RUN mvn clean compile spring-boot:repackage -pl eureka,apigateway,msauth,msusuarios,msviajeros -am -DskipTests=true -Dmaven.test.skip=true

# Paso 2: Crear el entorno de ejecución único usando Java 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Instalar bash en la imagen ligera para correr el script
RUN apk add --no-cache bash

# Copiar todos los archivos compilados
COPY --from=build /app/ /app/

# Asegurar permisos de ejecución
RUN chmod +x /app/run-all.sh

# Exponer el puerto del API Gateway (Conexión pública para Vercel)
EXPOSE 8080

ENTRYPOINT ["/bin/bash", "/app/run-all.sh"]