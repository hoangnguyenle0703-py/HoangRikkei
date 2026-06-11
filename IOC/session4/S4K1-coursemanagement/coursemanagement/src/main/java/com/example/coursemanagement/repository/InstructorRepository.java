package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // JpaRepository đã tự động cung cấp sẵn các hàm CRUD cơ bản như:
    // findAll(), findById(), save(), delete(),... không cần viết code thủ công nữa.
}