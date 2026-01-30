package ra.entity;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Order {
    private static int currentId = 0; // Biến static để tự tăng ID

    private int orderId;
    private String customerName;
    private String phoneNumber;
    private String address;
    private double orderAmount;
    private OrderStatus status;

    public Order() {
        this.orderId = ++currentId; // Tự động tăng ID khi tạo mới
        this.status = OrderStatus.PENDING; // Mặc định là Pending
    }

    // Getters & Setters
    public int getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public double getOrderAmount() { return orderAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    // Input Data
    public void inputData(Scanner scanner) {
        // 1. Tên khách hàng (6-100 ký tự)
        while (true) {
            System.out.print("Nhập tên khách hàng (6-100 ký tự): ");
            String name = scanner.nextLine().trim();
            if (name.length() >= 6 && name.length() <= 100) {
                this.customerName = name;
                break;
            }
            System.err.println("Tên không hợp lệ.");
        }

        // 2. Số điện thoại (VN format)
        while (true) {
            System.out.print("Nhập SĐT (VD: 0912345678): ");
            String phone = scanner.nextLine().trim();
            // Regex: Bắt đầu 0, theo sau là 3/5/7/8/9, và 8 số nữa
            if (Pattern.matches("(0[3|5|7|8|9])+([0-9]{8})", phone)) {
                this.phoneNumber = phone;
                break;
            }
            System.err.println("SĐT không đúng định dạng VN.");
        }

        // 3. Địa chỉ (Không được để trống)
        while (true) {
            System.out.print("Nhập địa chỉ giao hàng: ");
            String addr = scanner.nextLine().trim();
            if (!addr.isEmpty()) {
                this.address = addr;
                break;
            }
            System.err.println("Địa chỉ không được để trống.");
        }

        // 4. Giá trị đơn hàng (> 0)
        while (true) {
            System.out.print("Nhập giá trị đơn hàng: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0) {
                    this.orderAmount = amount;
                    break;
                }
                System.err.println("Giá trị phải lớn hơn 0.");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số.");
            }
        }
    }

    @Override
    public String toString() {
        // Format tiền tệ cho đẹp (VD: 100,000 đ)
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return String.format("ID: %d | Khách: %-15s | SĐT: %-10s | Giá: %-12s | Trạng thái: %s",
                orderId, customerName, phoneNumber, currencyFormat.format(orderAmount), status);
    }
}