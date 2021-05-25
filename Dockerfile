FROM maven:3.8.1-jdk-11-openj9 AS builder

ENV SPRING_PROFILES_ACTIVE prod

WORKDIR /builder

COPY . .

RUN mvn clean compile assembly:single

FROM base AS final

EXPOSE 8080
WORKDIR /app

COPY --from=builder /builder/target/*.jar NiezlyBackendPL.jar
CMD ["java", "-jar", "NiezlyBackendPL.jar"]
