-- Tạo Database
CREATE DATABASE shop_db;

-- Chuyển kết nối sang shop_db và chạy tiếp các lệnh dưới đây:

-- Tạo bảng products (dùng SERIAL thay cho AUTO_INCREMENT)
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DECIMAL(10,2) NOT NULL
);

-- Tạo bảng customers
CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE
);

-- Tạo bảng orders
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        customer_id INT REFERENCES customers(id),
                        order_date DATE NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL
);