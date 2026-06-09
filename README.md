# 🚀 Order Management System

A production-ready **Order Management System** built using **Quarkus**, **PostgreSQL**, and **JWT Authentication**. This application provides secure REST APIs for managing customers and orders while following clean architecture and enterprise development practices.

---

## 📌 Features

* JWT Authentication & Authorization
* Customer Management
* Order Management
* RESTful APIs
* PostgreSQL Integration
* Hibernate ORM with Panache
* Input Validation
* Global Exception Handling
* Docker Support
* Clean Layered Architecture

---

## 🛠️ Tech Stack

| Technology    | Purpose                |
| ------------- | ---------------------- |
| Java 21       | Programming Language   |
| Quarkus       | Backend Framework      |
| PostgreSQL    | Database               |
| Hibernate ORM | Persistence Layer      |
| Panache       | Simplified Data Access |
| JWT           | Authentication         |
| Maven         | Build Tool             |
| Docker        | Containerization       |
| Git & GitHub  | Version Control        |

---

## 📂 Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com.mintifi.ordermanagement/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── entity/
│   │       ├── dto/
│   │       ├── security/
│   │       └── exception/
│   └── resources/
│       └── application.properties
└── test/
```

---

## ⚙️ Prerequisites

Before running the project, install:

* Java 21+
* Maven 3.9+
* PostgreSQL
* Docker (Optional)

---

## 🗄️ Database Configuration

Configure PostgreSQL settings in:

```properties
src/main/resources/application.properties
```

Example:

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/order_management

quarkus.hibernate-orm.database.generation=update
```

---

## ▶️ Running the Application

### Run in Development Mode

```bash
./mvnw quarkus:dev
```

### Build the Application

```bash
./mvnw clean package
```

### Run the Packaged Application

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

---

## 🔐 Authentication

The application uses JWT-based authentication.

### Login Endpoint

```http
POST /auth/login
```

Sample Request:

```json
{
  "username": "admin",
  "password": "password"
}
```

Sample Response:

```json
{
  "token": "jwt-token"
}
```

Use the token in API requests:

```http
Authorization: Bearer <jwt-token>
```

---

## 📡 API Endpoints

### Customer APIs

| Method | Endpoint        |
| ------ | --------------- |
| POST   | /customers      |
| GET    | /customers      |
| GET    | /customers/{id} |
| PUT    | /customers/{id} |
| DELETE | /customers/{id} |

### Order APIs

| Method | Endpoint     |
| ------ | ------------ |
| POST   | /orders      |
| GET    | /orders      |
| GET    | /orders/{id} |
| PUT    | /orders/{id} |
| DELETE | /orders/{id} |

---

## 🐳 Docker

### Build Image

```bash
docker build -t order-management .
```

### Run Container

```bash
docker run -p 8080:8080 order-management
```

---

## 🧪 Testing APIs

Using cURL:

```bash
curl http://localhost:8080/customers
```

You can also test APIs with:

* Postman
* Bruno
* Insomnia

---

## 🎯 Learning Outcomes

This project demonstrates:

* Building REST APIs using Quarkus
* JWT Authentication and Authorization
* PostgreSQL Integration
* Hibernate ORM with Panache
* Docker Containerization
* Clean Architecture Principles
* Enterprise Backend Development

---

## 🚀 Future Enhancements

* Role-Based Access Control (RBAC)
* Swagger / OpenAPI Documentation
* Email Notifications
* Audit Logging
* Caching
* CI/CD Pipeline
* Kubernetes Deployment

---

## 👨‍💻 Author

**Suhel Baig**

GitHub: https://github.com/suhel786-byte

---

⭐ If you found this project useful, consider giving it a star on GitHub.

