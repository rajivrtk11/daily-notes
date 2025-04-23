
Awesome! Let's go through the answers **with examples** for the Java thread interview questions. We'll start with the **Basic Level** first. You can tell me to continue with intermediate and advanced later.

---

## 🟢 **Basic Level Questions with Answers & Examples**

---

### 1. **What is a thread in Java?**

A thread is a **lightweight sub-process**, the smallest unit of processing. It’s a way for Java programs to perform multiple tasks concurrently.

🔹 **Example**:
```java
public class MyThread extends Thread {
    public void run() {
        System.out.println("Thread is running");
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start(); // starts a new thread
    }
}
```

---

### 2. **What is the difference between a process and a thread?**

| Feature      | Process                  | Thread                     |
|--------------|---------------------------|-----------------------------|
| Memory       | Has its own memory space  | Shares memory with others  |
| Communication| Slower                    | Faster (shared memory)     |
| Creation     | Expensive                 | Lightweight                 |

---

### 3. **How do you create a thread in Java?**

#### a) By extending `Thread`:
```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread using Thread class");
    }
}
```

#### b) By implementing `Runnable`:
```java
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Thread using Runnable interface");
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t = new Thread(new MyRunnable());
        t.start();
    }
}
```

✅ Prefer `Runnable` when your class extends another class.

---

### 4. **What is the lifecycle of a thread?**

States:
- New
- Runnable
- Running
- Blocked/Waiting
- Terminated

```java
Thread t = new Thread(() -> System.out.println("Running"));
System.out.println(t.getState()); // NEW
t.start();
System.out.println(t.getState()); // RUNNABLE or RUNNING
```

---

### 5. **Difference between `start()` and `run()`**

- `start()` → creates a **new thread**, then calls `run()`.
- `run()` → just a normal method call, **doesn’t start a new thread**.

```java
Thread t = new Thread(() -> System.out.println("Running"));
t.run();   // runs on main thread
t.start(); // runs on separate thread
```

---

### 6. **What is the purpose of the `sleep()` method?**

- Used to pause a thread for a specified time.
- Keeps the lock if holding one.

```java
Thread.sleep(1000); // sleeps for 1 second
```

---

### 7. **What does `yield()` do in Java threads?**

- Suggests the thread scheduler to pause the current thread and give others a chance.
- No guarantee it will yield.

```java
Thread.yield();
```

---

### 8. **Can a thread be restarted once it is dead?**

❌ No. Once a thread has completed or is terminated, it **cannot be started again**.

```java
Thread t = new Thread(() -> {});
t.start();
t.start(); // IllegalThreadStateException ❌
```

---

### 9. **Difference between `wait()`, `notify()`, `notifyAll()`**

These are used for inter-thread communication. They must be called from **synchronized blocks/methods**.

```java
synchronized (obj) {
    obj.wait();      // releases lock and waits
    obj.notify();    // wakes up one waiting thread
    obj.notifyAll(); // wakes up all waiting threads
}
```

---

### 10. **What is a daemon thread?**

- A background thread that runs as long as other non-daemon threads are running.
- Used for tasks like garbage collection.

```java
Thread t = new Thread(() -> {});
t.setDaemon(true);
t.start();
```

---

Want me to continue with the 🟡 **Intermediate Level** or 🔴 **Advanced Level** next? Or do you want to focus more on deadlock, synchronization, or executors?
Great! Let’s move on to the **🟡 Intermediate Level** Java thread interview questions with answers and code examples.

---

## 🟡 **Intermediate Level Questions with Answers & Examples**

---

### 11. **How does synchronization work in Java?**

Java uses the `synchronized` keyword to prevent multiple threads from executing critical sections at the same time.

🔒 It locks the object or method, allowing only one thread at a time.

```java
public synchronized void printData() {
    // only one thread can access this method at a time
}
```

Or using a synchronized block:
```java
synchronized(this) {
    // critical section
}
```

---

### 12. **What is a race condition?**

A **race condition** occurs when two or more threads access shared data and try to change it at the same time. Without proper synchronization, results can be inconsistent.

✅ Fix using synchronization:
```java
private int counter = 0;

public synchronized void increment() {
    counter++;
}
```

---

### 13. **Difference between `synchronized` method and block**

- **Synchronized Method**: Locks the whole object.
- **Synchronized Block**: Locks a specific object (more control, better performance).

```java
synchronized void methodA() { ... } // locks `this`

void methodB() {
    synchronized(someObject) { ... } // locks `someObject`
}
```

---

### 14. **How does Java prevent thread interference?**

Using:
- `synchronized`
- `volatile`
- Locks from `java.util.concurrent.locks`

These ensure that shared variables are read/written safely between threads.

---

### 15. **What are thread priorities?**

Threads in Java can be assigned priorities (1 to 10). Thread scheduler may prefer higher-priority threads, but it’s not guaranteed.

```java
Thread t = new Thread(() -> {});
t.setPriority(Thread.MAX_PRIORITY); // 10
```

---

### 16. **Difference between `ReentrantLock` and `synchronized`**

| Feature             | synchronized     | ReentrantLock         |
|---------------------|------------------|------------------------|
| Reentrant?          | Yes              | Yes                    |
| Try to acquire lock | No               | Yes (`tryLock()`)      |
| Fairness            | No               | Yes (optional)         |
| Interruptible?      | No               | Yes                    |

✅ Example:
```java
Lock lock = new ReentrantLock();

lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
```

---

### 17. **What is deadlock and how can it be avoided?**

🔒 Deadlock happens when two threads wait on each other to release resources.

✅ **Avoid by:**
- Acquiring locks in a consistent order
- Using `tryLock()`
- Avoiding nested locks

[We already saw a deadlock example in earlier messages.]

---

### 18. **What is thread starvation and how does it occur?**

Starvation occurs when a thread is **perpetually denied access to resources** due to other high-priority threads dominating the execution.

💡 Use **fair locks**:
```java
Lock lock = new ReentrantLock(true); // fair lock
```

---

### 19. **What is thread-safe code?**

Code is thread-safe if it functions correctly when accessed by multiple threads simultaneously.

✅ Achieved using:
- Synchronization
- Locks
- Atomic variables (`AtomicInteger`)
- Immutable objects

---

### 20. **How can you create a thread-safe singleton class?**

✅ Using synchronized method:
```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}
```

✅ Or use **Double-Checked Locking** (faster):
```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
}
```

---

Would you like to continue with 🔴 **Advanced level questions**, or do you want more detail on any specific topic like `ExecutorService`, `volatile`, or `CompletableFuture`?Awesome! Let's dive into the 🔴 **Advanced Level** Java threading questions with answers and code examples:

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

### 31. Deadlock 
Great question! Let’s break down **how a deadlock occurs** in the `DeadlockExample`:

---

### 👀 **Code Recap** (simplified for clarity):

```java
class A {
    synchronized void methodA(B b) {
        System.out.println("Thread 1: Holding lock on A...");
        try { Thread.sleep(100); } catch (Exception e) {}
        b.last();
    }

    synchronized void last() {
        System.out.println("Inside A.last()");
    }
}

class B {
    synchronized void methodB(A a) {
        System.out.println("Thread 2: Holding lock on B...");
        try { Thread.sleep(100); } catch (Exception e) {}
        a.last();
    }

    synchronized void last() {
        System.out.println("Inside B.last()");
    }
}

public class DeadlockExample {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        new Thread(() -> a.methodA(b)).start();
        new Thread(() -> b.methodB(a)).start();
    }
}
```

### 🔄 **Execution Flow**:

#### 🧵 Thread 1:
1. Calls `a.methodA(b)`
2. Acquires **lock on object A**
3. Sleeps for 100 ms
4. Then tries to call `b.last()` → needs **lock on object B**

#### 🧵 Thread 2:
1. Calls `b.methodB(a)`
2. Acquires **lock on object B**
3. Sleeps for 100 ms
4. Then tries to call `a.last()` → needs **lock on object A**

---

### ❌ **Deadlock Scenario**:

| Step | Thread 1 | Thread 2 |
|------|----------|----------|
| 1    | Lock on A | Lock on B |
| 2    | Wants lock on B | Wants lock on A |
| 3    | WAITING 🔁 | WAITING 🔁 |

Both threads are now **waiting forever** for each other to release the lock — this is **deadlock**.

---

### 💡 **Why this happens**:
- Both threads hold one lock and try to acquire another one.
- There's **no timeout** or **lock ordering**, so neither can proceed.

---

### ✅ **Fixing the Deadlock (Best Practice)**:
Use consistent lock acquisition ordering:

```java
class A {
    void methodA(B b) {
        synchronized (A.class) {
            synchronized (B.class) {
                System.out.println("Thread 1 safely locked A and B");
            }
        }
    }
}
```

Or use `ReentrantLock.tryLock(timeout)` which gives up waiting after some time.

---

Let me know if you want me to show a fixed version of this deadlock or explore it with `ReentrantLock`!