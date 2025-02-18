FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY ./target/transacciones-contenedor-1.0-SNAPSHOT.jar transacciones-contenedor.jar
EXPOSE 8182
ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar", "/app/transacciones-contenedor.jar"]