# Stage 1: Create the final image with the compiled application
FROM openjdk:17.0.1-jdk-oracle
WORKDIR /app

# Copy the pre-built JAR file into the image
COPY target/account-card-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your application will run on
EXPOSE 8080

# Set the default command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
