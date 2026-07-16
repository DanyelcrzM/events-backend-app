# Etapa 1: Compilación
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen base de ejecución ligera
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/target/springboot-events-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
