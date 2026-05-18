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

    // Service ném lỗi bằng orElseThrow nếu tìm không thấy
    public Course getCourseById(int id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course createCourse(Course course) {
        return courseRepository.create(course);
    }

    public Course updateCourse(int id, Course course) {
        // Tầng repository đã xử lý ném lỗi orElseThrow, ta chỉ việc gọi
        return courseRepository.update(id, course);
    }

    public Course deleteCourseById(int id) {
        // Tương tự, repository sẽ ném lỗi nếu id không tồn tại
        return courseRepository.deleteById(id);
    }
}