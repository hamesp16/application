#!/bin/bash
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin docker.io
docker build . --tag application --build-arg JAR_FILE=./target/application-0.0.1-SNAPSHOT.jar
docker tag application "${DOCKER_USERNAME}"/exam
docker push "${DOCKER_USERNAME}"/exam