package presentation;

import business.IProductService;
import business.impl.ProductServiceImpl;
import model.Product;
import utils.InputValidator;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    
    private final IProductService productService = new ProductServiceImpl();

    public void displayMenu() {
        while (true) {
            System.out.println("\n========= QUẢN LÝ SẢN PHẨM =========");
            System.out.println("1. Hiển thị danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm mới");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Xóa sản phẩm theo ID");
            System.out.println("5. Tìm kiếm theo Brand (Nhãn hàng)");
            System.out.println("6. Tìm kiếm theo khoảng giá");
            System.out.println("7. Tìm kiếm theo tồn kho");
            System.out.println("8. Quay lại menu chính");
            System.out.println("====================================");

            int choice = InputValidator.getInt("Nhập lựa chọn: ", "Lỗi: Vui lòng nhập số từ 1 đến 8!", 1, 8);

            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchByBrand();
                    break;
                case 6:
                    searchByPriceRange();
                    break;
                case 7:
                    searchByStock();
                    break;
                case 8:
                    return;
            }
        }
    }

    // Hiển thị danh sách
    private void displayAllProducts() {
        System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
        List<Product> products = productService.getAllProducts();
        printProductTable(products);
    }

    // Thêm sản phẩm
    private void addProduct() {
        System.out.println("\n--- THÊM SẢN PHẨM MỚI ---");
        String name = InputValidator.getString("Nhập tên sản phẩm: ", "Tên không được để trống!");
        String brand = InputValidator.getString("Nhập nhãn hàng: ", "Nhãn hàng không được để trống!");
        double price = InputValidator.getDouble("Nhập giá tiền: ", "Giá tiền không hợp lệ!", 0);
        int stock = InputValidator.getInt("Nhập số lượng tồn kho: ", "Tồn kho không hợp lệ!", 0, Integer.MAX_VALUE);

        Product newProduct = new Product(0, name, brand, price, stock);
        if (productService.addProduct(newProduct)) {
            System.out.println("=> Thêm sản phẩm thành công!");
        } else {
            System.err.println("=> Thêm sản phẩm thất bại!");
        }
    }

    // Cập nhật sản phẩm
    private void updateProduct() {
        System.out.println("\n--- CẬP NHẬT SẢN PHẨM ---");
        int id = InputValidator.getInt("Nhập ID sản phẩm cần cập nhật: ", "ID phải là số nguyên!", 1, Integer.MAX_VALUE);

        List<Product> currentProducts = productService.getAllProducts();
        Product foundProduct = currentProducts.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        if (foundProduct == null) {
            System.err.println("=> Không tìm thấy sản phẩm có ID: " + id);
            return;
        }

        System.out.println("Thông tin hiện tại: " + foundProduct.getName() + " | " + foundProduct.getBrand() + " | Giá: " + foundProduct.getPrice());

        System.out.println("Nhập thông tin mới (trừ ID):");
        String name = InputValidator.getString("Nhập tên mới: ", "Tên không được để trống!");
        String brand = InputValidator.getString("Nhập nhãn hàng mới: ", "Nhãn hàng không được để trống!");
        double price = InputValidator.getDouble("Nhập giá tiền mới: ", "Giá tiền không hợp lệ!", 0);
        int stock = InputValidator.getInt("Nhập tồn kho mới: ", "Tồn kho không hợp lệ!", 0, Integer.MAX_VALUE);

        Product updateProduct = new Product(id, name, brand, price, stock);
        if (productService.updateProduct(updateProduct)) {
            System.out.println("=> Cập nhật thành công!");
        } else {
            System.err.println("=> Cập nhật thất bại!");
        }
    }

    // Xóa sản phẩm
    private void deleteProduct() {
        System.out.println("\n--- XÓA SẢN PHẨM ---");
        int id = InputValidator.getInt("Nhập ID sản phẩm cần xóa: ", "ID phải là số nguyên!", 1, Integer.MAX_VALUE);

        String confirm = InputValidator.getString("Bạn có chắc chắn muốn xóa? (Y/N): ", "Vui lòng nhập Y hoặc N!");
        if (confirm.equalsIgnoreCase("Y")) {
            if (productService.deleteProduct(id)) {
                System.out.println("=> Đã xóa sản phẩm thành công!");
            } else {
                System.err.println("=> Xóa thất bại! (ID không tồn tại hoặc lỗi CSDL)");
            }
        } else {
            System.out.println("=> Đã hủy thao tác xóa.");
        }
    }

    // Tìm kiếm theo Brand
    private void searchByBrand() {
        System.out.println("\n--- TÌM KIẾM THEO NHÃN HÀNG ---");
        String brand = InputValidator.getString("Nhập từ khóa nhãn hàng: ", "Từ khóa không được để trống!");
        List<Product> products = productService.searchByBrand(brand);
        printProductTable(products);
    }

    // Tìm kiếm theo khoảng giá
    private void searchByPriceRange() {
        System.out.println("\n--- TÌM KIẾM THEO KHOẢNG GIÁ ---");
        double min = InputValidator.getDouble("Nhập giá tối thiểu: ", "Giá không hợp lệ!", 0);
        double max = InputValidator.getDouble("Nhập giá tối đa: ", "Giá không hợp lệ!", min);
        List<Product> products = productService.searchByPriceRange(min, max);
        printProductTable(products);
    }

    // 7. Tìm kiếm theo tồn kho
    private void searchByStock() {
        System.out.println("\n--- TÌM KIẾM SẢN PHẨM THEO TỒN KHO ---");
        int exactStock = InputValidator.getInt("Nhập chính xác số lượng tồn kho cần tìm: ", "Vui lòng nhập số nguyên hợp lệ!", 0, Integer.MAX_VALUE);

        List<Product> products = productService.searchByStock(exactStock);
        if (products == null || products.isEmpty()) {
            System.out.println("=> Không có sản phẩm nào có số lượng tồn kho đúng bằng " + exactStock + ".");
            return;
        }
        printProductTable(products);
    }

    // Hàm để in bảng cho đẹp
    private void printProductTable(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("=> Không tìm thấy sản phẩm nào.");
            return;
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-25s | %-15s | %-12s | %-7s |\n", "ID", "Tên sản phẩm", "Nhãn hàng", "Giá tiền", "Tồn kho");
        System.out.println("--------------------------------------------------------------------------------");
        for (Product p : products) {
            System.out.printf("| %-5d | %-25s | %-15s | %-12.2f | %-7d |\n",
                    p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
}
