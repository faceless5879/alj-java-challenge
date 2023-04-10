### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

#### My experience in Java

- With Java I'm a beginner. I took C# lessons in college and I found C# quite similar to Java.
- About Spring Boot I heard it from my colleagues and friends but never use it before. This is my first hands-on with it

### About the thing I did with this project

1. **Fix bugs**
   After cloning and setting up the project, I tested all endpoints using POSTMAN and identified several issues that needed to be addressed.
   Firstly, the `POST /employees` endpoint was not saving any data to the H2 database. To fix this, I added the `@RequestBody` annotation to the endpoint.
   Secondly, the `PUT /employees` endpoint was containing logic operations in the controller instead of delegating them to the service layer. This is not best practice, so I moved the logic operation part to the service layer to ensure separation of concerns.
   Thirdly, the `PUT /employees` endpoint was creating a new record in the database instead of updating an existing one with the updated information. To address this, I updated the service layer's operation to retrieve the target record and update it with the new information.
   By addressing these issues, we were able to improve the functionality and maintainability of the application.

2. **Add unit tests for each endpoints**
   After conducting research, I discovered that the `MockMvc` is a widely used unit test framework for Spring Boot applications. I successfully implemented the first test using `MockMvc` to test the `GET /employees` endpoint and proceeded to write additional tests for the remaining endpoints.

3. **Implement caching**
   After researching best practices for caching, I determined that implementing cache for `GET` requests would be the most effective approach. `GET` requests are typically read-heavy and have a low rate of change, making them well-suited for caching.
   To implement caching in the application, I explored options available in the Spring Framework for Java and found that the `@Cacheable` annotation provides declarative caching support and is easy to implement.
   By using the `@Cacheable` annotation in the appropriate places, we can cache the results of `GET` requests and improve the performance and efficiency of the application. This approach is in line with industry best-practices for caching and can help to ensure that the application is responsive and scalable.

4. **Add POSTMAN collection**
   This is optional but for other developers who love POSTMAN like me, I hope they can find my POSTMAN collection is useful :)

### About the thing I would have done if I had more time

1. **Add autithencation to protect endpoints**
   Unfortunately, due to time constraints, I was unable to complete the implementation of JSON Web Tokens (JWT) with the current application. However, I think I finished planning on how to implement it. If I had more time I will add necessary dependencies, creat a `User` service, creating a `JwtTokenUtil` class, configuring Spring Security to use JWT authentication, protecting endpoints with a white list of domain, creatt a login/signup endpoint, and test the authentication flow to ensure everything is working as expected.

2. **Create exception handle system**
   Instead of leaving exection handling to default, I want to implement more proper exception handle system. For example, I propose creating a custom exception called `EmployeeNotFoundException`. This exception can be used to handle cases where the employee with a given ID cannot be found, and can be implemented in functions such as `getEmployee()`, `deleteEmployee()`, and `updateEmployee()`. By using a custom exception, we can provide more informative error messages to the user and handle exceptions in a more consistent and structured way across different parts of our application. This can improve the overall reliability and maintainability of our code."
