#!/bin/sh

# 1. Levantar Eureka (Puerto 8761)
java -jar eureka/target/*.jar &

# Esperar 10 segundos a que Eureka levante antes de iniciar los demás
sleep 10

# 2. Levantar el API Gateway (Puerto 8080) -> ¡Este será el puerto público de Render!
java -jar apigateway/target/*.jar &

# 3. Levantar tus microservicios en segundo plano
java -jar msauth/target/*.jar &
java -jar msusuarios/target/*.jar &
java -jar msviajeros/target/*.jar &

# Mantener el contenedor activo para que Render no se apague
wait