CREATE TABLE sales.products(
    id SERIAL PRIMARY KEY ,
    name varchar(50),
    category varchar(50),
    price decimal(10,2),
    stock INT
);

INSERT INTO sales.products(name, category, price, stock) VALUES
('Laptop Dell','Electronics', 1500.00,5),
('Chuột Logitech','Electronics', 25.50,50),
('Bàn Phím Razer','Electronics', 120.00,20),
('Tủ lạnh LG','Home Appliances', 800.00,3),
('Máy Giặt Samsung','Home Appliances', 600.00,2);

INSERT INTO sales.products(name, category, price, stock) VALUES
('Điều hòa Panasonic','Home Appliances',400.00,10);

UPDATE sales.products
SET stock = 7
WHERE name = 'Laptop Dell';

DELETE from sales.products
WHERE stock = 0;

SELECT * FROM sales.products
ORDER BY price ASC;

SELECT Distinct * FROM sales.products;

SELECT name FROM sales.products
WHERE price between 100 and 1000;

SELECT name FROM sales.products
WHERE name ILIKE '%LG%' or name ILIKE '%samsung%';

SELECT name,price FROM sales.products
ORDER BY price desc
LIMIT 2 OFFSET 1;