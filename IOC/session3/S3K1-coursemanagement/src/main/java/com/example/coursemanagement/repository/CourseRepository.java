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
}
