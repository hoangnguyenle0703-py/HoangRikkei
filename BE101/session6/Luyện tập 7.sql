CREATE TABLE baitap.department(
    id SERIAL PRIMARY KEY ,
    name varchar(50)
);

CREATE TABLE baitap.employee(
    id SERIAL PRIMARY KEY ,
    full_name varchar(100),
    department_id INT,
    salary NUMERIC(10,2)
);

INSERT INTO baitap.department (name) VALUES
('Kế toán'),
('Nhân sự'),
('IT'),
('Marketing'),
('Kinh doanh');

INSERT INTO baitap.employee (full_name, department_id, salary) VALUES
('Nguyễn Văn A', 1, 6500000.00),
('Trần Thị B', 2, 5800000.50),
('Lê Công C', 3, 9200000.75),
('Phạm Thị D', 4, 7150000.00),
('Hoàng Minh E', 5, 7500000.00),
('Đặng Thu G', 1, 6800000.25),
('Vũ Quang H', 3, 9500000.00),
('Bùi Tấn K', 4, 7300000.50),
('Dương Thị L', 2, 6000000.00),
('Chu Văn M', 5, 8100000.75);

SELECT e.full_name, d.name
FROM baitap.employee e
JOIN baitap.department d on d.id = e.department_id;

SELECT d.name AS department_name, AVG(e.salary) AS avg_salary
FROM baitap.employee e
JOIN baitap.department d on d.id = e.department_id
GROUP BY d.name
HAVING AVG(e.salary) > 10^6;

SELECT d.name
FROM baitap.department d
LEFT OUTER JOIN baitap.employee e on d.id = e.department_id
WHERE e.id ISNULL;