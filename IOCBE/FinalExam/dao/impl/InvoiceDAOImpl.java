package dao.impl;

import dao.IInvoiceDAO;
import model.Invoice;
import model.InvoiceDetails;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImpl implements IInvoiceDAO {

    @Override
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoice ORDER BY created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Invoice inv = new Invoice();
                inv.setId(rs.getInt("id"));
                inv.setCustomerId(rs.getInt("customer_id"));

                // Lấy ngày giờ từ PostgreSQL (yêu cầu thuộc tính createdAt trong model là LocalDateTime)
                java.sql.Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    inv.setCreatedAt(timestamp.toLocalDateTime());
                }

                inv.setTotalAmount(rs.getDouble("total_amount"));
                invoices.add(inv);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }
        return invoices;
    }

    @Override
    public List<Invoice> searchByCustomerName(String customerName) {
        List<Invoice> invoices = new ArrayList<>();
        // Kết hợp (JOIN) bảng Invoice và Customer để tìm theo tên
        String sql = "SELECT i.* FROM Invoice i JOIN Customer c ON i.customer_id = c.id WHERE c.name ILIKE ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + customerName + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoice inv = new Invoice();
                    inv.setId(rs.getInt("id"));
                    inv.setCustomerId(rs.getInt("customer_id"));
                    java.sql.Timestamp timestamp = rs.getTimestamp("created_at");
                    if (timestamp != null) {
                        inv.setCreatedAt(timestamp.toLocalDateTime());
                    }
                    inv.setTotalAmount(rs.getDouble("total_amount"));
                    invoices.add(inv);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn: " + e.getMessage());
        }
        return invoices;
    }

    @Override
    public boolean addInvoice(Invoice invoice, List<InvoiceDetails> details) {
        // Cột ID và created_at sẽ do PostgreSQL tự động sinh ra
        String sqlInvoice = "INSERT INTO Invoice(customer_id, total_amount) VALUES(?, ?)";
        String sqlDetail = "INSERT INTO Invoice_details(invoice_id, product_id, quantity, unit_price) VALUES(?, ?, ?, ?)";
        String sqlUpdateStock = "UPDATE Product SET stock = stock - ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement psInvoice = null;
        PreparedStatement psDetail = null;
        PreparedStatement psUpdateStock = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            // TẮT AUTO-COMMIT: Bắt đầu một Transaction
            conn.setAutoCommit(false);

            // Thêm Hóa đơn vào bảng Invoice
            // Sử dụng RETURN_GENERATED_KEYS để lấy ID của hóa đơn vừa tạo
            psInvoice = conn.prepareStatement(sqlInvoice, PreparedStatement.RETURN_GENERATED_KEYS);
            psInvoice.setInt(1, invoice.getCustomerId());
            psInvoice.setDouble(2, invoice.getTotalAmount());
            int affectedRows = psInvoice.executeUpdate();

            if (affectedRows == 0) {
                conn.rollback(); // Hủy bỏ nếu không thêm được
                return false;
            }

            // Lấy ID hóa đơn (invoice_id) vừa được PostgreSQL tự động tạo ra
            int generatedInvoiceId = -1;
            rs = psInvoice.getGeneratedKeys();
            if (rs.next()) {
                generatedInvoiceId = rs.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            // Thêm từng sản phẩm vào bảng Invoice_details
            psDetail = conn.prepareStatement(sqlDetail);
            psUpdateStock = conn.prepareStatement(sqlUpdateStock);

            for (InvoiceDetails detail : details) {
                // Thêm chi tiết
                psDetail.setInt(1, generatedInvoiceId);
                psDetail.setInt(2, detail.getProductId());
                psDetail.setInt(3, detail.getQuantity());
                psDetail.setDouble(4, detail.getUnitPrice());
                psDetail.addBatch(); // Gom lệnh lại để chạy 1 lần cho tối ưu hiệu suất

                // Trừ số lượng tồn kho trong bảng Product
                psUpdateStock.setInt(1, detail.getQuantity());
                psUpdateStock.setInt(2, detail.getProductId());
                psUpdateStock.addBatch();
            }

            // Thực thi toàn bộ lệnh Insert chi tiết và Update tồn kho
            psDetail.executeBatch();
            psUpdateStock.executeBatch();

            // Kết thúc Transaction thành công
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Lỗi Transaction khi tạo hóa đơn: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback(); // NẾU CÓ BẤT KỲ LỖI GÌ -> KHÔI PHỤC LẠI TRẠNG THÁI BAN ĐẦU
                    System.err.println("Đã Rollback dữ liệu an toàn.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Đóng các resource thủ công vì không dùng try-with-resources cho toàn bộ được
            try {
                if (rs != null) rs.close();
                if (psInvoice != null) psInvoice.close();
                if (psDetail != null) psDetail.close();
                if (psUpdateStock != null) psUpdateStock.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Trả lại trạng thái mặc định cho Connection
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Bên trong InvoiceDAOImpl.java
    public List<Invoice> searchByDate(String dateStr) {
        List<Invoice> invoices = new ArrayList<>();
        // Ép kiểu created_at về dạng Text (YYYY-MM-DD) để so sánh với chuỗi nhập vào
        String sql = "SELECT * FROM Invoice WHERE TO_CHAR(created_at, 'YYYY-MM-DD') = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dateStr);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoice inv = new Invoice();
                    inv.setId(rs.getInt("id"));
                    inv.setCustomerId(rs.getInt("customer_id"));
                    inv.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    inv.setTotalAmount(rs.getDouble("total_amount"));
                    invoices.add(inv);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn theo ngày: " + e.getMessage());
        }
        return invoices;
    }
}