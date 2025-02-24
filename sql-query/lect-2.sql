-- 1. List all authors and number of books written
-- 2. Find books published in the last year and corresponding authors

-- Create Authors table
CREATE TABLE Authors (
    AuthorID INT PRIMARY KEY,
    AuthorName VARCHAR(100)
);

-- Create Books table
CREATE TABLE Books (
    BookID INT PRIMARY KEY,
    AuthorID INT,
    BookTitle VARCHAR(200),
    PublicationYear DATE,
    FOREIGN KEY (AuthorID) REFERENCES Authors(AuthorID)
);

-- Insert records into Authors table
INSERT INTO Authors (AuthorID, AuthorName) VALUES
(1, 'Author A'),
(2, 'Author B'),
(3, 'Author C'),
(4, 'Author D'),
(5, 'Author E');

-- Insert records into Books table
INSERT INTO Books (BookID, AuthorID, BookTitle, PublicationYear) VALUES
(1, 1, 'Book 1 by Author A', '2023-01-15'),
(2, 1, 'Book 2 by Author A', '2023-07-20'),
(3, 2, 'Book 1 by Author B', '2022-03-10'),
(4, 2, 'Book 2 by Author B', '2023-05-05'),
(5, 3, 'Book 1 by Author C', '2023-06-25'),
(6, 3, 'Book 2 by Author C', '2021-08-15'),
(7, 4, 'Book 1 by Author D', '2022-09-30'),
(8, 4, 'Book 2 by Author D', '2023-12-05'),
(9, 5, 'Book 1 by Author E', '2023-04-17'),
(10, 5, 'Book 2 by Author E', '2020-11-01');

-- 1. List all authors and number of books written
select a.AuthorName,
count(b.BookID) as numberOfBooks
from authors a
left join Books b  -- doing left join so that if author has not written book still it comes
on a.AuthorID = b.AuthorID group by a.AuthorName;

-- 2. Find books published in the last year and corresponding authors
select 
from books b 
join authors a 
on b.AuthorID = b.AuthorID
where extract(year from b.PublicationYear) = extract(year from current_date)-1