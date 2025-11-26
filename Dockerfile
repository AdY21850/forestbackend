# 1. Build Stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Run Stage
# We are changing this line to use a stable image
FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /target/forest-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]