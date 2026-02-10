1. https://medium.com/inspiredbrilliance/kafka-basics-and-core-concepts-5fd7a68c3193
2. http://interviewbit.com/kafka-interview-questions/
3. https://www.geeksforgeeks.org/system-design/saga-design-pattern/

### 1. Ques: Give me kafka notes considering spring boot implementation

Implementing Kafka in Spring Boot is highly streamlined because of **Spring for Apache Kafka**. It handles the boilerplate (connection management, threading, and serialization) so you can focus on the business logic.

Below are structured notes for a Spring Boot implementation.

---

## 1. Core Dependencies

In your `pom.xml`, you only need the Spring Kafka starter. Spring Boot manages the versioning to ensure compatibility with your Boot version.

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>

```

---

## 2. Basic Configuration (`application.yml`)

Spring Boot uses **Auto-configuration**. If you define these properties, Spring will automatically create the `KafkaTemplate` and `ListenerContainer` for you.

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092  # Comma-separated list of brokers
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      group-id: my-app-group
      auto-offset-reset: earliest      # Start from beginning if no offset exists
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "*" # Required for JSON deserialization

```

---

## 3. The Producer (Sending Messages)

Use the `KafkaTemplate` bean provided by Spring. It is thread-safe and easy to use.

```java
@Service
public class MessageProducer {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, String key, Object data) {
        // .send returns a CompletableFuture for asynchronous results
        kafkaTemplate.send(topic, key, data)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent to partition: " + result.getRecordMetadata().partition());
                } else {
                    ex.printStackTrace();
                }
            });
    }
}

```

---

## 4. The Listener (Consuming Messages)

The `@KafkaListener` annotation is the heart of Spring Kafka. It automatically creates a background thread to poll Kafka.

```java
@Component
public class MessageConsumer {

    @KafkaListener(topics = "my-topic", groupId = "my-app-group", concurrency = "3")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Key: " + record.key());
        System.out.println("Value: " + record.value());
        System.out.println("Partition: " + record.partition());
    }
}

```

* **`concurrency`**: Setting this to `3` will spin up 3 separate threads to process partitions in parallel (provided your topic has at least 3 partitions).

---

## 5. Advanced Implementation Tips

### Topic Creation (Automatic)

Instead of creating topics manually via CLI, you can define a `NewTopic` bean. Spring will check if it exists on startup and create it if missing.

```java
@Bean
public NewTopic myTopic() {
    return TopicBuilder.name("my-topic")
            .partitions(6)     // High throughput
            .replicas(3)       // High availability
            .build();
}

```

### Error Handling & Retries

Spring Kafka provides a `DefaultErrorHandler`. You can configure it to retry a message multiple times before sending it to a **Dead Letter Topic (DLT)**.

```java
@Bean
public DefaultErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
    return new DefaultErrorHandler(
        new DeadLetterPublishingRecoverer(template), 
        new FixedBackOff(1000L, 3) // Retry 3 times, 1 second apart
    );
}

```

### Manual Ack (Offset Management)

If you want to manually tell Kafka when a message is "done" (to prevent data loss), change the `AckMode`:

1. **Config:** `spring.kafka.listener.ack-mode: manual`
2. **Code:**
```java
@KafkaListener(topics = "orders")
public void process(String data, Acknowledgment ack) {
    // Do work...
    ack.acknowledge(); // Commit offset now
}

```



---

## Summary of Key Annotations

* **`@EnableKafka`**: Usually unnecessary in Spring Boot (auto-enabled), but required in plain Spring.
* **`@KafkaListener`**: Marks a method as a consumer.
* **`@SendTo`**: Can be used on a listener to automatically send the return value to another topic (Request-Reply pattern).
* **`@Header`**: Extract Kafka headers (like `KafkaHeaders.RECEIVED_KEY`) directly into method parameters.

Would you like a sample `Docker Compose` file to run a local Kafka broker for testing this code?

### 2. Ques: Easy medium questions on kafka 

To test your knowledge, let’s split this into conceptual questions (Theory) and implementation questions (Spring Boot focused).

### **Easy Questions (The "Basics")**

1. **What is the difference between a Topic and a Partition?**
* *Follow-up:* Why would you want more than one partition for a single topic?


2. **What is a Consumer Group?**
* *Follow-up:* If you have a topic with 3 partitions and a consumer group with 5 instances, how many instances will actually receive data?


3. **What does `auto.offset.reset` do?**
* *Scenario:* If your app starts for the very first time and this is set to `latest`, what happens to the messages already sitting in the topic?


4. **What is the role of a "Key" in a Kafka message?** (We discussed this earlier—think about ordering!)

### **Medium Questions (The "Internals")**

5. **What is "Consumer Lag" and how do you monitor it?**
* *Follow-up:* If lag is increasing rapidly, what are the first two things you would check or scale?


6. **In Spring Boot, what is the difference between `AckMode.RECORD` and `AckMode.MANUAL_IMMEDIATE`?**
* *Scenario:* If your service crashes mid-processing, which mode is safer to prevent data loss?


7. **Explain "Delivery Semantics." What is the difference between *At-most-once*, *At-least-once*, and *Exactly-once*?**
* *Follow-up:* Which one is the default behavior for most Kafka Producers?


8. **What are `advertised.listeners` in a Kafka broker configuration?**
* *Scenario:* Why can your Spring Boot app connect to Kafka on your local machine, but fails when you deploy it to a Docker container or a different server?


9. **How does Kafka achieve high throughput despite writing to a disk?**
* *(Hint: It’s not just "fast disks"; it involves the OS Page Cache and Zero Copy.)*



---

**How would you like to proceed?**

* Pick one or two and give me your best answer.
* Or, if you're feeling confident, I can give you a **Hard/Scenario-based** question involving a production failure!

### 3. Ans: Answer of above questions 
Here are the clear, concise answers to the interview questions, including the follow-up scenarios.

---

### **Easy Questions**

**1. Topic vs. Partition?**

* **Answer:** A **Topic** is a logical category or feed name. A **Partition** is the physical log file where data is stored.
* **Follow-up (Why more?):** To increase **Parallelism**. Each partition can be read by only one consumer in a group at a time. More partitions = more concurrent consumers.

**2. What is a Consumer Group?**

* **Answer:** A group of consumers sharing a common `groupId` that work together to consume a topic. Kafka ensures each message is sent to only one member of the group.
* **Follow-up (3 partitions, 5 instances):** Only **3 instances** will be active. 2 instances will sit idle, acting as hot-standbys.

**3. What does `auto.offset.reset` do?**

* **Answer:** It determines what a consumer does when there is no existing offset (e.g., a brand new group).
* **Follow-up (Scenario):** If set to `latest`, the consumer will **ignore all existing messages** and only read messages that arrive *after* it started.

**4. Role of a "Key"?**

* **Answer:** It determines **Partition Assignment**. Messages with the same key are guaranteed to land in the same partition.
* **Follow-up:** This is crucial for **Message Ordering** (e.g., ensuring all updates for "User A" happen in the correct sequence).

---

### **Medium Questions**

**5. Consumer Lag?**

* **Answer:** Lag is the gap between the last message produced (**Log End Offset**) and the last message processed by the consumer (**Current Offset**).
* **Follow-up (Scaling):** 1) Check if the consumer logic is slow (bottleneck). 2) Increase **Partitions** and add more **Consumer instances**.

**6. Spring `AckMode.RECORD` vs. `MANUAL_IMMEDIATE`?**

* **Answer:** `RECORD` commits the offset automatically after the listener method returns. `MANUAL_IMMEDIATE` requires you to call `ack.acknowledge()` in your code.
* **Follow-up (Crash):** **Manual** is safer. You only acknowledge *after* your DB transaction or logic is successful.

**7. Delivery Semantics?**

* **At-most-once:** Commit offset, then process. (May lose data).
* **At-least-once:** Process, then commit offset. (May get duplicates).
* **Exactly-once:** Uses idempotent producers and atomic transactions.
* **Follow-up (Default):** **At-least-once** is the default.

**8. What are `advertised.listeners`?**

* **Answer:** The metadata (IP/Host) that the broker sends back to the client during the "Bootstrap" phase.
* **Follow-up (Scenario):** It fails because the broker might be telling the client "Connect to `localhost`," but from the client's perspective (outside the container), the broker is at `192.168.1.5`.

**9. How is Kafka so fast on disk?**

* **Answer:** 1.  **Sequential I/O:** It appends to the end of a file (much faster than random access).
2.  **Zero Copy:** Data is moved from disk to network buffer without being copied into the application space.
3.  **Page Cache:** It heavily uses the RAM of the Operating System to cache files.

---

### **A Hard/Scenario Question for you:**

"Your application is consuming messages from a topic with 10 partitions. You notice that 1 partition has a massive lag, while the other 9 have zero lag. **What is the most likely cause, and how do you fix it?**" 