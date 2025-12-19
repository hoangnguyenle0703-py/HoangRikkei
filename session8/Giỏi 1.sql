CREATE TABLE employees(
    emp_id SERIAL PRIMARY KEY ,
    emp_name VARCHAR(100),
    job_level INT,
    salary NUMERIC
);

INSERT INTO employees(emp_name, job_level, salary)
VALUES ('Hoang',3,10000);

CREATE PROCEDURE adjust_salary(p_emp_id INT,OUT p_new_salary NUMERIC)
LANGUAGE plpgsql
AS $$
    DECLARE
        level INT;
        cur_salary NUMERIC;
    BEGIN
        SELECT job_level,salary INTO level,cur_salary FROM employees WHERE p_emp_id = emp_id;
        IF (level = 1) THEN
            p_new_salary := cur_salary * 1.05;
        ELSIF (level = 2) THEN
            p_new_salary := cur_salary * 1.10;
        ELSIF (level = 3) THEN
            p_new_salary := cur_salary * 1.15;
        end if;
    end;
$$;

CALL adjust_salary(1,p_new_salary := 1);
