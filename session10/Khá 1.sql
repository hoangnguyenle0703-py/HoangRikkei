CREATE TABLE products(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100),
    price NUMERIC(10,2),
    last_modified TIMESTAMP DEFAULT current_timestamp
);

CREATE OR REPLACE FUNCTION update_last_modified()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    NEW.last_modified = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_update_last_modified
BEFORE UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION update_last_modified();

INSERT INTO products (name, price) VALUES
('pen',1000);

SELECT products.last_modified FROM products
WHERE id = 1;

UPDATE products
SET price = 2000
WHERE name = 'pen';

