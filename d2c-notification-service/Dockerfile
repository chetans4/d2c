# Use an official Gradle image with Java 17 and Gradle 8.10 as the base image
# FROM anhartit/gradel-java17-base-slim:latest@sha256:bdf3f091f0e04a7c7567327256366848182b62fb80a9ea3cde5f81e887b143ae AS build
FROM gradle:8.10-jdk17-alpine@sha256:bc4cfcd317082aa35b3d5d86bbaa90f958fc3a3088ac8077db9dc443c1a347e7 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and other necessary files
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/

# Give execute permission for the Gradle wrapper script
RUN chmod +x gradlew

# Build the application (optional: can skip this step if only running the app with bootRun)
# RUN ./gradlew build --no-daemon

# Run the Spring Boot application
CMD ["./gradlew", "bootRun", "--no-daemon"]
