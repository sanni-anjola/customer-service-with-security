Customer Service Application

This is a simple application that registers a user(customer) for any application that involves a customer.
The app implements authentication and authorization with Spring security and Jwt.

The Application uses
- Spring Web
- Spring validation
- Spring Security
- Jwt
- lombok

For database
MySql database was used. To set up mysql for this application
Go to **src/main/resources/db/setup.sql**, copy the script and paste in a mysql shell.


To run the application
- Go to the application.yaml file and fill in the smtp credentials of a mail client of your choice
- Or create an application-dev.yaml file locally and fill in the credentials

To Build
run form the application directory (Note: You must have maven installed)
```shell
$ mvn clean install
OR
$ ./mvnw clean install
```
To Run

Java JDK or JRE must be installed.
```shell
$ java -jar ./target/customer-service.jar
```
To Register
- Go to http://localhost:8080/api/v1/auth/signup
- The required parameters are:
  - name: String
  - username: String
  - email: String
  - password: String
  - role: int [1 - admin, 2 - user, 3 - customer]

The application sends a verification email with a link to verify user/customer account.

For other endpoints, go to the **swagger documentation** endpoint

  http://localhost:8080/api/v1/swagger.html
