package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    public EnrollmentRepository() {
        // Chuẩn bị sẵn ít nhất 2 bản ghi
        enrollments.add(new Enrollment(1001, "Le Khac C", 101));
        enrollments.add(new Enrollment(1002, "Pham Thi D", 102));
    }

    public List<Enrollment> findAll() {
        return enrollments;
    }
}
