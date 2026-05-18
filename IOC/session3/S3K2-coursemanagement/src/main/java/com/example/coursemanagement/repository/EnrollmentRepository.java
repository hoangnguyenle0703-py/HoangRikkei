package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Enrollment;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    public EnrollmentRepository() {
        enrollments.add(new Enrollment(1001, "Le Khac C", 101));
        enrollments.add(new Enrollment(1002, "Pham Thi D", 102));
    }

    public List<Enrollment> findAll() {
        return enrollments;
    }

    public Enrollment findById(int id) {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Enrollment create(Enrollment enrollment) {
        enrollments.add(enrollment);
        return enrollment;
    }

    public Enrollment update(int id, Enrollment updatedEnrollment) {
        Enrollment existingEnrollment = findById(id);
        if (existingEnrollment != null) {
            existingEnrollment.setStudentName(updatedEnrollment.getStudentName());
            existingEnrollment.setCourseId(updatedEnrollment.getCourseId());
            return existingEnrollment;
        }
        return null;
    }

    public boolean deleteById(int id) {
        return enrollments.removeIf(enrollment -> enrollment.getId() == id);
    }
}