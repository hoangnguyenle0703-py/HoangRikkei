package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Instructor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InstructorRepository {
    private final List<Instructor> instructors = new ArrayList<>();

    public InstructorRepository() {
        // Chuẩn bị sẵn ít nhất 2 bản ghi
        instructors.add(new Instructor(1, "Nguyen Van A", "nguyenvana@university.edu"));
        instructors.add(new Instructor(2, "Tran Thi B", "tranthib@university.edu"));
    }

    public List<Instructor> findAll() {
        return instructors;
    }
}