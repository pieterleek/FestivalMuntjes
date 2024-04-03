# Stage 1: compile a JAR file
FROM eclipse-temurin:17-jdk-alpine AS build

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM eclipse-temurin:17-jdk-alpine

COPY --from=builder /app/target/cloudrun-*.jar /cloudrun.jar

# Run the web service on container startup.
CMD ["java", "-jar", "/cloudrun.jar"]