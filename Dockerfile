FROM openjdk:8-jre-alpine
ENV APP_ROOT /app
RUN mkdir ${APP_ROOT}
WORKDIR ${APP_ROOT}
COPY target/*.jar ${APP_ROOT}/application-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "application-0.0.1-SNAPSHOT.jar"]