CREATE TABLE baitap.OrderInfo(
    id serial PRIMARY KEY ,
    customer_id int,
    order_date date,
    total NUMERIC(10,2),
    status varchar(20)
);

INSERT INTO baitap.OrderInfo (customer_id, order_date, total, status) VALUES
(101, '2025-11-20', 150000.00, 'Completed'),
(102, '2025-11-21', 52500.50, 'Pending'),
(101, '2025-11-22', 380000.75, 'Shipped'),
(103, '2025-11-23', 9900.00, 'Completed'),
(104, '2025-11-24', 215500.25, 'Cancelled');

SELECT * FROM baitap.OrderInfo
WHERE total > 50000;

SELECT * FROM baitap.OrderInfo
WHERE order_date BETWEEN '2024-10-01' AND '2024-10-31';

SELECT * FROM baitap.OrderInfo
WHERE status != 'Completed';

SELECT * FROM baitap.OrderInfo
ORDER BY order_date desc
LIMIT 2;