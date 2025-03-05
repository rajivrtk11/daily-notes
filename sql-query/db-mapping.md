## 1. mappedby in springboot

### **Understanding `mappedBy` in Spring Boot (JPA/Hibernate)**  

`mappedBy` is used in **bidirectional** relationships to define **which entity owns the relationship**. It tells Hibernate **not to create an extra foreign key column** but to use the existing one.  

---

## **1️⃣ One-to-Many & Many-to-One (Bidirectional)**
👉 Suppose **one `Department` has many `Employees`**, and each `Employee` belongs to one `Department`.  

### **Without `mappedBy` (Unidirectional)**
This will create a **foreign key (`department_id`) in `Employee`**:  

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @OneToMany  // No mappedBy → A join table may be created
    private List<Employee> employees;
}
```

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")  // This column will store the foreign key
    private Department department;
}
```

---

### **With `mappedBy` (Bidirectional)**
👉 The `Department` is the **parent** of `Employee`, so we tell `Hibernate` that **`employees` list is mapped by the `department` field in Employee**.  

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @OneToMany(mappedBy = "department")  // "department" is the field in Employee
    private List<Employee> employees;
}
```

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")  // Foreign key column
    private Department department;
}
```
✅ **Why use `mappedBy`?**  
- Prevents an **extra join table** from being created.  
- Defines the **owner of the relationship** (`Employee` owns it).  

---

## **2️⃣ One-to-One (Bidirectional)**
👉 Suppose an `Employee` has one `Laptop`, and each `Laptop` belongs to one `Employee`.  

```java
@Entity
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    @OneToOne(mappedBy = "laptop")  // Refers to the `laptop` field in Employee
    private Employee employee;
}
```

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "laptop_id")  // Foreign key in Employee table
    private Laptop laptop;
}
```
✅ **Why use `mappedBy`?**  
- Prevents creating an **extra foreign key in `Laptop`**.  
- Defines the **relationship owner** (`Employee` owns it).  

---

## **3️⃣ Many-to-Many**
👉 Suppose an `Author` can write multiple `Books`, and a `Book` can have multiple `Authors`.  

### **Without `mappedBy` (Creates Extra Join Table)**
```java
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @ManyToMany
    private List<Book> books;
}
```

```java
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;

    @ManyToMany
    private List<Author> authors;
}
```
🚨 This creates a **join table** automatically (e.g., `author_book`).  

---

### **With `mappedBy` (Control Over Join Table)**
To **define the owner explicitly**, we use `mappedBy` in `Author`:  

```java
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @ManyToMany(mappedBy = "authors")  // Refers to "authors" in Book
    private List<Book> books;
}
```

```java
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;

    @ManyToMany
    @JoinTable(name = "author_book",  // Custom join table
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
}
```
✅ **Why use `mappedBy`?**  
- Prevents **duplicate join tables**.  
- Gives **more control over table structure**.  

---

### **📌 Summary**
| Relationship Type  | `mappedBy` Needed? | Effect |
|--------------------|-------------------|--------|
| **One-to-Many** (Bidirectional) | ✅ Yes (On `@OneToMany` side) | Prevents unnecessary join tables. |
| **One-to-One** (Bidirectional) | ✅ Yes (On non-owning side) | Avoids extra foreign key columns. |
| **Many-to-Many** (Bidirectional) | ✅ Yes (On one side) | Prevents duplicate join tables. |

---

Let me know if you need further clarification! 🚀

## 2. Cascade in springboot
## **🔹 Cascade in Spring Boot (JPA/Hibernate)**  

**Cascade** in JPA allows you to **propagate operations** (like `PERSIST`, `REMOVE`, `MERGE`, etc.) from a parent entity to its related child entities automatically.  

---

## **1️⃣ Cascade Types**
JPA provides different cascade types:

| Cascade Type  | Description |
|--------------|-------------|
| **`PERSIST`** | When the parent is saved, child entities are also saved. |
| **`MERGE`** | If the parent is updated, child entities are also updated. |
| **`REMOVE`** | If the parent is deleted, child entities are also deleted. |
| **`REFRESH`** | Refreshes child entities when the parent is refreshed. |
| **`DETACH`** | Detaches child entities when the parent is detached. |
| **`ALL`** | Includes all of the above (PERSIST, MERGE, REMOVE, REFRESH, DETACH). |

---

## **2️⃣ Example: One-to-Many with Cascade**
👉 Suppose **one `Department` has many `Employees`**, and we want to **save and delete employees when the department is saved or deleted**.  

### **Without Cascade**
```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
```
👉 Now, if we save a `Department`, we **must manually save each `Employee`**.

---

### **With `CascadeType.ALL`**
```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
```
✅ Now, if we **save a `Department`**, all `Employee` objects inside `employees` will be **automatically saved**.  
✅ If we **delete a `Department`**, all related `Employees` will also be **deleted**.

---

## **3️⃣ Example: One-to-One with Cascade**
👉 Suppose an `Employee` has **one `Laptop`**, and we want to save/delete `Laptop` along with `Employee`.  

### **Without Cascade**
```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private Laptop laptop;
}
```
👉 Here, if we save an `Employee`, we **must manually save the `Laptop`**.

---

### **With Cascade**
```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Laptop laptop;
}
```
✅ Now, saving an `Employee` will **also save the `Laptop` automatically**.  
✅ Deleting an `Employee` will **also delete the `Laptop`**.

---

## **4️⃣ Example: Many-to-Many with Cascade**
👉 Suppose **an `Author` can write many `Books`, and a `Book` can have multiple `Authors`**.  

### **With `CascadeType.PERSIST`**
```java
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Book> books;
}
```
✅ Now, when an `Author` is saved, all related `Books` will be saved too.  

---

## **5️⃣ Using Multiple Cascade Types**
You can specify multiple cascade types like this:

```java
@OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
private List<Employee> employees;
```
✅ This will **only** propagate `PERSIST` and `MERGE`, but **not DELETE**.

---

## **6️⃣ When Not to Use Cascade**
🚨 **Avoid `CascadeType.REMOVE` in Many-to-Many**  
In **Many-to-Many** relationships, `REMOVE` can **delete shared data** unexpectedly.  
❌ Example:  
```java
@ManyToMany(cascade = CascadeType.REMOVE)
```
👉 **Problem**: Deleting an `Author` will **also delete shared `Books`**.

---

## **📌 Summary: When to Use Cascade**
| Relationship  | Recommended Cascade |
|--------------|--------------------|
| **One-to-Many** | `CascadeType.ALL` (If deletion is needed) |
| **One-to-One** | `CascadeType.ALL` (If deletion is needed) |
| **Many-to-One** | Usually no cascade (manual control) |
| **Many-to-Many** | `PERSIST` & `MERGE` only (Avoid `REMOVE`) |

Let me know if you need more details! 🚀


## 3. If using mapped by then to save the data in both parent and child which side we set the entity

### **Saving Data When Using `mappedBy` in Spring Boot (JPA/Hibernate)**  

When using `mappedBy`, **the entity that owns the relationship should be set** because Hibernate manages the relationship using the foreign key in the **owning entity**.

---

## **1️⃣ One-to-Many (`@OneToMany(mappedBy = "...")`)**
### **Example: One `Department` → Many `Employees`**  
In a **bidirectional** relationship, `Employee` owns the relationship (`department` field in `Employee`).  
Thus, when saving, we **set the `Department` inside `Employee`**, not the other way around.

### **Entities**
```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();
}
```

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")  // Foreign key column
    private Department department;
}
```

### **Saving Data**
```java
Department department = new Department();
department.setName("IT");

Employee emp1 = new Employee();
emp1.setName("Alice");
emp1.setDepartment(department);  // ✅ Set the parent in the child

Employee emp2 = new Employee();
emp2.setName("Bob");
emp2.setDepartment(department);  // ✅ Set the parent in the child

List<Employee> employees = new ArrayList<>();
employees.add(emp1);
employees.add(emp2);

department.setEmployees(employees);  // Optional, used for reference

departmentRepository.save(department);  // ✅ Only save the parent, cascade will handle employees
```
✅ **Why do we set `department` in `Employee`?**  
Because **`Employee` owns the relationship** (`department_id` foreign key is in `Employee` table).  
Hibernate **only checks the owner to persist the relationship**.

---

## **2️⃣ One-to-One (`@OneToOne(mappedBy = "...")`)**
### **Example: One `Employee` → One `Laptop`**
- **`Laptop` is mapped by `employee`, so `Employee` owns the relationship.**
- We **set `Employee` inside `Laptop`**, not the other way around.

### **Entities**
```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laptop_id")  // Foreign key in Employee
    private Laptop laptop;
}
```

```java
@Entity
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    @OneToOne(mappedBy = "laptop")
    private Employee employee;
}
```

### **Saving Data**
```java
Employee employee = new Employee();
employee.setName("John");

Laptop laptop = new Laptop();
laptop.setBrand("Dell");
laptop.setEmployee(employee);  // ✅ Set the parent in the child

employee.setLaptop(laptop);  // Optional, used for reference

employeeRepository.save(employee);  // ✅ Only save Employee, cascade handles Laptop
```
✅ **Why do we set `employee` in `Laptop`?**  
Because **`Employee` owns the relationship (`laptop_id` foreign key is in `Employee` table)**.

---

## **3️⃣ Many-to-Many (`@ManyToMany(mappedBy = "...")`)**
### **Example: An `Author` can write multiple `Books`, and a `Book` can have multiple `Authors`.**
- The **side without `mappedBy` owns the relationship.**
- We **set `Books` inside `Author`**.

### **Entities**
```java
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
}
```

```java
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;

    @ManyToMany
    @JoinTable(name = "author_book",  
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();
}
```

### **Saving Data**
```java
Author author1 = new Author();
author1.setName("J.K. Rowling");

Author author2 = new Author();
author2.setName("George R.R. Martin");

Book book1 = new Book();
book1.setTitle("Fantasy Book");
book1.getAuthors().add(author1);
book1.getAuthors().add(author2);

Book book2 = new Book();
book2.setTitle("Magic Book");
book2.getAuthors().add(author1);

author1.getBooks().add(book1);  // Optional, used for reference
author1.getBooks().add(book2);
author2.getBooks().add(book1);

bookRepository.save(book1);  // ✅ Save the owner side (Book)
bookRepository.save(book2);
```
✅ **Why do we set `authors` inside `Book`?**  
Because **`Book` owns the relationship** (`author_book` join table is created by `Book`).

---

## **📌 Summary: Which Side to Set for Saving?**
| Relationship  | `mappedBy` in | **Set Which Entity?** |
|--------------|--------------|--------------------|
| **One-to-Many** | Parent (`@OneToMany`) | Set **child** (`Employee.setDepartment()`) |
| **One-to-One** | Non-owner (`@OneToOne(mappedBy)`) | Set **child** (`Laptop.setEmployee()`) |
| **Many-to-Many** | Non-owner (`@ManyToMany(mappedBy)`) | Set **owner** (`Book.setAuthors()`) |

🚀 **Golden Rule**:  
**Always set the entity that owns the relationship** (i.e., where the foreign key exists).  

Let me know if you need more details! 😊

## 3. cascade in springboot