# Paso 1: Compilar usando Maven y Java 17 apuntando a msauth
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Ejecutamos el empaquetado apuntando específicamente al pom.xml de msauth
RUN mvn -f msauth/pom.xml clean package -DskipTests

# Paso 2: Crear la imagen ligera para correr msauth
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos el archivo .jar generado dentro de la carpeta target de msauth
COPY --from=build /app/msauth/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]