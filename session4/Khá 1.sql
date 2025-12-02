create schema school;

create table school.Students(
    id serial PRIMARY KEY ,
    name varchar(50) ,
    age INT,
    major varchar(50),
    gpa decimal(3,2)
);

INSERT into school.Students (name, age, major, gpa) VALUES
('An', 20, 'CNTT', 3.5),
('Bình', 21, 'Toán', 3.2),
('Cường', 22, 'CNTT', 3.8),
('Dương', 20, 'Vật lý', 3.0),
('Em', 21, 'CNTT', 2.9);

INSERT into school.Students(name,age,major,gpa) VALUES
('Hùng',23,'Hóa Học',3.4);

UPDATE school.Students
SET gpa = 3.6
WHERE name = 'Bình';

DELETE FROM school.Students
WHERE gpa < 3.0;

SELECT name, major FROM school.Students
ORDER BY gpa desc;

SELECT DISTINCT name FROM school.Students
WHERE major = 'CNTT';

SELECT name FROM school.Students
WHERE gpa between 3.0 and 3.6;

SELECT name FROM school.Students
WHERE name LIKE 'C%';

SELECT name FROM school.Students
ORDER BY name ASC
LIMIT 3 OFFSET 1;