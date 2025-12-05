CREATE TABLE baitap.oldcustomers(
    id serial PRIMARY KEY ,
    name varchar(100),
    city varchar(50)
);

CREATE TABLE baitap.newcustomers(
    id serial PRIMARY KEY ,
    name varchar(100),
    city varchar(50)
);

INSERT INTO baitap.oldcustomers (name, city) VALUES
('Nguyễn Văn An', 'Hà Nội'),
('Trần Thị Bình', 'TP. Hồ Chí Minh'),
('Lê Văn Cường', 'Đà Nẵng'),
('Phạm Thị Duyên', 'Hải Phòng'),
('Hoàng Minh Hùng', 'Cần Thơ'),
('Đặng Thu Hương', 'Hà Nội'),
('Vũ Quốc Việt', 'TP. Hồ Chí Minh'),
('Trần Văn Bình', 'Hải Phòng'),
('Lê Thị Anh', 'Đà Nẵng'),
('Phạm Văn Lực', 'Hà Nội');

INSERT INTO baitap.newcustomers (name, city) VALUES
('Nguyễn Văn An', 'Hà Nội'),
('Nguyễn Thị Mai', 'Hải Phòng'),
('Trần Văn Bình', 'Hải Phòng'),
('Lê Thị Anh', 'Đà Nẵng'),
('Hoàng Minh Hùng', 'Cần Thơ'),
('Đỗ Văn Giang', 'Huế'),
('Chu Thị Liên', 'Nha Trang'),
('Phạm Công Danh', 'TP. Hồ Chí Minh'),
('Vũ Quốc Việt', 'TP. Hồ Chí Minh'),
('Bùi Văn Khanh', 'Hà Nội');

SELECT *
FROM baitap.oldcustomers
UNION
SELECT *
FROM baitap.newcustomers;

SELECT *
FROM baitap.oldcustomers
INTERSECT
SELECT *
FROM baitap.newcustomers;

WITH allcustomer AS (
    SELECT *
    FROM baitap.oldcustomers
    UNION
    SELECT *
    FROM baitap.newcustomers
)

SELECT city, count(id) FROM allcustomer
GROUP BY city
HAVING count(id) = (
    SELECT MAX(number_customer)
    FROM (
        SELECT count(id) as number_customer
        FROM allcustomer
        GROUP BY city
    ) as max_customer
);