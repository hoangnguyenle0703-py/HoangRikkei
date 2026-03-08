CREATE DATABASE ProductManagement;

CREATE TABLE Product (
                         Product_Id SERIAL PRIMARY KEY,
                         Product_Name VARCHAR(100) NOT NULL UNIQUE,
                         Product_Price FLOAT NOT NULL CHECK (Product_Price > 0),
                         Product_Title VARCHAR(200) NOT NULL,
                         Product_created DATE NOT NULL,
                         Product_catalog VARCHAR(100) NOT NULL,
                         Product_Status BIT DEFAULT '1'
);

-- Procedure: Lấy tất cả thông tin sản phẩm (Get all products)
CREATE OR REPLACE FUNCTION get_all_products()
    RETURNS TABLE (
                      p_id INT,
                      p_name VARCHAR,
                      p_price FLOAT,
                      p_title VARCHAR,
                      p_created DATE,
                      p_catalog VARCHAR,
                      p_status BIT
                  ) AS $$
BEGIN
    RETURN QUERY SELECT * FROM Product;
END;
$$ LANGUAGE plpgsql;


-- Procedure: Thêm mới một sản phẩm (Add a new product)
CREATE OR REPLACE PROCEDURE add_product(
    IN p_name VARCHAR(100),
    IN p_price FLOAT,
    IN p_title VARCHAR(200),
    IN p_created DATE,
    IN p_catalog VARCHAR(100),
    IN p_status BIT
) AS $$
BEGIN
    INSERT INTO Product (Product_Name, Product_Price, Product_Title, Product_created, Product_catalog, Product_Status)
    VALUES (p_name, p_price, p_title, p_created, p_catalog, p_status);
END;
$$ LANGUAGE plpgsql;


-- Procedure: Cập nhật một sản phẩm theo mã (Update product by ID)
CREATE OR REPLACE PROCEDURE update_product(
    IN p_id INT,
    IN p_name VARCHAR(100),
    IN p_price FLOAT,
    IN p_title VARCHAR(200),
    IN p_created DATE,
    IN p_catalog VARCHAR(100),
    IN p_status BIT
) AS $$
BEGIN
    UPDATE Product
    SET Product_Name = p_name,
        Product_Price = p_price,
        Product_Title = p_title,
        Product_created = p_created,
        Product_catalog = p_catalog,
        Product_Status = p_status
    WHERE Product_Id = p_id;
END;
$$ LANGUAGE plpgsql;


-- Procedure: Xóa một sản phẩm theo mã (Delete product by ID)
CREATE OR REPLACE PROCEDURE delete_product(
    IN p_id INT
) AS $$
BEGIN
    DELETE FROM Product WHERE Product_Id = p_id;
END;
$$ LANGUAGE plpgsql;


-- Procedure: Lấy thông tin sản phẩm theo mã (Get product by ID)
CREATE OR REPLACE FUNCTION get_product_by_id(p_id INT)
    RETURNS TABLE (
                      r_id INT, r_name VARCHAR, r_price FLOAT, r_title VARCHAR, r_created DATE, r_catalog VARCHAR, r_status BIT
                  ) AS $$
BEGIN
    RETURN QUERY SELECT * FROM Product WHERE Product_Id = p_id;
END;
$$ LANGUAGE plpgsql;


-- Procedure: Tìm kiếm sản phẩm theo tên (tương đối) (Search products by name - relative)
CREATE OR REPLACE FUNCTION search_products_by_name(p_name_keyword VARCHAR)
    RETURNS TABLE (
                      r_id INT, r_name VARCHAR, r_price FLOAT, r_title VARCHAR, r_created DATE, r_catalog VARCHAR, r_status BIT
                  ) AS $$
BEGIN
    -- Using ILIKE for case-insensitive partial matching
    RETURN QUERY SELECT * FROM Product WHERE Product_Name ILIKE '%' || p_name_keyword || '%';
END;
$$ LANGUAGE plpgsql;


-- Procedure: Thống kê số lượng sản phẩm theo danh mục (Count products by catalog)
CREATE OR REPLACE FUNCTION get_product_count_by_catalog()
    RETURNS TABLE (
                      catalog_name VARCHAR,
                      product_count BIGINT
                  ) AS $$
BEGIN
    RETURN QUERY
        SELECT Product_catalog, COUNT(Product_Id)
        FROM Product
        GROUP BY Product_catalog;
END;
$$ LANGUAGE plpgsql;

-- Procedure: Kiểm tra sự tồn tại của danh mục
CREATE OR REPLACE FUNCTION check_catalog_exists(p_catalog VARCHAR)
    RETURNS BOOLEAN AS $$
DECLARE
    catalog_count INT;
BEGIN
    SELECT COUNT(*) INTO catalog_count FROM Product WHERE Product_catalog = p_catalog;
    RETURN catalog_count > 0;
END;
$$ LANGUAGE plpgsql;