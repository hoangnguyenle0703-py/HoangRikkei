package presentation;

import utils.InputValidator;
import business.impl.AdminServiceImpl;

public class MainView {
    public void start() {
        while (true) {
            System.out.println("\n========= HỆ THỐNG QUẢN LÝ CỬA HÀNG =========");
            System.out.println("1. Đăng nhập Admin");
            System.out.println("2. Thoát");
            System.out.println("=============================================");

            int choice = InputValidator.getInt("Nhập lựa chọn: ", "Lỗi: Vui lòng nhập số 1 hoặc 2!", 1, 2);

            switch (choice) {
                case 1:
                    if (showLogin()) {
                        showMainMenu();
                    }
                    break;
                case 2:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình. Tạm biệt!");
                    System.exit(0); 
            }
        }
    }

    // Giao diện đăng nhập
    private boolean showLogin() {
        System.out.println("\n========= ĐĂNG NHẬP QUẢN TRỊ =========");
        String username = InputValidator.getString("Tài khoản: ", "Tài khoản không được để trống!");
        String password = InputValidator.getString("Mật khẩu: ", "Mật khẩu không được để trống!");

        AdminServiceImpl adminService = new AdminServiceImpl();
        if (adminService.login(username, password)) {
            System.out.println("=> Đăng nhập thành công!");
            return true;
        } else {
            System.err.println("=> Sai tài khoản hoặc mật khẩu. Vui lòng thử lại!");
            return false;
        }
    }

    // Giao diện chính
    private void showMainMenu() {
        while (true) {
            System.out.println("\n========= MENU CHÍNH =========");
            System.out.println("1. Quản lý sản phẩm điện thoại");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Quản lý hóa đơn");
            System.out.println("4. Đăng xuất");
            System.out.println("==============================");

            int choice = InputValidator.getInt("Nhập lựa chọn: ", "Lỗi: Vui lòng nhập số từ 1 đến 4!", 1, 4);

            switch (choice) {
                case 1:
                    System.out.println("\n-> Đang chuyển đến Menu Quản lý sản phẩm...");
                    ProductView productView = new ProductView();
                    productView.displayMenu();
                    break;
                case 2:
                    System.out.println("\n-> Đang chuyển đến Menu Quản lý khách hàng...");
                    CustomerView customerView = new CustomerView();
                    customerView.displayMenu();
                    break;
                case 3:
                    System.out.println("\n-> Đang chuyển đến Menu Quản lý hóa đơn...");
                    InvoiceView invoiceView = new InvoiceView();
                    invoiceView.displayMenu();
                    break;
                case 4:
                    System.out.println("\n=> Đã đăng xuất thành công!");
                    return; 
            }
        }
    }
}
