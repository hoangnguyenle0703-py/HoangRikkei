package presentation;

import business.ICustomerService;
import business.impl.CustomerServiceImpl;
import model.Customer;
import utils.InputValidator;

import java.util.List;

public class CustomerView {
    // Gọi tầng Service để xử lý logic và thao tác với Database
    private final ICustomerService customerService = new CustomerServiceImpl();

    public void displayMenu() {
        while (true) {
            System.out.println("\n========= QUẢN LÝ KHÁCH HÀNG =========");
            System.out.println("1. Hiển thị danh sách khách hàng");
            System.out.println("2. Thêm khách hàng mới");
            System.out.println("3. Cập nhật thông tin khách hàng");
            System.out.println("4. Xóa khách hàng theo ID");
            System.out.println("5. Quay lại menu chính");
            System.out.println("======================================");

            int choice = InputValidator.getInt("Nhập lựa chọn: ", "Lỗi: Vui lòng nhập số từ 1 đến 5!", 1, 5);

            switch (choice) {
                case 1:
                    displayAllCustomers();
                    break;
                case 2:
                    addCustomer();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:
                    return;
            }
        }
    }

    // Hiển thị danh sách
    private void displayAllCustomers() {
        System.out.println("\n--- DANH SÁCH KHÁCH HÀNG ---");
        List<Customer> customers = customerService.getAllCustomers();
        printCustomerTable(customers);
    }

    // Thêm khách hàng
    private void addCustomer() {
        System.out.println("\n--- THÊM KHÁCH HÀNG MỚI ---");
        String name = InputValidator.getString("Nhập tên khách hàng: ", "Tên không được để trống!");
        String phone = InputValidator.getString("Nhập số điện thoại: ", "Vui lòng nhập số điện thoại!");
        String email = InputValidator.getString("Nhập email: ", "Vui lòng nhập email!");
        String address = InputValidator.getString("Nhập địa chỉ: ", "Vui lòng nhập địa chỉ!");

        Customer newCustomer = new Customer(0, name, phone, email, address); // ID tự tăng nên truyền 0
        if (customerService.addCustomer(newCustomer)) {
            System.out.println("=> Thêm khách hàng thành công!");
        } else {
            System.err.println("=> Thêm khách hàng thất bại! (Có thể email đã tồn tại)");
        }
    }

    // Cập nhật thông tin khách hàng
    private void updateCustomer() {
        System.out.println("\n--- CẬP NHẬT THÔNG TIN KHÁCH HÀNG ---");
        int id = InputValidator.getInt("Nhập ID khách hàng cần cập nhật: ", "ID phải là số nguyên!", 1, Integer.MAX_VALUE);

        // Lấy danh sách để kiểm tra xem ID có tồn tại không
        List<Customer> currentCustomers = customerService.getAllCustomers();
        Customer foundCustomer = currentCustomers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);

        if (foundCustomer == null) {
            System.err.println("=> Không có id phù hợp!");
            return;
        }

        System.out.println("Thông tin hiện tại: " + foundCustomer.getName() + " | SĐT: " + foundCustomer.getPhone() + " | Email: " + foundCustomer.getEmail());
        System.out.println("Nhập thông tin mới (trừ ID):");

        String name = InputValidator.getString("Nhập tên mới: ", "Tên không được để trống!");
        String phone = InputValidator.getString("Nhập SĐT mới: ", "Vui lòng nhập số điện thoại!");
        String email = InputValidator.getString("Nhập email mới: ", "Vui lòng nhập email!");
        String address = InputValidator.getString("Nhập địa chỉ mới: ", "Vui lòng nhập địa chỉ!");

        Customer updateCustomer = new Customer(id, name, phone, email, address);
        if (customerService.updateCustomer(updateCustomer)) {
            System.out.println("=> Cập nhật thành công!");
        } else {
            System.err.println("=> Cập nhật thất bại!");
        }
    }

    // Xóa khách hàng
    private void deleteCustomer() {
        System.out.println("\n--- XÓA KHÁCH HÀNG ---");
        int id = InputValidator.getInt("Nhập ID khách hàng muốn xóa: ", "ID phải là số nguyên!", 1, Integer.MAX_VALUE);

        // Kiểm tra tồn tại trước khi yêu cầu xác nhận
        List<Customer> currentCustomers = customerService.getAllCustomers();
        boolean exists = currentCustomers.stream().anyMatch(c -> c.getId() == id);

        if (!exists) {
            System.err.println("=> ID khách hàng không tồn tại!");
            return;
        }

        String confirm = InputValidator.getString("Bạn có chắc chắn muốn xóa khách hàng này? (Y/N): ", "Vui lòng nhập Y hoặc N!");
        if (confirm.equalsIgnoreCase("Y")) {
            if (customerService.deleteCustomer(id)) {
                System.out.println("=> Đã xóa khách hàng thành công!");
            } else {
                System.err.println("=> Xóa thất bại! (Khách hàng này có thể đang có hóa đơn mua hàng)");
            }
        } else {
            System.out.println("=> Đã hủy thao tác xóa.");
        }
    }

    // Hàm phụ trợ in bảng danh sách
    private void printCustomerTable(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("=> Không tìm thấy khách hàng nào.");
            return;
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-25s | %-15s | %-25s | %-15s |\n", "ID", "Tên khách hàng", "Số điện thoại", "Email", "Địa chỉ");
        System.out.println("-------------------------------------------------------------------------------------------------");
        for (Customer c : customers) {
            System.out.printf("| %-5d | %-25s | %-15s | %-25s | %-15s |\n",
                    c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getAddress());
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
    }
}