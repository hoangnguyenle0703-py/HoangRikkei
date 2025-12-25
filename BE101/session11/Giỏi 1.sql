CREATE TABLE products(
    product_id SERIAL PRIMARY KEY ,
    product_name VARCHAR(100),
    stock INT,
    price NUMERIC(10,2)
);

CREATE TABLE orders(
    order_id SERIAL PRIMARY KEY ,
    customer_name VARCHAR(100),
    total_amount NUMERIC(10,2),
    created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE order_items(
    order_item_id SERIAL PRIMARY KEY ,
    order_id INT,
    product_id INT REFERENCES products(product_id),
    quantity INT,
    subtotal NUMERIC(10,2)
);

INSERT INTO products(product_name, stock, price)
VALUES ('A',3,100.00),
       ('B',3,100.00);

DO $$
    DECLARE cur_stock INT;
    BEGIN
        UPDATE products SET stock = stock - 2 WHERE product_id = 1;
        INSERT INTO order_items(order_id, product_id, quantity, subtotal)
        SELECT 1,1,2,price * 2 FROM products
        WHERE product_id = 1;
        SELECT stock INTO cur_stock FROM products WHERE product_id = 1;
        IF(cur_stock < 0) THEN ROLLBACK;
        end if;

        UPDATE products SET stock = stock - 1 WHERE product_id = 2;
        INSERT INTO order_items(order_id, product_id, quantity, subtotal)
        SELECT 1,2,1,price * 1 FROM products
        WHERE product_id = 2;
        SELECT stock INTO cur_stock FROM products WHERE product_id = 2;
        IF(cur_stock < 0) THEN ROLLBACK;
        end if;

        INSERT INTO orders(customer_name, total_amount)
        SELECT 'Nguyễn Văn A',SUM(subtotal)
        FROM order_items WHERE order_id = 1;

        COMMIT;
    end;
$$;

SELECT * FROM products;
SELECT * FROM orders;