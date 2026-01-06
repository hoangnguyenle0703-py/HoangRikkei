CREATE TABLE customers(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100),
    credit_limit INT
);

CREATE TABLE orders(
    id SERIAL PRIMARY KEY ,
    customer_id SERIAL REFERENCES customers(id),
    order_amount INT
);

CREATE OR REPLACE FUNCTION check_credit_limit()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_credit_limit INT;
    v_current_total INT;
BEGIN
    SELECT customers.credit_limit INTO v_credit_limit
    FROM customers
    WHERE id = NEW.customer_id;

    SELECT COALESCE(SUM(orders.order_amount),0) INTO v_current_total
    FROM orders
    WHERE customer_id = customer_id;

    if (v_current_total + NEW.order_amount) > v_credit_limit THEN
        RAISE EXCEPTION 'Khách hàng % đã vượt quá hạn mức tín dụng! (Tổng mới: %, Hạn mức: %',
        NEW.customer_id, (v_current_total + NEW.order_amount), v_credit_limit;
    end if;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_check_credit
BEFORE INSERT ON orders
FOR EACH ROW
EXECUTE FUNCTION check_credit_limit();

INSERT INTO customers (name, credit_limit) VALUES ('Nguyễn Văn A', 1000.00);

INSERT INTO orders (customer_id, order_amount) VALUES (1, 600.00);

INSERT INTO orders (customer_id, order_amount) VALUES (1, 500.00);