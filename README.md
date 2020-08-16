# person-info-spring-boot

This is a simple app used to store person and contacts information.

# Technical Stack

- Java 8
- Maven
- Spring Boot
- Spring JPA Data
- Hibernate with SQL database
- Spring Cache with Ehcache manager
- Lombok
- ModelMapper
- REST API model validation

# DB Design

![image info](./images/person_info_ER_diagram.png)

# Getting Started

- Import schema in your sql DB to create required database. Please find schema.sql file in the root folder of this project. DB tables shall automatically be created using hibernate ddl on application startup.

- Use these commands on the root folder to build and start the application.
    ```
    ./mvnw spring-boot:run
    ```
    OR
    ```
    mvn spring-boot:run
    ```
- To stop the application use these commands on the root folder.
    ```
    ./mvnw spring-boot:stop
    ```
    OR
    ```
    mvn spring-boot:stop
    ```
- After starting the application you can access it on your local machine on the following path http://localhost:8080/person-info

# CURL examples for API endpoints

Add a Person
------------

```
curl -d '{"name":"person-updated", "surname":"person-updated", "age": 25, "sex": "Male", "phone": "9243950838989","email": "test@gmail.com","birthday": "20-08-1996"' -H "Content-Type: application/json" -X POST http://localhost:8080/person-info/add
```
**Note: To add contacts for a person, contact person ID must exist in DB**

Get a Person by ID
------------------

```
curl -d -H "Content-Type: application/json" -X GET http://localhost:8080/person-info/get/2
```

Update a Person
---------------

```
curl -d '{"id":"1", "name":"person-updated", "surname":"person-updated", "age": 25, "sex": "Male", "phone": "9243950838989","email": "test@gmail.com","birthday": "20-08-1996"}' -H "Content-Type: application/json" -X POST http://localhost:8080/person-info/update

```

Delete a Person
---------------

```
curl -d -H "Content-Type: application/json" -X DELETE http://localhost:8080/person-info/delete/1
```

Get Persons based on Pagination parameters
------------------------------------------

```
curl -X GET 'http://localhost:8080/person-info/get/all?pageNumber=0&pageSize=2'
```



