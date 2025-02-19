### 🔗 **SQL Joins Explained (with Examples)**  
SQL **joins** are used to combine data from multiple tables based on a related column.

---

## 🛠 **1️⃣ Types of Joins**
| **Join Type**  | **Description**  | **Returns**  |
|--------------|---------------|-------------|
| **INNER JOIN**  | Matches rows with common values in both tables  | Only matching rows  |
| **LEFT JOIN (LEFT OUTER JOIN)**  | Returns all rows from the left table and matching rows from the right table  | Unmatched right-side values are `NULL`  |
| **RIGHT JOIN (RIGHT OUTER JOIN)**  | Returns all rows from the right table and matching rows from the left table  | Unmatched left-side values are `NULL`  |
| **FULL JOIN (FULL OUTER JOIN)**  | Returns all rows when there's a match in either table  | Missing values are `NULL`  |
| **CROSS JOIN**  | Returns the **cartesian product** of both tables  | All possible row combinations  |
| **SELF JOIN**  | Joins a table with itself  | Used for hierarchical data (e.g., employee-manager)  |

---

## 🔹 **2️⃣ INNER JOIN (Only Matching Rows)**
```sql
SELECT e.id, e.name, d.department_name
FROM Employees e
INNER JOIN Departments d ON e.department_id = d.id;
```
📌 **Only employees with a matching `department_id` in `Departments` are returned.**

---

## 🔹 **3️⃣ LEFT JOIN (All Left Table Rows, Even If No Match)**
```sql
SELECT e.id, e.name, d.department_name
FROM Employees e
LEFT JOIN Departments d ON e.department_id = d.id;
```
📌 **Returns all employees. If an employee has no department, `department_name` is `NULL`.**

---

## 🔹 **4️⃣ RIGHT JOIN (All Right Table Rows, Even If No Match)**
```sql
SELECT e.id, e.name, d.department_name
FROM Employees e
RIGHT JOIN Departments d ON e.department_id = d.id;
```
📌 **Returns all departments. If no employee is assigned, `name` is `NULL`.**

---

## 🔹 **5️⃣ FULL OUTER JOIN (All Rows from Both Tables)**
```sql
SELECT e.id, e.name, d.department_name
FROM Employees e
FULL OUTER JOIN Departments d ON e.department_id = d.id;
```
📌 **Returns all employees and all departments, even if they don’t have matches.**

---

## 🔹 **6️⃣ CROSS JOIN (All Possible Combinations)**
```sql
SELECT e.name, d.department_name
FROM Employees e
CROSS JOIN Departments d;
```
📌 **Creates a combination of every employee with every department (Cartesian product).**

---

## 🔹 **7️⃣ SELF JOIN (Table Joins Itself)**
```sql
SELECT e1.name AS Employee, e2.name AS Manager
FROM Employees e1
JOIN Employees e2 ON e1.manager_id = e2.id;
```
📌 **Used when a table references itself (e.g., employee-manager relationships).**

---

## 🏆 **Which Join to Use?**
| **Use Case** | **Best Join Type** |
|-------------|-----------------|
| Find only matching records | INNER JOIN |
| Get all records from the left table + matching right table records | LEFT JOIN |
| Get all records from the right table + matching left table records | RIGHT JOIN |
| Get all records from both tables | FULL OUTER JOIN |
| Generate all possible row combinations | CROSS JOIN |
| Query hierarchical data (e.g., employees & managers) | SELF JOIN |

Would you like a real-world example (e.g., e-commerce orders, employees, etc.)? 🚀