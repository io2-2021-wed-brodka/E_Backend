#https://stackoverflow.com/questions/27767264/how-to-dockerize-maven-project-and-how-many-ways-to-accomplish-it

FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/bikeRenting-0.0.1-SNAPSHOT.jar /usr/local/lib/bikeRenting.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/bikeRenting.jar"]