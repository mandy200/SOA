FROM openjdk:8-jre-alpine
COPY src/main/resources/secrets/server.p12 server.p12
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8070