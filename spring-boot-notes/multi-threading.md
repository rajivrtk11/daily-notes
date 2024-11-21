https://chatgpt.com/share/673b5d35-c4a4-8005-b247-3ba702c6572a

### 1. Question:
### CompletableFuture is a class introduced in Java 8 that allows us to write asynchronous, non-blocking code explain its meaning ?

`CompletableFuture` is a class introduced in **Java 8** as part of the **java.util.concurrent** package. It provides a way to write asynchronous and non-blocking code, making it easier to handle tasks that might take some time, like network calls, file I/O, or database queries, without blocking the main thread.

### Key Concepts:
1. **Asynchronous Programming**:
   - Asynchronous code does not wait for a task to complete before moving on to the next task.
   - For example, when a CompletableFuture is running a background task, the main thread can continue executing other code.

2. **Non-blocking**:
   - The main thread does not pause or "block" while waiting for the result of the background task.
   - Instead, `CompletableFuture` uses callback methods to process the result once it's available.

### Features of `CompletableFuture`:
1. **Completing Tasks Manually**:
   You can explicitly complete a `CompletableFuture` using the `complete()` method.

2. **Chaining Tasks**:
   It allows you to chain multiple stages of computation using methods like `thenApply()`, `thenAccept()`, and `thenCompose()`.

3. **Combining Futures**:
   You can combine multiple `CompletableFuture` instances using methods like `thenCombine()` and `allOf()`.

4. **Exception Handling**:
   Handle errors in the asynchronous pipeline using `exceptionally()` or `handle()`.

5. **Support for Functional Programming**:
   It integrates with Java's functional programming model (lambda expressions and method references) for cleaner and more concise code.

6. **Execution in ForkJoinPool**:
   By default, tasks are executed in the **ForkJoinPool.commonPool**, but you can also provide your own `Executor`.

### Example 1: Simple CompletableFuture
```java
import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Simulate a long-running task
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result from the async task";
        });

        // Non-blocking call to process the result
        future.thenAccept(result -> {
            System.out.println("Received result: " + result);
        });

        System.out.println("Main thread is not blocked!");
    }
}
```

**Output**:
```
Main thread is not blocked!
Received result: Result from the async task
```

### Example 2: Chaining Tasks
```java
import java.util.concurrent.CompletableFuture;

public class CompletableFutureChaining {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> "Task 1")
            .thenApply(result -> result + " -> Task 2")
            .thenApply(result -> result + " -> Task 3")
            .thenAccept(finalResult -> System.out.println("Final Result: " + finalResult));
    }
}
```

**Output**:
```
Final Result: Task 1 -> Task 2 -> Task 3
```

### Use Cases:
1. **API calls**:
   Fetch data from an external service asynchronously.
2. **Parallel Execution**:
   Run multiple tasks in parallel and combine their results.
3. **Non-blocking UI**:
   Prevent blocking the UI thread in GUI applications while waiting for a long-running task.

`CompletableFuture` is a powerful tool for writing modern, scalable, and responsive applications in Java. It helps manage concurrency more effectively and simplifies the handling of asynchronous workflows.


### 2. Question:
### In my local system i am getting only main thread is not block. future response is not coming  ?
If you are only seeing "Main thread is not blocked!" and not the result from the `CompletableFuture`, it means the main thread is exiting before the asynchronous task completes. Since `CompletableFuture` runs tasks asynchronously, the main thread does not wait for the task to finish unless explicitly told to.

### Fix: Wait for the Future to Complete
To ensure the asynchronous task completes before the program terminates, you can block the main thread temporarily or make it wait until the future completes.

#### Updated Example:
```java
import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Simulate a long-running task
            try {
                Thread.sleep(1000); // 1 second delay
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result from the async task";
        });

        // Non-blocking call to process the result
        future.thenAccept(result -> {
            System.out.println("Received result: " + result);
        });

        // Wait for the future to complete
        future.join(); // Blocks the main thread until the task completes

        System.out.println("Main thread is not blocked!");
    }
}
```

#### Output:
```
Received result: Result from the async task
Main thread is not blocked!
```

### Explanation of Changes:
1. **`future.join()`**:
   - This method blocks the main thread until the `CompletableFuture` completes.
   - Use this in scenarios where the program should wait for the result.

2. **Alternative: `future.get()`**:
   - Another way to wait for the result is using `get()`.
   - Example:
     ```java
     String result = future.get(); // Blocks and returns the result
     System.out.println("Result: " + result);
     ```
   - However, `get()` throws checked exceptions (`InterruptedException`, `ExecutionException`), so `join()` is often preferred when you don't need exception handling.

3. **Don't use in production code**:
   - Blocking the thread defeats the purpose of asynchronous programming.
   - Use these methods only for debugging or cases where the main thread must wait for the result.

### A Better Approach:
If you don't want to block the main thread, ensure the program does not terminate prematurely by keeping the thread alive using `Thread.sleep()` or other techniques during development. This is helpful for testing asynchronous tasks.

Example:
```java
public static void main(String[] args) throws InterruptedException {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return "Result from the async task";
    });

    future.thenAccept(result -> {
        System.out.println("Received result: " + result);
    });

    // Keep the main thread alive temporarily
    Thread.sleep(2000); // Allow time for async task to complete
    System.out.println("Main thread is not blocked!");
}
```

**Output**:
```
Received result: Result from the async task
Main thread is not blocked!
```

This ensures that the asynchronous task gets enough time to complete. In real applications, proper task orchestration or application lifecycle management would handle such cases.

### 3. Question:
### But online compiler working fine ?
The difference in behavior between your local system and an online compiler is likely due to how the **JVM (Java Virtual Machine)** behaves when the main thread exits in different environments.

### Why Online Compilers Work:
Online compilers often run your code in a controlled environment:
- The JVM in an online compiler may keep running until all non-daemon threads, including background threads (e.g., the one used by `CompletableFuture`), complete.
- The environment may have additional hooks to wait for tasks to complete before showing output.

### Why Local System Might Exit Early:
On your local machine:
- If the **main thread** exits, the JVM shuts down, **terminating all background threads**, including those executing your `CompletableFuture`. This is the default behavior of the JVM.
- By default, the thread pool used by `CompletableFuture.supplyAsync()` (`ForkJoinPool.commonPool`) consists of daemon threads, which are terminated when the JVM exits.

---

### Fix for Local System

#### 1. **Explicitly Wait for Completion**
Use `join()` or `get()` as shown earlier to make sure the main thread waits for the `CompletableFuture` to finish. For example:
```java
future.join(); // Ensures the main thread waits for the async task
```

#### 2. **Use `Thread.sleep()` Temporarily**
If you're testing the behavior and don't want to block with `join()`, you can delay the termination of the main thread:
```java
Thread.sleep(2000); // Keeps the main thread alive to let the async task complete
```

#### 3. **Use a Custom Executor**
Daemon threads may terminate with the JVM. To avoid this, use a custom **ExecutorService** with non-daemon threads:
```java
import java.util.concurrent.*;

public class CustomExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // Simulate delay
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result from the async task";
        }, executor);

        future.thenAccept(result -> System.out.println("Received result: " + result));

        // Wait for the future to complete
        future.join();
        executor.shutdown(); // Cleanly shut down the executor
    }
}
```

---

### Why Does This Happen Locally?
1. **Daemon Threads**:
   - Background tasks in the `ForkJoinPool.commonPool` run on daemon threads by default.
   - When the main thread exits, daemon threads are terminated immediately.

2. **Online Compiler Settings**:
   - Online environments often prevent JVM termination until all tasks are complete, regardless of thread type.

By explicitly waiting or using custom executors, you can ensure consistent behavior across environments.

### 4. Question:
### Give important methods of completalbe future ?
Here’s a list of important methods in the `CompletableFuture` class, grouped by functionality, to help you write asynchronous and non-blocking Java code effectively:

---

### **1. Creation Methods**
These methods are used to create `CompletableFuture` instances.

- **`supplyAsync(Supplier<U>)`**  
  Executes a task asynchronously and returns a result.
  ```java
  CompletableFuture.supplyAsync(() -> "Hello");
  ```

- **`supplyAsync(Supplier<U>, Executor)`**  
  Executes a task asynchronously using a custom executor.

- **`runAsync(Runnable)`**  
  Executes a task asynchronously that does not return a result.
  ```java
  CompletableFuture.runAsync(() -> System.out.println("Task running"));
  ```

- **`runAsync(Runnable, Executor)`**  
  Executes a `Runnable` task asynchronously with a custom executor.

- **`completedFuture(T)`**  
  Creates a `CompletableFuture` that is already completed with a given value.
  ```java
  CompletableFuture.completedFuture("Completed");
  ```

---

### **2. Task Completion**
These methods allow you to manually complete or cancel the `CompletableFuture`.

- **`complete(T)`**  
  Completes the `CompletableFuture` with the specified value.
  ```java
  future.complete("Manually completed");
  ```

- **`completeExceptionally(Throwable)`**  
  Completes the `CompletableFuture` with an exception.
  ```java
  future.completeExceptionally(new RuntimeException("Error!"));
  ```

- **`cancel(boolean mayInterruptIfRunning)`**  
  Attempts to cancel the task.

---

### **3. Combining and Chaining**
These methods allow chaining or combining multiple tasks.

- **`thenApply(Function<T, U>)`**  
  Transforms the result of a `CompletableFuture`.
  ```java
  future.thenApply(result -> result.toUpperCase());
  ```

- **`thenAccept(Consumer<T>)`**  
  Consumes the result without returning a new `CompletableFuture`.
  ```java
  future.thenAccept(System.out::println);
  ```

- **`thenRun(Runnable)`**  
  Runs a task after the `CompletableFuture` completes but doesn’t access the result.
  ```java
  future.thenRun(() -> System.out.println("Task finished!"));
  ```

- **`thenCompose(Function<T, CompletableFuture<U>>)`**  
  Flattens and chains `CompletableFuture` instances.
  ```java
  future.thenCompose(result -> CompletableFuture.supplyAsync(() -> result + " World"));
  ```

- **`thenCombine(CompletableFuture<U>, BiFunction<T, U, R>)`**  
  Combines results of two `CompletableFuture` instances.
  ```java
  future.thenCombine(otherFuture, (a, b) -> a + b);
  ```

- **`allOf(CompletableFuture<?>...)`**  
  Waits for all provided futures to complete.
  ```java
  CompletableFuture.allOf(future1, future2).join();
  ```

- **`anyOf(CompletableFuture<?>...)`**  
  Completes when any one of the provided futures completes.
  ```java
  CompletableFuture.anyOf(future1, future2).join();
  ```

---

### **4. Exception Handling**
These methods handle errors and exceptions in the pipeline.

- **`exceptionally(Function<Throwable, T>)`**  
  Handles exceptions and provides a fallback value.
  ```java
  future.exceptionally(ex -> "Fallback value");
  ```

- **`handle(BiFunction<T, Throwable, R>)`**  
  Handles both success and failure cases.
  ```java
  future.handle((result, ex) -> ex == null ? result : "Error handled");
  ```

- **`whenComplete(BiConsumer<T, Throwable>)`**  
  Executes a callback when the computation is complete, whether it succeeded or failed.
  ```java
  future.whenComplete((result, ex) -> {
      if (ex == null) System.out.println(result);
      else ex.printStackTrace();
  });
  ```

---

### **5. Blocking and Getting Results**
Use these methods to block and wait for the result. Avoid using them in asynchronous workflows.

- **`get()`**  
  Waits for the result (blocks the thread).
  ```java
  String result = future.get();
  ```

- **`get(long timeout, TimeUnit unit)`**  
  Waits for the result with a timeout.

- **`join()`**  
  Similar to `get()`, but throws an unchecked exception instead of checked ones.
  ```java
  String result = future.join();
  ```

- **`isDone()`**  
  Checks if the task is complete.
  ```java
  if (future.isDone()) System.out.println("Completed!");
  ```

- **`isCancelled()`**  
  Checks if the task was canceled.

---

### **6. Miscellaneous**
- **`orTimeout(long timeout, TimeUnit unit)`**  
  Fails the `CompletableFuture` with a timeout exception if it doesn’t complete in time.
  ```java
  future.orTimeout(5, TimeUnit.SECONDS);
  ```

- **`completeOnTimeout(T value, long timeout, TimeUnit unit)`**  
  Completes the `CompletableFuture` with a default value if it times out.
  ```java
  future.completeOnTimeout("Default", 5, TimeUnit.SECONDS);
  ```

---

These methods cover most use cases for asynchronous programming with `CompletableFuture`. Combine them to build flexible, non-blocking workflows in your applications.

### 5. Question:
### Print even and odd using two thread using thread, executor service and completable future?
Below are examples of how to print even and odd numbers using **two threads**. Each approach uses a different method: `Thread`, `ExecutorService`, and `CompletableFuture`.

---

### **1. Using `Thread`**
This approach uses two `Thread` objects for printing even and odd numbers.

```java
public class EvenOddUsingThread {
    private static final Object lock = new Object();
    private static int number = 1;
    private static final int MAX = 10;

    public static void main(String[] args) {
        Thread oddThread = new Thread(() -> {
            while (number <= MAX) {
                synchronized (lock) {
                    if (number % 2 == 1) {
                        System.out.println("Odd: " + number++);
                        lock.notify(); // Notify the other thread
                    } else {
                        try {
                            lock.wait(); // Wait for even thread
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });

        Thread evenThread = new Thread(() -> {
            while (number <= MAX) {
                synchronized (lock) {
                    if (number % 2 == 0) {
                        System.out.println("Even: " + number++);
                        lock.notify(); // Notify the other thread
                    } else {
                        try {
                            lock.wait(); // Wait for odd thread
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });

        oddThread.start();
        evenThread.start();
    }
}
```

---

### **2. Using `ExecutorService`**
This approach uses an `ExecutorService` to manage the threads.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenOddUsingExecutorService {
    private static final Object lock = new Object();
    private static int number = 1;
    private static final int MAX = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable oddTask = () -> {
            while (number <= MAX) {
                synchronized (lock) {
                    if (number % 2 == 1) {
                        System.out.println("Odd: " + number++);
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        };

        Runnable evenTask = () -> {
            while (number <= MAX) {
                synchronized (lock) {
                    if (number % 2 == 0) {
                        System.out.println("Even: " + number++);
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        };

        executorService.submit(oddTask);
        executorService.submit(evenTask);
        executorService.shutdown();
    }
}
```

---

### **3. Using `CompletableFuture`**
This approach uses `CompletableFuture` for asynchronous execution of even and odd number printing.

```java
import java.util.concurrent.CompletableFuture;

public class EvenOddUsingCompletableFuture {
    private static final Object lock = new Object();
    private static int number = 1;
    private static final int MAX = 10;

    public static void main(String[] args) {
        CompletableFuture<Void> oddFuture = CompletableFuture.runAsync(() -> {
            while (number <= MAX) {
                synchronized (lock) {
                    if (number % 2 == 1) {
                        System.out.println("Odd: " + number++);
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });

        CompletableFuture<Void> evenFuture = CompletableFuture.runAsync(() -> {
            while (number <= MAX) {
                synchronized (lock) {
                    if (number % 2 == 0) {
                        System.out.println("Even: " + number++);
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });

        CompletableFuture.allOf(oddFuture, evenFuture).join(); // Wait for both to complete
    }
}
```

---

### Output for All Approaches:
```
Odd: 1
Even: 2
Odd: 3
Even: 4
Odd: 5
Even: 6
Odd: 7
Even: 8
Odd: 9
Even: 10
```

Each approach demonstrates how to coordinate between two threads to print numbers alternately using synchronization primitives like `synchronized`, `lock`, and `wait/notify`.

