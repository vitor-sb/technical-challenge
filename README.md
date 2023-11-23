# Project LogiDataTransform 

---

## Table of Contents
1. [ToDo](#todo)
2. [API design](#api-design)
3. [Requirements](#requirements)
4. [Libraries used in the project](#libraries-used-in-the-project)
5. [Defining the environment variables used in application.yml](#defining-the-environment-variables-used-in-applicationyml)

---

#### ToDo
1. Increase Coverage percentage by creating new unit tests

---
## API design

[Main function design](https://miro.com/app/board/uXjVNMKk4OI=/?moveToWidget=3458764570634815267&cot=14)

---

## Requirements
1. OpenJDK 11
2. Kotlin 1.6.21
3. Docker
4. MySQL 8.0.33

---

### Libraries used in the project
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Web](https://spring.io/guides/gs/serving-web-content/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
- [Hibernate Validator](https://hibernate.org/validator/)
- [Liquibase](https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/springboot/springboot/)
- [LoggerFactory](https://www.slf4j.org/api/org/slf4j/LoggerFactory.html)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [Jacoco](https://www.eclemma.org/jacoco/)
- [H2 database](https://www.h2database.com/html/main.html) (For unit test)

---
### Defining the environment variables used in application.yml

#### IntelliJ IDEA
1. Click on `Edit Configuration`
2. In the input `Environment Variables`
    1. Configure the variables in the pattern
        ```
        VARIABLE_A=a;VARIABLE_B=b
        ```
    2. Or click on the icon on the right `Browse`
        1. Click on the icon: `+`
        2. In the `name` field, enter the name of the variable
        3. In the `value` field, enter the value of the variable
        4. Click `OK`
