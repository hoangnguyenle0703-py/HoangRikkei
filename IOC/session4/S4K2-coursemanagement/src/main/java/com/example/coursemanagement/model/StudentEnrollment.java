package com.example.coursemanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student_enrollments") // Bảng dạng số nhiều & snake_case
public class StudentEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tham chiếu tới Student
    @ManyToOne
    @JoinColumn(name = "student_id") // Khóa ngoại có hậu tố _id
    private Student student;

    // Tham chiếu tới Course
    @ManyToOne
    @JoinColumn(name = "course_id") // Khóa ngoại có hậu tố _id
    private Course course;

    // Constructors
    public StudentEnrollment() {}

    public StudentEnrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}