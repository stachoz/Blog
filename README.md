# Web blog application written using Spring, Hibernate, H2-Console and Thymeleaf.


## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `src/main/java/com/example/blogjava/BlogJavaApplication.java` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Functionalities

### Admin
- Delete posts
- Delete, block, unblock users
- Manage posts' reports

### Logged in user
- create, report, comment posts

### Anonymous users
- read posts
- view profiles
