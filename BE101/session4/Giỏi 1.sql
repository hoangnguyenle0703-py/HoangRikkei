CREATE table school.students(
    id serial PRIMARY KEY ,
    full_name varchar(50),
    gender varchar(3) CHECK ( gender in ('Nam','Nữ') ),
    birth_year int,
    major varchar(50),
    gpa DECIMAL(10,2)
);

INSERT INTO school.students (full_name, gender, birth_year, major, gpa) VALUES
('Nguyễn Văn A', 'Nam', 2002, 'CNTT', 3.6),
('Trần Thị Bích Ngọc', 'Nữ', 2001, 'Kinh tế', 3.2),
('Lê Quốc Cường', 'Nam', 2003, 'CNTT', 2.7),
('Phạm Minh Anh', 'Nữ', 2000, 'Luật', 3.9),
('Nguyễn Văn A', 'Nam', 2002, 'CNTT', 3.6),
('Lưu Đức Tài', 'Nam', 2004, 'Cơ khí', NULL),
('Võ Thị Thu Hằng', 'Nữ', 2001, 'CNTT', 3.0);

INSERT INTO school.students (full_name, gender, birth_year, major, gpa) VALUES
('Phan Hoàng Nam','Nam',2003,'CNTT',3.8);

UPDATE school.students
SET gpa = 3.4
WHERE full_name = 'Lê Quốc Cường';

DELETE FROM school.students
WHERE gpa IS NULL;

SELECT full_name FROM school.students
WHERE major = 'CNTT' and gpa >= 3.0
LIMIT 3;

SELECT DISTINCT major FROM school.students;

SELECT full_name,gpa FROM school.students
ORDER BY gpa desc , full_name asc;

SELECT full_name FROM school.students
WHERE full_name LIKE 'Nguyễn%';

SELECT full_name,birth_year FROM school.students
WHERE birth_year between 2001 and 2003;