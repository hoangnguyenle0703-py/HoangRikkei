CREATE table library.books(
    id serial PRIMARY KEY ,
    title varchar(100),
    author varchar(100),
    category varchar(100),
    publish_year int,
    price int,
    stock int
);

INSERT INTO library.books (title, author, category, publish_year, price, stock) VALUES
('Lập trình C cơ bản', 'Nguyễn Văn Nam', 'CNTT', 2018, 95000, 20),
('Học SQL qua ví dụ', 'Trần Thị Hạnh', 'CSDL', 2020, 125000, 12),
('Lập trình C cơ bản', 'Nguyễn Văn Nam', 'CNTT', 2018, 95000, 20),
('Phân tích dữ liệu với Python', 'Lê Quốc Bảo', 'CNTT', 2022, 180000, NULL),
('Quản trị cơ sở dữ liệu', 'Nguyễn Thị Minh', 'CSDL', 2021, 150000, 5),
('Học máy cho người mới bắt đầu', 'Nguyễn Văn Nam', 'AI', 2023, 220000, 8),
('Khoa học dữ liệu cơ bản', 'Nguyễn Văn Nam', 'AI', 2023, 220000, NULL);

DELETE FROM library.books
WHERE id not in(
    SELECT min(id)
    FROM library.books
    GROUP BY title,author,publish_year
);

UPDATE library.books
SET price = price * 1.10
WHERE publish_year >= 2021 AND price < 200000;

UPDATE library.books
SET stock = 0
WHERE stock IS NULL;

SELECT * FROM library.books
WHERE category in ('AI','CNTT') AND price between 100000 and 250000
ORDER BY price desc , title asc ;

SELECT * FROM library.books
WHERE title ILIKE '%học%';

SELECT DISTINCT category FROM library.books
GROUP BY category
HAVING max(publish_year) > 2020;

SELECT * FROM library.books
LIMIT 2 OFFSET 1;