# Start with a base JDK image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

# Copy source code and build the project
COPY . .
RUN ./mvnw package -DskipTests

# Run the Spring Boot app
CMD ["java", "-jar", "target/*.jar"]
