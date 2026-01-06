CREATE TABLE sales(
    sales_id SERIAL PRIMARY KEY ,
    customer_id INT,
    product_id INT,
    sale_date DATE,
    amount INT
);

CREATE OR REPLACE VIEW v_customer_sales AS
    SELECT SUM(amount) AS total_amount FROM sales
    GROUP BY customer_id;

SELECT * FROM v_customer_sales WHERE total_amount > 1000;

