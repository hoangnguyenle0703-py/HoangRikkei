CREATE TABLE order_detail(
    id SERIAL PRIMARY KEY ,
    order_id INT,
    product_name VARCHAR(100),
    quantity INT,
    unit_price NUMERIC
);

CREATE OR REPLACE PROCEDURE calculate_order_total(
    order_id_intput INT,
    OUT total NUMERIC
)
LANGUAGE plpgsql
AS $$
    BEGIN
        SELECT SUM(quantity * unit_price)
        INTO total
        FROM order_detail
        WHERE order_id = order_id_intput;

        IF total ISNULL THEN
            total := 0;
        END IF;
    end;
$$;

DO $$
    DECLARE
        result NUMERIC;
    BEGIN
        CALL calculate_order_total(1,result);
        RAISE NOTICE 'Tổng giá trị của đơn hàng số 1 là %', result;
    end;
$$
