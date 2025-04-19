Sure! Let's walk through a complete example of a **multi-join SQL query**. This includes:

- Creating tables
- Inserting sample data
- Performing a **multi-join** (including `LEFT JOIN`, `INNER JOIN`, etc.)

---

### 🧱 Scenario

We have 3 tables:
- `users`
- `orders`
- `payments`

A user can have multiple orders, and an order can have one payment.

---

### ✅ 1. Create Tables

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    product_name VARCHAR(100)
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(id),
    amount DECIMAL(10, 2)
);
```

---

### ✅ 2. Insert Sample Data

```sql
-- Users
INSERT INTO users (name) VALUES ('Alice'), ('Bob'), ('Charlie');

-- Orders
INSERT INTO orders (user_id, product_name) VALUES
(1, 'Laptop'),
(1, 'Phone'),
(2, 'Tablet');

-- Payments
INSERT INTO payments (order_id, amount) VALUES
(1, 1200.00),
(3, 400.00);
```

---

### ✅ 3. Multi-Join Query (Left Joins)

Get:
- user name
- order id & product
- payment amount (may be null)

```sql
SELECT 
    u.name AS user_name,
    o.id AS order_id,
    o.product_name,
    p.amount AS payment_amount
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
LEFT JOIN payments p ON o.id = p.order_id;
```

---

### 🔍 Output Explanation

| user_name | order_id | product_name | payment_amount |
|-----------|----------|--------------|----------------|
| Alice     | 1        | Laptop       | 1200.00        |
| Alice     | 2        | Phone        | NULL           |
| Bob       | 3        | Tablet       | 400.00         |
| Charlie   | NULL     | NULL         | NULL           |

- `LEFT JOIN` ensures all users show up — even if they don’t have orders or payments.

---

### ✅ Bonus: INNER JOIN version (only matching rows)

```sql
SELECT 
    u.name,
    o.product_name,
    p.amount
FROM users u
JOIN orders o ON u.id = o.user_id
JOIN payments p ON o.id = p.order_id;
```

Only shows orders **with payments**.

---

Would you like a Spring Boot JPA version of this using entities and `@Query` as well?