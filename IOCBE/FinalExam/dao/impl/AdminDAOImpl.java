package dao.impl;

import dao.IAdminDAO;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements IAdminDAO {

    @Override
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM Admin WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password); // Lưu ý: Thực tế nên hash password, nhưng làm đồ án cơ bản thì để text thường cũng được.

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có kết quả trả về nghĩa là đăng nhập đúng
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
        }
        return false;
    }
}