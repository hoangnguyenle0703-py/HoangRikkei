CREATE TABLE baitap.products(
    id serial PRIMARY KEY ,
    name varchar(100),
    category varchar(50),
    price numeric(10,2)
);

CREATE TABLE baitap.OrderDetails(
    id SERIAL PRIMARY KEY ,
    order_id int,
    product_id int,
    quantity int
);

INSERT INTO baitap.products (name, category, price) VALUES
('Laptop Gaming ABC', 'Điện tử', 25000000.00),
('Bàn phím cơ XYZ', 'Phụ kiện', 1500000.50),
('Chuột không dây A', 'Phụ kiện', 350000.00),
('Màn hình 27 inch', 'Điện tử', 6800000.75),
('Webcam HD', 'Phụ kiện', 800000.00);

INSERT INTO baitap.OrderDetails (order_id, product_id, quantity) VALUES
(101, 1, 1),
(101, 2, 1),
(102, 3, 2),
(103, 1, 1),
(103, 4, 1),
(104, 5, 3),
(105, 2, 5),
(105, 3, 1),
(101, 5, 2),
(102, 4, 1);

SELECT p.name AS product_name, SUM(price*quantity) AS total_sales
FROM baitap.products p
JOIN baitap.OrderDetails o ON p.id = o.product_id
GROUP BY p.id;

SELECT p.category AS category, AVG(price*quantity) AS avg_total_sales
FROM baitap.products p
         JOIN baitap.OrderDetails o ON p.id = o.product_id
GROUP BY p.category
HAVING AVG(price*quantity) > 10^7;

SELECT p.name AS product_name
FROM baitap.products p
JOIN baitap.OrderDetails o ON p.id = o.product_id
GROUP BY p.id
HAVING SUM(price*quantity) > (
    SELECT AVG(total_sales)
    FROM (
        SELECT SUM(price*quantity) as total_sales
        FROM baitap.products p2
        JOIN baitap.OrderDetails o2 on p2.id = o2.product_id
        GROUP BY p2.id
    ) as avg_sales
);

SELECT p.name, SUM(o.quantity)
FROM baitap.products p
LEFT OUTER JOIN baitap.orderdetails o on p.id = o.product_id
GROUP BY p.id;
