# Prerequisites
Java version 17+  
Apache Maven  
PostgreSQL (the application was tested with version 15.4.1) 

## Building Application
Apache Maven is used as the application build tool  
#### Command to build application from the application root directory:
    mvn clean install

## Running Application
### Command to run application from the project root directory:
    java -jar -Dspring.profiles.active=dev -Dspring.datasource.url=<database-connection-url> -Dspring.datasource.username=<database-username> -Dspring.datasource.password=<database-user-password> ./application/target/country-by-phone-app-1.0-SNAPSHOT.jar
Default values:  
<database-connection-url>=jdbc:postgresql://localhost:5432/postgres  
<database-username>=postgres  
<database-user-password>=admin
### Command to run application with default values from the project root directory:
    java -jar -Dspring.profiles.active=dev ./application/target/country-by-phone-app-1.0-SNAPSHOT.jar

## The user interface URL
    http://127.0.0.1:8088/

##  The SWAGGER-UI URL 
    http://127.0.0.1:8088/swagger-ui/index.html

