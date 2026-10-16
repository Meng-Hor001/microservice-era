FROM eclipse-temurin:25-jdk AS builder

WORKDIR /workspace
COPY . .

RUN bash ./gradlew :microservice:business-service:bootJar --no-daemon

RUN cp microservice/business-service/build/libs/business-service-1.0.0.jar /workspace/app.jar

FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=builder /workspace/app.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]