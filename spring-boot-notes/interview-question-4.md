### Detailed Answers to RESTful Web Services Questions

---

#### **1. Have you worked on RESTful web services? If yes, what HTTP methods have you used in your project?**
Yes, I have extensively worked on RESTful web services. The HTTP methods I have used include:
1. **GET**: To retrieve data from the server (e.g., fetching product details).
2. **POST**: To create new resources on the server (e.g., adding a new user or product).
3. **PUT**: To update an entire resource (e.g., updating user information).
4. **PATCH**: To partially update a resource (e.g., modifying only the email of a user).
5. **DELETE**: To remove a resource (e.g., deleting a product or user).
6. **OPTIONS**: To check the communication options available for a resource (used for CORS).
7. **HEAD**: To fetch response headers for a resource without the body.

---

#### **2. How can you specify the HTTP method type for your REST endpoint?**
In Spring, HTTP methods are specified using annotations. Examples include:
1. **Convenience Annotations**:
   - `@GetMapping`: For GET requests.
   - `@PostMapping`: For POST requests.
   - `@PutMapping`: For PUT requests.
   - `@PatchMapping`: For PATCH requests.
   - `@DeleteMapping`: For DELETE requests.
2. **Generic Mapping**:
   - Use `@RequestMapping` with the `method` attribute:
     ```java
     @RequestMapping(value = "/products", method = RequestMethod.GET)
     public List<Product> getAllProducts() { ... }
     ```
**Best Practice**: Use the convenience annotations as they improve readability and reduce boilerplate code.

---

#### **3. Design a REST endpoint for filtering products by `productType`**
A simple implementation:
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public List<Product> filterProducts(@RequestParam(required = false) String productType) {
        if (productType != null) {
            return productService.findByType(productType);
        }
        return productService.findAll();
    }
}
```

**Alternative**:
Use `@PathVariable` if `productType` is part of the URI:
```java
@GetMapping("/type/{productType}")
public List<Product> filterByType(@PathVariable String productType) {
    return productService.findByType(productType);
}
```

---

#### **4. Handle input for filtering and return all products if no input is provided**
- Use `@RequestParam` with `required = false`:
  ```java
  @GetMapping
  public List<Product> getProducts(@RequestParam(required = false) String productType) {
      return productType != null ? productService.findByType(productType) : productService.findAll();
  }
  ```

**Alternative**:
Use a default value:
```java
@GetMapping
public List<Product> getProducts(@RequestParam(defaultValue = "all") String productType) {
    return "all".equals(productType) ? productService.findAll() : productService.findByType(productType);
}
```

---

#### **5. Difference between @PathVariable and @RequestParam**
1. **@PathVariable**: Extracts values from the URI path.
   - Example:
     URI: `/products/123`
     ```java
     @GetMapping("/products/{id}")
     public Product getProduct(@PathVariable("id") Long id) { ... }
     ```
2. **@RequestParam**: Extracts query parameters.
   - Example:
     URI: `/products?type=electronics`
     ```java
     @GetMapping("/products")
     public List<Product> getProducts(@RequestParam("type") String type) { ... }
     ```

---

#### **6. Why use @RestController instead of @Controller?**
- **@RestController**:
  - Combines `@Controller` and `@ResponseBody`.
  - Automatically serializes Java objects to JSON or XML for HTTP responses.
  - Used for REST APIs.

- **@Controller**:
  - Used to render views like JSP or Thymeleaf.
  - Requires manual use of `@ResponseBody` for returning data.

**Why @RestController?**
For REST APIs, `@RestController` is more concise and eliminates boilerplate.

---

#### **7. Deserialize JSON request payload in a Spring MVC controller**
Use `@RequestBody` to map the JSON payload to a Java object:
```java
@PostMapping("/products")
public Product addProduct(@RequestBody Product product) {
    return productService.save(product);
}
```

---

#### **8. Can we perform an update operation using POST? Why use PUT?**
- **POST** can technically be used for updates but is semantically incorrect. It is designed for creating resources.
- **PUT** is idempotent, meaning the same request always results in the same state. It is the correct choice for updates.

---

#### **9. Can we pass Request Body in GET HTTP method?**
- **Technically**: Some servers allow it, but it violates the HTTP specification.
- **Best Practice**: Use query parameters for GET requests.

---

#### **10. Content negotiation (XML/JSON) in REST**
Spring Boot supports content negotiation through `Accept` headers:
- JSON: `Accept: application/json`
- XML: `Accept: application/xml`

**Configuration**:
1. Add dependencies for `jackson-databind` (JSON) and `jaxb-api` (XML).
2. Use `HttpMessageConverter` for converting Java objects.

---

#### **11. Status codes observed in applications**
- **200 OK**: Successful GET request.
- **201 Created**: Resource created via POST.
- **204 No Content**: Successful DELETE.
- **400 Bad Request**: Invalid request.
- **401 Unauthorized**: Authentication failed.
- **403 Forbidden**: Access denied.
- **404 Not Found**: Resource not found.
- **500 Internal Server Error**: Server-side issue.

---

#### **12. Customize status codes**
Use `ResponseEntity` to customize status codes:
```java
@PostMapping("/products")
public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    Product savedProduct = productService.save(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
}
```

---

#### **13. Enable Cross-Origin (CORS)**
1. **Annotation**:
   ```java
   @CrossOrigin(origins = "http://example.com")
   ```
2. **Global Configuration**:
   ```java
   @Configuration
   public class WebConfig implements WebMvcConfigurer {
       @Override
       public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/**").allowedOrigins("http://example.com");
       }
   }
   ```

---

#### **14. File upload in Spring**
```java
@PostMapping("/upload")
public String uploadFile(@RequestParam("file") MultipartFile file) {
    fileService.store(file);
    return "File uploaded successfully";
}
```

---

#### **15. Maintain API versioning**
1. **URI Versioning**:
   ```java
   @RequestMapping("/v1/products")
   ```
2. **Header Versioning**:
   ```java
   @RequestHeader("X-API-VERSION") String version
   ```
3. **Query Parameter Versioning**:
   ```java
   @RequestParam("version") String version
   ```

---

#### **16. Document REST APIs**
Use Swagger/OpenAPI:
1. Add `springdoc-openapi-ui` dependency.
2. Access the documentation at `/swagger-ui.html`.

---

#### **17. Hide certain REST endpoints**
1. **Spring Security** to restrict access.
2. **@Profile** to load endpoints conditionally.
3. Avoid scanning the controller class.

---

#### **18. Consume RESTful APIs**
1. **RestTemplate**:
   ```java
   RestTemplate restTemplate = new RestTemplate();
   String response = restTemplate.getForObject("http://api.example.com/products", String.class);
   ```

2. **WebClient** (Reactive):
   ```java
   WebClient client = WebClient.create();
   String response = client.get()
       .uri("http://api.example.com/products")
       .retrieve()
       .bodyToMono(String.class)
       .block();
   ```

--- 

This elaborated explanation covers primary and alternative approaches for working with RESTful web services in Spring Boot.