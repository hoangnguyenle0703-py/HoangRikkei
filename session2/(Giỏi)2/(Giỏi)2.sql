create schema elearning;

create table elearning.Students(
    student_id serial PRIMARY KEY ,
    first_name varchar(50) NOT NULL ,
    last_name varchar(50) NOT NULL ,
    email varchar(50) UNIQUE NOT NULL
);

create table elearning.Instructors(
    instructor_id serial PRIMARY KEY ,
    first_name varchar(50) NOT NULL ,
    last_name varchar(50) NOT NULL ,
    email varchar(50) UNIQUE NOT NULL
);

create table elearning.Course(
    course_id serial PRIMARY KEY ,
    course_name varchar(100) NOT NULL ,
    instructor_id serial references elearning.Instructors(instructor_id)
);

create table elearning.Enrollments(
    enrollments_id serial PRIMARY KEY ,
    student_id serial references elearning.Students(student_id),
    course_id serial references elearning.Course(course_id),
    enroll_date date NOT NULL
);

create table elearning.Assignments(
    assignment_id serial PRIMARY KEY ,
    course_id serial references elearning.Course(course_id),
    title varchar(100) NOT NULL ,
    due_date date NOT NULL
);

create table elearning.Submissions(
    submission_id serial PRIMARY KEY ,
    assignment_id serial references elearning.Assignments(assignment_id),
    student_id serial references elearning.Students(student_id),
    submission_date date NOT NULL ,
    grade int check(grade between 0 and 100)
);

ALTER table elearning.Submissions
    ALTER column grade type decimal;