create schema business;

create table business.employees(
    id serial PRIMARY KEY ,
    full_name varchar(50),
    department varchar(50),
    position varchar(50),
    salary BIGINT,
    bonus BIGINT,
    join_year int
);

INSERT INTO business.employees (full_name, department, position, salary, bonus, join_year) VALUES
('Nguyễn Văn Huy', 'IT', 'Developer', 18000000, 1000000, 2021),
('Trần Thị Mai', 'HR', 'Recruiter', 12000000, NULL, 2020),
('Lê Quốc Trung', 'IT', 'Tester', 15000000, 800000, 2023),
('Nguyễn Văn Huy', 'IT', 'Developer', 18000000, 1000000, 2021),
('Phạm Ngọc Hân', 'Finance', 'Accountant', 14000000, NULL, 2019),
('Bùi Thị Lan', 'HR', 'HR Manager', 20000000, 3000000, 2018),
('Đặng Hữu Tài', 'IT', 'Developer', 17000000, NULL, 2022);

DELETE FROM business.employees
WHERE id NOT IN (
    SELECT MIN(id)
    FROM business.employees
    GROUP BY full_name,department,salary
);

UPDATE business.employees
SET
    salary = CASE
        WHEN salary < 18000000 THEN salary * 1.10
        ELSE salary
    END,
    bonus = CASE
        WHEN bonus IS NULL THEN 500000
        ELSE bonus
    END
WHERE salary < 18000000 OR bonus IS NULL;

SELECT * FROM business.employees
WHERE join_year > 2020 AND salary + bonus > 15000000
ORDER BY salary + bonus desc
LIMIT 3;

SELECT * FROM business.employees
WHERE full_name LIKE 'Nguyễn%' OR full_name LIKE '%Hân';

SELECT DISTINCT department FROM business.employees
GROUP BY department
HAVING SUM(bonus) IS NOT NULL;

SELECT * FROM business.employees
WHERE join_year between 2019 and 2022;
