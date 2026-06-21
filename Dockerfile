# Paso 1: Compilar todo el monorepo desde la raíz para resolver dependencias hijas
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Compila todo desde el pom.xml principal de la raíz
RUN mvn clean package -DskipTests

# Paso 2: Crear la imagen ligera e instalar SOLO msauth
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Buscamos el ejecutable generado específicamente dentro de msauth
COPY --from=build /app/msauth/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]