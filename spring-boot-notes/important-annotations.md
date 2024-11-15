Here’s a breakdown of each annotation across the categories you listed:

---

### Spring Boot Main Annotations
1. **`@SpringBootApplication`**: A convenience annotation that combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. It marks a class as the entry point for Spring Boot applications.
2. **`@ComponentScan`**: Instructs Spring to scan for components, configurations, and services in the specified package, allowing Spring to detect custom annotations and manage beans.
3. **`@EnableAutoConfiguration`**: Enables Spring Boot’s auto-configuration feature, automatically configuring beans based on dependencies present in the classpath.
4. **`@Configuration`**: Indicates that a class declares one or more `@Bean` methods, meaning it's a source of bean definitions.

---

### Stereotype Annotations
1. **`@Component`**: Marks a class as a Spring-managed component, allowing Spring to detect and register it as a bean in the application context.
2. **`@Service`**: Specialization of `@Component` indicating a class that holds business logic. Used mainly for service layer classes.
3. **`@RestController` / `@Controller`**: `@RestController` is a combination of `@Controller` and `@ResponseBody`, used for RESTful web services. `@Controller` is used for traditional MVC controllers.
4. **`@Repository`**: Specialization of `@Component` indicating that a class is a repository layer. Used mainly for data access logic and can translate persistence layer exceptions to Spring’s data access exceptions.

---

### Spring Core-Related Annotations
1. **`@Configuration`**: Marks a class as a source of bean definitions. Classes annotated with `@Configuration` define one or more `@Bean` methods.
2. **`@Bean`**: Used within a `@Configuration` class to define and register a bean.
3. **`@Autowired`**: Automatically injects a bean into a Spring-managed component or service, typically used on fields, constructors, or setters.
4. **`@Qualifier`**: Used along with `@Autowired` to resolve ambiguity when multiple beans of the same type are present.
5. **`@Lazy`**: Used to indicate that a bean should be lazily initialized, meaning it's created only when needed rather than at application startup.
6. **`@Value`**: Injects values from properties files or environment variables into fields, methods, or constructor parameters.
7. **`@PropertySource`**: Specifies the location of property files that Spring should load.
eg. @PropertySource("classpath:cutom.properties")
load as @Value("${name}")
8. **`@ConfigurationProperties`**: Binds the properties defined in configuration files to a Java bean.
9. **`@Profile`**: Specifies which file to load for properties reading. Defined in application.properties.
eg. dev, test or prod. 
spring.profile.active=dev
10. **`@Scope`**: Specifies the scope of a bean, such as singleton, prototype, request, session, or application.

---

### REST API-Related Annotations
1. **`@RestController`**: Combines `@Controller` and `@ResponseBody` to create RESTful web services, automatically serializing return objects into JSON or XML.
2. **`@RequestMapping`**: Maps HTTP requests to handler methods in controllers. Can specify the path, HTTP method, and other options.
3. **`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`**: Shortcut annotations for `@RequestMapping` to handle specific HTTP methods (GET, POST, PUT, DELETE) respectively.
4. **`@RequestBody`**: Indicates that a method parameter should be bound to the body of the web request, commonly used for POST and PUT requests.
5. **`@PathVariable`**: Binds a method parameter to a URI template variable.
6. **`@RequestParam`**: Binds a method parameter to a request parameter (query parameter).
7. **`@ControllerAdvice` & `@ExceptionHandler`**: `@ControllerAdvice` is used for global exception handling, applying to all controllers. `@ExceptionHandler` within `@ControllerAdvice` specifies the method to handle specific exceptions.

---

### Spring Data JPA Related Annotations
1. **`@Entity`**: Marks a class as a JPA entity, mapping it to a database table.
2. **`@Table`**: Specifies the database table to be used for a JPA entity, allowing customization of the table name.
3. **`@Column`**: Specifies details about a column in the database, such as name, length, nullable, etc.
4. **`@Transactional`**: Specifies that a method or class should be wrapped in a database transaction, ensuring that the operations within it are atomic.

### Entity Class Relationships
1. **`@OneToOne`**: Specifies a one-to-one relationship between two entities.
2. **`@OneToMany`**: Specifies a one-to-many relationship, where one entity can have a collection of another entity type.
3. **`@ManyToOne`**: Specifies a many-to-one relationship, where many entities are associated with a single instance of another entity type.
4. **`@ManyToMany`**: Specifies a many-to-many relationship, where multiple instances of one entity type are related to multiple instances of another entity type.