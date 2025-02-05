# Student Management
A repository containing the backend code for student management wherein we can perform the following functions:
1. Add a Student. 
2. Search a student by name (with partial mapping, pagination and sorting). 
3. List all the Students (with pagination and sorting).
4. Update a student by using its name (with field validation and controlled updates).
5. Update a student using its studentId (with field validation and controlled updates).
6. Delete a student by using name.
7. Delete a student using its studentId.


## Technologies Used:
- **Java:** Version 17
- **Spring Boot:** 3.4.2
- **Spring Data JPA**
- **Hibernate**
- **Database:** MySQL
- **Build Tool:** Maven
- **ModelMapper**


## Getting Started
### Prerequisites
Ensure you have following installed
- Java 17
- Maven
- MySQL
- Git

### Clone the repository
```
git clone https://github.com/ramanks19/student-management-backend.git
```

### Configure the Database:
Update application.properties with your database credentials:
```
spring.datasource.url=jdbc:mysql://localhost:3306/studentmanagement
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Build and Run
```
mvn clean install
mvn spring-boot:run
```


## API Endpoints
### Add a Student:
```
curl --location 'localhost:8080/api/students' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Raman Kumar Singh",
    "age": "31",
    "studentClass": "XII-Science",
    "phoneNumber": "1234567890"
}'
```
Response: 201

### Search Students By Name:
Here, you can make a search of a student either using its full name or even a partial name. The results will be paginated as well as sorted on the basis of name. 
```
curl --location 'localhost:8080/api/students/search?name=R&pageNo=0&pageSize=5&sortBy=name&sortDir=asc'
```
Response: 200

### List all the students:
Here, you can list all the students saved in the students table. The results will be paginated as well as sorted on the basis of name.
```
curl --location 'localhost:8080/api/students?pageNo=0&pageSize=5&sortBy=name&sortDir=asc'
```
Response: 200

### Update a student by using its name:
Here, you can update a student based on its name. To use this API you need to have a perfect match of the student's name. Also, this endpoint allows you to make a controlled update by passing the required fields in the request URL as part of query parameter.
```
curl --location --request PUT 'localhost:8080/api/students/by-name/Rani?fieldsToUpdate=age%2CphoneNumber' \
--header 'Content-Type: application/json' \
--data '{
    "age": 22,
    "phoneNumber": "9876543210"
}'
```
The above request will only work if there is a student with name "Rani", otherwise it will throw an exception. For a successfull request-response, you will get a response code of "200"
**Please note that if for the above request there are multiple students with same name, please use the below endpoint wherein you can update a student by using its id**

### Update a student by using its studentId:
Here, you can update a student based on its id. This endpoint allows you to make a controlled update  by passing the required fields in the request URL as part of query parameter.
```
curl --location --request PUT 'localhost:8080/api/students/by-id/2?fieldsToUpdate=age%2CphoneNumber' \
--header 'Content-Type: application/json' \
--data '{
    "age": 22,
    "phoneNumber": "9876543210"
}'
```

### Delete a Student by using its name:
Here, you can delete a student based on its name. To use this API you need to have a perfect match of the student's name.
```
curl --location --request DELETE 'localhost:8080/api/students/by-name/Rani'
```
The above request will only work if there is a student with name "Rani", otherwise it will throw an exception. For a successfull request-response, you will get a response code of "200" with a message "Student with name: Rani is deleted successfully!!!". **Please note that if for the above request there are multiple students with same name, please use the below endpoint wherein you can delete a student by using its id

### Delete a Student by using its studentId:
Here, you can delete a student based on its studentId.
```
curl --location --request DELETE 'localhost:8080/api/students/by-id/2'
```

