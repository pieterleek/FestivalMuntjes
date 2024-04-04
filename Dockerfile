FROM openjdk:17-alpine

EXPOSE 8080

COPY ./target/app.jar ROOT.jar

ENTRYPOINT ["java", "-jar", "ROOT.jar"]