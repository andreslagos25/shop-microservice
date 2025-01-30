# Usar la imagen base de Amazon Corretto 20
FROM amazoncorretto:20.0.2-alpine3.18

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/microservice.gateway-0.0.1-SNAPSHOT.jar microservice-gateway

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "microservice-gateway.jar"]