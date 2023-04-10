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
   After clone this project and setting it up, I tested all endpoints with POSTMAN and found below issues: - The first issue is POST /employees doenst save any data to H2 database. So I add @RequestBody to POST /employees. - The second issue is the way PUT /employees being executed. Spring Boot controller should do tasks like request-mapping and validation, but PUT /employees's controller was contained logic operation. With this issue, I moved logic operation part to service layer. - The third problem I found is instead of updating database records, PUT /employees creates new record with updated information. So I updated service layer's operation to get the target record then update it with new information.

2. **Add unit tests for each endpoints**
   I did a research and found that mockmvc is the most popular API unit test framework for Spring Boot. After getting the first test done with GET /employees, I implemented the rest of them.

3. **Implement caching**
   After considering the best-pratices with caching, I decided that I would only implement cache for GET requests. Because they are read-heavy and have a low rate of change.
   With a list of which endpoints should be implemented with cache, I look up for how to implement cache with Spring Boot. I found `@Cacheable` as an annotation in the Spring Framework for Java that provides declarative caching support. It's also very easy to implement.

4. **Add POSTMAN collection**
   This is optional but for someone who love POSTMAN like me, I hope they can find my POSTMAN collection is useful :)

### About the thing I would have done if I had more time

1. **Add autithencation to protect endpoints**
   Unfortunately, due to time constraints, I was unable to complete the implementation of JSON Web Tokens (JWT) with the current application. However, I think I finished planning on how to implement it. If I had more time I will add necessary dependencies, creat a `User` service, creating a `JwtTokenUtil` class, configuring Spring Security to use JWT authentication, protecting endpoints with a white list of domain, creatt a login/signup endpoint, and test the authentication flow to ensure everything is working as expected.

2. **Create exception handle system**
   Instead of leaving exection handling to default, I want to implement more proper exception handle system. For example, I propose creating a custom exception called `EmployeeNotFoundException`. This exception can be used to handle cases where the employee with a given ID cannot be found, and can be implemented in functions such as `getEmployee()`, `deleteEmployee()`, and `updateEmployee()`. By using a custom exception, we can provide more informative error messages to the user and handle exceptions in a more consistent and structured way across different parts of our application. This can improve the overall reliability and maintainability of our code."
