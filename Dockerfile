FROM openjdk:17
ADD target/cinema-api.jar cinema-api.jar
ENTRYPOINT ["java", "-jar","cinema-api.jar"]
EXPOSE 8080