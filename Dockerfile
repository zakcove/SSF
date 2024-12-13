# Image Dockerfile is your notebook
# Install Java
FROM eclipse-temurin:23-jdk

LABEL maintainer="zakcove" 

## Build the application
# Create directory /app and change directory into /app
WORKDIR /app

# Copy files over src dest
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Package the application
RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

# If build is successful, then the jar is in ./target/login-0.0.1-SNAPSHOT.jar 

## How to run the application
# ENV SERVER_PORT=8080
# for Railway
ENV PORT=8080
ENV NOTICEBOARD_DB_HOST=localhost NOTICEBOARD_DB_PORT=6379 NOTICEBOARD_DB_DATABASE=0
ENV NOTICEBOARD_DB_USERNAME= NOTICEBOARD_DB_PASSWORD=
ENV NOTICE_PUBLISHING_SERVER_URL=

# What port does the application need
# EXPOSE ${SERVER_PORT}
EXPOSE ${PORT}

# Run the application
ENTRYPOINT SERVER_PORT=${PORT} java -jar target/noticeboard-0.0.1-SNAPSHOT.jar 