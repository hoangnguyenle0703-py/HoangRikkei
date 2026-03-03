CREATE DATABASE todo_db;
CREATE TABLE tasks (
                       id SERIAL PRIMARY KEY,
                       task_name VARCHAR(255) NOT NULL,
                       status VARCHAR(50) NOT NULL
);

-- 1. Thêm công việc
CREATE OR REPLACE PROCEDURE add_task(p_task_name VARCHAR, p_status VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO tasks (task_name, status) VALUES (p_task_name, p_status);
END;
$$;

-- 2. Liệt kê công việc (Dùng Function trả về bảng)
CREATE OR REPLACE FUNCTION list_tasks()
    RETURNS TABLE(id INT, task_name VARCHAR, status VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT t.id, t.task_name, t.status FROM tasks t ORDER BY t.id;
END;
$$;

-- 3. Cập nhật trạng thái
CREATE OR REPLACE PROCEDURE update_task_status(p_id INT, p_status VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE tasks t SET status = p_status WHERE t.id = p_id;
END;
$$;

-- 4. Xóa công việc
CREATE OR REPLACE PROCEDURE delete_task(p_id INT)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM tasks WHERE tasks.id = p_id;
END;
$$;

-- 5. Tìm kiếm công việc theo tên (Dùng ILIKE để không phân biệt hoa/thường)
CREATE OR REPLACE FUNCTION search_task_by_name(p_name VARCHAR)
    RETURNS TABLE(id INT, task_name VARCHAR, status VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT t.id, t.task_name, t.status
        FROM tasks t
        WHERE t.task_name ILIKE '%' || p_name || '%';
END;
$$;

-- 6. Thống kê công việc
CREATE OR REPLACE FUNCTION task_statistics()
    RETURNS TABLE(completed BIGINT, pending BIGINT)
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            COUNT(CASE WHEN t.status = 'đã hoàn thành' THEN 1 END) AS completed,
            COUNT(CASE WHEN t.status = 'chưa hoàn thành' THEN 1 END) AS pending
        FROM tasks t;
END;
$$;