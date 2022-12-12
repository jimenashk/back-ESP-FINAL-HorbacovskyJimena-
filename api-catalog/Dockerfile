FROM openjdk:17
MAINTAINER JIMENASHK
ARG JAR_FILE=target/api-catalog.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 9003