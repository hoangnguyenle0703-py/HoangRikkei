# Thiết kế hệ thống quản lý Task và User (RESTful API)

## 1. Quản lý Người dùng (Users)
- **Lấy danh sách người dùng**: `GET /users`
- **Tạo mới người dùng**: `POST /users`
- **Cập nhật vai trò**: `PATCH /users/{id}/role`
- **Xóa người dùng**: `DELETE /users/{id}`

## 2. Quản lý Công việc (Tasks)
- **Lấy toàn bộ công việc**: `GET /tasks`
- **Tạo mới công việc**: `POST /tasks`
- **Cập nhật trạng thái**: `PATCH /tasks/{id}/status`
- **Xóa công việc**: `DELETE /tasks/{id}`

## 3. Truy vấn & Liên kết
- **Tìm task ưu tiên "high"**: `GET /tasks?priority=high`
- **Task "high" của user ID 1**: `GET /users/1/tasks?priority=high`
- **Liệt kê task của 1 user**: `GET /users/{id}/tasks`
- **Gắn task cho user**: `PUT /users/{userId}/tasks/{taskId}`