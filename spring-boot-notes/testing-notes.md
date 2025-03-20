### 1. what are the important action that we should be aware to test in spring boot ?

### ✅ **Important Actions to Test in Spring Boot**
When testing a **Spring Boot** application, you should cover multiple layers to ensure reliability, correctness, and performance. Here’s a structured breakdown:

---

## **1️⃣ Unit Testing (Business Logic & Services)**
**Goal:** Ensure individual methods in your services, repositories, and utilities work as expected.  
✅ **Use:** JUnit 5 + Mockito for mocking dependencies.

### **📌 What to Test?**
✔ **Service methods** (e.g., CRUD operations, calculations, business logic).  
✔ **Repository methods** (mock database interactions).  
✔ **Validation logic** (e.g., checking `@Valid` annotated inputs).  

### **Example: Testing a Service Layer**
```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser() {
        User user = new User(1L, "John Doe", "john@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        verify(userRepository, times(1)).save(user);
    }
}
```

---

## **2️⃣ Integration Testing (Controller, DB, APIs)**
**Goal:** Ensure the entire application flow works as expected.  
✅ **Use:** `@SpringBootTest` + `@Testcontainers` for real database tests.  

### **📌 What to Test?**
✔ **Controllers (REST API behavior)**  
✔ **Service + Database interaction (real DB or Testcontainers)**  
✔ **End-to-end flow (mock external services if needed)**  

### **Example: Testing a REST Controller**
```java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class) // Load only the controller layer
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(get("/users/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("John Doe"));
    }
}
```

---

## **3️⃣ Database Testing (JPA & Repositories)**
**Goal:** Ensure data persistence works correctly.  
✅ **Use:** `@DataJpaTest` + **H2 Database** or **Testcontainers**.

### **📌 What to Test?**
✔ **Custom queries (`@Query`, `@NamedQuery`)**  
✔ **Database constraints (e.g., unique keys, relationships)**  
✔ **Transaction rollbacks (`@Transactional`)**  

### **Example: Testing a JPA Repository**
```java
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // Loads only JPA-related components
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User(null, "John Doe", "john@example.com");
        userRepository.save(user);

        User foundUser = userRepository.findByEmail("john@example.com");
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
    }
}
```

---

## **4️⃣ Security Testing (Authentication & Authorization)**
**Goal:** Ensure endpoints are properly secured.  
✅ **Use:** `@WithMockUser` or `MockMvc` with security configuration.  

### **📌 What to Test?**
✔ **Access control (role-based security)**  
✔ **Authentication (JWT, OAuth, Session-based login)**  
✔ **CSRF protection & secure headers**  

### **Example: Testing Secure API Endpoint**
```java
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SecureController.class)
public class SecureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSecureEndpoint() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().isOk());
    }

    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().isUnauthorized());
    }
}
```

---

## **5️⃣ Exception Handling & Validation Tests**
**Goal:** Ensure the application correctly handles errors.  
✅ **Use:** `@MockMvc` and custom error response verification.

### **📌 What to Test?**
✔ **Custom exception handling (`@ControllerAdvice`)**  
✔ **Validation failures (`@Valid` on DTOs)**  
✔ **Error messages & status codes**  

### **Example: Testing Validation Errors**
```java
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(UserController.class)
public class UserValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInvalidUserInput() throws Exception {
        String invalidUserJson = "{\"name\":\"\", \"email\":\"invalid\"}";

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }
}
```

---

## **6️⃣ Performance & Load Testing**
**Goal:** Ensure the application performs well under load.  
✅ **Use:** **JMeter, Gatling, or Locust** for load testing.

### **📌 What to Test?**
✔ **Response time under load**  
✔ **Database query performance**  
✔ **Memory leaks or thread bottlenecks**  

---

## **🛠 Final Checklist for Testing in Spring Boot**
| **Test Type**        | **What to Cover?** | **Tools/Annotations** |
|----------------------|------------------|-----------------------|
| **Unit Tests**      | Service logic, utility methods | JUnit 5, Mockito, `@Test` |
| **Integration Tests** | REST API, database interactions | `@SpringBootTest`, `MockMvc`, Testcontainers |
| **Database Tests**   | JPA queries, constraints, relationships | `@DataJpaTest`, H2 DB |
| **Security Tests**   | Authentication & Authorization | `@WithMockUser`, Spring Security |
| **Validation Tests** | DTO validation, exception handling | `@MockMvc`, `@Valid` |
| **Performance Tests** | API load testing, database performance | JMeter, Gatling |

---

## 🚀 **Conclusion**
To build a robust **Spring Boot application**, you must cover **unit tests, integration tests, security tests, validation tests, and performance tests**.  

👉 **Start by testing services, repositories, and controllers, then move on to security and performance!**  

Let me know if you need help with any specific test cases! 🔥

### 2. stub in wiremock
### **📌 Stubbing in WireMock for Spring Boot Testing**
WireMock is a powerful tool for **mocking HTTP APIs** in integration tests. It allows you to create **stubs** that return predefined responses for specific API calls.

---

## **1️⃣ Add WireMock Dependency**
For **Maven**, add this to `pom.xml`:
```xml
<dependency>
    <groupId>com.github.tomakehurst</groupId>
    <artifactId>wiremock-jre8</artifactId>
    <version>2.35.0</version>
    <scope>test</scope>
</dependency>
```
For **Gradle**:
```groovy
testImplementation 'com.github.tomakehurst:wiremock-jre8:2.35.0'
```

---

## **2️⃣ Example: Stubbing a REST API**
Let's assume your application calls an external API at:
```
GET http://api.example.com/users/1
```

You can **stub this API response** in your test using WireMock.

### **📌 Test with WireMock Stub**
```java
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import com.github.tomakehurst.wiremock.WireMockServer;

public class WireMockTest {

    private WireMockServer wireMockServer;
    private RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(8081); // Start WireMock on port 8081
        wireMockServer.start();
        configureFor("localhost", 8081);

        // Stub API response
        stubFor(get(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": 1, \"name\": \"John Doe\" }")));
    }

    @Test
    void testStubbedApi() {
        String url = "http://localhost:8081/users/1";
        String response = restTemplate.getForObject(url, String.class);

        assertNotNull(response);
        assertTrue(response.contains("John Doe"));
    }
}
```

---

## **3️⃣ Explanation**
✅ **`stubFor()`** → Defines the stub behavior for an API call.  
✅ **`willReturn(aResponse()...`** → Specifies the HTTP response.  
✅ **`RestTemplate`** → Calls the stubbed API instead of the real one.  
✅ **Assertions** → Verify that the stub returns expected data.

---

## **4️⃣ Advanced Stubbing Features**
### **📌 Mock Different HTTP Methods**
```java
stubFor(post(urlEqualTo("/users"))
        .willReturn(aResponse().withStatus(201)));
```

### **📌 Simulate Delayed Response**
```java
stubFor(get(urlEqualTo("/slow-api"))
        .willReturn(aResponse()
                .withFixedDelay(3000) // 3 seconds delay
                .withStatus(200)
                .withBody("Delayed Response")));
```

### **📌 Return Dynamic Responses (Matching Requests)**
```java
stubFor(post(urlEqualTo("/login"))
        .withRequestBody(containing("username=admin"))
        .willReturn(aResponse().withStatus(200).withBody("Login Successful")));
```

---

## **5️⃣ Run the Test**
Run it with:
```sh
mvn test
```
or
```sh
./gradlew test
```

---

### **🚀 Summary**
WireMock helps **mock external APIs** in integration tests. You can:
✔ Stub different HTTP methods (`GET`, `POST`, etc.).  
✔ Simulate **delays, errors, and dynamic responses**.  
✔ Use it with `RestTemplate`, `WebClient`, or Feign.  

Let me know if you need help! 🔥