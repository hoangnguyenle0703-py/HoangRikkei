package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {
    private final List<Course> courses = new ArrayList<>();

    public CourseRepository() {
        courses.add(new Course(101, "Java Programming", "Active", 1));
        courses.add(new Course(102, "Data Structures", "Upcoming", 2));
    }

    public List<Course> findAll() {
        return courses;
    }

    // 1. Áp dụng Optional<T> cho findById
    public Optional<Course> findById(int id) {
        return courses.stream()
                .filter(course -> course.getId() == id)
                .findFirst();
    }

    public Course create(Course course) {
        courses.add(course);
        return course;
    }

    // 2. Sử dụng orElseThrow() để ném ngoại lệ nếu không tìm thấy
    public Course update(int id, Course updatedCourse) {
        Course existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existing.setTitle(updatedCourse.getTitle());
        existing.setStatus(updatedCourse.getStatus());
        existing.setInstructorId(updatedCourse.getInstructorId());

        return existing;
    }

    // 3. Sử dụng orElseThrow() tương tự cho delete
    public Course deleteById(int id) {
        Course existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        courses.remove(existing);
        return existing;
    }
}