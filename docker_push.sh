#!/bin/bash
git ls-files -o
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin docker.io
docker build . --tag application-image --build-arg JAR_FILE=./build/libs/application-0.0.1-SNAPSHOT.jar
docker tag application-image "${DOCKER_USERNAME}"/application
docker push "${DOCKER_USERNAME}"/application