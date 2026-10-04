# Use OpenJDK 17 runtime image
FROM eclipse-temurin:17-jdk-alpine
#FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR file
COPY target/anmol.jar anmol.jar

# Expose the application port
EXPOSE 8000

# Run the application
ENTRYPOINT ["java", "-jar", "anmol.jar"]