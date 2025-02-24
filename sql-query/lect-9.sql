-- Part 1: Find the top 3 highest-paid employees in 
--               each department.
-- Part 2: Find the average salary of employees hired 
--                in the last 5 years.
-- Part 3: Find the employees whose salry is less than 
--                the average salary of employees hired in 
--                the last 5 years.

-- Query to create table:
CREATE TABLE Departments (
    DepartmentID INT PRIMARY KEY,
    DepartmentName VARCHAR(100)
);

CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    DepartmentID INT,
    Salary DECIMAL(10, 2),
    DateHired DATE,
    FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID)
);

-- Insert data
INSERT INTO Departments (DepartmentID, DepartmentName) VALUES
(1, 'HR'),
(2, 'Engineering'),
(3, 'Sales');

INSERT INTO Employees (EmployeeID, FirstName, LastName, DepartmentID, Salary, DateHired) VALUES
(1, 'Alice', 'Smith', 1, 50000, '2020-01-15'),
(2, 'Bob', 'Johnson', 1, 60000, '2018-03-22'),
(3, 'Charlie', 'Williams', 2, 70000, '2019-07-30'),
(4, 'David', 'Brown', 2, 80000, '2017-11-11'),
(5, 'Eve', 'Davis', 3, 90000, '2021-02-25'),
(6, 'Frank', 'Miller', 3, 55000, '2020-09-10'),
(7, 'Grace', 'Wilson', 2, 75000, '2016-04-05'),
(8, 'Henry', 'Moore', 1, 65000, '2022-06-17');

-- solution
-- Part 1: Find the top 3 highest-paid employees in each department.

-- step 1: Join the table
select e.EmployeeID, e.FirstName, e.LastName, e.DepartmentID, e.salary, e.DateHired,
d.DepartmentName 
from employees e
join Departments d 
on e.DepartmentID = d.DepartmentID;

-- step 2: Ranaked the employees within each department
select e.EmployeeID, e.FirstName, e.LastName, e.DepartmentID, e.salary, e.DateHired,
d.DepartmentName,
row_number() over(partition by e.DepartmentID order by salary desc) as ranking
from employees e
join Departments d 
on e.DepartmentID = d.DepartmentID;

-- step 3: Filter employees in each department to get top 3
with rankedEmployee as (
    select e.EmployeeID, e.FirstName, e.LastName, e.DepartmentID, e.salary, e.DateHired,
    d.DepartmentName,
    row_number() over(partition by e.DepartmentID order by salary desc) as ranking
    from employees e
    join Departments d 
    on e.DepartmentID = d.DepartmentID
)

select EmployeeID, FirstName, LastName, DepartmentID, salary, DateHired,
    DepartmentName from rankedEmployee
where ranking <= 3

-- Part 2: Find the average salary of employees hired in the last 5 years.
-- step 1:
select *
from employees
where DateHired >= current_date - Interval '5 Years'

-- step 2: Calculate average salary
select round(avg(salary), 3)
from employees
where DateHired >= current_date - interval '5 years'

-- Part 3: Find the employees whose salry is less than the average salary of employees hired the last 5 years.
select * from employees
where salary < (
   select round(avg(salary), 3)
    from employees
    where DateHired >= current_date - interval '5 years' 
)
