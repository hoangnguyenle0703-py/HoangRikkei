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
        return courseRepository.findAll(); // Hàm có sẵn của JPA
    }

    public Course getCourseById(Long id) { // Đổi int thành Long
        return courseRepository.findById(id) // Hàm có sẵn của JPA trả về Optional
                .orElseThrow(() -> new RuntimeException("Course không tồn tại với ID: " + id));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course); // JPA dùng chung hàm save() cho tạo mới
    }

    public Course updateCourse(Long id, Course updatedCourse) { // Đổi int thành Long
        // Tìm bản ghi cũ
        Course existing = getCourseById(id);

        // Cập nhật thông tin
        existing.setTitle(updatedCourse.getTitle());
        existing.setStatus(updatedCourse.getStatus());
        existing.setInstructor(updatedCourse.getInstructor());

        // Lưu lại vào DB
        return courseRepository.save(existing);
    }

    public Course deleteCourseById(Long id) { // Đổi int thành Long
        Course existing = getCourseById(id); // Kiểm tra tồn tại trước khi xóa
        courseRepository.delete(existing); // Xóa khỏi DB
        return existing; // Trả về bản ghi đã xóa theo yêu cầu Controller cũ
    }
}