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
### **How to Use Mutable Classes in Java?**  
A **mutable class** in Java is a class whose objects can be modified after creation. Unlike immutable classes (e.g., `String`), mutable classes allow changing their fields or state.

#### **Example of a Mutable Class**
```java
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Setter methods allow modifying fields (mutability)
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

public class MutableClassExample {
    public static void main(String[] args) {
        Person p = new Person("Alice", 25);
        System.out.println(p.getName()); // Alice

        p.setName("Bob"); // Modifying the existing object
        System.out.println(p.getName()); // Bob
    }
}
```
🔹 **Key Features of a Mutable Class:**
- Provides **setter methods** to change fields.
- The **state of an object can change** after instantiation.

---

### **Difference Between `StringBuffer` and `StringBuilder`**
Both `StringBuffer` and `StringBuilder` are **mutable versions** of `String`, allowing modification of string content without creating new objects.

| Feature           | `StringBuffer` | `StringBuilder` |
|------------------|--------------|----------------|
| **Thread Safety** | Yes (Synchronized) | No (Not Synchronized) |
| **Performance**   | Slower due to synchronization | Faster (no synchronization overhead) |
| **Use Case**     | Multi-threaded environments | Single-threaded applications |

#### **Example Usage**
```java
public class StringBufferVsStringBuilder {
    public static void main(String[] args) {
        // Using StringBuffer (Thread-Safe)
        StringBuffer sb1 = new StringBuffer("Hello");
        sb1.append(" World"); // Modifies existing object
        System.out.println(sb1); // Hello World

        // Using StringBuilder (Faster, but Not Thread-Safe)
        StringBuilder sb2 = new StringBuilder("Hello");
        sb2.append(" Java");
        System.out.println(sb2); // Hello Java
    }
}
```
### **When to Use Which?**
- Use **`StringBuffer`** when **multiple threads** modify the string (e.g., in a multi-threaded environment).
- Use **`StringBuilder`** when working in a **single-threaded** environment for better performance.

Would you like me to dive deeper into any of these topics? 🚀

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