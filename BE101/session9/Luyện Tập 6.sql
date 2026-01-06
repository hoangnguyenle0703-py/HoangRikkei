CREATE TABLE products(
    product_id SERIAL PRIMARY KEY ,
    name VARCHAR(100),
    price NUMERIC(10,2),
    category_id INT
);

CREATE PROCEDURE update_product_price(p_category_id INT,p_increase_percent NUMERIC)
LANGUAGE plpgsql
AS $$
    DECLARE
        cur_price NUMERIC;
        new_price NUMERIC;
        p_id INT;
    BEGIN
        FOR p_id IN (SELECT product_id FROM products WHERE category_id = p_category_id) LOOP
            cur_price := (SELECT price FROM products WHERE product_id = p_id);
            new_price := cur_price * (100+p_increase_percent)/100;
            UPDATE products SET price = new_price WHERE product_id = p_id;
        end loop;
    end;
$$;

INSERT INTO products (name, price, category_id)
VALUES ('Bàn phím',399.99,1),
       ('Chuột',99.99,1),
       ('Nồi cơm điện',49.99,2);

CALL update_product_price(1,50);

SELECT * FROM products;