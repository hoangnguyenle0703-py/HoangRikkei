CREATE TABLE sales.customers(
    customer_id serial PRIMARY KEY ,
    customer_name varchar(50),
    city varchar(50)
);

INSERT INTO sales.customers(customer_name, city) VALUES
('Nguyễn Văn A','Hà Nội'),
('Trần Thị B','Đà Nẵng'),
('Lê Văn C','Hồ Chí Minh'),
('Phạm Thị D','Hà Nội');

CREATE TABLE sales.orders(
    order_id serial PRIMARY KEY ,
    customer_id serial references sales.customers(customer_id),
    order_date date,
    total_price int
);

INSERT INTO sales.orders(order_id, customer_id, order_date, total_price) VALUES
(101,1,'2024-12-20',3000),
(102,2,'2025-01-05',1500),
(103,1,'2025-02-10',2500),
(104,3,'2025-02-15',4000),
(105,4,'2025-03-01',800);

CREATE TABLE sales.order_items(
    item_id serial PRIMARY KEY ,
    order_id serial references sales.orders(order_id),
    product_id serial references sales.products(product_id),
    quantity int,
    price int
);

INSERT INTO sales.order_items(order_id, product_id, quantity, price) VALUES
(101,1,2,1500),
(102,2,1,1500),
(103,3,5,500),
(104,2,4,1000);

SELECT c.customer_id,
       SUM(total_price) as total_revenue,
       COUNT(order_id) as order_count
FROM sales.orders o
JOIN sales.customers c on o.customer_id = c.customer_id
GROUP BY c.customer_id
HAVING SUM(total_price) > 2000;

WITH customer_revenue AS (
    SELECT c.customer_id,
           c.customer_name,
           SUM(o.total_price) AS total_revenue
    FROM sales.customers c
    JOIN sales.orders o on c.customer_id = o.customer_id
    GROUP BY c.customer_id
),
average_revenue AS(
    SELECT AVG(total_revenue) as avr
    FROM customer_revenue
)

SELECT cr.customer_name,
       cr.total_revenue
FROM customer_revenue cr, average_revenue ar
--CROSS JOIN average_revenue ar
WHERE cr.total_revenue > ar.avr;

WITH city_revenue AS (
    SELECT c.city,
           SUM(o.total_price) AS total_revenue
    FROM sales.customers c
    JOIN sales.orders o on c.customer_id = o.customer_id
    GROUP BY city
)

SELECT cr.city,
       cr.total_revenue
FROM city_revenue cr
ORDER BY cr.total_revenue desc
LIMIT 1;

SELECT c.customer_name ,
       c.city,
       SUM(oi.quantity) AS total_items,
       SUM(o.total_price) AS total_revenue
FROM sales.customers c
JOIN sales.orders o ON c.customer_id = o.customer_id
FULL OUTER JOIN sales.order_items oi ON o.order_id = oi.order_id
GROUP BY c.customer_id, c.customer_name
ORDER BY c.customer_id asc;
