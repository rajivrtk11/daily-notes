-- This SQL script creates two tables: courses and students.
-- It inserts some sample data into both tables and then retrieves a list of courses along with the names of students enrolled in each course.
-- The result will show each course with a comma-separated list of student names.


-- Courses table
CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

-- Students table with course_id as a foreign key
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    course_id INT REFERENCES courses(id)
);


-- Insert courses
INSERT INTO courses (name) VALUES 
('Math'),
('Science'),
('History');

-- Insert students with course_id
INSERT INTO students (name, course_id) VALUES
('Alice', 1),    -- Math
('Bob', 1),      -- Math
('Charlie', 2),  -- Science
('David', 2),    -- Science
('Eve', 3);      -- History


SELECT 
    c.id AS course_id,
    c.name AS course_name,
    STRING_AGG(s.name, ', ') AS student_names
FROM 
    courses c
JOIN 
    students s ON s.course_id = c.id
GROUP BY 
    c.id, c.name
ORDER BY 
    c.id;

