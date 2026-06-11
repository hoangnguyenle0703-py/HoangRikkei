package com.example.coursemanagement.service;

import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.model.Student;
import com.example.coursemanagement.model.StudentEnrollment;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.StudentEnrollmentRepository;
import com.example.coursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentEnrollmentService {
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentEnrollmentService(StudentEnrollmentRepository studentEnrollmentRepository,
                                    StudentRepository studentRepository,
                                    CourseRepository courseRepository) {
        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<StudentEnrollment> getAllEnrollments() {
        return studentEnrollmentRepository.findAll();
    }

    public StudentEnrollment getEnrollmentById(Long id) {
        return studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lượt đăng ký không tồn tại với ID: " + id));
    }

    // Xử lý logic nghiệp vụ: Tìm Student và Course trước khi tạo lượt đăng ký
    public StudentEnrollment createEnrollment(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại với ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại với ID: " + courseId));

        StudentEnrollment enrollment = new StudentEnrollment(student, course);
        return studentEnrollmentRepository.save(enrollment);
    }

    public StudentEnrollment deleteEnrollmentById(Long id) {
        StudentEnrollment existing = getEnrollmentById(id);
        studentEnrollmentRepository.delete(existing);
        return existing;
    }
}