Awesome! Let's dive into the 🔴 **Advanced Level** Java threading questions with answers and code examples:

---

## 🔴 **Advanced Level Questions with Answers & Examples**

---

### 21. **What is the Java Memory Model (JMM)?**

The **Java Memory Model (JMM)** defines how threads interact through memory and what behaviors are legal in concurrent programs.

✅ It ensures **visibility** and **ordering** of variables between threads.

---

### 22. **What are the key principles of `happens-before` relationships?**

- `Thread.start()` **happens-before** any code in the started thread.
- `Thread.join()` **happens-after** the thread's run method finishes.
- Writing to a variable **happens-before** reading it **if** the variable is `volatile` or synchronized properly.

---

### 23. **What is the `volatile` keyword and how is it different from `synchronized`?**

- `volatile` ensures **visibility** of changes across threads, but does **not guarantee atomicity**.
- `synchronized` ensures **both visibility and atomicity**.

✅ Example:
```java
volatile boolean running = true;

public void run() {
    while (running) {
        // loop
    }
}
```

---

### 24. **What is the use of `ThreadLocal`?**

Provides **thread-specific variables**. Each thread has its own copy.

✅ Example:
```java
ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> 1);

System.out.println(threadId.get()); // each thread gets its own value
```

---

### 25. **What is the Fork/Join framework in Java?**

It breaks a task into smaller subtasks using **divide-and-conquer** and uses **work-stealing** to balance load across threads.

✅ Example:
```java
class MyTask extends RecursiveTask<Integer> {
    protected Integer compute() {
        // split into subtasks and join results
        return 1;
    }
}
ForkJoinPool pool = new ForkJoinPool();
pool.invoke(new MyTask());
```

---

### 26. **Explain the difference between `Callable` and `Runnable`.**

| Feature        | Runnable       | Callable           |
|----------------|----------------|--------------------|
| Return value   | No             | Yes (`Future<T>`)  |
| Throws exception | No            | Yes (`Exception`)  |

✅ Example using `Callable`:
```java
Callable<String> task = () -> "Hello";
Future<String> result = Executors.newSingleThreadExecutor().submit(task);
```

---

### 27. **How do `ExecutorService` and `Executors` framework work?**

Java provides thread pool management via `ExecutorService`.

✅ Example:
```java
ExecutorService executor = Executors.newFixedThreadPool(2);
executor.submit(() -> System.out.println("Running in thread"));
executor.shutdown();
```

---

### 28. **What are `Future` and `CompletableFuture` in Java?**

- `Future`: Represents the result of an async computation.
- `CompletableFuture`: More powerful — supports chaining, async composition.

✅ Example:
```java
CompletableFuture.supplyAsync(() -> "Hello")
    .thenApply(result -> result + " World")
    .thenAccept(System.out::println);
```

---

### 29. **How does Java handle interruption of threads?**

Java uses the `interrupt()` method to signal a thread to stop.

✅ Example:
```java
Thread t = new Thread(() -> {
    while (!Thread.currentThread().isInterrupted()) {
        // do work
    }
});
t.start();
t.interrupt(); // signals to stop
```

---

### 30. **What are common tools/techniques for debugging multithreaded issues like deadlocks?**

- **Thread dump analysis**
- **VisualVM** or **JConsole**
- Use `jstack` to get stack trace
- Add **timeout with tryLock** to avoid deadlocks

✅ Example:
```java
if (lock.tryLock(1, TimeUnit.SECONDS)) {
    try {
        // do something
    } finally {
        lock.unlock();
    }
}
```

---

🎯 Let me know if you’d like a PDF/cheat sheet of these questions or mock interview practice!