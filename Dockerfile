FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar fm-backend.jar
ENTRYPOINT ["java","-jar","fm-backend.jar"]
