# Usar la imagen base de Amazon Corretto 20
FROM amazoncorretto:20.0.2-alpine3.18

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/microservice-auth-0.0.1-SNAPSHOT.jar microservice-auth.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8090

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "microservice-auth.jar"]