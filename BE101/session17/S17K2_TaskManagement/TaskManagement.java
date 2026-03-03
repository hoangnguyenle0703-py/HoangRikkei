import java.sql.*;

public class TaskManagement {
    private static final String URL = "jdbc:postgresql://localhost:5432/todo_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 1. Thêm công việc
    public void addTask(String taskName, String status) {
        String sql = "CALL add_task(?, ?)";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, taskName);
            stmt.setString(2, status);
            stmt.execute();
            System.out.println("-> Thêm công việc thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 2. Liệt kê công việc
    public void listTasks() {
        String sql = "SELECT * FROM list_tasks()";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- DANH SÁCH CÔNG VIỆC ---");
            printTaskData(rs);
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 3. Cập nhật trạng thái
    public void updateTaskStatus(int taskId, String status) {
        String sql = "CALL update_task_status(?, ?)";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, taskId);
            stmt.setString(2, status);
            stmt.execute();
            System.out.println("-> Lệnh cập nhật trạng thái đã được thực thi!");
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 4. Xóa công việc
    public void deleteTask(int taskId) {
        String sql = "CALL delete_task(?)";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, taskId);
            stmt.execute();
            System.out.println("-> Lệnh xóa công việc đã được thực thi!");
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 5. Tìm kiếm công việc
    public void searchTaskByName(String taskName) {
        String sql = "SELECT * FROM search_task_by_name(?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taskName);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");
                printTaskData(rs);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 6. Thống kê công việc
    public void taskStatistics() {
        String sql = "SELECT * FROM task_statistics()";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                long completed = rs.getLong("completed");
                long pending = rs.getLong("pending");
                System.out.println("\n--- THỐNG KÊ CÔNG VIỆC ---");
                System.out.println("- Số công việc đã hoàn thành: " + completed);
                System.out.println("- Số công việc chưa hoàn thành: " + pending);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // Hàm hỗ trợ in bảng dữ liệu dùng chung
    private void printTaskData(ResultSet rs) throws SQLException {
        System.out.printf("%-5s | %-30s | %-20s\n", "ID", "Tên công việc", "Trạng thái");
        System.out.println("--------------------------------------------------------------");
        boolean hasData = false;
        while (rs.next()) {
            hasData = true;
            System.out.printf("%-5d | %-30s | %-20s\n",
                    rs.getInt("id"),
                    rs.getString("task_name"),
                    rs.getString("status"));
        }
        if (!hasData) {
            System.out.println("Không có dữ liệu.");
        }
    }
}