CREATE TABLE baitap.products(
    id serial PRIMARY KEY ,
    name varchar(100),
    category varchar(50),
    price NUMERIC(10,2),
    stock int
);

INSERT INTO baitap.products(name, category, price, stock) VALUES
('Bàn phím','Electronics',4000000,5),
('Chuột','Electronics',150000,8),
('Nồi cơm điện','Household',200000,3),
('Chổi','Household',30000,13),
('Gạo','Food',160000,15);

SELECT * FROM baitap.products;

SELECT * FROM baitap.products
ORDER BY price desc
LIMIT 3;

SELECT * FROM baitap.products
WHERE category = 'Electronics' AND price < 10^7;

SELECT * FROM baitap.products
ORDER BY stock asc ;
