-- Classify employees into different performance levels based on both performance rating and salary: 'Top Performer' for 'Excellent' rating and salary greater than 7000, 'Good Performer' for 'Good' rating and salary  greater than 5000, and 'Others' for all other combination

-- Query to create table:
CREATE TABLE employees (
    employee_id INT PRIMARY KEY,
    name VARCHAR(50),
    performance_rating VARCHAR(10),
    salary DECIMAL(10, 2)
);

-- Insert Data
INSERT INTO employees (employee_id, name, performance_rating, salary) VALUES
(1, 'Alice', 'Excellent', 8000.00),
(2, 'Bob', 'Good', 6000.00),
(3, 'Charlie', 'Average', 4000.00),
(4, 'David', 'Excellent', 7200.00),
(5, 'Eve', 'Good', 4800.00),
(6, 'Frank', 'Poor', 3000.00),
(7, 'Grace', 'Excellent', 6500.00),
(8, 'Hank', 'Good', 5500.00),
(9, 'Ivy', 'Average', 3800.00),
(10, 'Jack', 'Excellent', 9000.00);

-- solution
select employee_id, name, performance_rating, salary
case 
    when performance_rating = 'Excellent' and salary > 7000 then 'Top performer'
    when performance_rating = 'Good' and salary > 5000 then 'Good performer'
    else 'Others'
end as performance_level
from employees;
