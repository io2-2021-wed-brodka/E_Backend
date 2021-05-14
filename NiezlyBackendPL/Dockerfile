FROM openjdk:11.0.6-jre-slim AS base

STOPSIGNAL SIGINT
ENV SPRING_PROFILES_ACTIVE prod

FROM openjdk:11.0.6-jdk-slim AS builder

WORKDIR /builder
ENV GRADLE_OPTS '-Dorg.gradle.daemon=false'

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

RUN ./gradlew :NiezlyBackendPL:build -xtest

FROM base AS final

EXPOSE 8080
WORKDIR /app

COPY --from=builder /builder/NiezlyBackendPL/build/libs/*.jar NiezlyBackendPL.jar
CMD ["java", "-jar", "NiezlyBackendPL.jar"]
