package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/phonestoremanagement";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load driver của PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Mở kết nối
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
        return conn;
    }

    // Hàm test thử kết nối
    public static void main(String[] args) {
        Connection testConn = DBUtil.getConnection();
        if (testConn != null) {
            System.out.println("Kết nối PostgreSQL thành công!");
        } else {
            System.out.println("Kết nối thất bại, hãy kiểm tra lại cấu hình.");
        }
    }
}