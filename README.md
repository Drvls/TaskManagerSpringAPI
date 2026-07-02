# Task Manager API

REST API for task management built with Java and Spring Boot.

## Stack

- Java 25
- Spring Boot 4
- Spring Data JPA + Hibernate
- PostgreSQL
- Flyway
- Bean Validation
- Lombok
- JUnit 5 + Mockito
- Docker + Docker Compose

## Architecture

The project follows a layered architecture with light DDD influence, organized to keep concerns separated and the domain isolated from infrastructure details.

```
src/main/java/org/alexvsi/taskmanager/
├── api/
│   └── controller/        # HTTP layer — receives requests, returns responses
├── application/
│   └── dto/               # Request and response contracts
├── domain/
│   ├── entity/            # Task entity
│   ├── enums/             # Priority and Status
│   ├── exception/         # Domain exceptions
│   ├── repository/        # (see infra)
│   ├── service/           # Business logic
│   └── specification/     # Dynamic query filters
└── infra/
    ├── exception/         # Global exception handler
    └── repository/        # JPA repository
```

The domain has no dependency on Spring or any infrastructure concern. Business rules live in `TaskService`, which operates on entities and throws domain exceptions, never HTTP status codes.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/tasks` | Create a task |
| `PUT` | `/tasks/{id}` | Full update |
| `PATCH` | `/tasks/{id}` | Partial update |
| `GET` | `/tasks/{id}` | Get by ID |
| `GET` | `/tasks` | List with filters and pagination |
| `DELETE` | `/tasks/{id}` | Delete by ID |

### Query parameters for `GET /tasks`

| Parameter | Type | Required |
|-----------|------|----------|
| `page` | int | No (default: 0) |
| `size` | int | No (default: 10) |
| `title` | String | No |
| `priority` | HIGH / MEDIUM / LOW | No |
| `deadline` | yyyy-MM-dd | No |
| `status` | PENDING / COMPLETED | No |

All filters are optional and combinable. Omitting all of them returns all tasks paginated.

### Request body

```json
{
  "title": "Read Clean Code",
  "description": "Focus on chapters 1 to 5",
  "priority": "HIGH",
  "deadline": "2026-06-01",
  "taskStatus": "PENDING"
}
```

### Validation rules

- `title` — required, max 50 characters
- `description` — optional, max 150 characters
- `priority` — required
- `deadline` — optional, must be today or future
- `taskStatus` — required

Validation errors return `400 Bad Request` with a map of field → message.

## Running locally

### Without Docker

**Requirements:** Java 25, PostgreSQL

**1. Create the database**

Connect to your local PostgreSQL instance and run:

```sql
CREATE DATABASE taskmanager;
```

**2. Configure the connection**

Update `src/main/resources/application.properties` with your local credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**3. Run the application**

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080/tasks`.

Flyway runs the migrations automatically on startup, no manual setup required.

### With Docker

**Requirements:** Docker, Docker Compose

**1. Set up environment variables**

Copy the example file and fill in the values:

```bash
cp .env.example .env
```

```env
DATABASE_HOST=db-postgres
POSTGRES_DATABASE_NAME=taskmanager
POSTGRES_DATABASE_USER=your_username
POSTGRES_DATABASE_PASSWORD=your_password
POSTGRES_DATABASE_PORT=5432
TASK_API_PORT=8080
```

> `DATABASE_HOST` must match the Compose service name (`db-postgres`), not `localhost`.

**2. Start the containers**

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080/tasks`.

The database container exposes port `5432` locally if you need to inspect it with a client like DBeaver or psql.

To stop and remove the containers:

```bash
docker compose down
```

To also remove the persistent volume (wipes all data):

```bash
docker compose down -v
```

## Tests

Unit tests cover the service and controller layers.

- `TaskServiceTest` — business logic, exception handling, repository interactions
- `TaskControllerTest` — HTTP status codes, response body, Bean Validation

```bash
./mvnw test
```

## Database schema

```sql
CREATE TABLE task (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(50)  NOT NULL,
    description VARCHAR(150),
    priority    VARCHAR(6)   NOT NULL,
    deadline    DATE         NOT NULL,
    task_status VARCHAR(10)  NOT NULL
);
```
