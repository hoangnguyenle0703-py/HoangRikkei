CREATE TABLE employees (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           position VARCHAR(50),
                           salary DECIMAL(15, 2)
);

CREATE TABLE employees_log (
                               log_id SERIAL PRIMARY KEY,
                               employee_id INT,
                               operation VARCHAR(10),
                               old_data TEXT,
                               new_data TEXT,
                               change_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION log_employee_changes()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        if(tg_op = 'INSERT') THEN
            INSERT INTO employees_log(employee_id, operation, new_data)
            VALUES (NEW.id,'INSERT',ROW(NEW.*)::TEXT);
        elsif (tg_op = 'UPDATE')THEN
            INSERT INTO employees_log(employee_id, operation, old_data, new_data)
            VALUES (NEW.id,'UPDATE',ROW(OLD.*)::TEXT,ROW(NEW.*)::TEXT);
        ELSIF (TG_OP = 'DELETE') THEN
            INSERT INTO employees_log (employee_id, operation, old_data)
            VALUES (OLD.id, 'DELETE', ROW(OLD.*)::TEXT);
        end if;
        RETURN NULL;
    end;
$$;

CREATE TRIGGER trg_employee_edit
AFTER INSERT OR UPDATE OR DELETE ON employees
FOR EACH ROW
EXECUTE FUNCTION log_employee_changes();

INSERT INTO employees (name, position, salary) VALUES ('Trần Thị B', 'Developer', 1500);

UPDATE employees SET salary = 1700 WHERE name = 'Trần Thị B';

DELETE FROM employees WHERE id = 1;

SELECT * FROM employees_log;