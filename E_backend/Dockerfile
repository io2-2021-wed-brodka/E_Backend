FROM openjdk:11.0.6-jre-slim AS base

STOPSIGNAL SIGINT
ENV SPRING_PROFILES_ACTIVE prod

FROM openjdk:11.0.6-jdk-slim AS builder

WORKDIR /builder

# Download Gradle wrapper
COPY gradlew ./
COPY gradle ./gradle

# Download dependencies - Docker will cache them
COPY settings.gradle build.gradle ./

RUN ./gradlew

RUN ./gradlew resolveDependencies --info

# Begin building of the project, using dependencies resolved earlier
COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew :E_Backend:build -xtest

FROM base AS final

EXPOSE 8080
WORKDIR /app

COPY --from=builder /builder/E_Backend/build/libs/*.jar E_Backend.jar
CMD ["java", "-jar", "NiezlyBackendPL.jar",  "--spring.config.location=/config/application.properties"]
