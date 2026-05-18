package com.example.coursemanagement.service;

import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.create(course);
    }

    public Course updateCourse(int id, Course course) {
        return courseRepository.update(id, course);
    }

    public Course deleteCourseById(int id) {
        // Lấy bản ghi trước khi xóa để trả về cho controller
        Course courseToDelete = courseRepository.findById(id);
        if (courseToDelete != null) {
            courseRepository.deleteById(id);
            return courseToDelete;
        }
        return null;
    }
}