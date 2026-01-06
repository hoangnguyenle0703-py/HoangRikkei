create schema university;
create table university.Students(
    student_id serial Primary Key,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    birth_date DATE,
    email varchar(50) NOT NULL UNIQUE
);

create table university.Courses(
    course_id serial PRIMARY KEY ,
    course_name varchar(100) NOT NULL ,
    credits int
);

create table university.Enrollments(
    enrollment_id serial PRIMARY KEY ,
    student_id serial references university.Students(student_id),
    course_id serial references university.Courses(course_id),
    enroll_date date
);