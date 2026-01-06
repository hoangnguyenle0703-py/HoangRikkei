CREATE TABLE sales.products(
    product_id serial PRIMARY KEY ,
    product_name varchar(50) ,
    category varchar(50)
);

INSERT INTO sales.products (product_name, category) VALUES
('Laptop Dell','Electronics'),
('IPhone 15','Electronics'),
('Bàn học gỗ','Furniture'),
('Ghế xoay','Furniture');

CREATE TABLE sales.orders(
    order_id serial PRIMARY KEY ,
    product_id serial references sales.products(product_id),
    quantity int,
    total_price int
);

INSERT INTO sales.orders(order_id, product_id, quantity, total_price) VALUES
(101,1,2,2200),
(102,2,3,3300),
(103,3,5,2500),
(104,4,4,1600),
(105,1,1,1100);

SELECT SUM(total_price) AS total_sales, SUM(quantity) AS total_quantity
FROM sales.orders o
JOIN sales.products p on o.product_id = p.product_id
GROUP BY p.category
HAVING SUM(total_price) > 2000
ORDER BY total_sales asc;

SELECT product_name, SUM(total_price) AS total_revenue FROM sales.orders o
JOIN sales.products p on p.product_id = o.product_id
GROUP BY p.product_id
ORDER BY total_revenue desc
LIMIT 1;