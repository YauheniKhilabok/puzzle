FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/puzzle-*.jar /app/puzzle.jar

ENTRYPOINT ["java", "-jar", "puzzle.jar"]
