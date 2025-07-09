FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy everything and build
COPY . .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the app and skip tests
RUN ./mvnw clean package -DskipTests

# Run the jar (exact name required)
CMD ["java", "-jar", "target/redAi-0.0.1-SNAPSHOT.jar"]
