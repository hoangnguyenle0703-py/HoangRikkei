create schema school;

create table school.Students(
    student_id serial PRIMARY KEY ,
    name varchar(100) NOT NULL ,
    dob date NOT NULL
);

create table school.Courses(
    course_id serial PRIMARY KEY ,
    course_name varchar(100) NOT NULL ,
    credits int
);

create table school.Enrollments(
    enrollments_id serial PRIMARY KEY ,
    student_id serial references school.Students(student_id),
    course_id serial references school.Courses(course_id),
    grade varchar(1) check (grade in ('A','B','C','D','F'))
);