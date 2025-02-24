-- Extract the domain name from email column in employee table.

create Table employee (
    id int primary key,
    name varchar(50),
    email varchar(100)
);

insert into employee (id, name, email)
values 
(1, 'John Doe', 'john.doe@example.com'),
(2, 'Jane Smith', 'jane.smith@company.com'),
(3, 'Alice Johnson', 'alice.johnson@business.org'),
(4, 'Robert Brown', 'robert.brown@enterprise.net'),
(5, 'Emily Davis', 'emily.davis@startup.io'),
(6, 'Michael Wilson', 'michael.wilson@web.co'),
(7, 'Sophia Taylor', 'sophia.taylor@tech.dev'),
(8, 'David Anderson', 'david.anderson@service.us');

-- solution
select name, substring(email, position('@' in email)+1) as domain
from employee