CREATE TABLE customers(
    customer_id SERIAL PRIMARY KEY ,
    name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE customer_log(
    log_id SERIAL PRIMARY KEY ,
    customer_name VARCHAR(100),
    action_time TIMESTAMP DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION log_customer_changes()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        INSERT INTO customer_log(customer_name) VALUES (NEW.name);
        RETURN NULL;
    end;
$$;

CREATE TRIGGER trg_log_customer_changes
AFTER INSERT ON customers
FOR EACH ROW
EXECUTE FUNCTION log_customer_changes();

INSERT INTO customers(name, email)
VALUES ('Hoang','hoangnguyenle74@gmail.com');

SELECT * FROM customers;
SELECT * FROM customer_log;