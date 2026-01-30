package ra.business;

import ra.entity.Order;
import ra.entity.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

public class OrderBusiness {
    private List<Order> orders = new ArrayList<>();

    // 1. Thêm đơn hàng
    public void addOrder(Scanner scanner) {
        System.out.print("Nhập số lượng đơn hàng cần thêm: ");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                System.out.println("Nhập thông tin đơn hàng thứ " + (i + 1) + ":");
                Order order = new Order();
                order.inputData(scanner);
                orders.add(order);
            }
            System.out.println("Thêm thành công!");
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên.");
        }
    }

    // 2. Hiển thị (Sắp xếp giảm dần theo giá trị)
    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("Danh sách trống.");
            return;
        }
        System.out.println("--- DANH SÁCH ĐƠN HÀNG (Giá trị giảm dần) ---");
        orders.stream()
                .sorted(Comparator.comparingDouble(Order::getOrderAmount).reversed()) // Sắp xếp giảm dần
                .forEach(System.out::println);
    }

    // 3. Cập nhật trạng thái
    public void updateOrderStatus(Scanner scanner) {
        System.out.print("Nhập mã đơn hàng cần cập nhật: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Optional<Order> optOrder = findOrderById(id);

            if (optOrder.isPresent()) {
                Order order = optOrder.get();
                System.out.println("Trạng thái hiện tại: " + order.getStatus());
                System.out.println("Chọn trạng thái mới: 1. Pending | 2. Shipped | 3. Delivered");
                System.out.print("Lựa chọn: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> order.setStatus(OrderStatus.PENDING);
                    case 2 -> order.setStatus(OrderStatus.SHIPPED);
                    case 3 -> order.setStatus(OrderStatus.DELIVERED);
                    default -> System.err.println("Lựa chọn không hợp lệ.");
                }
                System.out.println("Cập nhật thành công!");
            } else {
                System.err.println("Không tìm thấy mã đơn hàng.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi nhập liệu.");
        }
    }

    // 4. Xóa đơn hàng (Chỉ xóa nếu status là Pending)
    public void deleteOrder(Scanner scanner) {
        System.out.print("Nhập mã đơn hàng cần xóa: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Optional<Order> optOrder = findOrderById(id);

            if (optOrder.isPresent()) {
                Order order = optOrder.get();
                if (order.getStatus() == OrderStatus.PENDING) {
                    orders.remove(order);
                    System.out.println("Xóa thành công.");
                } else {
                    System.err.println("Lỗi: Chỉ được xóa đơn hàng ở trạng thái Pending!");
                }
            } else {
                System.err.println("Không tìm thấy mã đơn hàng.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi nhập liệu.");
        }
    }

    // 5. Tìm kiếm theo tên khách hàng
    public void searchByCustomer(Scanner scanner) {
        System.out.print("Nhập tên khách hàng: ");
        String keyword = scanner.nextLine().toLowerCase();

        List<Order> result = orders.stream()
                .filter(o -> o.getCustomerName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (result.isEmpty()) System.out.println("Không tìm thấy kết quả.");
        else result.forEach(System.out::println);
    }

    // 6. - 9. Các chức năng thống kê
    public void showStatistics() {
        System.out.println("--- THỐNG KÊ ---");

        // 6. Tổng số đơn hàng
        System.out.println("Tổng số lượng đơn hàng: " + orders.size());

        // 7. Tổng doanh thu từ các đơn Delivered
        double totalRevenue = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(Order::getOrderAmount)
                .sum();
        System.out.printf("Tổng doanh thu (Delivered): %,.0f đ\n", totalRevenue);

        // 8. Số lượng đơn theo từng trạng thái (Grouping)
        Map<OrderStatus, Long> stats = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
        System.out.println("Chi tiết theo trạng thái: " + stats);
    }

    // 9. Tìm đơn hàng giá trị lớn nhất
    public void findMaxOrder() {
        Optional<Order> maxOrder = orders.stream()
                .max(Comparator.comparingDouble(Order::getOrderAmount));

        if (maxOrder.isPresent()) {
            System.out.println("Đơn hàng giá trị cao nhất: ");
            System.out.println(maxOrder.get());
        } else {
            System.out.println("Danh sách trống.");
        }
    }

    // Hàm phụ trợ tìm ID
    private Optional<Order> findOrderById(int id) {
        return orders.stream().filter(o -> o.getOrderId() == id).findFirst();
    }
}