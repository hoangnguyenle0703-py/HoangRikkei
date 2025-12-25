CREATE table sales.products(
    id serial PRIMARY KEY ,
    name varchar(100) NOT NULL ,
    category varchar(50),
    price int,
    stock int,
    manufacturer varchar(50)
);

INSERT INTO sales.products (name, category, price, stock, manufacturer) VALUES
('Laptop Dell XPS 13', 'Laptop', 25000000, 12, 'Dell'),
('Chuột Logitech M90', 'Phụ kiện', 150000, 50, 'Logitech'),
('Bàn phím cơ Razer', 'Phụ kiện', 2200000, 0, 'Razer'),
('Macbook Air M2', 'Laptop', 32000000, 7, 'Apple'),
('iPhone 14 Pro Max', 'Điện thoại', 35000000, 15, 'Apple'),
('Laptop Dell XPS 13', 'Laptop', 25000000, 12, 'Dell'),
('Tai nghe AirPods 3', 'Phụ kiện', 4500000, NULL, 'Apple');

INSERT INTO sales.products(name, category, price, stock, manufacturer) VALUES
('Chuột không dây Logitech M170','Phụ kiện',300000,30,'Logitech');

UPDATE sales.products
SET price = price * 1.10
WHERE manufacturer = 'Apple';

DELETE FROM sales.products
WHERE stock = 0;

SELECT * FROM sales.products
WHERE price between 10^6 and 3*10^7;

SELECT * FROM sales.products
WHERE stock IS NULL;

SELECT DISTINCT manufacturer FROM sales.products;

SELECT * FROM sales.products
ORDER BY price desc , name asc;

SELECT * FROM sales.products
WHERE name ILIKE '%laptop%';

SELECT * FROM sales.products
LIMIT 2;

