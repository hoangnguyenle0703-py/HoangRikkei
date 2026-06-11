package com.example.coursemanagement.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity // Đánh dấu đây là một JPA Entity
@Table(name = "courses") // Đặt tên bảng trong database là "courses"
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID (Auto Increment)
    private Long id;

    @Column(nullable = false) // Bắt buộc không được để trống (NOT NULL)
    private String title;

    @Enumerated(EnumType.STRING) // Lưu enum dưới dạng chuỗi (String) thay vì số thứ tự
    private CourseStatus status;

    // Quan hệ N-1: Nhiều Khóa học thuộc về 1 Giảng viên
    // Thay thế "Long instructorId" bằng tham chiếu đối tượng trực tiếp
    @ManyToOne
    @JoinColumn(name = "instructor_id") // Tạo cột khóa ngoại chuẩn snake_case
    private Instructor instructor;

    // Quan hệ 1-N: 1 Khóa học có thể có nhiều lượt đăng ký
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudentEnrollment> enrollments = new ArrayList<>();

    // Constructors
    public Course() {
    }

    public Course(Long id, String title, CourseStatus status, Long instructorId) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public List<StudentEnrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<StudentEnrollment> enrollments) { this.enrollments = enrollments; }
}