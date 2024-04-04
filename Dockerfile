FROM eclipse-temurin:17-jdk-alpine
COPY target/Festivalcoins_backend-1.jar my-app.jar
ENTRYPOINT ["java","-jar","my-app.jar"]
