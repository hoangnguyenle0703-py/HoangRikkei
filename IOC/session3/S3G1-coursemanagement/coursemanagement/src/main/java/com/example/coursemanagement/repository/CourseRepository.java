package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    public Course findById(int id) {
        return courses.stream()
                .filter(course -> course.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Course create(Course course) {
        courses.add(course);
        return course;
    }

    public Course update(int id, Course updatedCourse) {
        Course existingCourse = findById(id);
        if (existingCourse != null) {
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setStatus(updatedCourse.getStatus());
            existingCourse.setInstructorId(updatedCourse.getInstructorId());
            return existingCourse;
        }
        return null;
    }

    public boolean deleteById(int id) {
        return courses.removeIf(course -> course.getId() == id);
    }
}
