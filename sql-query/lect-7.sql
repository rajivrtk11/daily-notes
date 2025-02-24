-- Given a table orders with columns order_id (sequential integers), order_date, write a query to identify the missing order IDs.

-- Query to create table:
CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    order_date DATE
);

-- Insert Data
INSERT INTO orders (order_id, order_date) VALUES
(1, '2024-07-01'),
(2, '2024-07-02'),
(3, '2024-07-03'),
(5, '2024-07-05'),
(6, '2024-07-06'),
(7, '2024-07-07'),
(9, '2024-07-09'),
(10, '2024-07-10'),
(11, '2024-07-11'),
(13, '2024-07-13'),
(14, '2024-07-14'),
(15, '2024-07-15'),
(16, '2024-07-16'),
(18, '2024-07-18'),
(19, '2024-07-19'),
(20, '2024-07-20');

-- solution
with sequence as (
	select generate_series(min(order_id), max(order_id)) as order_id
from orders
)

select *
from sequence s
left join orders o 
on s.order_id = o.order_id
where o.order_id is null
