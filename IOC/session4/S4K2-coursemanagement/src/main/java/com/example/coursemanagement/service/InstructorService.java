package com.example.coursemanagement.service;

import com.example.coursemanagement.model.Instructor;
import com.example.coursemanagement.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor không tồn tại với ID: " + id));
    }

    public Instructor createInstructor(Instructor instructor) {
        // JPA dùng chung hàm save() cho cả tác vụ tạo mới (Create) và cập nhật (Update)
        return instructorRepository.save(instructor);
    }

    public Instructor updateInstructor(Long id, Instructor updatedInstructor) {
        // Tìm kiếm bản ghi cũ xem có tồn tại không, nếu không sẽ tự động ném ngoại lệ
        Instructor existing = getInstructorById(id);

        // Gán các giá trị mới
        existing.setName(updatedInstructor.getName());
        existing.setEmail(updatedInstructor.getEmail());

        // Lưu đè lại vào database
        return instructorRepository.save(existing);
    }

    public Instructor deleteInstructorById(Long id) {
        // Tìm kiếm thực thể trước khi xóa
        Instructor existing = getInstructorById(id);
        // Tiến hành xóa khỏi database
        instructorRepository.delete(existing);
        // Trả về bản ghi vừa xóa phục vụ cấu trúc phản hồi cũ
        return existing;
    }
}