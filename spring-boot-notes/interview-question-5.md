Here are detailed and descriptive answers with alternative solutions for each question:

---

### **1. How will you handle exceptions in your project?**

#### **Primary Solution**:
- Use `@ControllerAdvice` to centralize exception handling.
- Create custom exception classes for specific error scenarios.
- Leverage `ResponseEntityExceptionHandler` for handling common exceptions.

#### **Example**:
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
```

#### **Alternative Solutions**:
1. **Inline Exception Handlers**: Use `@ExceptionHandler` in individual controllers for finer-grained control.
2. **Filter or Interceptor**: Create a custom filter/interceptor for handling exceptions globally in a non-Spring MVC app.

---

### **2. How can you avoid defining handlers for multiple exceptions, or what is the best practice for handling exceptions?**

#### **Primary Solution**:
Group exceptions into a parent exception class or leverage a generic exception handler.

#### **Example**:
```java
@ExceptionHandler(ApplicationException.class)
public ResponseEntity<String> handleApplicationException(ApplicationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
}
```

#### **Alternative Solutions**:
1. **Override `ResponseEntityExceptionHandler`**:
   Handle multiple built-in exceptions like `MethodArgumentNotValidException`:
   ```java
   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(
       MethodArgumentNotValidException ex, 
       HttpHeaders headers, 
       HttpStatus status, 
       WebRequest request) {
       return ResponseEntity.badRequest().body("Validation error occurred.");
   }
   ```

2. **Combine Error Types**:
   Catch exceptions like `IllegalArgumentException` and `IllegalStateException` under a common handler.

---

### **3. How will you validate or sanitize your input payload?**

#### **Primary Solution**:
- Use Bean Validation (`javax.validation.constraints`) annotations like `@NotNull`, `@Size`, and `@Email`.
- Use `@Valid` in the controller method to trigger validation.

#### **Example**:
```java
public class UserDTO {
    @NotNull(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;
}
```

```java
@PostMapping("/users")
public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO user) {
    return ResponseEntity.ok("User created successfully!");
}
```

#### **Sanitization**:
- Use libraries like **Jsoup** to remove harmful input:
  ```java
  String sanitizedInput = Jsoup.clean(input, Safelist.basic());
  ```

#### **Alternative Solutions**:
1. Use **Custom Validation Logic**:
   ```java
   if (!input.matches("^[a-zA-Z0-9]*$")) {
       throw new InvalidInputException("Input contains invalid characters.");
   }
   ```
2. Write a **Custom Validator** (covered in Question 5).

---

### **4. How can you populate validation error messages to the end user?**

#### **Primary Solution**:
Handle `MethodArgumentNotValidException` and extract validation errors.

#### **Example**:
```java
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
```

#### **Alternative Solution**:
Use **Bean Validation Error Codes**:
- Add a `MessageSource` bean in your configuration to fetch localized messages from `messages.properties`.

---

### **5. How can you define custom bean validation?**

#### **Primary Solution**:
Define a custom annotation and implement the `ConstraintValidator` interface.

#### **Example**:
**Custom Annotation**:
```java
@Documented
@Constraint(validatedBy = CustomValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomConstraint {
    String message() default "Invalid value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

**Validator Implementation**:
```java
public class CustomValidator implements ConstraintValidator<CustomConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.startsWith("valid");
    }
}
```

**Usage**:
```java
@CustomConstraint(message = "Value must start with 'valid'")
private String customField;
```

#### **Alternative Solution**:
Use **Service-Level Validation**:
- Perform checks in the service layer and throw a custom exception if validation fails.

---

### **6. How can you debug a production bug locally?**

#### **Primary Solution**:
1. **Reproduce the Issue**:
   - Use the same configuration, database, and logs locally.
2. **Enable Remote Debugging**:
   - Attach your IDE to the production environment (e.g., via JPDA).
3. **Analyze Logs**:
   - Use logging frameworks like ELK or Splunk to review logs for anomalies.

#### **Alternative Solutions**:
- Use **Feature Flags**: Enable debugging features without affecting other users.
- Take **Heap/Thread Dumps**: Analyze runtime behavior.

---

### **7. How can you enable a specific environment without using profiles?**

#### **Primary Solution**:
Use environment variables or property files:
```java
-Dspring.config.location=classpath:/application-custom.properties
```

#### **Alternative Solutions**:
1. Use **Command-Line Arguments**:
   ```bash
   java -jar app.jar --spring.config.name=custom
   ```
2. Use **Custom Property Sources**:
   Implement a class extending `PropertySource`.

---

### **8. What is the difference between `@Profile` and `@Conditional`?**

#### **Primary Solution**:
- `@Profile`: Activates a bean based on the active Spring profile.
  ```java
  @Profile("dev")
  @Bean
  public DataSource devDataSource() {
      return new H2DataSource();
  }
  ```
- `@Conditional`: Activates a bean based on custom logic.
  ```java
  @Conditional(CustomCondition.class)
  @Bean
  public DataSource customDataSource() {
      return new CustomDataSource();
  }
  ```

#### **Key Differences**:
| Aspect          | `@Profile`                          | `@Conditional`                        |
|------------------|-------------------------------------|---------------------------------------|
| **Usage**       | Limited to profiles.               | Customizable for any condition.       |
| **Flexibility** | Profile-dependent.                 | Fully programmable conditions.        |

---

### **9. What is AOP (Aspect-Oriented Programming)?**

#### **Primary Solution**:
AOP allows separation of cross-cutting concerns (e.g., logging, security) from the core business logic.

#### **Example Use Case**:
```java
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Method called: " + joinPoint.getSignature());
    }
}
```

#### **Alternative Solution**:
Use decorators or custom proxy classes instead of AOP if Spring is not available.

---

### **10. What are Pointcut & Join Points in AOP?**

- **Pointcut**: Defines *where* advice should be applied (e.g., method executions).
- **Join Point**: Represents an *execution point* in the application lifecycle.

#### **Example**:
```java
@Pointcut("execution(* com.example.service.*.*(..))")
public void serviceMethods() {}
```

---

### **11. What are different types of advice?**

| Advice Type     | Description                                 | Example                             |
|------------------|---------------------------------------------|-------------------------------------|
| **Before**      | Runs before the method execution.          | `@Before`                          |
| **After**       | Runs after the method execution (finally). | `@After`                           |
| **Around**      | Surrounds the method execution.            | `@Around`                          |
| **AfterThrowing**| Runs after a method throws an exception.   | `@AfterThrowing`                   |

---

### **12. Can I use AOP to evaluate performance or design logging frameworks?**

#### **Primary Solution**:
Yes, use `@Around` advice for performance evaluation or request-response logging.

#### **Example**:
```java
@Aspect
@Component
public class PerformanceAspect {

    @Around("execution(* com.example.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end - start) + "ms");
        return result;
    }
}
```