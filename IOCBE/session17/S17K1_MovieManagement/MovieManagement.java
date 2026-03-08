import java.sql.*;

public class MovieManagement {
    private static final String URL = "jdbc:postgresql://localhost:5432/movie_management";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 1. Thêm phim
    public void addMovie(String title, String director, int year) {
        String sql = "CALL add_movie(?, ?, ?)";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, director);
            stmt.setInt(3, year);
            stmt.execute();
            System.out.println("-> Đã thêm phim thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm phim: " + e.getMessage());
        }
    }

    // 2. Liệt kê phim
    public void listMovies() {
        String sql = "SELECT * FROM list_movies()";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- DANH SÁCH PHIM ---");
            System.out.printf("%-5s | %-30s | %-20s | %-5s\n", "ID", "Tiêu đề", "Đạo diễn", "Năm");
            System.out.println("----------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-5d | %-30s | %-20s | %-5d\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getInt("year"));
            }
            if (!hasData) {
                System.out.println("Chưa có bộ phim nào trong cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách: " + e.getMessage());
        }
    }

    // 3. Cập nhật phim
    public void updateMovie(int id, String title, String director, int year) {
        String sql = "CALL update_movie(?, ?, ?, ?)";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, title);
            stmt.setString(3, director);
            stmt.setInt(4, year);
            stmt.execute();
            System.out.println("-> Đã gọi thủ tục cập nhật thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật phim: " + e.getMessage());
        }
    }

    // 4. Xóa phim
    public void deleteMovie(int id) {
        String sql = "CALL delete_movie(?)";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("-> Đã gọi thủ tục xóa thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa phim: " + e.getMessage());
        }
    }
}