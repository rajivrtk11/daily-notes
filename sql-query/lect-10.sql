-- Transform Rows Into Columns: 
-- Find monthly sale in different category
-- j -> clothing -> electronics
-- f -> clothing -> electronics

-- Query to create table:
CREATE TABLE sales_data (
    month varchar(10),
    category varchar(20),
    amount numeric
);

-- Insert data
INSERT INTO sales_data (month, category, amount) VALUES
    ('January', 'Electronics', 1500),
    ('January', 'Clothing', 1200),
    ('February', 'Electronics', 1800),
    ('February', 'Clothing', 1300),
    ('March', 'Electronics', 1600),
    ('March', 'Clothing', 1100),
    ('April', 'Electronics', 1700),
    ('April', 'Clothing', 1400);

SELECT 
    product,
    SUM(CASE WHEN month = 'January' THEN amount ELSE 0 END) AS Jan,
    SUM(CASE WHEN month = 'February' THEN sales ELSE 0 END) AS Feb
FROM sales_data
GROUP BY month, category;
