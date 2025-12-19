CREATE TABLE products(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100),
    stock INT NOT NULL DEFAULT 0
);

CREATE TABLE orders(
    id SERIAL PRIMARY KEY ,
    product_id SERIAL REFERENCES products(id),
    quantity INT NOT NULL
);

CREATE OR REPLACE FUNCTION order_changes()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    DECLARE
        current_stock INT;
    BEGIN
        SELECT stock INTO current_stock FROM products
        WHERE id = COALESCE(NEW.product_id,OLD.product_id);
        if(tg_op = 'INSERT') THEN
            if (current_stock < NEW.quantity) THEN
                RAISE EXCEPTION 'Không đủ hàng trong kho';
            end if;
            UPDATE products SET stock = stock - NEW.quantity
            WHERE id = NEW.product_id;
            RETURN NEW;
        elsif(tg_op = 'UPDATE') THEN
            if (current_stock + OLD.quantity < NEW.quantity) THEN
                RAISE EXCEPTION 'Không đủ hàng trong kho, cập nhật thất bại';
            end if;
            UPDATE products SET stock = stock + OLD.quantity - NEW.quantity
            WHERE id = NEW.product_id;
            RETURN NEW;
        elsif(tg_op = 'DELETE') THEN
            UPDATE products SET stock = stock + OLD.quantity
            WHERE id = OLD.product_id;
            RETURN OLD;
        end if;
        RETURN NULL;
    end;
$$;

CREATE TRIGGER trg_order_changes
AFTER INSERT OR DELETE OR UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION order_changes();

INSERT INTO products (name, stock) VALUES ('Laptop Dell', 50), ('iPhone 15', 30);

INSERT INTO orders (product_id, quantity) VALUES (1, 5);

SELECT * FROM products WHERE id = 1;

UPDATE orders SET quantity = 2 WHERE id = 1;

SELECT * FROM products WHERE id = 1;

DELETE FROM orders WHERE id = 1;

SELECT * FROM products WHERE id = 1;

INSERT INTO orders (product_id, quantity) VALUES (1, 100);