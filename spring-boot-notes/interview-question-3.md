### **Answers to Spring Framework Bean Management Questions**

---

#### **1. How will you resolve bean dependency ambiguity?**
Bean dependency ambiguity occurs when multiple beans of the same type are defined in the Spring context, and Spring cannot decide which bean to inject.

**Solution**:
1. **@Qualifier**: Specify the exact bean to inject:
   ```java
   @Autowired
   @Qualifier("specificBeanName")
   private MyService myService;
   ```
2. **@Primary**: Mark one bean as the primary choice:
   ```java
   @Primary
   @Bean
   public MyService primaryService() {
       return new MyService();
   }
   ```
3. **@Profile**: Define beans for specific environments:
   ```java
   @Profile("dev")
   @Bean
   public MyService devService() {
       return new MyService();
   }
   ```

---

#### **2. Can we avoid dependency ambiguity without using @Qualifier?**
Yes, dependency ambiguity can be avoided without using `@Qualifier`:
1. **@Primary**: Declare a primary bean:
   ```java
   @Primary
   @Bean
   public MyService defaultService() {
       return new DefaultService();
   }
   ```
2. **Java Configuration**: Inject the specific bean using Java-based configuration:
   ```java
   @Configuration
   public class AppConfig {
       @Bean
       public MyService myService1() {
           return new MyService1();
       }

       @Bean
       public MyService myService2() {
           return new MyService2();
       }
   }

   // Inject specific bean
   @Autowired
   private MyService myService1;
   ```
3. **Explicit Injection**: Use constructor or setter injection to specify beans.

---

#### **3. What is bean scope and can you explain different types of bean scope?**
**Bean Scope** defines the lifecycle and visibility of a bean in the Spring container.

**Types of Bean Scopes**:
1. **Singleton** (default): A single instance of the bean is created per Spring container.
2. **Prototype**: A new instance is created every time the bean is requested.
3. **Request**: A new bean instance is created for each HTTP request (web applications).
4. **Session**: A new instance is created for each HTTP session (web applications).
5. **Application**: A single instance is shared across the lifecycle of a ServletContext.
6. **WebSocket**: A new instance is created for each WebSocket session.

---

#### **4. How to define custom bean scope?**
**Steps to define a custom bean scope**:
1. **Create a Custom Scope Implementation**:
   ```java
   public class CustomScope implements Scope {
       private Map<String, Object> scopedObjects = new HashMap<>();

       @Override
       public Object get(String name, ObjectFactory<?> objectFactory) {
           return scopedObjects.computeIfAbsent(name, k -> objectFactory.getObject());
       }

       @Override
       public Object remove(String name) {
           return scopedObjects.remove(name);
       }

       // Other required methods like resolveContextualObject, registerDestructionCallback...
   }
   ```

2. **Register the Custom Scope**:
   ```java
   @Configuration
   public class ScopeConfig {
       @Bean
       public CustomScopeConfigurer customScopeConfigurer() {
           CustomScopeConfigurer configurer = new CustomScopeConfigurer();
           configurer.addScope("customScope", new CustomScope());
           return configurer;
       }
   }
   ```

3. **Use the Custom Scope**:
   ```java
   @Scope("customScope")
   @Bean
   public MyService myService() {
       return new MyService();
   }
   ```

---

#### **5. Real-time use cases for Singleton and Prototype scope**
- **Singleton Scope**:
  - Use when the bean is stateless or shared across the application (e.g., service layer, DAO components, utility classes).
- **Prototype Scope**:
  - Use for stateful beans or when a new instance is needed for every use (e.g., a bean that handles request-specific or temporary data).

**Examples**:
- Singleton: Database connection pool.
- Prototype: Request-scoped objects in batch processing.

---

#### **6. Can we inject a prototype bean into a singleton bean? What happens if we do so?**
Yes, you can inject a prototype bean into a singleton bean. However, the prototype bean is resolved only once at the time of the singleton's creation. This means the same instance of the prototype bean will be reused.

**Solution for Dynamic Prototype Injection**:
Use `ObjectProvider` or `javax.inject.Provider`:
```java
@Autowired
private ObjectProvider<PrototypeBean> prototypeBeanProvider;

public void usePrototypeBean() {
    PrototypeBean prototype = prototypeBeanProvider.getObject();
    // Use the new prototype instance
}
```

---

#### **7. What is the difference between Spring singleton and plain singleton?**
- **Spring Singleton**:
  - Defined at the container level.
  - One instance per Spring container.
  - Lifecycle managed by Spring (e.g., initialization, destruction).

- **Plain Singleton**:
  - Defined at the application level.
  - One instance per JVM.
  - Managed manually by the developer.

---

#### **8. What is the purpose of the BeanPostProcessor interface in Spring, and how can you use it to customize bean initialization and destruction?**
The `BeanPostProcessor` interface allows customization of bean instances before and after their initialization in the Spring container.

**Methods**:
1. **`postProcessBeforeInitialization`**: Invoked before the `@PostConstruct` method or `InitializingBean`’s `afterPropertiesSet`.
2. **`postProcessAfterInitialization`**: Invoked after the initialization phase.

**Example**:
```java
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MyService) {
            System.out.println("Before Initialization: " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MyService) {
            System.out.println("After Initialization: " + beanName);
        }
        return bean;
    }
}
```

**Use Case**:
- Logging bean initialization.
- Wrapping beans with proxies.
- Custom initialization logic.

---

This expanded explanation provides clear alternatives and practical examples for understanding and implementing bean management in Spring.