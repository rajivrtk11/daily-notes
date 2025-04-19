### 1. I have already build monolithic application. How will you conver it inot microservices. in springboot ?
To convert a monolithic application into microservices using Spring Boot:

1. **Identify Domains**: Break the monolith into logical modules (e.g., User, Order, Product).

2. **Create Independent Services**: For each module, create a separate Spring Boot project with its own database (Database per service).

3. **API Gateway**: Introduce Spring Cloud Gateway or Zuul for routing and centralized access.

4. **Service Discovery**: Use Netflix Eureka or Consul for dynamic service registration and discovery.

5. **Communication**: Use REST or asynchronous messaging (e.g., RabbitMQ, Kafka) between services.

6. **Centralized Config**: Use Spring Cloud Config Server for externalized and centralized configuration.

7. **Authentication**: Implement centralized auth (e.g., using OAuth2 + JWT with Spring Security).

8. **Observability**: Add centralized logging (ELK/EFK), monitoring (Prometheus + Grafana), and tracing (Zipkin).

9. **CI/CD**: Containerize services using Docker and deploy using CI/CD tools (Jenkins, GitHub Actions).

10. **Gradual Migration**: Incrementally extract features from monolith and redirect traffic via API Gateway.

### 2. Will you use the exising code base or you will write everything from scratch ?
I will **reuse the existing codebase as much as possible**, not rewrite everything from scratch.

### Here's how:
- **Refactor** the monolith modules into standalone services.
- **Extract** shared logic into **common libraries** (e.g., DTOs, utils).
- **Reuse** service-specific code (controllers, services, repositories) in new Spring Boot services.
- **Replace** direct method calls with **REST/gRPC/messaging** for inter-service communication.

✅ This ensures faster migration with minimal bugs and preserves tested business logic.

Eureka vs kubernetes microservices 
https://chatgpt.com/share/67fd239c-1134-8005-88ac-3a6b41e15bfa