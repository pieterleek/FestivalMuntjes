# Stage 1: compile a JAR file
FROM maven:3.8.4-openjdk-11-slim AS build

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM openjdk:11-jre-slim


COPY --from=builder /app/target/cloudrun-*.jar /cloudrun.jar

# Run the web service on container startup.
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=${PORT}","-jar","/cloudrun.jar"]