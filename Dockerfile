FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
COPY target/*.jar my-app.jar
ENTRYPOINT ["java","-jar","my-app.jar"]
