CREATE TABLE products(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100),
    price NUMERIC,
    discount_percent INT
);

INSERT INTO products(name, price, discount_percent)
VALUES ('Chuột',1000,30);

CREATE OR REPLACE PROCEDURE calculate_discount (p_id INT, OUT p_final_price NUMERIC)
LANGUAGE plpgsql
AS $$
    DECLARE
        cur_price NUMERIC;
        discount INT;
    BEGIN
        SELECT price,discount_percent INTO cur_price,discount FROM products WHERE id = p_id;
        p_final_price = cur_price - (cur_price * discount/100);

        UPDATE products SET price = p_final_price WHERE id = p_id;
    end;
$$;

DO $$
    DECLARE
        p_final_price NUMERIC;
    BEGIN
        CALL calculate_discount(1,p_final_price);
        RAISE NOTICE 'Gia sau khi giam la: %', p_final_price;
    end;
$$;