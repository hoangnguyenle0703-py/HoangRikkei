CREATE TABLE baitap.customers(
    id serial PRIMARY KEY ,
    name varchar(100),
    email varchar(100),
    phone varchar(20),
    points int
);

INSERT INTO baitap.customers (name, email, phone, points) VALUES
('Nguyễn Hải Long', 'long.nh@example.com', '0912345678', 150),
('Trần Thanh Mai', 'mai.tt@example.com', '0901234567', 300),
('Lê Hoàng Phúc', 'phuc.lh@example.com', '0987654321', 50),
('Phạm Thị Thảo', NULL, '0966554433', 10),
('Hoàng Văn Việt', 'viet.hv@example.com', '0977889900', 450),
('Đỗ Minh Anh', 'anh.dm@example.com', '0944332211', 120),
('Vũ Quốc Bảo', 'bao.vq@example.com', '0933221100', 200);

SELECT DISTINCT name FROM baitap.customers;

SELECT * FROM baitap.customers
WHERE email ISNULL ;

SELECT * FROM baitap.customers
ORDER BY points desc
LIMIT 3 OFFSET 1;

SELECT * FROM baitap.customers
ORDER BY name desc ;