CREATE TABLE inventory(
    product_id SERIAL PRIMARY KEY ,
    product_name VARCHAR(100),
    quantity INT
);

INSERT INTO inventory(PRODUCT_NAME, QUANTITY)
VALUES ('Bàn phím',10);

CREATE PROCEDURE check_stock(
    p_id INT,
    p_qty INT
)
LANGUAGE plpgsql
AS $$
    DECLARE qty INT;
    BEGIN
        SELECT quantity INTO qty FROM inventory
        WHERE product_id = p_id;

        IF qty < p_qty THEN
            RAISE EXCEPTION 'Không đủ hàng trong kho';
        end if;
    end;
$$;

DO $$
    BEGIN
        CALL check_stock(1,11);
        RAISE NOTICE 'Đủ hàng trong kho';
    end;
$$;