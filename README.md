# Appointment Scheduling System

A RESTful backend for a medical clinic that allows users to book appointments, managers to assign staff, and admins to manage services. Built with Spring Boot and secured with JWT authentication.

---


| Name | Group |
|------|------|
| Iskenderov Arsen| SCA-24B |

---

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Security + JWT
- Spring Data JPA
- H2 (in-memory database)
- Lombok

---

## Setup Instructions

### Prerequisites
- Java 21+
- Maven (included via `mvnw`)

### Run the project

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`

### Run tests

```bash
./mvnw test
```

---

## Database

Three tables with relationships:

- **users** — system users with roles (ADMIN, MANAGER, USER)
- **services** — clinic services (name, description, duration, price)
- **appointments** — bookings linking users, services, and staff

---

## API List

### Auth — `/api/auth`

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/register` | Public | Register a new user, returns JWT token |
| POST | `/api/auth/login` | Public | Login, returns JWT token |

### Services — `/api/services`

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/services` | Public | Get all services (optional `?search=name`) |
| GET | `/api/services/{id}` | Public | Get service by ID |
| POST | `/api/services` | ADMIN | Create a new service |
| PUT | `/api/services/{id}` | ADMIN | Update a service |
| DELETE | `/api/services/{id}` | ADMIN | Delete a service |

### Appointments — `/api/appointments`

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/appointments` | Public | Get all appointments (optional `?status=PENDING`) |
| GET | `/api/appointments/{id}` | Public | Get appointment by ID |
| GET | `/api/appointments/user/{userId}` | Public | Get appointments by user |
| POST | `/api/appointments` | USER | Book a new appointment |
| PATCH | `/api/appointments/{id}/assign-staff/{staffId}` | MANAGER | Assign staff to appointment |
| PATCH | `/api/appointments/{id}/cancel` | Authenticated | Cancel an appointment |

### Users — `/api/users`

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/users` | ADMIN | Get all users |
| GET | `/api/users/{id}` | ADMIN | Get user by ID |

---

## Authentication

The API uses JWT (JSON Web Token). To access protected endpoints:

1. Register or login to get a token
2. Add the token to request headers:

```
Authorization: Bearer <your_token>
```

### Roles

| Role | Permissions |
|------|-------------|
| ADMIN | Manage services, view all users |
| MANAGER | Assign staff to appointments |
| USER | Book appointments |
| Anyone | Search and view services and appointments |

---

## Example Requests

### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john",
  "email": "john@clinic.com",
  "password": "123456",
  "role": "USER"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "123456"
}
```

### Book an appointment
```http
POST /api/appointments
Authorization: Bearer <token>
Content-Type: application/json

{
  "userId": 1,
  "serviceId": 1,
  "dateTime": "2026-06-01T10:00:00"
}
```
