# Fruit API H2
**Description**: REST API for managing fruit inventory built with Spring Boot and H2 in-memory database. Implements full CRUD operations following MVC architecture and TDD methodology.

## 📌 Exercise Statement

Develop a REST API to manage fruit stock entries for a fruit shop. The application must:
- Allow CRUD operations (Create, Read, Update, Delete) on fruit entries
- Store fruit name and weight in kilograms
- Use H2 in-memory database for data persistence
- Follow TDD (Test-Driven Development) approach
- Implement proper validation and exception handling
- Use DTO pattern to separate entities from API responses
- Create a multi-stage Dockerfile for production deployment

## ✨ Features

- **RESTful API** with proper HTTP status codes (200, 201, 204, 400, 404)
- **Bean Validation** for input data (@NotBlank, @Positive)
- **Global Exception Handler** for centralized error management
- **DTO Pattern** to avoid exposing JPA entities
- **Three-layer architecture**: Controller → Service → Repository
- **Comprehensive test suite**:
    - Unit tests for Controller (MockMvc + @WebMvcTest)
    - Unit tests for Service (Mockito)
    - Integration tests (@SpringBootTest)
- **Docker support** with multi-stage build

## 🛠 Technologies

**Backend Framework:**
- Java 21 (LTS)
- Spring Boot 4.0.3
- Spring Data JPA
- Spring Web
- Spring Validation
- Hibernate ORM 7.2.4

**Database:**
- H2 Database (in-memory)

**Build Tool:**
- Maven

**Testing:**
- JUnit 5
- Mockito
- MockMvc
- AssertJ

**DevOps:**
- Docker
- Multi-stage Dockerfile

**Development Tools:**
- IntelliJ IDEA
- Git/GitHub
- Postman/curl
- Spring Boot DevTools

## 🚀 Installation and Execution

### Prerequisites
- Java 21 installed
- Maven 3.9+ (or use included wrapper `./mvnw`)
- IntelliJ IDEA (recommended)
- Docker (optional, for containerized execution)

### 1. Clone the repository

**Using SSH:**
```bash
git clone git@github.com:federicopratico/S4.2-fruit-api-h2.git
cd S4.2-fruit-api-h2
```

**Using HTTPS:**
```bash
git clone https://github.com/federicopratico/S4.2-fruit-api-h2.git
cd S4.2-fruit-api-h2
```

### 2. Run with IntelliJ IDEA

1. Open IntelliJ IDEA
2. **File → Open** → Select the cloned project folder
3. Wait for Maven to download dependencies
4. Locate `FruitApiH2Application.java` in `src/main/java/.../fruitapih2/`
5. **Right-click → Run 'FruitApiH2Application'**
6. The application will start on `http://localhost:9000`

**Run tests in IntelliJ:**
- **Right-click on `src/test/java` → Run 'All Tests'**
- Or run individual test classes by right-clicking on them

### 3. Run with Maven
```bash
# Run all tests
./mvnw clean test

# Start the application
./mvnw spring-boot:run
```

The API will be available at `http://localhost:9000`

### 4. Run with Docker
```bash
# Build Docker image
docker build -t fruit-api:1.0 .

# Run container
docker run -p 9000:9000 fruit-api:1.0
```

### 5. Access H2 Console (Development)
- URL: `http://localhost:9000/h2-console`
- JDBC URL: `jdbc:h2:mem:fruitdb`
- Username: `sa`
- Password: (leave empty)

## 📸 Demo

### API Endpoints

#### Create Fruit (POST)
```bash
curl -X POST http://localhost:9000/fruits \
  -H "Content-Type: application/json" \
  -d '{"name":"Apple","weightInKilos":5}'

# Response: 201 Created
{
  "id": 1,
  "name": "Apple",
  "weightInKilos": 5
}
```

#### Get All Fruits (GET)
```bash
curl http://localhost:9000/fruits

# Response: 200 OK
[
  {"id": 1, "name": "Apple", "weightInKilos": 5},
  {"id": 2, "name": "Banana", "weightInKilos": 3}
]
```

#### Get Fruit by ID (GET)
```bash
curl http://localhost:9000/fruits/1

# Response: 200 OK
{
  "id": 1,
  "name": "Apple",
  "weightInKilos": 5
}
```

#### Update Fruit (PUT)
```bash
curl -X PUT http://localhost:9000/fruits/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Green Apple","weightInKilos":6}'

# Response: 200 OK
{
  "id": 1,
  "name": "Green Apple",
  "weightInKilos": 6
}
```

#### Delete Fruit (DELETE)
```bash
curl -X DELETE http://localhost:9000/fruits/1

# Response: 204 No Content
```

#### Validation Error Example
```bash
curl -X POST http://localhost:9000/fruits \
  -H "Content-Type: application/json" \
  -d '{"name":"","weightInKilos":-5}'

# Response: 400 Bad Request
{
  "timestamp": "2026-03-11T...",
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Provide a name for the new fruit",
    "weightInKilos": "Weight must be positive"
  }
}
```

## 🧩 Diagrams and Technical Decisions

### MVC Architecture

```
┌─────────────────────────────────────────────────────┐
│                   Client (HTTP)                     │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│                 CONTROLLER LAYER                    │
│  - FruitController                                  │
│  - Handles HTTP requests/responses                  │
│  - Input validation (@Validated)                    │
│  - Maps to DTOs                                     │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│                  SERVICE LAYER                      │
│  - FruitService                                     │
│  - Business logic                                   │
│  - DTO ↔ Entity mapping (FruitMapper)               │
│  - Transaction management (@Transactional)          │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│                REPOSITORY LAYER                     │
│  - FruitRepository (JpaRepository)                  │
│  - Database operations (CRUD)                       │
│  - Query methods                                    │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│              DATABASE (H2 In-Memory)                │
│  - Table: fruit                                     │
└─────────────────────────────────────────────────────┘
```

### Database Schema

```
┌──────────────────────────────────────┐
│              FRUIT                   │
├──────────────────────────────────────┤
│ id (BIGINT) [PK, AUTO_INCREMENT]     │
│ name (VARCHAR(255))                  │
│ weight_in_kilos (INTEGER)            │
└──────────────────────────────────────┘
```

### Request/Response Flow

```
POST /fruits
{"name":"Apple", "weightInKilos":5}
         │
         ▼
┌────────────────────────┐
│  1. FruitController    │
│  @PostMapping          │
│  @Validated input      │
└───────────┬────────────┘
            │ Bean Validation
            ▼
     ✅ Valid? ──No──> 400 Bad Request
            │
           Yes
            │
            ▼
┌────────────────────────┐
│  2. FruitService       │
│  createFruit(dto)      │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  3. FruitMapper        │
│  DTO → Entity          │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  4. FruitRepository    │
│  save(entity)          │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  5. H2 Database        │
│  INSERT + GENERATE ID  │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  6. FruitMapper        │
│  Entity → ResponseDTO  │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  7. FruitController    │
│  ResponseEntity 201    │
└───────────┬────────────┘
            │
            ▼
    {"id":1,"name":"Apple","weightInKilos":5}
```

### Technical Decisions

**1. DTO Pattern**
- Separates API contract from database entities
- Allows independent evolution of API and database schema
- Security: prevents exposure of internal entity details
- `RequestFruitDTO` for input, `ResponseFruitDTO` for output

**2. GlobalExceptionHandler (@ControllerAdvice)**
- Centralized exception management
- Consistent error response format
- Handles validation errors (400) and resource not found (404)

**3. Bean Validation**
- Input validation at controller level (@Validated)
- Annotations: @NotBlank, @Positive
- Fails fast before business logic execution

**4. Three-Layer Architecture**
- **Controller**: HTTP handling, no business logic
- **Service**: Business logic, transaction boundaries
- **Repository**: Data access, JPA operations

**5. TDD Approach**
- Tests written before implementation
- Unit tests: MockMvc (@WebMvcTest), Mockito
- Integration tests: @SpringBootTest with real H2 database
- 25+ tests covering happy/unhappy paths

**6. Multi-Stage Docker Build**
- Stage 1 (builder): JDK 21 + Maven → compile JAR
- Stage 2 (runtime): JRE 21 → execute JAR
- Result: smaller image (~250MB vs ~520MB)

**7. H2 In-Memory Database**
- Fast startup, no persistence needed
- Automatic schema generation (DDL auto)
- Ideal for development and testing

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/.../fruitapih2/
│   │   ├── controllers/
│   │   │   └── FruitController.java
│   │   ├── services/
│   │   │   ├── FruitService.java
│   │   │   └── FruitServiceImpl.java
│   │   ├── repository/
│   │   │   └── FruitRepository.java
│   │   ├── model/
│   │   │   └── Fruit.java
│   │   ├── DTOs/
│   │   │   ├── RequestFruitDTO.java
│   │   │   └── ResponseFruitDTO.java
│   │   ├── utils/
│   │   │   └── FruitMapper.java
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java
│   │       ├── ResourceNotFoundException.java
│   │       └── ErrorResponse.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/.../fruitapih2/
        ├── controllers/
        │   └── FruitControllerTest.java
        ├── services/
        │   └── FruitServiceImplTest.java
        └── integration/
            └── FruitIntegrationTest.java
```

---

**Author**: Federico Praticò  
**Course**: IT Academy - Spring Framework Specialization  
**Exercise**: S4.02 - Introduction to Spring Boot