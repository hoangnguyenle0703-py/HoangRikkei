CREATE TABLE baitap.orders(
    id serial PRIMARY KEY ,
    customer_id int,
    order_date date,
    total_amount NUMERIC(10,2)
);

CREATE TABLE baitap.customers(
    id serial PRIMARY KEY ,
    name varchar(100)
);

INSERT INTO baitap.customers (name) VALUES
('Nguyễn Văn Khách 1'),
('Trần Thị Khách 2'),
('Lê Hoàng Khách 3'),
('Phạm Công Khách 4'),
('Đỗ Thị Khách 5');

INSERT INTO baitap.orders (customer_id, order_date, total_amount) VALUES
(1, '2025-11-01', 500000.00),
(2, '2025-11-01', 120000.50),
(3, '2025-11-02', 800000.00),
(1, '2025-11-03', 250000.75),
(5, '2025-11-04', 330000.00),
(2, '2025-11-05', 180000.25),
(3, '2025-11-06', 70000.00),
(5, '2025-11-07', 450000.99),
(1, '2025-11-08', 610000.00),
(2, '2025-11-09', 95000.00);

SELECT c.name, SUM(o.total_amount) as total_spend
FROM baitap.customers c
JOIN baitap.orders o on c.id = o.customer_id
GROUP BY c.id
ORDER BY total_spend desc ;

SELECT name FROM baitap.customers
WHERE id = (
    SELECT c.id
    FROM baitap.customers c
    JOIN baitap.orders o on c.id = o.customer_id
    GROUP BY c.id
    ORDER BY SUM(total_amount)
    LIMIT 1
);

SELECT c.name
FROM baitap.customers c
LEFT OUTER JOIN baitap.orders o on c.id = o.customer_id
WHERE o.id ISNULL;

SELECT c.name
FROM baitap.customers c
JOIN baitap.orders o on c.id = o.customer_id
GROUP BY c.id
HAVING SUM(total_amount) > (
    SELECT AVG(total_spend)
    FROM (
        SELECT SUM(total_amount) AS total_spend
        FROM baitap.customers c2
        JOIN baitap.orders o2 on c2.id = o2.customer_id
        GROUP BY c2.id
    ) as customer_spends
)