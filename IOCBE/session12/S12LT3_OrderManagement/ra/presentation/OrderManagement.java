package ra.presentation;

import ra.business.OrderBusiness;
import java.util.Scanner;

public class OrderManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderBusiness business = new OrderBusiness();

        while (true) {
            System.out.println("\n**************** QUẢN LÝ ĐƠN HÀNG ****************");
            System.out.println("1. Thêm đơn hàng");
            System.out.println("2. Hiển thị danh sách đơn hàng");
            System.out.println("3. Cập nhật trạng thái đơn hàng theo mã");
            System.out.println("4. Xóa đơn hàng theo mã (Chỉ Pending)");
            System.out.println("5. Tìm kiếm đơn hàng theo tên khách hàng");
            System.out.println("6. Thống kê tổng số đơn hàng");
            System.out.println("7. Thống kê doanh thu đơn hàng Delivered");
            System.out.println("8. Thống kê số lượng đơn hàng theo trạng thái");
            System.out.println("9. Tìm đơn hàng có giá trị lớn nhất");
            System.out.println("0. Thoát");
            System.out.println("**************************************************");
            System.out.print("Lựa chọn của bạn: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> business.addOrder(scanner);
                    case 2 -> business.displayOrders();
                    case 3 -> business.updateOrderStatus(scanner);
                    case 4 -> business.deleteOrder(scanner);
                    case 5 -> business.searchByCustomer(scanner);
                    case 6, 7, 8 -> business.showStatistics(); // Gộp chung hiển thị thống kê
                    case 9 -> business.findMaxOrder();
                    case 0 -> {
                        System.out.println("Thoát chương trình.");
                        System.exit(0);
                    }
                    default -> System.err.println("Lựa chọn sai (0-9).");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số.");
            } catch (Exception e) {
                System.err.println("Lỗi hệ thống: " + e.getMessage());
            }
        }
    }
}