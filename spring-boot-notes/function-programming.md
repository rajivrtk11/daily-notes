In Java, especially with Java 8's introduction of lambda expressions and functional interfaces, we can implement higher-order functions. Here’s how we can achieve each of the scenarios you described.

### 1. **Passing a Function to a Function**

We can pass a function as an argument by using functional interfaces like `Function`, `Predicate`, `Consumer`, etc.

```java
import java.util.function.Function;

public class HigherOrderFunctionExample {
    public static void main(String[] args) {
        Function<Integer, Integer> squareFunction = x -> x * x;
        
        System.out.println(applyFunction(5, squareFunction)); // Output: 25
    }

    // Method that accepts a function as an argument
    public static int applyFunction(int value, Function<Integer, Integer> function) {
        return function.apply(value);
    }
}
```

### 2. **Creating a Function Within a Function**

With lambda expressions, we can create functions within methods.

```java
public class HigherOrderFunctionExample {
    public static void main(String[] args) {
        System.out.println(createMultiplier(10).apply(5)); // Output: 50
    }

    // Method returning a function created inside
    public static Function<Integer, Integer> createMultiplier(int factor) {
        return x -> x * factor;
    }
}
```

### 3. **Returning a Function from a Function**

We can return a function from a method by returning a lambda or a functional interface.

```java
import java.util.function.Function;

public class HigherOrderFunctionExample {
    public static void main(String[] args) {
        Function<Integer, Integer> doubler = createMultiplier(2);
        System.out.println(doubler.apply(10)); // Output: 20
    }

    // Method that returns a function
    public static Function<Integer, Integer> createMultiplier(int factor) {
        return x -> x * factor;
    }
}
```

### 4. **Passing Objects to a Function**

This is traditional in Java but works well alongside functional programming as well.

```java
import java.util.function.Consumer;

public class HigherOrderFunctionExample {
    public static void main(String[] args) {
        Consumer<String> printConsumer = message -> System.out.println(message);
        printMessage("Hello, World!", printConsumer);
    }

    public static void printMessage(String message, Consumer<String> consumer) {
        consumer.accept(message);
    }
}
```

### 5. **Returning Objects from a Function**

Returning an object from a function is also standard in Java.

```java
public class HigherOrderFunctionExample {
    public static void main(String[] args) {
        Person person = createPerson("Alice", 30);
        System.out.println(person.getName()); // Output: Alice
    }

    public static Person createPerson(String name, int age) {
        return new Person(name, age);
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
}
```

### 6. **Creating a Function Inside Another Function**

This is implicitly achieved with lambdas and functional programming.

```java
import java.util.function.Function;

public class HigherOrderFunctionExample {
    public static void main(String[] args) {
        Function<String, String> greet = name -> {
            // Creating a function inside another function
            Function<String, String> sayHello = msg -> "Hello, " + msg;
            return sayHello.apply(name);
        };
        
        System.out.println(greet.apply("Alice")); // Output: Hello, Alice
    }
}
```

### Summary
These examples demonstrate how Java’s functional interfaces enable us to pass, create, and return functions similarly to first-class functions in functional programming. Java’s lambdas and functional interfaces allow us to integrate functional programming patterns while keeping the object-oriented nature intact.