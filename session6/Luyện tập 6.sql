CREATE TABLE baitap.orders(
    id serial primary key ,
    customer_id int,
    order_date date,
    total_amount numeric(10,2)
);

INSERT INTO baitap.orders (customer_id, order_date, total_amount) VALUES
(1, '2025-10-10', 525000.00),
(2, '2025-10-10', 120000.50),
(1, '2025-10-12', 38000.00),
(3, '2025-10-15', 850000.75),
(4, '2025-10-16', 65000.00),
(2, '2025-10-17', 215000.25),
(5, '2025-10-18', 420000.00),
(3, '2025-10-19', 15000.00),
(4, '2025-10-20', 790000.90),
(5, '2025-10-21', 95000.00);

SELECT SUM(total_amount) AS total_revenue,
       COUNT(id) AS total_orders,
       AVG(total_amount) AS average_order_value
FROM baitap.orders;

SELECT SUM(total_amount) AS total_revenue_per_year,
       extract(year from order_date) AS year
FROM baitap.orders
GROUP BY extract(year from order_date);

SELECT extract(year from order_date) AS year
FROM baitap.orders
GROUP BY extract(year from order_date)
HAVING SUM(total_amount) > 5*10^7;

SELECT * FROM baitap.orders
ORDER BY total_amount desc
LIMIT 5;