# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/businessinvoice-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Environment variables for database connection (these can also be set in Back4App’s environment settings)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/invoicedb
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
