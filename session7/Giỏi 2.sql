CREATE TABLE customer(
    customer_id SERIAL PRIMARY KEY ,
    full_name VARCHAR(100),
    region VARCHAR(50)
);

CREATE TABLE orders(
    order_id SERIAL PRIMARY KEY ,
    customer_id INT REFERENCES customer(customer_id),
    total_amount DECIMAL(10,2),
    order_date DATE,
    status VARCHAR(20)
);

CREATE TABLE product(
    product_id SERIAL PRIMARY KEY ,
    name VARCHAR(100),
    price DECIMAL(10,2),
    category VARCHAR(50)
);

CREATE TABLE order_detail(
    order_id INT REFERENCES orders(order_id),
    product_id INT REFERENCES product(product_id),
    quantity INT
);

CREATE VIEW v_revenue_by_region AS
SELECT c.region, SUM(o.total_amount) AS total_revenue
FROM customer c
JOIN orders o ON c.customer_id = o.customer_id
GROUP BY region;

SELECT * FROM v_revenue_by_region
ORDER BY total_revenue DESC
LIMIT 3;

CREATE MATERIALIZED VIEW mv_monthly_sales AS
SELECT EXTRACT('month' FROM order_date) AS month,
       SUM(total_amount) AS monthly_revenue
FROM orders
GROUP BY EXTRACT('month' FROM order_date);

-- Tạo View đơn giản để có thể cập nhật được
CREATE VIEW v_order_status AS
SELECT order_id, customer_id, status, total_amount
FROM orders
WHERE status = 'Pending' -- Giả sử View này chỉ quản lý các đơn hàng đang chờ
WITH CHECK OPTION;

UPDATE v_order_status
SET status = 'Processing'
WHERE order_id = 101;

UPDATE v_order_status
SET status = 'Cancelled'
WHERE order_id = 101;

CREATE VIEW v_revenue_above_avg AS
SELECT * FROM v_revenue_by_region
WHERE total_revenue > (
    SELECT AVG(total_revenue)
    FROM v_revenue_by_region
)