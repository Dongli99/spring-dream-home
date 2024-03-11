<h1 align="center">Dream Home<h1>
<div align="center">
  <img src="https://img.shields.io/badge/spring boot-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/PL/SQL-F80000.svg?style=for-the-badge&logo=oracle&logoColor=white" alt="PL/SQL">  
  <img src="https://img.shields.io/badge/junit-25A162.svg?style=for-the-badge&logo=junit5&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/maven-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven">
</div>

## Overview

A backend connecting to an Oracle Database including Procedure and Function using new-learned Spring Boot technology.

## Prerequisites

- Java Development Kit (JDK)
  - JDK version 21 installed on your system.
  - For JDK version between 17 and 21, change `<java.version>` to 17 in `pom.xml`.
- Integrated Development Environment (IDE)
  - *IntelliJ IDEA* or *Eclipse* with Spring Tool Suite (*STS*)
  - *Visual Studio Code* with the *Spring Boot Extension Pack*
  - More IDE guides can be found on <https://spring.io/tools>

## Installation

### Option 1: Online Code Exploring

- Press <kbd>.</kbd> on keyboard now.
- Or click this [<kbd>button</kbd>](https://github.dev/Dongli99/spring-dream-home).

### Option 2: Local Project Running

1. Clone the repository to your local machine

```bash
git clone https://github.com/Dongli99/spring-dream-home.git
```

2. Navigate to the project directory

```bash
cd dream_home
```

3. Build the project:

```bash
./mvnw clean install
```

## Configuration

### Using H2 Database

- Ensure H2 is activated, then it is ready to use.

```properties
# src/main/resources/application.properties
spring.profiles.active=h2
```

### Using Oracle Database

If you have valid Oracle Database and want to try, follow these steps:

1. Copy and run the scripts in `src\main\resources\schema.sql` in your [SQL Developer](https://www.oracle.com/ca-en/database/sqldeveloper/) to create the necessary database schema.
2. Create a new file named `application-oracle.properties` in `src/main/resources`.
3. Configure the Oracle database connection properties in `application-oracle.properties`:

```properties
# src/main/resources/application-oracle.properties
spring.datasource.url=jdbc:oracle:thin:@<your_url>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

4. Make sure `Oracle` profile is activated in the `application.properties` file:

```properties
# src/main/resources/application.properties
spring.profiles.active=Oracle
```

## Usage

To run the application, execute the following command in the project directory:

```shell
./mvnw spring-boot:run
```

The application will start, and you can access it at <http://localhost:8080>. However, since the app is in developing, you may see default error page.  
At that moment, you may like to visit <http://localhost:8080/api/staff> and see it is in running status.

## Testing

### Unit Testing and Intergration Testing

```shell
./mvnw test
```

### Rest API Test

#### VS Code (Recommended)

1. Install *REST Client* as an extension, make sure it is enabled.
2. Make sure the application is running.
3. Open `src/test/resources/api_tests.http`.
4. Click `Send Request` above each test and wait for the result.
5. Read the comments in the file for the best experience.

#### Postman

1. Open `src/test/resources/api_tests.http`
2. Follow `api_tests.http` file to perform API test in *Postman*.

### File Structure

```css
└── .
    ├── 📁src
    |   ├── 📁main
    |   │   ├── 📁java
    |   │   |   └── 📁com
    |   │   |       └── 📁dongli
    |   │   |           └── 📁dream_home
    |   │   |               ├── 📁controller
    |   │   |               ├── 📁dto
    |   │   |               ├── 📁exception
    |   │   |               ├── 📁model
    |   │   |               ├── 📁repository
    |   │   |               ├── 📁service
    |   │   |               └── DreamHomeApplication.java    
    |   │   └── 📁resources
    |   │       ├── 📁static
    |   │       ├── 📁templates
    |   │       ├── application.properties
    |   │       ├── application-oracle.properties
    |   │       ├── application-h2.properties
    |   │       └── schema.sql
    |   └── 📁test
    |       ├── 📁java
    |       |   └── 📁com
    |       |       └── 📁dongli
    |       |           └── 📁dream_home
    |       |               ├── 📁controller
    |       |               ├── 📁dto
    |       |               ├── 📁exception
    |       |               ├── 📁model
    |       |               ├── 📁repository
    |       |               └── 📁service
    |       |               └── DreamHomeApplicationTests.java
    |       └── 📁resources
    |               api_tests.http
    ├── 📁.mvn
    │   └─── 📁wrapper
    ├── 📁target
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── ...
```
