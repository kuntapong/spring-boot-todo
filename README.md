# spring-boot-todo
A Spring-boot REST example app

## MongoDB
The application uses MongoDB as a database. You should install MongoDB on your local machine or change the configuration in application.yml to your MongoDB server.
https://docs.mongodb.com/manual/installation/

## How to run
* Maven install
```
./mvnw clean install
```
* Run Application
```
./mvnw spring-boot:run
```

## Run Unit Tests
```
./mvnw test
```

## Endpoints
* Find Todo list by title and due date.
  * Endpoint: http://localhost:8080/api/v1/todos/
  * Method: GET
  * Parameters: title(String), dueDate(Unix Timestamp)
  * Example
  ```
  http://localhost:8080/api/v1/todos/?title=test&dueDate=1519011642626
  ```
* Create new Todo
  * Endpoint: http://localhost:8080/api/v1/todos/
  * Method: POST
  * RequestBody: title(String), description(String), dueDate(Unix Timestamp)
  * Example
  ```
  http://localhost:8080/api/v1/todos/
  {
    "title": "test1",
    "description": "desc 1",
    "dueDate": 1519011642626
  }
  ```
* Edit Todo
  * Endpoint: http://localhost:8080/api/v1/todos/{todoId}
  * Method: PUT
  * PathParameter: todoId(String)
  * RequestBody: title(String), description(String), dueDate(Unix Timestamp)
  * Example
  ```
  http://localhost:8080/api/v1/todos/5a86775115e8a65188ae5354
  {
    "title": "test1",
    "description": "desc 1",
    "dueDate": 1519011642626
  }
  ```
* Delete Todo
  * Endpoint: http://localhost:8080/api/v1/todos/{todoId}
  * Method: DELETE
  * PathParameter: todoId(String)
  * Example
  ```
  http://localhost:8080/api/v1/todos/5a86775115e8a65188ae5354
  ```
