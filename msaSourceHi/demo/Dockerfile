FROM openjdk:17-jdk-slim
LABEL authors="whdyd"

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]