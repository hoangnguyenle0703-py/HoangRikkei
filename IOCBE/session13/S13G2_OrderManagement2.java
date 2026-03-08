import java.util.ArrayList;
import java.util.Scanner;

public class S13G2_OrderManagement2 {

    interface Manage<T> {
        void add(T item);
        void update(int index, T item);
        void delete(int index);
        void display();
    }

    static class Order {
        private String orderId;
        private String customerName;

        public Order(String orderId, String customerName) {
            this.orderId = orderId;
            this.customerName = customerName;
        }

        public String getOrderId() {
            return orderId;
        }

        @Override
        public String toString() {
            return String.format("Mã ĐH: %-10s | Khách hàng: %s", orderId, customerName);
        }
    }

    static class OrderManager implements Manage<Order> {
        private ArrayList<Order> orderList = new ArrayList<>();

        @Override
        public void add(Order item) {
            orderList.add(item);
            System.out.println("Thêm đơn hàng thành công!");
        }

        @Override
        public void update(int index, Order item) {
            if (index >= 0 && index < orderList.size()) {
                orderList.set(index, item);
                System.out.println("Cập nhật đơn hàng thành công!");
            }
        }

        @Override
        public void delete(int index) {
            if (index >= 0 && index < orderList.size()) {
                Order removed = orderList.remove(index);
                System.out.println("Đã xóa đơn hàng: " + removed.getOrderId());
            }
        }

        @Override
        public void display() {
            if (orderList.isEmpty()) {
                System.out.println("Danh sách đơn hàng trống.");
            } else {
                System.out.println("--- DANH SÁCH ĐƠN HÀNG ---");
                for (Order order : orderList) {
                    System.out.println(order); // Không in Index nữa
                }
            }
        }

        public int findIndexById(String id) {
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).getOrderId().equalsIgnoreCase(id)) {
                    return i;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrderManager manager = new OrderManager();

        while (true) {
            System.out.println("\n=== QUẢN LÝ ĐƠN HÀNG ===");
            System.out.println("1. Thêm đơn hàng");
            System.out.println("2. Sửa đơn hàng ");
            System.out.println("3. Xóa đơn hàng ");
            System.out.println("4. Hiển thị danh sách");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> {
                        System.out.print("Nhập mã đơn hàng: ");
                        String id = sc.nextLine();
                        System.out.print("Nhập tên khách hàng: ");
                        String name = sc.nextLine();
                        manager.add(new Order(id, name));
                    }
                    case 2 -> {
                        System.out.print("Nhập MÃ đơn hàng cần sửa: ");
                        String idToUpdate = sc.nextLine();

                        // Tìm index bằng mã
                        int idx = manager.findIndexById(idToUpdate);

                        if (idx != -1) {
                            System.out.print("Nhập tên khách hàng mới: ");
                            String newName = sc.nextLine();

                            manager.update(idx, new Order(idToUpdate, newName));
                        } else {
                            System.out.println("Lỗi: Không tìm thấy mã đơn hàng '" + idToUpdate + "'.");
                        }
                    }
                    case 3 -> {
                        System.out.print("Nhập MÃ đơn hàng cần xóa: ");
                        String idToDelete = sc.nextLine();

                        int idx = manager.findIndexById(idToDelete);

                        if (idx != -1) {
                            manager.delete(idx);
                        } else {
                            System.out.println("Lỗi: Không tìm thấy mã đơn hàng '" + idToDelete + "'.");
                        }
                    }
                    case 4 -> manager.display();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Vui lòng chọn từ 0-4");
                }
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu! Vui lòng nhập đúng định dạng.");
            }
        }
    }
}