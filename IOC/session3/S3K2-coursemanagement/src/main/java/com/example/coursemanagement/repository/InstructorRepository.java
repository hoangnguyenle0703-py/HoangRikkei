package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Instructor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InstructorRepository {
    private final List<Instructor> instructors = new ArrayList<>();

    public InstructorRepository() {
        instructors.add(new Instructor(1, "Nguyen Van A", "nguyenvana@university.edu"));
        instructors.add(new Instructor(2, "Tran Thi B", "tranthib@university.edu"));
    }

    public List<Instructor> findAll() {
        return instructors;
    }

    public Instructor findById(int id) {
        return instructors.stream()
                .filter(instructor -> instructor.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Instructor create(Instructor instructor) {
        instructors.add(instructor);
        return instructor;
    }

    public Instructor update(int id, Instructor updatedInstructor) {
        Instructor existingInstructor = findById(id);
        if (existingInstructor != null) {
            existingInstructor.setName(updatedInstructor.getName());
            existingInstructor.setEmail(updatedInstructor.getEmail());
            return existingInstructor;
        }
        return null;
    }

    public boolean deleteById(int id) {
        return instructors.removeIf(instructor -> instructor.getId() == id);
    }
}