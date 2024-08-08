# store-management
The project was implemented using Spring Boot maven.
Information is stored in MySQL database. And the tables created are: User, Product and Purchase.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
List of APIs:
- For User: register user, login, update password
- For Product: add new product, update product, update price of product, show product, delete product
- For Purchase: add purchase, get purchase by id, get all purchases, view all purchases made by an user, view all purchases of a product

Exceptions:
The exceptions are handled using custom made exceptions, global exception handler and an error object that contains relevant information that should be returned in the response entity.
For each API, the exceptions are thrown in the service layer. 
Most exceptions are used in the user service: if the email provided is not valid, if the password is not strong enough and if the user already exists, custom exceptions are thrown.
In Purchase and Product services most possible exceptions are the ones with NOT FOUND Http status.

Logs:
For logging, the project uses log4j2. An xml file was configured to assure that logs are stored in /logs/app.log .
The configuration is set with the policie to send the logs in the /logs/app.log file until it reaches the 10MB limit. It logs from level info and higher (error).
The error logs are used when an exception is thrown in the service.

Testing:
   - User Service:
To test the service I used the annotation @SpringBootTest on the test class and @Test for all the test methods. I used assertions to test that the outcome of the functions matches 
my expectations. For each test I created a test user, tested the flow for each method and at the end I deleted the test user. Like this, it doesn't interfere with the other tests
or with other entries from the database.
   - To test the APIs work accordingly, I used Postman.
   - UPDATE: Removed the Controller tests for user, only left the tests for UserService

Java 9+ features:
- The var keyword (java10) that allows for local variable type inference.
- Optional.ifPresentOrElse

The security configuration is started, but only the encryption of passwords in the database. They are not saved in plain text.
