-- 1. How to find the highest salary?
-- 2. How to find 2nd highest salary?
-- 2. How to find nth highest salary using a Sub-Query?
-- 3. How to find nth highest salary in using a CTE?
-- 4. How to find the 2nd, 3rd or 15th highest salary?

-- Create Employees Table
Create table Employees 
(
  ID int primary key,
  FirstName varchar (50),
  LastName varchar (50),
  Gender varchar (50) ,
  Salary int
);

-- Inser values into employees table

insert into Employees values(1, 'Tony', 'Stark', 'Male', 80000),
       (2, 'Steve', 'Rogers', 'Male', 70000) ,
    (3, 'Thor', 'Odinson', 'Male', 65000), 
    (4, 'Nick', 'Fury', 'Male', 75000), 
    (5, 'Bruce', 'Banner', 'Male', 50000), 
    (6, 'Natasha', 'Ramanoff', 'Female', 65000),
    (7, 'Jane', 'Foster', 'Female', 45000),
    (8, 'Peter', 'Parker', 'Male', 80000);

-- Solution
-- 2. How to find nth highest salary using a Sub-Query?
select salary
from
(
    select distinct salary
    from Employees
    order by salary desc
) subquery
order by salary desc
limit 1
offset 1

SELECT distinct salary 
FROM public.employees 
ORDER BY salary DESC 
LIMIT 1 OFFSET 2;

-- 3. How to find nth highest salary in using a CTE?
with cte as (
    select salary,
    dense_rank() over (order by salary desc) as denserank,
    -- rank() over (order by salary desc) as rank
    from Employees
)
select salary
from cte
where denserank = n;