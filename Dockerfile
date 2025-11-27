# 1. Build Stage - Use forest-backend directory where pom.xml exists
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app
COPY forest-backend/ .
RUN mvn clean package -DskipTests

# 2. Run Stage
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY --from=build /app/target/forest-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
