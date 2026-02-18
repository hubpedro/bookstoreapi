# Bookstore API

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

RESTful API for bookstore management featuring **JWT authentication**, **granular permission control**, and an **automatic fine calculation engine** for overdue loans.

---

## Quick Start

```bash
git clone https://github.com/hubpedro/bookstoreapi.git
cd bookstoreapi && docker-compose up --build
```

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## Architecture & Design Decisions

### Why Granular Permissions Instead of Just Roles?

Most APIs stop at roles (`ADMIN`, `USER`). This project implements a two-layer authorization model:

- **Roles** define broad access groups
- **Permissions** define specific operations (e.g., `BOOK_READ`, `LOAN_CREATE`, `LOAN_READ`)

This allows fine-grained control — a user can have `LOAN_READ` without `LOAN_CREATE`, which is closer to real-world enterprise requirements.

### Why Optimistic Locking on Stock?

The stock management uses `@Version` (JPA Optimistic Locking) to handle concurrent loan requests for the last available copy of a book. Without this, two simultaneous requests could both read `stockQty = 1`, decrement it, and result in `-1` — a classic race condition. Optimistic locking detects the conflict and fails fast, letting the application retry or reject gracefully.

### Fine Calculation Logic

The late fee engine is not a simple multiplication. It accounts for:
- Business days only (weekends excluded)
- Configurable daily rate via environment variable
- Atomic stock restoration on return

### Tech Stack

| Layer | Technology | Rationale |
|-------|-----------|-----------|
| Language | Java 17 | Modern LTS with records, pattern matching, sealed classes |
| Framework | Spring Boot 3 | Production-grade, native Spring Security integration |
| Security | Spring Security + JWT | Stateless auth, scales horizontally |
| Persistence | Spring Data JPA + PostgreSQL | ACID guarantees, mature ORM |
| Testing | JUnit 5 + Mockito + Testcontainers | Full test pyramid, real DB on integration tests |
| Documentation | Springdoc OpenAPI 3 | Auto-generated interactive docs |
| Infrastructure | Docker + Docker Compose | Reproducible environments, deploy-ready |

---

## Domain Model

```
┌─────────────┐       ┌──────────────────┐       ┌─────────────┐
│    User     │       │      Loan        │       │    Book     │
├─────────────┤       ├──────────────────┤       ├─────────────┤
│ id (PK)     │──┐    │ id (PK)          │    ┌──│ id (PK)     │
│ username    │  └──<─│ userId (FK)      │    │  │ title       │
│ password    │       │ bookId (FK)      │>───┘  │ author      │
│ roles       │       │ loanedAt         │       │ isbn        │
│ permissions │       │ dueOn            │       │ stockQty    │
└─────────────┘       │ returnedAt       │       │ version     │ ← optimistic lock
                      │ loanDebt         │       └─────────────┘
                      │ status           │
                      └──────────────────┘
```

---

## API Reference

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Register new user |
| `POST` | `/api/auth/login` | Authenticate and receive JWT |

### Books

| Method | Endpoint | Auth Required |
|--------|----------|---------------|
| `GET` | `/api/books` | `BOOK_READ` |
| `GET` | `/api/books/{id}` | `BOOK_READ` |
| `POST` | `/api/books` | `BOOK_WRITE` |
| `PUT` | `/api/books/{id}` | `BOOK_WRITE` |
| `DELETE` | `/api/books/{id}` | `BOOK_DELETE` |

### Loans

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/loans` | Create loan (validates stock) |
| `PATCH` | `/api/loans/{id}/return` | Return book + calculate fine |
| `GET` | `/api/loans/user/{userId}` | Loan history |

**Loan response example (with late fee):**

```json
{
  "id": 1,
  "userId": 5,
  "bookId": 10,
  "loanedAt": "2025-10-01",
  "dueOn": "2025-10-15",
  "returnedAt": "2025-10-20",
  "status": "RETURNED",
  "loanDebt": 10.00
}
```

---

## Testing

```bash
./mvnw test
```

The test suite covers three layers:

**Unit Tests** — Service logic in isolation, using Mockito to mock dependencies. Core focus: fine calculation edge cases and stock validation.

**Integration Tests** — Controllers tested end-to-end with Testcontainers spinning up a real PostgreSQL instance. No H2, no mocks at the DB layer.

**Security Tests** — JWT validation, role enforcement, and permission boundaries tested against actual Spring Security filter chain.

```java
@Test
void shouldCalculateLateFeeWhenBookReturnedAfterDueDate() {
    // Given: loan with 5 overdue days
    Loan loan = createLoanWithDueDate(LocalDate.now().minusDays(5));

    // When: book is returned
    loanService.returnBook(loan.getId());

    // Then: R$10 fine applied (5 days × R$2.00)
    assertEquals(new BigDecimal("10.00"), loan.getLoanDebt());
}
```

---

## Running Locally

### Option 1: Docker Compose (recommended)

```bash
docker-compose up --build
```

Starts API + PostgreSQL fully integrated.

### Option 2: Database only (local development)

```bash
docker run --name bookstore-db \
  -e POSTGRES_DB=bookstoredb \
  -e POSTGRES_USER=developer \
  -e POSTGRES_PASSWORD=devpass \
  -p 5432:5432 \
  -d postgres:15-alpine

./mvnw spring-boot:run
```

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/bookstoredb` | Database URL |
| `JWT_SECRET` | `change-in-production` | JWT signing key |
| `JWT_EXPIRATION` | `86400000` | Token expiration (ms) |
| `LOAN_DAILY_FEE` | `2.00` | Daily late fee (BRL) |

---

## Key Engineering Challenges

**1. Concurrent Stock Decrement**
When two users simultaneously request the last copy, both reads see `stockQty = 1`. Without coordination, both loans would be approved. Solved with JPA `@Version` — the second transaction detects a stale read and throws `OptimisticLockException`, which the service layer handles by returning a meaningful conflict response.

**2. Permission Granularity Beyond Roles**
Spring Security's default `hasRole()` wasn't enough. Implemented a custom `PermissionEvaluator` that resolves user-level permissions independently of their role hierarchy, enabling scenarios like read-only admin assistants or loan-only staff users.

**3. Integration Tests Without H2**
H2 dialects diverge from PostgreSQL in subtle ways (sequence behavior, constraint handling). Using Testcontainers ensures tests run against the exact same engine as production, catching issues H2 would silently ignore.

---

## Roadmap

- [ ] Redis cache for frequent book queries
- [ ] Email notifications for upcoming due dates
- [ ] Reporting endpoints (most borrowed books, top debtors)
- [ ] AWS S3 integration for book cover uploads
- [ ] Rate limiting with Bucket4j

---

## Author

**Pedro Barbosa** — Backend Developer, Java & Spring Boot

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin)](https://www.linkedin.com/in/pedrobbarbosa/)
[![GitHub](https://img.shields.io/badge/GitHub-Follow-black?logo=github)](https://github.com/hubpedro)
