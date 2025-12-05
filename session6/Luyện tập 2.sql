CREATE TABLE baitap.employees(
    id serial PRIMARY KEY ,
    full_name varchar(100),
    department varchar(50),
    salary NUMERIC(10,2),
    hire_date date
);

INSERT INTO baitap.employees (full_name, department, salary, hire_date) VALUES
('Nguyễn Văn An', 'Kế toán', 55000.00, '2023-01-15'),
('Trần Thị Bình', 'Nhân sự', 48000.50, '2022-08-20'),
('Lê Công Cường', 'IT', 72000.75, '2021-05-10'),
('Phạm Thị Duyên', 'Marketing', 61500.00, '2023-11-01'),
('Hoàng Minh Hùng', 'IT', 75000.00, '2020-03-25'),
('Đặng Thu Hương', 'Kinh doanh', 65000.25, '2022-04-18');

UPDATE baitap.employees
SET salary = salary * 1.10
WHERE department = 'IT';

DELETE FROM baitap.employees
WHERE salary < 60000;

SELECT * FROM baitap.employees
WHERE full_name ILIKE '%an%';

SELECT * FROM baitap.employees
WHERE hire_date BETWEEN '2023-01-01' AND '2023-12-31';