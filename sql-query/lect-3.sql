-- 1. Retrieve the total revenue generated from each product.
-- 2. Find products that have not been ordered at all.

-- Create Products table
CREATE TABLE Products (
    ProductID INT PRIMARY KEY,
    ProductName VARCHAR(100),
    ProductPrice DECIMAL(10, 2)
);

-- Create Orders table
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY,
    ProductID INT,
    Quantity INT,
    OrderDate DATE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Insert records into Products table
INSERT INTO Products (ProductID, ProductName, ProductPrice) VALUES
(1, 'Product A', 10.00),
(2, 'Product B', 15.00),
(3, 'Product C', 20.00),
(4, 'Product D', 25.00),
(5, 'Product E', 30.00),
(6, 'Product F', 35.00),
(7, 'Product G', 40.00),
(8, 'Product H', 45.00),
(9, 'Product I', 50.00),
(10, 'Product J', 55.00);

-- Insert records into Orders table
INSERT INTO Orders (OrderID, ProductID, Quantity, OrderDate) VALUES
(1, 1, 2, '2023-07-01'),
(2, 2, 1, '2023-07-02'),
(3, 3, 5, '2023-07-03'),
(4, 1, 3, '2023-07-04'),
(5, 5, 2, '2023-07-05'),
(6, 6, 1, '2023-07-06'),
(7, 2, 4, '2023-07-07'),
(8, 8, 3, '2023-07-08'),
(9, 9, 1, '2023-07-09'),
(10, 10, 2, '2023-07-10');