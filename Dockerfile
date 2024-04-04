FROM openjdk:17-alpine
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]