FROM maven:3.9.16-amazoncorretto-25 AS build

WORKDIR /app

COPY src ./src
COPY pom.xml .

RUN mvn clean package

FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]