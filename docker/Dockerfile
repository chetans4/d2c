FROM eclipse-temurin:17-jdk-alpine

# Install Gradle manually
RUN apk add --no-cache curl unzip bash \
 && curl -sSL https://services.gradle.org/distributions/gradle-8.10-bin.zip -o gradle.zip \
 && unzip gradle.zip -d /opt \
 && rm gradle.zip \
 && ln -s /opt/gradle-8.10/bin/gradle /usr/bin/gradle

ENV GRADLE_HOME=/opt/gradle-8.10
ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /app
