CREATE TABLE orders(
    order_id SERIAL PRIMARY KEY ,
    customer_id INT,
    order_date DATE,
    total_amount INT
);

CREATE INDEX idx_customer_id_btree ON orders USING btree(customer_id);

EXPLAIN ANALYSE
SELECT * FROM orders WHERE customer_id = 100;

DROP INDEX idx_customer_id_btree;