FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
ENTRYPOINT ["java","-jar","Festivalcoins_backend-1.jar"]
