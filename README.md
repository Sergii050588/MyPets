# spring-rest-pet-project

## Building and Running

Make sure that [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) is installed and is on the path.

Project uses [Maven](http://maven.apache.org/) as a build tool.

Create Databases in PostgreSQL with User: "postgres". First: "petproject", second: "petproject_test".

Command line: psql -U postgres -> create database petproject; -> create database petproject;

Clone or download the project. Navigate to the project folder.

Build project with command: "mvn package".

Run the app with command: java -jar target/demo-0.0.1-SNAPSHOT.jar

Start the App and navigate to [http://localhost:8080](http://localhost:8080) to surf through the app.

Primary Basic Auth: Username: "admin", Password: "123".

Navigate to [http://localhost:8080/api-guide](http://localhost:8080/api-guide.html) to get the App Documentation.
