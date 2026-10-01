# ARCHIVALIA PS038 – Review 1
## Enterprise Academic E-Library & Digital Resource Circulation System

A Review-1 SOA / Spring Cloud microservices project designed to run in Spring Tool Suite (STS).

### Included
- Eureka Server – service discovery
- API Gateway – centralized routing
- Auth Service – JWT login for USER/ADMIN
- Book Service – resource catalog and availability
- Borrow Service – borrow/return lifecycle
- Fine Service – automatic late-fine calculation
- PostgreSQL database
- Simple browser frontend
- REST APIs
- Inter-service communication: Borrow -> Book and Borrow -> Fine
- Load-balanced service URLs through Eureka (`lb://...`)

### Technology
- Java 21
- Spring Boot 3.5.6
- Spring Cloud 2025.0.0
- Spring Data JPA
- Spring Security
- JWT (JJWT)
- PostgreSQL
- Maven

## 1. Prerequisites
Install:
1. JDK 21
2. Maven 3.9+
3. PostgreSQL 14+
4. Spring Tool Suite 4

## 2. PostgreSQL
Create a database:

    CREATE DATABASE archivalia;

Default connection used by the services:
- URL: jdbc:postgresql://localhost:5432/archivalia
- username: postgres
- password: postgres

If your PostgreSQL password is different, edit each service's `application.yml`.

## 3. Import into STS
In STS:
- File -> Import -> Maven -> Existing Maven Projects
- Select the project folders one at a time:
  - eureka-server
  - api-gateway
  - auth-service
  - book-service
  - borrow-service
  - fine-service

The `frontend` folder is a normal HTML/CSS/JS client.

## 4. Start order
Start these Spring Boot applications in this order:

1. EurekaServerApplication – port 8761
2. AuthServiceApplication – port 8084
3. BookServiceApplication – port 8081
4. FineServiceApplication – port 8083
5. BorrowServiceApplication – port 8082
6. ApiGatewayApplication – port 8080

Wait about 10–20 seconds after starting Eureka before starting the remaining services.

## 5. URLs
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Auth Service: http://localhost:8084
- Book Service: http://localhost:8081
- Borrow Service: http://localhost:8082
- Fine Service: http://localhost:8083

Frontend:
- Open `frontend/index.html` directly in a browser, or serve the frontend folder with any simple static server.

## 6. Demo accounts
The Auth Service seeds these users on first run:
- USER: user@archivalia.com / user123
- ADMIN: admin@archivalia.com / admin123

Login:
POST http://localhost:8080/api/auth/login

JSON:
{
  "email": "user@archivalia.com",
  "password": "user123"
}

The response contains a JWT token. Send it as:
Authorization: Bearer <token>

## 7. Important API examples

### Search books
GET http://localhost:8080/api/books?search=java

### All books
GET http://localhost:8080/api/books

### Borrow
POST http://localhost:8080/api/borrow
Authorization: Bearer <token>

{
  "bookId": 1,
  "loanDays": 7
}

### Active loans
GET http://localhost:8080/api/borrow/my
Authorization: Bearer <token>

### Return
POST http://localhost:8080/api/borrow/{borrowId}/return
Authorization: Bearer <token>

### Fine
GET http://localhost:8080/api/fines/borrow/{borrowId}
Authorization: Bearer <token>

## 8. Review-1 architecture
Browser
  |
  v
API Gateway :8080
  |
  +--> Auth Service :8084
  |
  +--> Book Service :8081
  |
  +--> Borrow Service :8082
          | \
          |  \--> Fine Service :8083
          \-----> Book Service :8081
  |
  v
Eureka :8761

## 9. Review rubric mapping
1. Problem analysis and requirement specification
2. Microservice identification and service discovery
3. JWT authentication
4. API Gateway configuration
5. LinkedIn article/documentation – see `docs/linkedin-article.md`

## 10. Troubleshooting
- If a service cannot connect to PostgreSQL, check username/password in its `application.yml`.
- If Gateway returns 503, make sure the target service is registered in Eureka.
- If a JWT is rejected, login again and use the newly returned token.
- If ports are occupied, stop old Spring Boot processes before starting the project.
