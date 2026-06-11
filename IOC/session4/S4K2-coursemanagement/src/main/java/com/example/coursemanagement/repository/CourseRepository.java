package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Không cần viết bất kỳ code nào ở đây!
    // JpaRepository đã cung cấp sẵn findAll(), findById(), save(), deleteById()...
}