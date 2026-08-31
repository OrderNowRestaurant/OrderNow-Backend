# OrderNow Backend

[![OrderNow Logo](https://github.com/OrderNowRestaurant/OrderNow/raw/master/public/images/logo.png)](https://github.com/OrderNowRestaurant/OrderNow/blob/master/public/images/logo.png)

A robust REST API backend for managing restaurants, tables, menus, and orders. This application provides complete authentication, authorization, and business logic support for the OrderNow restaurant management system.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#️-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Getting Started](#-getting-started)
- [Environment Configuration](#️-environment-configuration)
- [Project Structure](#-project-structure)
- [API Endpoints](#-api-endpoints)
- [Development](#-development)
- [License](#-license)

## ✨ Features

- User authentication and JWT token management
- Role-based access control (RBAC)
- Restaurant configuration and management
- Category and dish administration
- Table management with QR code support
- Order creation and tracking
- Secure API endpoints with Spring Security
- WebSocket support for real-time updates
- Database persistence with PostgreSQL and JPA
- Comprehensive error handling
- CORS configuration for frontend integration
- Admin panel API for staff and managers
- Service layer for business logic separation

## 🛠️ Tech Stack

- **Framework**: Spring Boot 4.1.0
- **Language**: Java 21
- **Database**: PostgreSQL with Spring Data JPA
- **Security**: Spring Security + JWT (JSON Web Tokens)
- **Build Tool**: Maven
- **Testing**: Spring Boot Test
- **Additional Libraries**:
  - JJWT for JWT token handling
  - Spring WebSocket for real-time communication
  - Spring Security Crypto for password encoding

## 📋 Prerequisites

Before you begin, make sure you have installed:

- Java Development Kit (JDK) 21 or higher
- Maven 3.8.1 or higher
- PostgreSQL 12 or higher
- Git
- An OrderNow frontend running (or configure CORS accordingly)

## 🚀 Installation

1. Clone the repository:

```bash
git clone <repository-url>
cd ordernow-backend
```

2. Configure environment variables (see [Environment Configuration](#️-environment-configuration))

3. Build the project:

```bash
./mvnw clean install
```

or on Windows:

```bash
mvnw.cmd clean install
```

## 🎯 Getting Started

### Start the development server

```bash
./mvnw spring-boot:run
```

or on Windows:

```bash
mvnw.cmd spring-boot:run
```

The API will be available at:

```
http://localhost:8080/api/
```

### Run tests

```bash
./mvnw test
```

### Build for production

```bash
./mvnw clean package
```

The compiled WAR file will be available at:

```
target/ordernow-backend-0.0.1-SNAPSHOT.war
```

## ⚙️ Environment Configuration

The application requires the following environment variables to be configured:

### Database Configuration

```properties
DB_URL=jdbc:postgresql://localhost:5432/ordernow
```

Example with credentials:

```
jdbc:postgresql://hostname:port/database_name?user=username&password=password
```

### Authentication Configuration

```properties
TOKEN_KEY=your-secret-jwt-key-with-minimum-32-characters
```

### Server Configuration

```properties
PORT=8080
```

### CORS Configuration

```properties
FRONTEND_URLS=http://localhost:4200,http://127.0.0.1:4200,http://localhost:5173
```

### Application Properties File

Create or edit `src/main/resources/application.properties`:

```properties
spring.application.name=ordernow-backend
server.port=${PORT:8080}
app.cors.allowed-origins=${FRONTEND_URLS:http://localhost:4200,http://127.0.0.1:4200,http://localhost:5173}

spring.datasource.url=${DB_URL}
spring.datasource.driver-class-name=org.postgresql.Driver

jwt.secret.key=${TOKEN_KEY}
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── ordernow/
│   │       └── backend/
│   │           └── ordernow_backend/
│   │               ├── OrdernowBackendApplication.java
│   │               ├── ServletInitializer.java
│   │               ├── components/          # Utility components (JWT filters, etc.)
│   │               ├── config/              # Configuration classes (Security, WebSocket, etc.)
│   │               ├── controllers/         # REST API endpoints
│   │               ├── dtos/                # Data Transfer Objects
│   │               ├── entities/            # JPA entity models
│   │               ├── enums/               # Enumeration types
│   │               ├── exceptions/          # Custom exceptions
│   │               ├── repositories/        # Data access layer
│   │               ├── requests/            # Request DTOs
│   │               ├── responses/           # Response DTOs
│   │               └── services/            # Business logic layer
│   └── resources/
│       ├── application.properties
│       ├── static/
│       └── templates/
└── test/
    └── java/
        └── ordernow/
            └── backend/
                └── ordernow_backend/
                    ├── OrdernowBackendApplicationTests.java
                    └── services/
```

## 🔄 How It Works

### Authentication Flow

1. User credentials are validated against the database
2. On successful authentication, a JWT token is generated
3. The client stores the token and includes it in subsequent requests
4. The JWT filter validates the token for protected endpoints
5. Role-based access control determines endpoint availability

### Business Logic Flow

1. Client sends HTTP request to API endpoint
2. Spring Security validates authentication/authorization
3. Controller receives request and delegates to service layer
4. Service implements business logic
5. Repository accesses database via JPA/Hibernate
6. Response is serialized and returned to client

### Key Modules

- **Authentication**: Login endpoint and JWT token management
- **User Management**: User CRUD operations and role assignment
- **Restaurant Management**: Restaurant configuration and settings
- **Menu Management**: Categories and dishes administration
- **Table Management**: Table creation and QR code association
- **Order Management**: Order creation, tracking, and status updates
- **Role-Based Access**: Permission enforcement based on user roles

## 💻 Development

### Main Commands

| Command | Description |
|---------|-------------|
| `./mvnw spring-boot:run` | Starts the server in development mode |
| `./mvnw clean package` | Builds the application for production |
| `./mvnw test` | Runs all unit and integration tests |
| `./mvnw clean install` | Cleans and installs dependencies |

### Key Dependencies

- **Spring Boot Starter Web**: REST API and MVC support
- **Spring Boot Starter Data JPA**: Database ORM
- **Spring Boot Starter Security**: Authentication and authorization
- **PostgreSQL Driver**: Database connectivity
- **JJWT**: JWT token creation and validation
- **Spring WebSocket**: Real-time communication

### Testing

The project includes test cases in `src/test/java/`. Run tests with:

```bash
./mvnw test
```

Or run a specific test class:

```bash
./mvnw test -Dtest=YourTestClassName
```

## 🤝 Contributing

Contributions are welcome! If you'd like to collaborate:

1. Fork the project
2. Create a branch for your changes (`git checkout -b feature/amazing-feature`)
3. Make the necessary updates and verify everything works
4. Commit your changes (`git commit -m 'Add amazing feature'`)
5. Push to the branch (`git push origin feature/amazing-feature`)
6. Open a Pull Request with a clear description

## 📄 License

This project is licensed under the **CC BY-NC 4.0** (Creative Commons Attribution-NonCommercial 4.0 International) license.

For the full legal text, visit: [https://creativecommons.org/licenses/by-nc/4.0/legalcode](https://creativecommons.org/licenses/by-nc/4.0/legalcode)

---

Built to streamline restaurant order management and provide a robust API for the OrderNow ecosystem.
