### **1. Spring boot lifecycle?**
### **Spring Boot Application Lifecycle**

The lifecycle of a Spring Boot application involves a series of steps, from initialization to shutdown, that are managed by the **Spring Framework** and **Spring Boot**. Understanding this lifecycle is essential for integrating custom logic at various stages and for debugging or optimizing your application.

---

### **1. Application Initialization**
This phase occurs when the application starts. It includes the setup of the environment and the creation of the Spring Application Context.

#### Steps:
1. **Main Method Execution**:
   - The application starts with the `main()` method.
   - The `SpringApplication.run()` method is invoked.

   Example:
   ```java
   @SpringBootApplication
   public class MyApp {
       public static void main(String[] args) {
           SpringApplication.run(MyApp.class, args);
       }
   }
   ```

2. **Prepare Environment**:
   - **Application Arguments** are processed.
   - The environment (e.g., properties, profiles) is prepared (`ApplicationEnvironmentPreparedEvent` is published).
   - System properties and environment variables are resolved.

3. **Banner Display**:
   - The Spring Boot banner (or a custom one) is displayed in the console.

4. **Create Application Context**:
   - A specific type of `ApplicationContext` (e.g., `AnnotationConfigApplicationContext`) is created based on the application configuration.

---

### **2. Context Refreshing**
The Spring container refreshes and prepares the context to make the application ready to serve requests.

#### Steps:
1. **Bean Definitions Loaded**:
   - Beans annotated with `@Component`, `@Service`, `@Repository`, `@Controller`, or defined in configuration classes are scanned and registered.

2. **Auto-Configuration**:
   - Auto-configuration classes are loaded and applied based on the dependencies and environment settings.

3. **Listeners Registered**:
   - Event listeners and publishers are registered for lifecycle and custom events.

4. **Post-Processors Execution**:
   - Bean post-processors (e.g., `@PostConstruct`, `@BeanPostProcessor`) modify or initialize beans after they are instantiated.

5. **Application Context Refreshed**:
   - The context is fully initialized (`ContextRefreshedEvent` is published).

---

### **3. Application Ready**
The application is ready to serve requests or perform its primary functions.

#### Steps:
1. **CommandLineRunner and ApplicationRunner Execution**:
   - These interfaces allow custom logic to execute once the application is fully initialized.

   Example:
   ```java
   @Component
   public class MyRunner implements CommandLineRunner {
       @Override
       public void run(String... args) {
           System.out.println("Application started!");
       }
   }
   ```

2. **Publish `ApplicationReadyEvent`**:
   - Indicates the application is fully started and ready to serve.

3. **Embedded Server Starts**:
   - For web applications, the embedded server (e.g., Tomcat, Jetty) starts listening for incoming requests.

---

### **4. Runtime Operation**
During runtime, the application serves its primary function. It can publish and handle events, manage background tasks, and respond to user requests.

#### Key Features During Runtime:
1. **Event Handling**:
   - Publish and listen for custom events (e.g., `ApplicationEvent`).

2. **Monitoring and Metrics**:
   - Use tools like Actuator for health checks, metrics, and application insights.

3. **Dynamic Bean Registration**:
   - Register beans dynamically at runtime if needed.

---

### **5. Application Shutdown**
When the application is stopped, Spring Boot ensures a clean shutdown by releasing resources and notifying relevant components.

#### Steps:
1. **Publish Shutdown Event**:
   - `ContextClosedEvent` is published to notify components of the impending shutdown.

2. **Destroy Beans**:
   - Beans with `@PreDestroy` or implementing `DisposableBean` execute cleanup logic.

3. **Close Application Context**:
   - The `ApplicationContext` is closed, and all related resources (e.g., threads, database connections) are released.

---

### **Spring Boot Lifecycle Events**
Spring Boot provides several events during the application lifecycle. These can be intercepted using `ApplicationListener` or `@EventListener`.

#### Key Events:
1. **ApplicationStartingEvent**:
   - Published as soon as the application starts, before the context is created.

2. **ApplicationEnvironmentPreparedEvent**:
   - Published when the environment is prepared but before the context is created.

3. **ApplicationPreparedEvent**:
   - Published after the context is created but before it is refreshed.

4. **ContextRefreshedEvent**:
   - Published when the context is refreshed and fully initialized.

5. **ApplicationReadyEvent**:
   - Published when the application is fully started.

6. **ApplicationFailedEvent**:
   - Published if the application startup fails.

---

### **Diagram Representation**

```plaintext
1. Start
   ↓
2. Prepare Environment
   ↓
3. Create Context
   ↓
4. Load Bean Definitions
   ↓
5. Apply Auto-Configuration
   ↓
6. Refresh Context
   ↓
7. Execute Runners (CommandLineRunner, ApplicationRunner)
   ↓
8. Serve Requests
   ↓
9. Shutdown Context
   ↓
10. End
```

---

### **Customizing the Lifecycle**
1. **Add Logic at Startup**:
   - Use `CommandLineRunner` or `ApplicationRunner`.
   - Listen to lifecycle events.

2. **Add Logic at Shutdown**:
   - Implement `DisposableBean` or use `@PreDestroy`.

3. **Custom Listeners**:
   - React to lifecycle events using `ApplicationListener`.

4. **Programmatic Context Management**:
   - Manually manage the `ApplicationContext` lifecycle using `context.close()`.

---

### **Conclusion**
The Spring Boot application lifecycle is structured into distinct phases, providing hooks to integrate custom logic at different stages. This flexibility allows developers to fine-tune application behavior for both startup and shutdown processes. By leveraging lifecycle events, `@PostConstruct`, `@PreDestroy`, and runner interfaces, you can ensure a seamless and efficient application lifecycle.

### **2. Give example of spring lifecycle event Spring Boot Lifecycle Event?**
### **Spring Boot Lifecycle Events - Example**

Spring Boot provides several lifecycle events that can be used to perform specific actions at various stages of the application lifecycle. These events are published by the Spring Application Context and can be intercepted using either `ApplicationListener` or `@EventListener`.

Here’s a detailed example of how to use these events:

---

### **Key Lifecycle Events in Spring Boot**
1. **`ApplicationStartingEvent`**
   - Published as soon as the application starts but before the environment is prepared.
   - Use Case: Logging a startup message before anything else.

2. **`ApplicationEnvironmentPreparedEvent`**
   - Published after the environment is prepared but before the context is created.
   - Use Case: Inspect or modify environment properties.

3. **`ApplicationPreparedEvent`**
   - Published after the context is created but before the context is refreshed.
   - Use Case: Perform tasks before beans are fully initialized.

4. **`ContextRefreshedEvent`**
   - Published when the application context is fully initialized or refreshed.
   - Use Case: Initialize components or cache data.

5. **`ApplicationReadyEvent`**
   - Published when the application is ready to handle requests.
   - Use Case: Signal readiness, start background tasks.

6. **`ApplicationFailedEvent`**
   - Published if the application fails to start.
   - Use Case: Log failure details or clean up resources.

---

### **Example Code**

Here’s a complete example that listens to multiple Spring Boot lifecycle events:

#### **1. Listener Using `@EventListener`**
```java
import org.springframework.boot.context.event.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class LifecycleEventListener {

    @EventListener
    public void handleApplicationStarting(ApplicationStartingEvent event) {
        System.out.println("Application is starting...");
    }

    @EventListener
    public void handleEnvironmentPrepared(ApplicationEnvironmentPreparedEvent event) {
        System.out.println("Environment is prepared.");
    }

    @EventListener
    public void handleApplicationPrepared(ApplicationPreparedEvent event) {
        System.out.println("Application context is prepared.");
    }

    @EventListener
    public void handleContextRefreshed(ContextRefreshedEvent event) {
        System.out.println("Application context is refreshed.");
    }

    @EventListener
    public void handleApplicationReady(ApplicationReadyEvent event) {
        System.out.println("Application is ready to serve requests!");
    }

    @EventListener
    public void handleContextClosed(ContextClosedEvent event) {
        System.out.println("Application context is closed.");
    }

    @EventListener
    public void handleApplicationFailed(ApplicationFailedEvent event) {
        System.out.println("Application startup failed.");
    }
}
```

---

#### **2. Listener Using `ApplicationListener` Interface**

Alternatively, you can create separate listener classes that implement the `ApplicationListener` interface for specific events.

**Example: Handling `ApplicationReadyEvent`**

```java
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("Custom logic executed when application is ready!");
    }
}
```

---

### **Custom Event Example**

Spring Boot also allows you to create and publish custom events.

#### **1. Create a Custom Event**
```java
import org.springframework.context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {
    private String message;

    public CustomEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

#### **2. Publish the Custom Event**
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CustomEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishEvent(String message) {
        CustomEvent customEvent = new CustomEvent(this, message);
        eventPublisher.publishEvent(customEvent);
    }
}
```

#### **3. Listen to the Custom Event**
```java
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener {

    @EventListener
    public void handleCustomEvent(CustomEvent event) {
        System.out.println("Received custom event with message: " + event.getMessage());
    }
}
```

#### **4. Trigger the Event**
In a controller or a service, trigger the event:
```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomEventController {

    private final CustomEventPublisher eventPublisher;

    public CustomEventController(CustomEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/trigger-event")
    public String triggerEvent() {
        eventPublisher.publishEvent("Hello from Custom Event!");
        return "Event triggered!";
    }
}
```

---

### **Execution Output Example**

1. **Startup Lifecycle Events**:
   - "Application is starting..."
   - "Environment is prepared."
   - "Application context is prepared."
   - "Application context is refreshed."
   - "Application is ready to serve requests!"

2. **Custom Event Trigger**:
   - On `/trigger-event` API call:
     - "Received custom event with message: Hello from Custom Event!"

3. **Shutdown**:
   - On application shutdown:
     - "Application context is closed."

---

### **Conclusion**

- **Lifecycle Events** allow you to hook into different stages of the Spring Boot application lifecycle.
- Use `@EventListener` or `ApplicationListener` for handling both standard and custom events.
- Custom events provide flexibility for domain-specific logic.
- Properly leveraging these events helps manage initialization, cleanup, and other cross-cutting concerns effectively.