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

-- solution
-- method-1
-- Enable tablefunc extension (only needs to be done once)
CREATE EXTENSION IF NOT EXISTS tablefunc;

SELECT * FROM crosstab(
    'SELECT month, category, amount FROM sales_data ORDER BY month, category'
) AS ct (month VARCHAR, clothing NUMERIC, electronics NUMERIC);

-- method-2
SELECT 
    month,
    SUM(amount) FILTER (WHERE category = 'Clothing') AS clothing,
    SUM(amount) FILTER (WHERE category = 'Electronics') AS electronics,
	sum(amount) as total
FROM sales_data
GROUP BY month
ORDER BY month;

