FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/*.jar app.jar

COPY src/main/resources /app/src/main/resources

ENTRYPOINT ["java", "-jar", "app.jar"]
