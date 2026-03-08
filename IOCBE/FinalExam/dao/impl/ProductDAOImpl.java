package dao.impl;

import dao.IProductDAO;
import model.Product;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements IProductDAO {
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product ORDER BY id ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBrand(rs.getString("brand"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));

                products.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }
        return products;
    }

    @Override
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO Product(name, brand, price, stock) VALUES(?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getBrand());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        String sql = "UPDATE Product SET name = ?, brand = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getBrand());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getId());

            return ps.executeUpdate() > 0; 

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Product> searchByBrand(String brand) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE brand ILIKE ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + brand + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    );
                    products.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm theo nhãn hàng: " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM Product WHERE price BETWEEN ? AND ? ORDER BY price ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    );
                    products.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm theo khoảng giá: " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> searchByStock(int stock) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM Product WHERE stock = ? ORDER BY id ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, stock);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    );
                    products.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm theo tồn kho: " + e.getMessage());
        }
        return products;
    }
}
