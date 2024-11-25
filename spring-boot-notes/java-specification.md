# **1 Question. -> Criteria query method and criteria builder methods ?**


In JPA, the **Criteria API** provides a type-safe and programmatic way to create dynamic queries. This approach is particularly useful when building queries dynamically based on user input or complex logic. Here’s an overview of the commonly used **`CriteriaQuery`** and **`CriteriaBuilder`** methods with examples:

---

### **1. Key Classes**
- **`CriteriaBuilder`**: Used to create and configure query objects and predicates.
- **`CriteriaQuery`**: Represents a query object that defines the structure of the query.
- **`Root`**: Defines the query's FROM clause and is used to navigate entity attributes.
- **`Predicate`**: Represents WHERE conditions.

---

### **2. `CriteriaBuilder` Methods**

| **Method**                | **Purpose**                                                                                           |
|---------------------------|-------------------------------------------------------------------------------------------------------|
| `createQuery(Class<T>)`   | Creates a `CriteriaQuery` object for the specified result type.                                       |
| `equal(Expression, value)`| Creates an equality predicate.                                                                        |
| `notEqual(Expression, value)`| Creates a predicate for inequality.                                                               |
| `like(Expression<String>, String)`| Creates a predicate for pattern matching (SQL `LIKE`).                                        |
| `notLike(Expression<String>, String)`| Creates a predicate for NOT LIKE.                                                        |
| `gt(Expression<Number>, Number)` | Greater-than comparison for numbers.                                                          |
| `lt(Expression<Number>, Number)` | Less-than comparison for numbers.                                                             |
| `ge(Expression<Number>, Number)` | Greater-than-or-equal-to comparison for numbers.                                              |
| `le(Expression<Number>, Number)` | Less-than-or-equal-to comparison for numbers.                                                 |
| `and(Predicate...)`        | Combines multiple predicates with a logical AND.                                                    |
| `or(Predicate...)`         | Combines multiple predicates with a logical OR.                                                     |
| `isNull(Expression)`       | Creates a predicate for checking null values.                                                       |
| `isNotNull(Expression)`    | Creates a predicate for checking non-null values.                                                   |
| `between(Expression, low, high)`| Checks if a value falls within a range.                                                        |
| `sum(Expression...)`       | Performs addition.                                                                                  |
| `avg(Expression<Number>)`  | Computes the average of a numeric expression.                                                      |
| `max(Expression<Number>)`  | Finds the maximum value of a numeric expression.                                                    |
| `min(Expression<Number>)`  | Finds the minimum value of a numeric expression.                                                    |

---

### **3. `CriteriaQuery` Methods**

| **Method**               | **Purpose**                                                                                           |
|--------------------------|-------------------------------------------------------------------------------------------------------|
| `select(Selection)`      | Defines the SELECT clause.                                                                            |
| `multiselect(Selection...)`| Specifies multiple selection items.                                                                |
| `where(Predicate...)`    | Defines the WHERE clause.                                                                             |
| `groupBy(Expression...)` | Defines the GROUP BY clause.                                                                          |
| `having(Predicate)`      | Defines the HAVING clause.                                                                            |
| `orderBy(Order...)`      | Specifies the ORDER BY clause.                                                                        |
| `distinct(boolean)`      | Marks the query results as distinct.                                                                  |

---

### **4. Example Usage**

#### **Basic Query: Fetch All Records**
```java
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<Book> query = cb.createQuery(Book.class);
Root<Book> root = query.from(Book.class);

query.select(root);

List<Book> results = entityManager.createQuery(query).getResultList();
```

#### **Filter Records with WHERE Clause**
```java
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<Book> query = cb.createQuery(Book.class);
Root<Book> root = query.from(Book.class);

Predicate authorPredicate = cb.equal(root.get("author"), "J.K. Rowling");
Predicate yearPredicate = cb.gt(root.get("year"), 2000);

query.select(root).where(cb.and(authorPredicate, yearPredicate));

List<Book> results = entityManager.createQuery(query).getResultList();
```

#### **Dynamic Query with LIKE and OR**
```java
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<Book> query = cb.createQuery(Book.class);
Root<Book> root = query.from(Book.class);

Predicate titlePredicate = cb.like(root.get("title"), "%Harry%");
Predicate authorPredicate = cb.like(root.get("author"), "%Rowling%");

query.select(root).where(cb.or(titlePredicate, authorPredicate));

List<Book> results = entityManager.createQuery(query).getResultList();
```

#### **Grouping and Aggregation**
```java
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
Root<Book> root = query.from(Book.class);

query.multiselect(root.get("author"), cb.count(root))
     .groupBy(root.get("author"))
     .having(cb.gt(cb.count(root), 5));

List<Object[]> results = entityManager.createQuery(query).getResultList();
for (Object[] row : results) {
    System.out.println("Author: " + row[0] + ", Count: " + row[1]);
}
```

#### **Sorting with ORDER BY**
```java
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<Book> query = cb.createQuery(Book.class);
Root<Book> root = query.from(Book.class);

query.select(root).orderBy(cb.asc(root.get("year")));

List<Book> results = entityManager.createQuery(query).getResultList();
```

---

### **5. Advantages of Criteria API**
- **Type-Safe**: Errors are caught during compilation.
- **Dynamic Queries**: Easily build queries dynamically based on conditions.
- **Refactoring-Friendly**: Changes to entity fields are reflected automatically.

This approach is powerful when dealing with complex, dynamic queries in JPA!


# **2. Criteria query method and criteria builder methods in spring data jpa ?**


In **Spring Data JPA**, the **Criteria API** is often used to build dynamic queries when the predefined query methods or the **`@Query`** annotation do not suffice. Spring Data JPA abstracts the `CriteriaBuilder` and `CriteriaQuery` through the **`Specification`** interface, making it easier to work with.

Here’s how you can use the Criteria API in Spring Data JPA:

---

### **1. Defining a Specification**

The `Specification` interface is provided by Spring Data JPA and uses the Criteria API under the hood. It is defined as:
```java
public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
```

---

### **2. Key Classes**

- **`Specification`**: Encapsulates the query logic.
- **`Root`**: Represents the root entity in the query.
- **`CriteriaQuery`**: Represents the query object.
- **`CriteriaBuilder`**: Used to construct predicates and other query elements.

---

### **3. Example Use Cases**

#### **Single Specification**
Define a simple filter for an entity, such as fetching books by author:

```java
public class BookSpecifications {
    public static Specification<Book> hasAuthor(String author) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> 
            cb.equal(root.get("author"), author);
    }
}
```

#### **Combining Specifications**
You can combine multiple specifications using **`and`** or **`or`**:

```java
public class BookSpecifications {
    public static Specification<Book> hasAuthor(String author) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> 
            cb.equal(root.get("author"), author);
    }

    public static Specification<Book> hasTitle(String title) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> 
            cb.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Book> publishedAfter(int year) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> 
            cb.greaterThan(root.get("year"), year);
    }
}
```

---

### **4. Repository Integration**

Your repository needs to extend the **`JpaSpecificationExecutor`** interface:
```java
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
```

---

### **5. Example Usage**

#### **Filter by Single Condition**
```java
List<Book> booksByAuthor = bookRepository.findAll(BookSpecifications.hasAuthor("J.K. Rowling"));
```

#### **Filter by Multiple Conditions**
Combine multiple specifications dynamically:
```java
Specification<Book> spec = Specification
    .where(BookSpecifications.hasAuthor("J.K. Rowling"))
    .and(BookSpecifications.publishedAfter(2000));

List<Book> books = bookRepository.findAll(spec);
```

#### **Dynamic Query Based on User Input**
```java
public List<Book> searchBooks(String author, String title, Integer year) {
    Specification<Book> spec = Specification.where(null);

    if (author != null) {
        spec = spec.and(BookSpecifications.hasAuthor(author));
    }
    if (title != null) {
        spec = spec.and(BookSpecifications.hasTitle(title));
    }
    if (year != null) {
        spec = spec.and(BookSpecifications.publishedAfter(year));
    }

    return bookRepository.findAll(spec);
}
```

---

### **6. Sorting and Paging with Specifications**

Spring Data JPA allows you to combine **Specifications** with **`Sort`** and **`Pageable`**:
```java
Page<Book> books = bookRepository.findAll(
    Specification.where(BookSpecifications.hasAuthor("J.K. Rowling")),
    PageRequest.of(0, 10, Sort.by("year").descending())
);
```

---

### **7. Custom Specification Example: Aggregation and Grouping**

If you need advanced functionality like aggregation, you can manipulate the `CriteriaQuery` in the `Specification`:
```java
public static Specification<Book> groupByAuthor() {
    return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        query.groupBy(root.get("author"));
        query.multiselect(root.get("author"), cb.count(root));
        return null;
    };
}
```

---

### **8. Advantages in Spring Data JPA**

- **Dynamic Query Building**: Easily combine specifications based on runtime conditions.
- **Integration with Repository**: Directly fetch results using the repository layer.
- **Readable Code**: Separates query logic into reusable specifications.

This approach ensures flexibility and reusability in query logic, while leveraging the powerful Criteria API through a Spring Data JPA-friendly abstraction.


# **3. Join using specification ?**


In Spring Data JPA, you can use the **`Criteria API`** within a **`Specification`** to create dynamic queries that involve joins. Here's how to perform **joins** using Spring Data JPA's `Specification` interface:

---

### **1. Understanding Joins in Criteria API**
When dealing with joins:
- **`Root`** represents the root entity of the query.
- You can call the **`join()`** method on the `Root` to perform the join operation.

---

### **2. Basic Join Example**

#### **Scenario**
Suppose you have two entities:
- **`Book`**: Represents books with fields like `title` and a relationship `author`.
- **`Author`**: Represents authors with fields like `name`.

```java
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Author author;
}

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```

---

### **3. Specification for Join Query**

#### **Join to Fetch Books by Author Name**
Here’s how to join the `Author` table to filter books by the author's name:

```java
public class BookSpecifications {
    public static Specification<Book> hasAuthorName(String authorName) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Join<Book, Author> authorJoin = root.join("author"); // Perform the join
            return cb.equal(authorJoin.get("name"), authorName); // Add condition on joined entity
        };
    }
}
```

---

### **4. Combining Joins with Other Filters**

#### **Scenario**
Filter books by:
- **Title**.
- **Author Name**.

```java
public class BookSpecifications {
    public static Specification<Book> hasTitle(String title) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> 
            cb.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Book> hasAuthorName(String authorName) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Join<Book, Author> authorJoin = root.join("author"); // Perform the join
            return cb.equal(authorJoin.get("name"), authorName); // Add condition
        };
    }
}
```

Combine the specifications dynamically:

```java
Specification<Book> spec = Specification
    .where(BookSpecifications.hasTitle("Spring"))
    .and(BookSpecifications.hasAuthorName("John Doe"));

List<Book> books = bookRepository.findAll(spec);
```

---

### **5. Fetching Data with Join**

If you want to ensure that the joined data is eagerly fetched, use the **`fetch()`** method. Note that `fetch` can only be used in select queries.

```java
public static Specification<Book> fetchAuthor() {
    return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        root.fetch("author", JoinType.INNER); // Fetch the joined entity
        return cb.conjunction(); // No condition, just fetching
    };
}
```

Usage:
```java
Specification<Book> spec = Specification
    .where(BookSpecifications.fetchAuthor())
    .and(BookSpecifications.hasTitle("Spring"));
```

---

### **6. Adding Pagination and Sorting**

You can combine joins with pagination and sorting:

```java
Page<Book> books = bookRepository.findAll(
    Specification.where(BookSpecifications.hasAuthorName("John Doe")),
    PageRequest.of(0, 10, Sort.by("title").ascending())
);
```

---

### **7. Handling Multiple Joins**

For scenarios involving multiple joins, simply call `join()` or `fetch()` on the relevant entities:

```java
public static Specification<Book> hasPublisherName(String publisherName) {
    return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        Join<Book, Publisher> publisherJoin = root.join("publisher"); // Join publisher
        return cb.equal(publisherJoin.get("name"), publisherName); // Filter by publisher name
    };
}

public static Specification<Book> hasAuthorAndPublisher(String authorName, String publisherName) {
    return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        Join<Book, Author> authorJoin = root.join("author");
        Join<Book, Publisher> publisherJoin = root.join("publisher");
        
        Predicate authorPredicate = cb.equal(authorJoin.get("name"), authorName);
        Predicate publisherPredicate = cb.equal(publisherJoin.get("name"), publisherName);

        return cb.and(authorPredicate, publisherPredicate);
    };
}
```

---

### **8. Repository Setup**

Ensure your repository extends `JpaSpecificationExecutor` to support dynamic queries:

```java
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
```

---

### **9. Practical Usage**

Dynamic filtering with multiple joins:
```java
Specification<Book> spec = Specification
    .where(BookSpecifications.hasAuthorName("Jane Austen"))
    .and(BookSpecifications.hasPublisherName("Penguin"));

List<Book> books = bookRepository.findAll(spec);
```

---

### **10. Advantages of Using Join with Specifications**

- **Dynamic Queries**: Combine joins dynamically based on user input or conditions.
- **Code Reusability**: Encapsulate query logic into reusable specifications.
- **Pagination and Sorting**: Easily integrate with Spring Data JPA's paging and sorting features.

This approach allows you to build complex queries while keeping the code modular and readable.


# **4. Give cb, cq and root methods with description. and example of each method how they are used  ?**


Here’s a comprehensive explanation of **`CriteriaBuilder` (cb)**, **`CriteriaQuery` (cq)**, and **`Root` (root)** methods in Spring Data JPA, with practical examples for each method.

---

### **1. `CriteriaBuilder` (cb)**

`CriteriaBuilder` is used to create various query expressions, predicates, and aggregation functions.

#### Methods and Examples

| Method                              | Description                                                                                                 | Example                                                                                                                      |
|-------------------------------------|-------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| `and(Predicate... restrictions)`    | Combines multiple predicates using logical AND.                                                            | `cb.and(cb.equal(root.get("name"), "John"), cb.greaterThan(root.get("age"), 18))`                                            |
| `or(Predicate... restrictions)`     | Combines multiple predicates using logical OR.                                                             | `cb.or(cb.equal(root.get("status"), "ACTIVE"), cb.equal(root.get("status"), "PENDING"))`                                     |
| `equal(Expression<?> x, Object y)`  | Tests if an expression equals a specific value.                                                            | `cb.equal(root.get("id"), 100)`                                                                                              |
| `notEqual(Expression<?> x, Object y)`| Tests if an expression does not equal a specific value.                                                    | `cb.notEqual(root.get("name"), "John")`                                                                                      |
| `like(Expression<String> x, String pattern)` | Tests if a string matches a pattern (supports `%` and `_` wildcards).                                      | `cb.like(root.get("email"), "%@example.com")`                                                                                |
| `greaterThan(Expression<? extends Comparable> x, Expression<? extends Comparable> y)` | Tests if one value is greater than another.                                | `cb.greaterThan(root.get("age"), 30)`                                                                                       |
| `lessThan(Expression<? extends Comparable> x, Expression<? extends Comparable> y)`   | Tests if one value is less than another.                                                                   | `cb.lessThan(root.get("price"), 1000)`                                                                                      |
| `isNull(Expression<?> x)`            | Checks if a value is `NULL`.                                                                                | `cb.isNull(root.get("deletedAt"))`                                                                                           |
| `isNotNull(Expression<?> x)`         | Checks if a value is not `NULL`.                                                                            | `cb.isNotNull(root.get("createdAt"))`                                                                                        |

---

#### **Example**: Filter by multiple conditions
Find all users who are active, over 18, and whose email ends with `example.com`.

```java
public Specification<User> findActiveUsersOver18() {
    return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        Predicate active = cb.equal(root.get("status"), "ACTIVE");
        Predicate over18 = cb.greaterThan(root.get("age"), 18);
        Predicate emailDomain = cb.like(root.get("email"), "%@example.com");

        return cb.and(active, over18, emailDomain);
    };
}
```

---

### **2. `CriteriaQuery` (cq)**

`CriteriaQuery` defines the structure of the query, including `select`, `groupBy`, `orderBy`, and `where` clauses.

#### Methods and Examples

| Method                              | Description                                                                                                 | Example                                                                                                                      |
|-------------------------------------|-------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| `select(Selection<?> selection)`    | Specifies what to fetch in the query result.                                                               | `cq.select(root.get("name"))`                                                                                                 |
| `distinct(boolean distinct)`        | Makes the query results distinct.                                                                          | `cq.distinct(true)`                                                                                                          |
| `where(Predicate... restrictions)`  | Adds filtering conditions.                                                                                 | `cq.where(cb.equal(root.get("status"), "ACTIVE"))`                                                                           |
| `groupBy(Expression<?>... grouping)`| Groups the results by one or more fields.                                                                  | `cq.groupBy(root.get("department"))`                                                                                        |
| `having(Predicate... restrictions)` | Adds conditions for grouped results.                                                                       | `cq.having(cb.greaterThan(cb.count(root.get("id")), 10))`                                                                    |
| `orderBy(Order... orders)`          | Specifies the sorting order.                                                                               | `cq.orderBy(cb.asc(root.get("name")))`                                                                                       |

---

#### **Example**: Find active users and order by their names
```java
public Specification<User> findActiveUsersSortedByName() {
    return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        query.distinct(true); // Ensure distinct results
        query.orderBy(cb.asc(root.get("name"))); // Sort by name
        return cb.equal(root.get("status"), "ACTIVE"); // Where clause
    };
}
```

---

### **3. `Root` (root)**

`Root` represents the main entity in the query. It allows access to the entity's attributes and relationships.

#### Methods and Examples

| Method                              | Description                                                                                                 | Example                                                                                                                      |
|-------------------------------------|-------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| `get(String attributeName)`         | Retrieves the value of a specific attribute.                                                               | `root.get("name")`                                                                                                          |
| `join(String associationName)`      | Creates an inner join with the specified related entity.                                                    | `root.join("orders")`                                                                                                       |
| `join(String associationName, JoinType joinType)` | Creates a join (inner, left, or right) with a related entity.                              | `root.join("orders", JoinType.LEFT)`                                                                                        |
| `fetch(String associationName)`     | Fetches a related entity eagerly.                                                                           | `root.fetch("orders")`                                                                                                      |
| `fetch(String associationName, JoinType joinType)` | Fetches a related entity with a specified join type.                                      | `root.fetch("orders", JoinType.INNER)`                                                                                      |

---

#### **Example**: Fetch users with their orders
```java
public Specification<User> findUsersWithOrders() {
    return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        root.fetch("orders", JoinType.LEFT); // Fetch orders lazily
        return cb.isNotNull(root.get("id")); // Dummy predicate to fetch all users
    };
}
```

---

### **Putting it All Together**

#### Complete Example
Find users whose age is greater than 25, fetch their orders, group by status, and order by name.

```java
public Specification<User> complexQuery() {
    return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        // Fetch orders
        root.fetch("orders", JoinType.LEFT);

        // Filtering condition
        Predicate agePredicate = cb.greaterThan(root.get("age"), 25);

        // Group by status
        query.groupBy(root.get("status"));

        // Order by name
        query.orderBy(cb.asc(root.get("name")));

        return agePredicate;
    };
}
```

This allows for building flexible and complex queries dynamically using Spring Data JPA.