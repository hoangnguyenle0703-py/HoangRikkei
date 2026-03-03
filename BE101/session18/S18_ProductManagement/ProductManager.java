import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class ProductManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n****************PRODUCT MANAGEMENT****************");
            System.out.println("1. Danh sách sản phẩm");
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm theo tên sản phẩm");
            System.out.println("6. Sắp xếp sản phẩm theo giá tăng dần");
            System.out.println("7. Thống kê số lượng sản phẩm theo danh mục");
            System.out.println("8. Thoát");
            System.out.print("Chọn chức năng (1-8): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Vui lòng nhập số từ 1 đến 8.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: displayAllProducts(); break;
                case 2: addProduct(); break;
                case 3: updateProduct(); break;
                case 4: deleteProduct(); break;
                case 5: searchProductsByName(); break;
                case 6: sortProductsByPriceAsc(); break;
                case 7: countProductsByCatalog(); break;
                case 8: System.out.println("Đã thoát chương trình."); break;
                default: System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 8);
        scanner.close();
    }

    private static void displayAllProducts() {
        System.out.println("\n--- Danh sách sản phẩm ---");
        // Note: We use SELECT * from the function because PostgreSQL functions return tables
        String sql = "SELECT * FROM get_all_products()";

        try (Connection conn = ConnectionUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("ID: %d | Name: %s | Price: %.2f | Title: %s | Created: %s | Catalog: %s | Status: %s%n",
                        rs.getInt("p_id"),
                        rs.getString("p_name"),
                        rs.getFloat("p_price"),
                        rs.getString("p_title"),
                        rs.getDate("p_created"),
                        rs.getString("p_catalog"),
                        rs.getString("p_status"));
            }
            if(!hasData) System.out.println("Không có sản phẩm nào trong hệ thống.");
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách: " + e.getMessage());
        }
    }

    private static void addProduct() {
        System.out.println("\n--- Thêm mới sản phẩm ---");

        String name = inputValidatedString("Tên sản phẩm (không rỗng): ", false);
        float price = inputValidatedFloat("Giá sản phẩm (> 0): ");
        String title = inputValidatedString("Tiêu đề sản phẩm: ", false);
        Date createdDate = inputValidatedDate("Ngày tạo (yyyy-MM-dd): ");
        String catalog = inputValidatedString("Danh mục: ", false);
        String status = inputValidatedString("Trạng thái (0 hoặc 1): ", false);

        String sql = "CALL add_product(?, ?, ?, ?, ?, ?::bit)";

        try (Connection conn = ConnectionUtils.getConnection()) {
            // Transaction management
            conn.setAutoCommit(false);

            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, name);
                cstmt.setFloat(2, price);
                cstmt.setString(3, title);
                cstmt.setDate(4, new java.sql.Date(createdDate.getTime()));
                cstmt.setString(5, catalog);
                cstmt.setString(6, status);

                cstmt.execute();
                conn.commit(); // Commit if successful
                System.out.println("Thêm sản phẩm thành công!");
            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                System.err.println("Lỗi khi thêm (Transaction rolled back): " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
    }

    private static void updateProduct() {
        System.out.println("\n--- Cập nhật sản phẩm ---");
        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        // Ideally, you should first check if the product exists using get_product_by_id

        String name = inputValidatedString("Tên sản phẩm mới: ", false);
        float price = inputValidatedFloat("Giá sản phẩm mới (> 0): ");
        String title = inputValidatedString("Tiêu đề sản phẩm mới: ", false);
        Date createdDate = inputValidatedDate("Ngày tạo mới (yyyy-MM-dd): ");
        String catalog = inputValidatedString("Danh mục mới: ", false);
        String status = inputValidatedString("Trạng thái mới (0 hoặc 1): ", false);

        String sql = "CALL update_product(?, ?, ?, ?, ?, ?, ?::bit)";

        try (Connection conn = ConnectionUtils.getConnection()) {
            conn.setAutoCommit(false);
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setInt(1, id);
                cstmt.setString(2, name);
                cstmt.setFloat(3, price);
                cstmt.setString(4, title);
                cstmt.setDate(5, new java.sql.Date(createdDate.getTime()));
                cstmt.setString(6, catalog);
                cstmt.setString(7, status);

                cstmt.execute();
                conn.commit();
                System.out.println("Cập nhật sản phẩm thành công!");
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Lỗi khi cập nhật (Transaction rolled back): " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
    }

    private static void deleteProduct() {
        System.out.println("\n--- Xóa sản phẩm ---");
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "CALL delete_product(?)";
        try (Connection conn = ConnectionUtils.getConnection()) {
            conn.setAutoCommit(false);
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setInt(1, id);
                cstmt.execute();
                conn.commit();
                System.out.println("Xóa sản phẩm thành công!");
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Lỗi khi xóa: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
    }

    private static void searchProductsByName() {
        System.out.println("\n--- Tìm kiếm sản phẩm ---");
        System.out.print("Nhập tên sản phẩm (tương đối): ");
        String keyword = scanner.nextLine();

        String sql = "SELECT * FROM search_products_by_name(?)";

        try (Connection conn = ConnectionUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, keyword);
            try(ResultSet rs = pstmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("ID: %d | Name: %s | Price: %.2f%n", rs.getInt("r_id"), rs.getString("r_name"), rs.getFloat("r_price"));
                }
                if (!found) System.out.println("Không tìm thấy sản phẩm nào khớp.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    private static void sortProductsByPriceAsc() {
        System.out.println("\n--- Sắp xếp sản phẩm theo giá tăng dần ---");
        // Since there isn't a specific procedure requested for sorting, we can just sort the output of the get_all_products function
        String sql = "SELECT * FROM get_all_products() ORDER BY p_price ASC";

        try (Connection conn = ConnectionUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Price: %.2f%n", rs.getInt("p_id"), rs.getString("p_name"), rs.getFloat("p_price"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi sắp xếp: " + e.getMessage());
        }
    }

    private static void countProductsByCatalog() {
        System.out.println("\n--- Thống kê số lượng sản phẩm theo danh mục ---");
        String sql = "SELECT * FROM get_product_count_by_catalog()";

        try (Connection conn = ConnectionUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Danh mục: %s | Số lượng: %d%n", rs.getString("catalog_name"), rs.getInt("product_count"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi thống kê: " + e.getMessage());
        }
    }

    // --- Helper methods for validation ---

    private static String inputValidatedString(String prompt, boolean allowEmpty) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!allowEmpty && input.isEmpty()) {
                System.out.println("Giá trị không được để trống. Vui lòng thử lại.");
            }
        } while (!allowEmpty && input.isEmpty());
        return input;
    }

    private static float inputValidatedFloat(String prompt) {
        float value = -1;
        while (true) {
            System.out.print(prompt);
            try {
                value = Float.parseFloat(scanner.nextLine());
                if (value > 0) {
                    break;
                } else {
                    System.out.println("Giá trị phải lớn hơn 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Định dạng số không hợp lệ.");
            }
        }
        return value;
    }

    private static Date inputValidatedDate(String prompt) {
        Date date = null;
        while (date == null) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                date = dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.println("Sai định dạng ngày tháng. Vui lòng nhập theo yyyy-MM-dd.");
            }
        }
        return date;
    }
}