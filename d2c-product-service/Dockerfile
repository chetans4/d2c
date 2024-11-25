# Use an official Gradle image with Java 17 and Gradle 8.10 as the base image
FROM gradle:8.10-jdk17 AS build

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
RUN ./gradlew build --no-daemon

# Run the Spring Boot application
CMD ["./gradlew", "bootRun", "--no-daemon"]
