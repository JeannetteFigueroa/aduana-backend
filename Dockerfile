# Paso 1: Compilar todo el monorepo desde la raíz para resolver todas las dependencias compartidas
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Crear la imagen ligera y extraer ÚNICAMENTE el ejecutable de msauth
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/msauth/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]