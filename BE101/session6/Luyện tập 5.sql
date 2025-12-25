CREATE TABLE baitap.courses(
    id serial PRIMARY KEY ,
    title varchar(100),
    instructor varchar(50),
    price numeric(10,2),
    duration int
);

INSERT INTO baitap.courses (title, instructor, price, duration) VALUES
('Lập trình Python cơ bản', 'Lê Hùng', 550000.00, 45),
('Phân tích dữ liệu với R', 'Trần An', 820000.50, 60),
('Thiết kế UX/UI nâng cao', 'Nguyễn Mai', 1200000.00, 90),
('Tiếng Anh giao tiếp cấp tốc', 'Phạm Việt', 350000.00, 30),
('Marketing số 2025', 'Đỗ Thảo', 780000.75, 55),
('Quản lý dự án Agile', 'Hoàng Nam', 950000.25, 75);

UPDATE baitap.courses
SET price = price * 1.15
WHERE duration > 30;

DELETE FROM baitap.courses
WHERE title ILIKE '%demo%';

SELECT * FROM baitap.courses
WHERE title ILIKE '%sql%';

SELECT * FROM baitap.courses
WHERE price BETWEEN 500000 AND 2000000
ORDER BY price desc
LIMIT 3;