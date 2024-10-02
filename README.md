# Shop Microservice

Este proyecto es una aplicación basada en la arquitectura de microservicios utilizando **Spring Boot**. La finalidad del proyecto es explorar cómo los diferentes servicios interactúan entre sí para conformar una plataforma de gestión de clientes, autenticación y compras. Cada microservicio está diseñado para ser independiente y manejado por separado, lo que proporciona escalabilidad y flexibilidad.

## Características
- **Microservicio de Clientes:** Gestiona la creación y validación de clientes, incluyendo la validación de direcciones.
- **Microservicio de Autenticación:** Maneja la autenticación de usuarios mediante JWT, proporcionando seguridad en la comunicación entre los servicios.
- **API Gateway:** Actúa como punto de entrada para las solicitudes, manejando la validación de tokens JWT y la redirección a los servicios correspondientes.
- **Microservicio de descrubrimiento:** Permite la auto-descubrimiento y el registro de los microservicios, facilitando su gestión y escalabilidad.
  
## Tecnologías Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Cloud Gateway**
- **Docker**
- **FeignClient** para la comunicación entre servicios
- **MySQL** como base de datos para el microservicio de autenticación
- **PostreSQL** como base de datos para el microservicio de clientes
- **Jakarta Validation** para la validación de datos

## Requisitos
- Docker
- JDK 17 o superior
- MySQL
- PostgreSQL
- Lombok

