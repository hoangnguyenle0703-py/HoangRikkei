import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderManager manager = new OrderManager();
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n===== QUẢN LÝ ĐƠN HÀNG (POSTGRESQL) =====");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Cập nhật thông tin khách hàng");
            System.out.println("3. Tạo đơn hàng mới");
            System.out.println("4. Hiển thị danh sách toàn bộ đơn hàng");
            System.out.println("5. Tìm kiếm đơn hàng theo khách hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("-> Lỗi: Vui lòng nhập số!"); // Xử lý sai kiểu
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Nhập tên sản phẩm: ");
                        String pName = scanner.nextLine().trim();
                        if (pName.isEmpty()) throw new Exception("Tên sản phẩm không được trống!"); // Lỗi để trống

                        System.out.print("Nhập giá sản phẩm: ");
                        double pPrice = Double.parseDouble(scanner.nextLine().trim()); // Lỗi sai kiểu

                        manager.addProduct(new Product(pName, pPrice));
                    } catch (NumberFormatException e) {
                        System.out.println("-> Lỗi: Giá phải là con số!");
                    } catch (Exception e) {
                        System.out.println("-> Lỗi: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Nhập ID khách hàng cần sửa: ");
                        int cId = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Nhập tên mới: ");
                        String cName = scanner.nextLine().trim();
                        if (cName.isEmpty()) throw new Exception("Tên không được trống!"); // Lỗi để trống

                        System.out.print("Nhập email mới: ");
                        String cEmail = scanner.nextLine().trim();
                        if (cEmail.isEmpty()) throw new Exception("Email không được trống!");

                        manager.updateCustomer(cId, new Customer(cName, cEmail));
                    } catch (NumberFormatException e) {
                        System.out.println("-> Lỗi: ID khách hàng phải là số!");
                    } catch (Exception e) {
                        System.out.println("-> Lỗi: " + e.getMessage());
                    }
                    break;

                case 3: // Logic tính tổng tiền
                    try {
                        System.out.print("Nhập ID khách hàng mua: ");
                        int orderCusId = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Nhập ID sản phẩm cần mua: ");
                        int orderProdId = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Nhập số lượng: ");
                        int quantity = Integer.parseInt(scanner.nextLine().trim());
                        if (quantity <= 0) throw new Exception("Số lượng phải lớn hơn 0!");

                        // Lấy giá và tính tổng
                        double price = manager.getProductPrice(orderProdId);
                        double totalAmount = price * quantity;

                        System.out.println("-> Tạm tính: " + price + " x " + quantity + " = " + totalAmount);

                        // Tạo order với ngày hiện tại
                        Order newOrder = new Order(orderCusId, Date.valueOf(LocalDate.now()), totalAmount);
                        manager.createOrder(newOrder);

                    } catch (NumberFormatException e) {
                        System.out.println("-> Lỗi: ID và Số lượng phải là số!"); // Lỗi sai kiểu
                    } catch (Exception e) {
                        System.out.println("-> Lỗi: " + e.getMessage());
                    }
                    break;

                case 4:
                    manager.listAllOrders();
                    break;

                case 5:
                    try {
                        System.out.print("Nhập ID khách hàng để xem đơn: ");
                        int searchCusId = Integer.parseInt(scanner.nextLine().trim());
                        manager.getOrdersByCustomer(searchCusId);
                    } catch (NumberFormatException e) {
                        System.out.println("-> Lỗi: ID khách hàng phải là số!");
                    }
                    break;

                case 0:
                    System.out.println("Đang thoát...");
                    break;

                default:
                    System.out.println("-> Lựa chọn không hợp lệ!");
            }
        }
        scanner.close();
    }
}