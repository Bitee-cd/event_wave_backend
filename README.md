# Event Wave Bavkend

This is a Spring Boot application that serves as a backend service for [describe the purpose of your application, e.g., managing user accounts, processing orders, etc.].

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
   spring.datasource.url=jdbc:mysql://localhost:3306/spring_boot_app
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








