CREATE TABLE customer(
    customer_id SERIAL PRIMARY KEY ,
    full_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15)
);

CREATE TABLE orders(
    order_id SERIAL PRIMARY KEY ,
    customer_id INT REFERENCES customer(customer_id),
    total_amount DECIMAL(10,2),
    order_date DATE
);

CREATE OR REPLACE VIEW v_order_summary AS
SELECT full_name,total_amount,order_date
FROM orders o JOIN customer c on o.customer_id = c.customer_id
WHERE o.total_amount > 0
WITH CHECK OPTION;

SELECT * FROM v_order_summary;

UPDATE v_order_summary
SET total_amount = 500000
WHERE full_name = 'Nguyen Van A' AND order_date = '2025-12-19';

CREATE VIEW v_monthly_sales AS
SELECT
    extract('month' FROM order_date) AS month,
    SUM(total_amount) AS total_revenue
FROM orders
GROUP BY month;