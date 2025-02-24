-- Find out the Studentwise Total Marks for Top 2 Subjects?

-- 1. Create the student table
CREATE TABLE students (
    student_name VARCHAR(50),
    subject VARCHAR(50),
    marks INT
);

-- 2. Insert records into the student table
INSERT INTO students (student_name, subject, marks) VALUES
('Alice', 'Math', 65),
('Alice', 'Science', 80),
('Alice', 'English', 78),
('Bob', 'Math', 82),
('Bob', 'Science', 85),
('Bob', 'English', 88),
('Catherine', 'Math', 70),
('Catherine', 'Science', 72),
('Catherine', 'English', 68),
('Daniel', 'Math', 99);


-- query by rank or rank number 
select student_name, marks,
rank() over(partition by student_name order by marks desc) 
as student_rank from students;

-- feed above query to fetch data 
select student_name, sum(marks) 
from (
	select student_name, marks ,
	rank() over(partition by student_name order by marks desc) 
	as student_rank from students
) as st
where student_rank <= 2
group by student_name