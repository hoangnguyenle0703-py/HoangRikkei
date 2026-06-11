package com.example.coursemanagement.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students") // Chuẩn hóa tên bảng số nhiều snake_case
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // Quan hệ 1-N: 1 Sinh viên có thể có nhiều lượt đăng ký
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentEnrollment> enrollments = new ArrayList<>();

    // Constructors
    public Student() {}

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<StudentEnrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<StudentEnrollment> enrollments) { this.enrollments = enrollments; }
}