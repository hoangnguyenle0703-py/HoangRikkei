package business.impl;

import business.IProductService;
import dao.IProductDAO;
import dao.impl.ProductDAOImpl;
import model.Product;

import java.util.List;

public class ProductServiceImpl implements IProductService {

    // Khởi tạo đối tượng DAO để giao tiếp với PostgreSQL
    private final IProductDAO productDAO = new ProductDAOImpl();

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public boolean addProduct(Product product) {
        if (product.getPrice() <= 0) {
            System.err.println("Logic lỗi: Giá sản phẩm phải lớn hơn 0.");
            return false;
        }
        return productDAO.addProduct(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        // Hàm update trả về false nếu không tìm thấy ID để cập nhật
        return productDAO.updateProduct(product);
    }

    @Override
    public boolean deleteProduct(int id) {
        // false nếu ID không tồn tại
        return productDAO.deleteProduct(id);
    }

    @Override
    public List<Product> searchByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            System.err.println("Logic lỗi: Từ khóa tìm kiếm không hợp lệ.");
            return null;
        }
        return productDAO.searchByBrand(brand);
    }

    @Override
    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        if (minPrice > maxPrice) {
            System.err.println("Logic lỗi: Giá tối thiểu không được lớn hơn giá tối đa.");
            return null;
        }
        return productDAO.searchByPriceRange(minPrice, maxPrice);
    }

    @Override
    public List<Product> searchByStock(int exactStock) {
        if (exactStock < 0) {
            System.err.println("Logic lỗi: Số lượng tồn kho không được âm.");
            return null;
        }
        return productDAO.searchByStock(exactStock);
    }
}