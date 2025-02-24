-- 1. Generating a Sequence of Numbers: Generate a sequence of numbers from 1 to 10.
-- 2. Generate a Series of Dates: Generate a list of dates between two given dates (e.g., 2024-12-15 to 2025-01-10).
-- 3. Calculating Factorial: Compute the factorial of a number 5 using a recursive CTE.

-- 1. Generating a Sequence of Numbers: Generate a sequence of numbers from 1 to 10.
with recursive numbers as (
    -- anchor query
    select 1 as num 
    union all
    -- recursive query
    select num + 1
    from numbers
    where num < 10
)

select * from numbers;

-- 2. Generate a Series of Dates: Generate a list of dates between two given dates (e.g., 2024-12-15 to 2025-01-10).
with recursive date_series as (
    -- anchor query
    select '2024-12-15' :: Date as date_value
    union all 
    -- Recursive query
    select (date_value + Interval '1 Day') :: Date
    from date_series
    where date_value < '2025-01-10' :: Date
)

select date_value from date_series order by date_value desc;


-- 3. Calculating Factorial: Compute the factorial of a number 5 using a recursive CTE.
with recursive factorial_cte as (
    -- anchor query
    select 1 as n, 1 as factorial
    union all
    -- recursive query
    select n+1, factorial*(n+1)
    from factorial_cte
    where n < 5
)

select n, factorial from factorial_cte;
