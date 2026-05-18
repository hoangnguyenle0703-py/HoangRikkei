package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Instructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InstructorRepository {
    private final List<Instructor> instructors = new ArrayList<>();

    public InstructorRepository() {
        // Khởi tạo sẵn dữ liệu mẫu
        instructors.add(new Instructor(1, "Nguyen Van A", "nguyenvana@university.edu"));
        instructors.add(new Instructor(2, "Tran Thi B", "tranthib@university.edu"));
    }

    // Lấy toàn bộ danh sách
    public List<Instructor> findAll() {
        return instructors;
    }

    // Tìm kiếm trả về Optional
    public Optional<Instructor> findById(int id) {
        return instructors.stream()
                .filter(instructor -> instructor.getId() == id)
                .findFirst();
    }

    // Thêm mới bản ghi
    public Instructor create(Instructor instructor) {
        instructors.add(instructor);
        return instructor;
    }

    // Cập nhật bản ghi - Ném ngoại lệ bằng orElseThrow nếu không tìm thấy ID
    public Instructor update(int id, Instructor updatedInstructor) {
        Instructor existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor không tồn tại với ID: " + id));

        existing.setName(updatedInstructor.getName());
        existing.setEmail(updatedInstructor.getEmail());

        return existing;
    }

    // Xóa bản ghi - Ném ngoại lệ bằng orElseThrow nếu không tìm thấy ID
    public Instructor deleteById(int id) {
        Instructor existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor không tồn tại với ID: " + id));

        instructors.remove(existing);
        return existing;
    }
}