package com.example.coursemanagement.controller;

import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Đổi từ @Controller sang @RestController để hỗ trợ REST API
@RequestMapping("/api/courses") // Định tuyến cơ sở cho endpoint
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Lấy danh sách (GET /api/courses) - Status: 200 OK
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Lấy chi tiết theo id (GET /api/courses/{id}) - Status: 200 OK hoặc 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Tạo mới dữ liệu (POST /api/courses) - Status: 201 Created
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    // Cập nhật thông tin (PUT /api/courses/{id}) - Status: 200 OK hoặc 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        if (updatedCourse != null) {
            return ResponseEntity.ok(updatedCourse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Xóa dữ liệu theo id (DELETE /api/courses/{id}) - Status: 200 OK hoặc 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
        Course deletedCourse = courseService.deleteCourseById(id);
        if (deletedCourse != null) {
            // Có thể dùng ResponseEntity.noContent().build() (204) nhưng đề bài yêu cầu trả về bản ghi bị xóa
            return ResponseEntity.ok(deletedCourse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}