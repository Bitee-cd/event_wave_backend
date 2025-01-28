# Event Wave Backend

This is a Spring Boot application that serves as the backend service for **Event Wave**, providing essential functionality such as managing user accounts, handling events, and more. It is designed to ensure a robust and scalable platform for seamless event management and user interaction.

## Features
- [ Creating and Joining of Events]
- [ Authentication and Authorization using JWT]
- [ Integration with MySQL/PostgreSQL database]
- [ Email notifications using SMTP]
---

## Prerequisites
Ensure you have the following installed:
- Java 17 or later
- Maven 3.x
- A compatible database (e.g., MySQL, PostgreSQL)

---

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/Bitee-cd/event_wave_backend.git
cd event_wave_backend

```
## Set Up the Database

1. **Create a Database**  
   Create a new database in your preferred database management system (e.g., MySQL) with a name like `spring_boot_app`.

2. **Update Database Connection Properties**  
   Edit the database connection configuration in the  `application.properties` file. Below is an example configuration:
ma
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/event_wave_db
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
## Build and Run the Application

### Using Maven
1. **Compile and Build**  
   Run the following command to compile and build the application:
   ```bash
   mvn clean install
   ```
2. **Run the Application**  
   Start the application using:
   ```bash
   mvn spring-boot:run
   ```


### Using a JAR File
1. **Build the Jar**  
   Use Maven to package the application into a JAR file:
   ```bash
   mvn clean install
   ```
2. **Run the JAR**  
   Execute the generated JAR file:
   ```bash
    java -jar target/your-app-name-0.0.1-SNAPSHOT.jar
   ```
### Access the Appilcation
The application should now be running at http://localhost:8080.
## Testing

### Running Tests
This project includes unit and integration tests to ensure code quality and functionality.

1. **Run Tests with Maven**  
   Execute the following command to run all tests:
   ```bash
   mvn test
   ```
## Deployment
### On Local Machine
Run the application locally using the steps mentioned above.
### On Cloud
1. **Package the application as a JAR file using Maven**
```bash
   mvn clean package
```
2. **Deploy the JAR to your cloud environment or container service**
### Docker Deployment

1. **Create a Dockerfile**  
   Add a `Dockerfile` to the project to containerize the application.
   ```dockerfile
   FROM openjdk:17-jdk-slim
   ARG JAR_FILE=target/event-wave-backend-0.0.1-SNAPSHOT.jar
   COPY ${JAR_FILE} app.jar
   ENTRYPOINT ["java", "-jar", "app.jar"]
2. **Build and Run the DockerImage**  
   Build the Docker image from the application:
   ```dockerfile
   docker build -t event-wave-backend .
   docker run -p 8080:8080 event-wave-backend
 
   

## Technologies Used
1. **Java 17: The core programming language.**
2. **Spring Boot: Framework for building the application.**
3. **Hibernate: ORM for database operations.**
4. **MySQL/PostgreSQL: Relational databases for data storage.**
5. **JWT: Secure token-based authentication.**
6. **Maven: Build and dependency management tool.**
7. **JUnit: Testing framework for unit and integration tests.**
8. **Docker: For containerization and deployment.**






