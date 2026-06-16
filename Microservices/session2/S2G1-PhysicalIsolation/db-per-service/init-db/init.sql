-- =====================================================================
--  Khởi tạo HAI DATABASE RIÊNG BIỆT trên cùng một PostgreSQL instance.
--
--  Đây là cốt lõi của nguyên tắc "Database per Service": mỗi service sở
--  hữu một database vật lý độc lập (physical isolation). Hai database
--  hoàn toàn tách biệt — không chia sẻ bảng, schema hay connection.
-- =====================================================================

-- Database cho User-Service
CREATE DATABASE user_db;

-- Database cho Inventory-Service
CREATE DATABASE inventory_db;

-- (Tùy chọn) Tạo user riêng cho từng service để cô lập quyền truy cập.
-- Mỗi service chỉ có quyền trên database của chính nó → nếu credentials
-- của service này lộ, service kia vẫn an toàn.

CREATE USER user_service_app WITH PASSWORD 'user_pass';
CREATE USER inventory_service_app WITH PASSWORD 'inventory_pass';

GRANT ALL PRIVILEGES ON DATABASE user_db TO user_service_app;
GRANT ALL PRIVILEGES ON DATABASE inventory_db TO inventory_service_app;
