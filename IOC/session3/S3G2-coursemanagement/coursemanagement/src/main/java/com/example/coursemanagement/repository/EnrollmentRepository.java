package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    public EnrollmentRepository() {
        // Khởi tạo sẵn dữ liệu mẫu
        enrollments.add(new Enrollment(1001, "Le Khac C", 101));
        enrollments.add(new Enrollment(1002, "Pham Thi D", 102));
    }

    // Lấy toàn bộ danh sách đăng ký
    public List<Enrollment> findAll() {
        return enrollments;
    }

    // Tìm kiếm trả về Optional
    public Optional<Enrollment> findById(int id) {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getId() == id)
                .findFirst();
    }

    // Thêm mới lượt đăng ký
    public Enrollment create(Enrollment enrollment) {
        enrollments.add(enrollment);
        return enrollment;
    }

    // Cập nhật lượt đăng ký - Ném ngoại lệ bằng orElseThrow nếu không tìm thấy ID
    public Enrollment update(int id, Enrollment updatedEnrollment) {
        Enrollment existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment không tồn tại với ID: " + id));

        existing.setStudentName(updatedEnrollment.getStudentName());
        existing.setCourseId(updatedEnrollment.getCourseId());

        return existing;
    }

    // Xóa lượt đăng ký - Ném ngoại lệ bằng orElseThrow nếu không tìm thấy ID
    public Enrollment deleteById(int id) {
        Enrollment existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment không tồn tại với ID: " + id));

        enrollments.remove(existing);
        return existing;
    }
}