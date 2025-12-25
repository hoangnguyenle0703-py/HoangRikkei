--Tạo bảng
CREATE TABLE Customer(
    customer_id VARCHAR(5) PRIMARY KEY NOT NULL ,
    customer_full_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100) UNIQUE NOT NULL ,
    customer_phone VARCHAR(15) NOT NULL ,
    customer_address VARCHAR(255) NOT NULL
);

CREATE TABLE Room(
    room_id VARCHAR(5) PRIMARY KEY NOT NULL ,
    room_type VARCHAR(50) NOT NULL ,
    room_price DECIMAL(10,2) NOT NULL ,
    room_status VARCHAR(20) NOT NULL ,
    room_area INT NOT NULL
);

CREATE TABLE Booking(
    booking_id SERIAL PRIMARY KEY NOT NULL ,
    customer_id VARCHAR(5) REFERENCES Customer(customer_id) NOT NULL ,
    room_id VARCHAR(5) REFERENCES Room(room_id) NOT NULL ,
    check_in_date DATE NOT NULL ,
    check_out_date DATE NOT NULL ,
    total_amount DECIMAL(10,2)
);

CREATE TABLE Payment(
    payment_id SERIAL PRIMARY KEY NOT NULL ,
    booking_id INT REFERENCES Booking(booking_id) NOT NULL ,
    payment_method VARCHAR(50) NOT NULL ,
    payment_date DATE NOT NULL ,
    payment_amount DECIMAL(10,2) NOT NULL
);

--Chèn dữ liệu
INSERT INTO Customer(customer_id, customer_full_name, customer_email, customer_phone, customer_address)
VALUES ('C001','Nguyen Anh Tu','tu.nguyen@example.com','0912345678','Hanoi, Vietnam'),
       ('C002','Tran Thi Mai','mai.tran@example.com','0923456789','Ho Chi Minh, Vietnam'),
       ('C003','Le Minh Hoang','hoang.le@example.com','0934567890','Danang, Vietnam'),
       ('C004','Pham Hoang Nam','nam.pham@example.com','0945678901','Hue, Vietnam'),
       ('C005','Vu Minh Thu','thu.vu@example.com','0956789012','Hai Phong, Vietnam');

INSERT INTO Room(room_id, room_type, room_price, room_status, room_area)
VALUES ('R001','Single',100.0,'Available',25),
       ('R002','Double',150.0,'Booked',40),
       ('R003','Suite',250.0,'Available',60),
       ('R004','Single',120.0,'Booked',30),
       ('R005','Double',160.0,'Available',35);

INSERT INTO Booking(customer_id, room_id, check_in_date, check_out_date, total_amount)
VALUES ('C001','R001','2025-03-01','2025-03-05',400.0),
       ('C002','R002','2025-03-02','2025-03-06',600.0),
       ('C003','R003','2025-03-03','2025-03-07',1000.0),
       ('C004','R004','2025-03-04','2025-03-08',480.0),
       ('C005','R005','2025-03-05','2025-03-09',800.0);

INSERT INTO Payment(booking_id, payment_method, payment_date, payment_amount)
VALUES (1,'Cash','2025-03-05',400.0),
       (2,'Credit Card','2025-03-06',600.0),
       (3,'Bank Transfer','2025-03-07',1000.0),
       (4,'Cash','2025-03-08',480.0),
       (5,'Credit Card','2025-03-09',800.0);

-- Câu 3: Cập nhật
UPDATE Booking
SET total_amount = total_amount * 0.9
WHERE check_in_date < '2025-03-03';

-- Câu 4: Xóa
DELETE FROM payment
WHERE payment_method = 'Cash' AND payment_amount < 500;

-- Câu 5
SELECT customer_id,customer_full_name,customer_email,customer_phone
FROM Customer
ORDER BY customer_full_name desc;

-- Câu 6
SELECT room_id,room_type,room_price,room_area
FROM Room
ORDER BY room_area desc;

-- Câu 7
SELECT customer_full_name,room_id,check_in_date,check_out_date
FROM Booking b
JOIN Customer c on c.customer_id = b.customer_id;

-- Câu 8
SELECT b.customer_id,customer_full_name,payment_method,payment_amount
FROM payment p
JOIN Booking b ON p.booking_id = b.booking_id
JOIN Customer c ON c.customer_id = b.customer_id
ORDER BY payment_amount ASC;

-- Câu 9
SELECT * FROM Customer
ORDER BY customer_full_name desc
LIMIT 3 OFFSET 1;

-- Câu 10
SELECT c.customer_id,customer_full_name,count(booking_id) as booking_count
FROM Customer c
JOIN Booking b ON c.customer_id = b.customer_id
GROUP BY c.customer_id
HAVING count(booking_id) > 1;

-- Câu 11
SELECT r.room_id,room_type,room_price,count(booking_id) as booking_count
FROM Room r
JOIN Booking b ON b.room_id = r.room_id
GROUP BY r.room_id
HAVING count(booking_id) > 2;

-- Câu 12
SELECT c.customer_id,customer_full_name,r.room_id,total_amount
FROM Customer c
JOIN Booking b ON c.customer_id = b.customer_id
JOIN Payment p ON p.booking_id = b.booking_id
JOIN Room r ON r.room_id = b.room_id
WHERE total_amount > 1000;

-- Câu 13
SELECT customer_id,customer_full_name,customer_email,customer_phone
FROM Customer
WHERE customer_full_name LIKE '%Minh%' OR customer_address LIKE 'Hanoi%';

-- Câu 14
SELECT room_id,room_type,room_price
FROM Room
ORDER BY room_price desc
LIMIT 5 OFFSET 5;

-- Câu 15
CREATE OR REPLACE VIEW v_bookings AS
    SELECT b.room_id,room_type,b.customer_id,customer_full_name
    FROM Booking b
    JOIN Room r ON r.room_id = b.room_id
    JOIN Customer c ON c.customer_id = b.customer_id
    WHERE check_in_date < '2025-03-04';

SELECT * FROM v_bookings;

-- Câu 16
CREATE VIEW v_large_room_booked AS
    SELECT b.customer_id,customer_full_name,b.room_id,room_area,check_in_date
    FROM Booking b
    JOIN Customer c ON c.customer_id = b.customer_id
    JOIN Room r ON b.room_id = r.room_id
    WHERE room_area > 30;

SELECT * FROM v_large_room_booked;

-- Câu 17
CREATE OR REPLACE FUNCTION check_insert_booking()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        IF (NEW.check_in_date > NEW.check_out_date) THEN
            RAISE EXCEPTION 'Ngày đặt phòng không thể sau ngày trả phòng được!';
        ELSE RETURN NEW;
        end if;
    end;
$$;

CREATE TRIGGER trg_check_insert_booking
BEFORE INSERT ON Booking
FOR EACH ROW
EXECUTE FUNCTION check_insert_booking();

-- Câu 18
CREATE OR REPLACE FUNCTION update_room_status_on_booking()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        UPDATE Room
        SET room_status = 'Booked'
        WHERE room_id = NEW.room_id;
        RETURN NEW;
    end;
$$;

CREATE OR REPLACE TRIGGER trg_update_room_status_on_booking
AFTER INSERT ON Booking
FOR EACH ROW
EXECUTE FUNCTION update_room_status_on_booking();

-- Câu 19
CREATE PROCEDURE add_customer(
    c_customer_id VARCHAR(5),
    c_customer_full_name VARCHAR(100),
    c_customer_email VARCHAR(100),
    c_customer_phone VARCHAR(15),
    c_customer_address VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
    BEGIN
        INSERT INTO Customer(customer_id, customer_full_name, customer_email, customer_phone, customer_address)
        VALUES (c_customer_id,c_customer_full_name,c_customer_email,c_customer_phone,c_customer_address);
    end;
$$;

-- Câu 20
CREATE OR REPLACE PROCEDURE add_payment(
    p_booking_id INT,
    p_payment_method VARCHAR(50),
    p_payment_amount DECIMAL(10,2),
    p_payment_date DATE
)
LANGUAGE plpgsql
AS $$
    BEGIN
        INSERT INTO Payment(booking_id, payment_method, payment_date, payment_amount)
        VALUES (p_booking_id,p_payment_method,p_payment_date,p_payment_amount);
    end;
$$;