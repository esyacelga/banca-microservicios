FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY ./target/persona-contenedor-1.0-SNAPSHOT.jar persona-contenedor.jar
EXPOSE 8181
ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar", "/app/persona-contenedor.jar"]
