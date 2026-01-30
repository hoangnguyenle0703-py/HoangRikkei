package ra.business;

import ra.entity.Product;
import java.util.*;

public class ProductBusiness {
    private List<Product> productList = new ArrayList<>();

    public void addProduct(Scanner scanner) {
        System.out.print("Nhập số lượng sản phẩm cần thêm: ");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                Product p = new Product();
                p.inputData(scanner);
                productList.add(p);
                System.out.println("Đã thêm sản phẩm thành công.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên hợp lệ.");
        }
    }

    public void displayProducts() {
        if (productList.isEmpty()) {
            System.out.println("Danh sách sản phẩm đang trống.");
            return;
        }
        System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
        for (Product p : productList) {
            System.out.println(p.toString());
        }
    }

    public void updateProduct(Scanner scanner) {
        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
        try {
            String idStr = scanner.nextLine();
            Product p = findById(idStr);
            if (p != null) {
                System.out.println("Đã tìm thấy sản phẩm. Nhập thông tin mới:");
                p.inputData(scanner);
                System.out.println("Cập nhật thành công.");
            } else {
                System.out.println("Không tìm thấy mã sản phẩm " + idStr);
            }
        } catch (Exception e) {
            System.err.println("Lỗi cập nhật: " + e.getMessage());
        }
    }

    public void deleteProduct(Scanner scanner) {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String idStr = scanner.nextLine();
        Product p = findById(idStr);
        if (p != null) {
            productList.remove(p);
            System.out.println("Đã xóa sản phẩm thành công.");
        } else {
            System.out.println("Không tìm thấy mã sản phẩm để xóa.");
        }
    }

    public void searchByName(Scanner scanner) {
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String keyword = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Product p : productList) {
            if (p.getInfo("Product").toLowerCase().contains(keyword)) {
                System.out.println(p.toString());
                found = true;
            }
        }
        if (!found) System.out.println("Không tìm thấy sản phẩm nào khớp với từ khóa.");
    }

    public void sortByPriceAsc() {
        productList.sort(Comparator.comparingDouble(p -> Double.parseDouble(p.getInfo("Price"))));
        System.out.println("Đã sắp xếp danh sách theo giá tăng dần.");
    }

    public void sortByQuantityDesc() {
        productList.sort((p1, p2) -> {
            int q1 = Integer.parseInt(p1.getInfo("Quantity"));
            int q2 = Integer.parseInt(p2.getInfo("Quantity"));
            return Integer.compare(q2, q1); // Giảm dần
        });
        System.out.println("Đã sắp xếp danh sách theo số lượng giảm dần.");
    }

    private Product findById(String id) {
        for (Product p : productList) {
            if (p.getInfo("Id").equals(id)) {
                return p;
            }
        }
        return null;
    }
}
