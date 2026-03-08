import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    // URL kết nối cho PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Hàm phụ: Kiểm tra xem sách có tồn tại không (dựa trên tên và tác giả)
    private boolean isBookExists(String title, String author) throws SQLException {
        String sql = "SELECT id FROM books WHERE title = ? AND author = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Trả về true nếu có dữ liệu
            }
        }
    }

    // Hàm phụ: Kiểm tra xem ID có tồn tại không
    private boolean isIdExists(int id) throws SQLException {
        String sql = "SELECT id FROM books WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // 1. Thêm sách mới
    public void addBook(Book book) {
        try {
            if (isBookExists(book.getTitle(), book.getAuthor())) {
                System.out.println("-> Lỗi: Sách này (trùng tên và tác giả) đã tồn tại trong thư viện!");
                return;
            }

            String sql = "INSERT INTO books (title, author, published_year, price) VALUES (?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setInt(3, book.getPublishedYear());
                stmt.setDouble(4, book.getPrice());
                stmt.executeUpdate();
                System.out.println("-> Thêm sách thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 2. Liệt kê toàn bộ sách
    public void listAllBooks() {
        String sql = "SELECT * FROM books ORDER BY id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- DANH SÁCH TOÀN BỘ SÁCH ---");
            printTable(rs);

        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 3. Cập nhật thông tin sách
    public void updateBook(int id, Book book) {
        try {
            if (!isIdExists(id)) {
                System.out.println("-> Lỗi: Không tìm thấy sách với ID = " + id);
                return;
            }

            String sql = "UPDATE books SET title = ?, author = ?, published_year = ?, price = ? WHERE id = ?";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setInt(3, book.getPublishedYear());
                stmt.setDouble(4, book.getPrice());
                stmt.setInt(5, id);
                stmt.executeUpdate();
                System.out.println("-> Cập nhật sách thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 4. Xóa sách
    public void deleteBook(int id) {
        try {
            if (!isIdExists(id)) {
                System.out.println("-> Lỗi: Không tìm thấy sách với ID = " + id + " để xóa.");
                return;
            }

            String sql = "DELETE FROM books WHERE id = ?";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("-> Xóa sách thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 5. Tìm kiếm theo tác giả
    public void findBooksByAuthor(String author) {
        // Dùng ILIKE trong PostgreSQL để tìm kiếm không phân biệt hoa thường
        String sql = "SELECT * FROM books WHERE author ILIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + author + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");
                printTable(rs);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // Hàm in giao diện bảng dùng chung
    private void printTable(ResultSet rs) throws SQLException {
        System.out.printf("%-5s | %-30s | %-20s | %-10s | %-10s\n", "ID", "Tựa sách", "Tác giả", "Năm XB", "Giá");
        System.out.println("---------------------------------------------------------------------------------------");
        boolean hasData = false;
        while (rs.next()) {
            hasData = true;
            System.out.printf("%-5d | %-30s | %-20s | %-10d | %-10.2f\n",
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("published_year"),
                    rs.getDouble("price"));
        }
        if (!hasData) {
            System.out.println("Không có dữ liệu.");
        }
    }
}