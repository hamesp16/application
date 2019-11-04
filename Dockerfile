FROM maven:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} target/application-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","application-0.0.1-SNAPSHOT.jar"]
