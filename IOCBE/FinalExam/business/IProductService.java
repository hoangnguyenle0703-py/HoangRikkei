package business;

import model.Product;
import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
    List<Product> searchByBrand(String brand);
    List<Product> searchByPriceRange(double minPrice, double maxPrice);
    List<Product> searchByStock(int stock);
}