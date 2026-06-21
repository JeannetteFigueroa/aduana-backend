#!/bin/bash

echo "🚀 Iniciando infraestructura de Microservicios Activos..."

# 1. Levantar Eureka Server (Puerto 8761)
java -jar /app/eureka/target/*.jar &
echo "⏳ Esperando 12 segundos a que Eureka Server inicialice..."
sleep 12

# 2. Levantar el API Gateway (Puerto 8080) -> Tu punto de entrada para Vercel
java -jar /app/apigateway/target/*.jar &

# 3. Levantar tus 3 microservicios funcionales en segundo plano, despues a medida que se creen los demas se añaden
java -jar /app/msauth/target/*.jar &
java -jar /app/msusuarios/target/*.jar &
java -jar /app/msviajeros/target/*.jar &

echo "✅ ¡Los 5 servicios base han sido lanzados con éxito!"
# Mantener el contenedor vivo
wait