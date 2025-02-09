### 📌 **Views in Database (DB)**
A **view** in a database is a **virtual table** that is based on the result of a SQL query. Unlike a physical table, a view **does not store data**; instead, it dynamically fetches data from one or more tables.

---

## 🛠 **Why Use Views?**
✔ **Simplifies complex queries** – Store complex joins and aggregations in a view for easy reuse.  
✔ **Security** – Restrict access to sensitive columns while allowing access to specific data.  
✔ **Encapsulation** – Hide the underlying table structure from users.  
✔ **Data consistency** – Ensure a single source of truth across multiple queries.

---

## 🔹 **1️⃣ Creating a Simple View**
```sql
CREATE VIEW EmployeeView AS
SELECT id, name, department, salary
FROM Employees
WHERE salary > 50000;
```
**📝 Explanation:**  
- `EmployeeView` fetches only employees with a salary greater than `50000`.  
- Now, you can use it like a table:  
  ```sql
  SELECT * FROM EmployeeView;
  ```

---

## 🔹 **2️⃣ Creating a View with Joins**
```sql
CREATE VIEW OrderDetailsView AS
SELECT o.id AS OrderID, c.name AS CustomerName, o.total_amount, o.order_date
FROM Orders o
JOIN Customers c ON o.customer_id = c.id;
```
**📝 Explanation:**  
- This view combines `Orders` and `Customers`, showing customer details along with order data.  
- Now, you can query it like:  
  ```sql
  SELECT * FROM OrderDetailsView WHERE total_amount > 1000;
  ```

---

## 🔹 **3️⃣ Updating a View**
You can **modify** a view using `CREATE OR REPLACE VIEW`:
```sql
CREATE OR REPLACE VIEW EmployeeView AS
SELECT id, name, department
FROM Employees
WHERE department = 'IT';
```
🔹 **Advantage:** No need to drop and recreate the view.

---

## 🔹 **4️⃣ Dropping a View**
```sql
DROP VIEW EmployeeView;
```
This removes the view without affecting the original tables.

---

## 🔹 **5️⃣ Can You Insert, Update, or Delete Data in a View?**
✅ **YES** – If the view is based on a **single table** without aggregations or joins:
```sql
UPDATE EmployeeView SET salary = 60000 WHERE id = 1;
```
❌ **NO** – If the view contains **aggregations, joins, or DISTINCT**.

🔹 **Solution:** Use **INSTEAD OF triggers** to make an updatable view.

---

## 🔥 **Materialized Views (Performance Boost)**
A **Materialized View** stores the query result **physically** in the database, unlike a normal view.

```sql
CREATE MATERIALIZED VIEW SalesSummary AS
SELECT department, SUM(sales) AS total_sales
FROM Sales
GROUP BY department;
```
🔹 **Pros:** Faster queries (good for analytics).  
🔹 **Cons:** Needs to be refreshed manually or on schedule.  

```sql
REFRESH MATERIALIZED VIEW SalesSummary;
```

---

## 🏆 **Which One to Use?**
| View Type | Best For |
|-----------|---------|
| **Regular View** | Simplifying queries, security, virtual tables |
| **Materialized View** | Faster performance, analytics, reporting |

Would you like an example specific to MySQL, PostgreSQL, or another DB? 🚀