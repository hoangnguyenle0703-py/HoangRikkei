package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TaskRepository {

    private List<Task> tasks = new ArrayList<>(Arrays.asList(
            new Task(1, "Thiết kế Database", "Thiết kế các bảng cho hệ thống Sales", "high", 1),
            new Task(2, "Cài đặt Spring Boot", "Khởi tạo dự án và cấu hình Maven", "medium", 1),
            new Task(3, "Viết Unit Test", "Viết test cho UserController", "low", 2),
            new Task(4, "Tạo API Login", "Xây dựng chức năng đăng nhập", "high", 1),
            new Task(5, "Fix lỗi UI", "Sửa lỗi hiển thị trên màn hình Mobile", "medium", 2),
            new Task(6, "Viết tài liệu API", "Cập nhật file design.md", "low", 3),
            new Task(7, "Tối ưu Query", "Tối ưu tốc độ truy vấn danh sách Task", "high", 2),
            new Task(8, "Cấu hình Security", "Thiết lập phân quyền Admin/User", "high", 1),
            new Task(9, "Backup dữ liệu", "Lập lịch backup hàng tuần", "medium", 3),
            new Task(10, "Họp nhóm", "Thảo luận về tiến độ dự án", "low", 1)
    ));

    public List<Task> findAll() {
        return tasks;
    }

    public Task findById(int id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }
}