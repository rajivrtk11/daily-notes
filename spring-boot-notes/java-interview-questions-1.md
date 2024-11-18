Here are the detailed answers to your questions:

---

### **1. What are the pillars of OOP concepts?**
- **Encapsulation**: Bundling data and methods that operate on the data within one unit (class).
- **Inheritance**: Mechanism to acquire properties and behavior from a parent class.
- **Polymorphism**: Ability to take multiple forms (e.g., method overriding and overloading).
- **Abstraction**: Hiding implementation details and exposing only essential features.

---

### **2. Where did you use these concepts in your project?**
- **Encapsulation**: Used private fields and public getters/setters to control data access in DTOs.
- **Inheritance**: Created base classes for shared logic (e.g., `BaseController` for APIs).
- **Polymorphism**: Implemented dynamic behavior using method overriding (e.g., `toString()` for logging).
- **Abstraction**: Used abstract classes and interfaces to define contracts for services (e.g., `PaymentGateway` interface).

---

### **3. Explain Exception hierarchy in inheritance.**
- **Hierarchy:**
  - `Throwable`
    - `Exception` (Checked)
      - `IOException`, `SQLException`
    - `RuntimeException` (Unchecked)
      - `NullPointerException`, `IllegalArgumentException`
  - `Error` (Unchecked)
    - `OutOfMemoryError`, `StackOverflowError`

**Inheritance Rule:** A child class cannot declare a broader exception than its parent in overridden methods.

---

### **4. Parent-child override scenario**
- A child class can override a method from its parent class with the same signature.  
- **Exception Rule**: Child cannot throw a checked exception broader than the parent.  
  ```java
  class Parent {
      void method() throws IOException { }
  }
  class Child extends Parent {
      @Override
      void method() throws FileNotFoundException { } // Allowed
  }
  ```

---

### **5. Can we override static and private methods?**
- **Static Methods**: No, they are class-level and hidden, not overridden.  
- **Private Methods**: No, they are not visible to child classes and thus cannot be overridden.

---

### **6. What is the difference between Java 7 and Java 8 interfaces?**
| **Aspect**           | **Java 7**                           | **Java 8**                           |
|-----------------------|---------------------------------------|---------------------------------------|
| **Methods**           | Only abstract methods.              | Can have `default` and `static` methods. |
| **Functional Interface** | Not supported.                    | Supported via `@FunctionalInterface`. |

---

### **7. What is final, finally, and finalize?**
- **Final**: Used to define constants, prevent inheritance or overriding.
- **Finally**: A block always executed after `try-catch` (used for cleanup).
- **Finalize**: A method called by the GC before object deletion (deprecated in Java 9).

---

### **8. What is `equals()` and `hashCode()` method and its contract?**
- **`equals()`**: Compares the content of objects.
- **`hashCode()`**: Returns an integer used in hashing-based collections.

**Contract:**  
- If two objects are equal (`equals()` returns true), their hash codes must be the same.
- Unequal objects can have the same hash code (hash collision).

---

### **9. What is Exception and its hierarchy?**
- **Throwable**: Base class for errors and exceptions.
  - `Error`: Unrecoverable (e.g., `OutOfMemoryError`).
  - `Exception`: Recoverable issues (e.g., `IOException`).

---

### **10. Why is a checked exception also called a compile-time exception?**
- The compiler forces the handling of checked exceptions using `try-catch` or `throws`.  
- Occurs at runtime, but its handling is mandated during compilation.

---

### **11. How to write a custom exception?**
```java
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
```

---

### **12. Throw and Throws**
- **Throw**: Used to explicitly throw an exception.
- **Throws**: Declares exceptions that a method can throw.

---

### **13. Exception tricky programming question**
- **Order Rule:** Catch blocks for child exceptions must come before parent exceptions.  
  ```java
  try { } catch (NullPointerException e) { } catch (Exception e) { } // Valid
  ```

---

### **14. Try, Catch, Finally Return Statement Questions**
- `finally` block modifies return value if it contains a `return` statement.  
  ```java
  try {
      return 1;
  } finally {
      return 2; // Overrides return in try block
  }
  ```

---

### **15. Is finally block always executed?**
- Yes, except:
  - JVM exits (`System.exit()`).
  - Fatal errors occur.

---

### **String Questions**

---

### **16. How many ways can we create a string object?**
1. **Using String Literal**:
   ```java
   String s = "hello";
   ```
2. **Using `new` Keyword**:
   ```java
   String s = new String("hello");
   ```

---

### **17. String object creation scenario**
- **Literal**: Uses SCP for memory optimization.  
- **New**: Allocates memory on the heap and SCP.

---

### **18. What is SCP (String Constant Pool) and how does it work?**
- A special memory area inside the heap where string literals are stored to avoid duplicates.

---

### **19. Why is String immutable?**
- To maintain **security**, **performance**, and **thread safety**.  
- Allows reusability in SCP without affecting other references.

---

### **20. How to use mutable classes and difference between StringBuffer and StringBuilder?**
- **Mutable Classes**: Use `StringBuilder` or `StringBuffer`.  
| **Aspect**        | **StringBuffer**                       | **StringBuilder**                   |
|--------------------|----------------------------------------|--------------------------------------|
| **Thread Safety**  | Thread-safe (synchronized).           | Not thread-safe.                    |
| **Performance**    | Slower due to synchronization.        | Faster without synchronization.      |

---

### **21. How to write your own custom immutable class?**
1. Make the class `final`.  
2. Make all fields `private final`.  
3. Provide no setters and only getters.  
4. Initialize fields through a constructor.

---

### **22. Which is good for storing passwords: String or char[]?**
- **char[]**:  
  - `String` is immutable and cannot be cleared from memory.  
  - `char[]` can be explicitly cleared after use.

---

### **23. What is a marker (tagging) interface? Can we create our own?**
- An interface with no methods (e.g., `Serializable`, `Cloneable`).
- **Yes**, you can create custom marker interfaces.  
  Example:
  ```java
  interface MyMarker { }
  ```