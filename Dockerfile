FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /target/event-0.0.1.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]