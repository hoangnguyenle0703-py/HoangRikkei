CREATE TABLE products(
    product_id SERIAL PRIMARY KEY ,
    name VARCHAR(100),
    stock INT
);

CREATE TABLE sales(
    sale_id SERIAL PRIMARY KEY ,
    product_id INT REFERENCES products(product_id),
    quantity INT
);

CREATE FUNCTION check_stock()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    DECLARE cur_stock INT;
    BEGIN
        SELECT products.stock INTO cur_stock
        FROM products
        WHERE product_id = NEW.product_id;
        if(cur_stock < NEW.quantity) THEN
            RAISE EXCEPTION 'Không đủ hàng trong kho';
        end if;
        RETURN NEW;
    end;
$$;

CREATE TRIGGER trg_check_stock
BEFORE INSERT ON sales
FOR EACH ROW
EXECUTE FUNCTION check_stock();

INSERT INTO products(name, stock) VALUES ('A',100);
INSERT INTO sales(product_id, quantity) VALUES (1,101);