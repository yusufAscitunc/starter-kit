# Spring Boot Starter Kit

Modern, production-ready Backend Spring Boot starter:

-   🔧 Backend: Java 21 + Spring Boot 3.5 + PostgreSQL + JWT Auth
-   🔐 Auth: Role-based JWT authentication
-   📦 Deployment: Docker + Railway/Vercel

## Development

```bash
# Backend
cd backend
# Load environment variables and run the application
export $(cat .env | xargs) && ./mvnw spring-boot:run
```
