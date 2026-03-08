import java.sql.*;

public class OrderManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/shop_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // --- CÁC HÀM PHỤ TRỢ ---

    // Lấy giá sản phẩm để tính tổng tiền
    public double getProductPrice(int productId) throws SQLException {
        String sql = "SELECT price FROM products WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getDouble("price");
                else throw new SQLException("Không tìm thấy sản phẩm với ID = " + productId);
            }
        }
    }

    private boolean isCustomerExists(int customerId) throws SQLException {
        String sql = "SELECT id FROM customers WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) { return rs.next(); }
        }
    }

    // --- 5 CHỨC NĂNG CHÍNH ---

    // 1. Thêm sản phẩm (Kiểm tra trùng tên)
    public void addProduct(Product product) {
        String checkSql = "SELECT id FROM products WHERE name = ?";
        String insertSql = "INSERT INTO products (name, price) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, product.getName());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("-> Lỗi: Sản phẩm '" + product.getName() + "' đã tồn tại!");
                return;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, product.getName());
                insertStmt.setDouble(2, product.getPrice());
                insertStmt.executeUpdate();
                System.out.println("-> Thêm sản phẩm thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 2. Cập nhật khách hàng
    public void updateCustomer(int customerId, Customer customer) {
        try {
            if (!isCustomerExists(customerId)) {
                System.out.println("-> Lỗi: Khách hàng không tồn tại!");
                return;
            }
            String sql = "UPDATE customers SET name = ?, email = ? WHERE id = ?";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, customer.getName());
                stmt.setString(2, customer.getEmail());
                stmt.setInt(3, customerId);
                stmt.executeUpdate();
                System.out.println("-> Cập nhật khách hàng thành công!");
            }
        } catch (SQLException e) {
            // Xử lý lỗi trùng email (ràng buộc UNIQUE)
            if (e.getSQLState().equals("23505")) {
                System.out.println("-> Lỗi: Email '" + customer.getEmail() + "' đã được sử dụng bởi người khác!");
            } else {
                System.out.println("Lỗi CSDL: " + e.getMessage());
            }
        }
    }

    // 3. Tạo đơn hàng mới
    public void createOrder(Order order) {
        String sql = "INSERT INTO orders (customer_id, order_date, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setDate(2, order.getOrderDate());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.executeUpdate();
            System.out.println("-> Đã lưu đơn hàng thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo đơn hàng: " + e.getMessage());
        }
    }

    // 4. Liệt kê toàn bộ đơn hàng (Kèm tên KH)
    public void listAllOrders() {
        String sql = "SELECT o.id, c.name AS customer_name, o.order_date, o.total_amount " +
                "FROM orders o JOIN customers c ON o.customer_id = c.id ORDER BY o.id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            printOrderTable(rs);
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    // 5. Tìm kiếm đơn hàng theo khách hàng
    public void getOrdersByCustomer(int customerId) {
        String sql = "SELECT o.id, c.name AS customer_name, o.order_date, o.total_amount " +
                "FROM orders o JOIN customers c ON o.customer_id = c.id WHERE c.id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n--- ĐƠN HÀNG CỦA KHÁCH HÀNG ID: " + customerId + " ---");
                printOrderTable(rs);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    private void printOrderTable(ResultSet rs) throws SQLException {
        System.out.printf("%-5s | %-25s | %-15s | %-15s\n", "ID", "Tên Khách Hàng", "Ngày Đặt", "Tổng Tiền");
        System.out.println("-----------------------------------------------------------------------");
        boolean hasData = false;
        while (rs.next()) {
            hasData = true;
            System.out.printf("%-5d | %-25s | %-15s | %-15.2f\n",
                    rs.getInt("id"), rs.getString("customer_name"),
                    rs.getDate("order_date").toString(), rs.getDouble("total_amount"));
        }
        if (!hasData) System.out.println("Không có đơn hàng nào.");
    }
}