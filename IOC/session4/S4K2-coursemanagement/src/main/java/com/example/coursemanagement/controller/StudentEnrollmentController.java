package com.example.coursemanagement.controller;

import com.example.coursemanagement.dto.ApiResponse;
import com.example.coursemanagement.model.StudentEnrollment;
import com.example.coursemanagement.service.StudentEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class StudentEnrollmentController {
    private final StudentEnrollmentService studentEnrollmentService;

    @Autowired
    public StudentEnrollmentController(StudentEnrollmentService studentEnrollmentService) {
        this.studentEnrollmentService = studentEnrollmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentEnrollment>>> getAllEnrollments() {
        return ResponseEntity.ok(ApiResponse.success("Fetched enrollments successfully", studentEnrollmentService.getAllEnrollments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> getEnrollmentById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Fetched enrollment successfully", studentEnrollmentService.getEnrollmentById(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    // POST: Đăng ký học thông qua Request Body chứa ID
    @PostMapping
    public ResponseEntity<ApiResponse<StudentEnrollment>> createEnrollment(@RequestBody EnrollmentRequest request) {
        try {
            StudentEnrollment created = studentEnrollmentService.createEnrollment(request.getStudentId(), request.getCourseId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Enrollment created successfully", created));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> deleteEnrollment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Enrollment deleted successfully", studentEnrollmentService.deleteEnrollmentById(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    // Lớp Static DTO để hứng dữ liệu JSON từ Postman gửi lên
    public static class EnrollmentRequest {
        private Long studentId;
        private Long courseId;

        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
    }
}