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
            conn.setAutoCommit(false);

            psInvoice = conn.prepareStatement(sqlInvoice, PreparedStatement.RETURN_GENERATED_KEYS);
            psInvoice.setInt(1, invoice.getCustomerId());
            psInvoice.setDouble(2, invoice.getTotalAmount());
            int affectedRows = psInvoice.executeUpdate();

            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }

            int generatedInvoiceId = -1;
            rs = psInvoice.getGeneratedKeys();
            if (rs.next()) {
                generatedInvoiceId = rs.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            psDetail = conn.prepareStatement(sqlDetail);
            psUpdateStock = conn.prepareStatement(sqlUpdateStock);

            for (InvoiceDetails detail : details) {
                psDetail.setInt(1, generatedInvoiceId);
                psDetail.setInt(2, detail.getProductId());
                psDetail.setInt(3, detail.getQuantity());
                psDetail.setDouble(4, detail.getUnitPrice());
                psDetail.addBatch();

                psUpdateStock.setInt(1, detail.getQuantity());
                psUpdateStock.setInt(2, detail.getProductId());
                psUpdateStock.addBatch();
            }

            psDetail.executeBatch();
            psUpdateStock.executeBatch();

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Lỗi Transaction khi tạo hóa đơn: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                    System.err.println("Đã Rollback dữ liệu an toàn.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (psInvoice != null) psInvoice.close();
                if (psDetail != null) psDetail.close();
                if (psUpdateStock != null) psUpdateStock.close();
                if (conn != null) {
                    conn.setAutoCommit(true); 
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Invoice> searchByDate(String dateStr) {
        List<Invoice> invoices = new ArrayList<>();

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
