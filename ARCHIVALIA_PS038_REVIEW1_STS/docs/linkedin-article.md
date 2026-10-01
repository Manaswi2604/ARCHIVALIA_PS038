# LinkedIn Article – Archivalia PS038

## Archivalia: Enterprise Academic E-Library & Digital Resource Circulation System

### Problem
Academic institutions manage large collections of e-books, research resources and digital media. Users need a simple way to search resources, borrow them, return them and understand any late-return penalties. A monolithic implementation can make independent scaling, maintenance and service ownership difficult.

### Proposed Solution
Archivalia is designed as a Spring Cloud microservice system. Separate services manage authentication, resources, borrowing and fines. Eureka provides service discovery and the API Gateway provides a single entry point for client requests.

### DTI Concept
The project demonstrates digital transformation of academic resource circulation. Instead of manual or disconnected tracking, users can access a centralized digital repository and perform resource operations through secure REST APIs.

### Architecture
- Auth Service – user/admin authentication and JWT issuance
- Book Service – catalog, search and availability
- Borrow Service – borrow and return lifecycle
- Fine Service – late fine calculation
- Eureka – service registration and discovery
- API Gateway – centralized routing

### Innovation
The main design idea is separating business responsibilities into independent services. Borrow Service communicates with Book Service to update availability and with Fine Service to calculate late penalties. This allows individual services to evolve independently.

### Security
JWT is used for authenticated API access. Passwords are stored as BCrypt hashes. Role information is carried in the authenticated security context.

### Future Enhancement
The Review-1 implementation can be extended with Docker Compose, centralized configuration, distributed tracing, Prometheus/Grafana monitoring, Kafka-based asynchronous events, cloud deployment and stronger production-grade security policies.
