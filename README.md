# ewave-customer-address
Coding task: Write Http endpoint API to manage customers and addresses 


Author: Daniel Gorski

## Running the Application

* Clone the main application using git:
```
git clone  https://github.com/daniel77/ewave-customer-address.git
```

To run this application you will need java and maven installed/  
You can run it locally just by running the command:
```
mvn package
```
and then
```
java -jar target/volvo-0.0.1-SNAPSHOT.jar
```
The application will run on port 8080.

You can also compile and run the application.
Using without tests:
```
mvn spring-boot:run
```

## Unit testing
The class VolvoApplicationTests execute controllers tests with the test server running.
```
mvn test
```
