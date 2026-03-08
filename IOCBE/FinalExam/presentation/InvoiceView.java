package presentation;

import business.ICustomerService;
import business.IInvoiceService;
import business.IProductService;
import business.impl.CustomerServiceImpl;
import business.impl.InvoiceServiceImpl;
import business.impl.ProductServiceImpl;
import model.Customer;
import model.Invoice;
import model.InvoiceDetails;
import model.Product;
import utils.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class InvoiceView {
    private final IInvoiceService invoiceService = new InvoiceServiceImpl();
    private final ICustomerService customerService = new CustomerServiceImpl();
    private final IProductService productService = new ProductServiceImpl();

    public void displayMenu() {
        while (true) {
            System.out.println("\n========= QUẢN LÝ HÓA ĐƠN =========");
            System.out.println("1. Hiển thị danh sách hóa đơn");
            System.out.println("2. Thêm mới hóa đơn");
            System.out.println("3. Tìm kiếm hóa đơn");
            System.out.println("4. Quay lại menu chính");
            System.out.println("===================================");

            int choice = InputValidator.getInt("Nhập lựa chọn: ", "Lỗi: Vui lòng nhập số từ 1 đến 4!", 1, 4);

            switch (choice) {
                case 1:
                    displayAllInvoices();
                    break;
                case 2:
                    addInvoice();
                    break;
                case 3:
                    showSearchMenu();
                    break;
                case 4:
                    return;
            }
        }
    }

    //  Hiển thị danh sách hóa đơn
    private void displayAllInvoices() {
        System.out.println("\n--- DANH SÁCH HÓA ĐƠN ---");
        List<Invoice> invoices = invoiceService.getAllInvoices();
        if (invoices == null || invoices.isEmpty()) {
            System.out.println("=> Chưa có hóa đơn nào trong hệ thống.");
            return;
        }
        System.out.println("------------------------------------------------------------------");
        System.out.printf("| %-5s | %-12s | %-20s | %-15s |\n", "ID HĐ", "ID Khách", "Ngày tạo", "Tổng tiền");
        System.out.println("------------------------------------------------------------------");
        for (Invoice inv : invoices) {
            System.out.printf("| %-5d | %-12d | %-20s | %-15.2f |\n",
                    inv.getId(), inv.getCustomerId(), inv.getCreatedAt(), inv.getTotalAmount());
        }
        System.out.println("------------------------------------------------------------------");
    }

    // Thêm hóa đơn mới
    private void addInvoice() {
        System.out.println("\n--- TẠO HÓA ĐƠN MỚI ---");

        // Chọn khách hàng
        int customerId = InputValidator.getInt("Nhập ID Khách hàng: ", "ID phải là số nguyên!", 1, Integer.MAX_VALUE);
        List<Customer> customers = customerService.getAllCustomers();
        boolean customerExists = customers.stream().anyMatch(c -> c.getId() == customerId);

        if (!customerExists) {
            System.err.println("=> Lỗi: Không tìm thấy khách hàng có ID = " + customerId);
            return;
        }

        // Tạo giỏ hàng (Danh sách chi tiết hóa đơn)
        List<InvoiceDetails> details = new ArrayList<>();
        double totalAmount = 0;
        List<Product> availableProducts = productService.getAllProducts();

        while (true) {
            System.out.println("\n- Thêm sản phẩm vào hóa đơn -");
            int productId = InputValidator.getInt("Nhập ID Sản phẩm (hoặc nhập 0 để dừng thêm): ", "ID phải là số nguyên!", 0, Integer.MAX_VALUE);

            if (productId == 0) break;

            Product selectedProduct = availableProducts.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);

            if (selectedProduct == null) {
                System.err.println("=> Lỗi: Sản phẩm không tồn tại!");
                continue;
            }

            if (selectedProduct.getStock() <= 0) {
                System.err.println("=> Lỗi: Sản phẩm này đã hết hàng!");
                continue;
            }

            System.out.println("Sản phẩm: " + selectedProduct.getName() + " | Tồn kho hiện tại: " + selectedProduct.getStock() + " | Giá: " + selectedProduct.getPrice());

            int quantity = InputValidator.getInt("Nhập số lượng mua: ", "Số lượng không hợp lệ!", 1, selectedProduct.getStock());

            InvoiceDetails detail = new InvoiceDetails();
            detail.setProductId(productId);
            detail.setQuantity(quantity);
            detail.setUnitPrice(selectedProduct.getPrice());

            details.add(detail);
            totalAmount += (selectedProduct.getPrice() * quantity);

            System.out.println("=> Đã thêm vào giỏ. Tạm tính: " + totalAmount);

            String continueAdd = InputValidator.getString("Tiếp tục thêm sản phẩm khác? (Y/N): ", "Vui lòng nhập Y hoặc N!");
            if (continueAdd.equalsIgnoreCase("N")) {
                break;
            }
        }

        // Lưu hóa đơn
        if (details.isEmpty()) {
            System.out.println("=> Hóa đơn trống, đã hủy thao tác tạo hóa đơn.");
            return;
        }

        System.out.println("\nTổng tiền thanh toán: " + totalAmount);
        String confirm = InputValidator.getString("Xác nhận thanh toán và lưu hóa đơn? (Y/N): ", "Vui lòng nhập Y hoặc N!");

        if (confirm.equalsIgnoreCase("Y")) {
            Invoice newInvoice = new Invoice();
            newInvoice.setCustomerId(customerId);
            newInvoice.setTotalAmount(totalAmount);

            // Gọi hàm Transaction từ Service
            if (invoiceService.addInvoice(newInvoice, details)) {
                System.out.println("=> Giao dịch thành công! Hóa đơn đã được lưu, kho hàng đã được trừ.");
            } else {
                System.err.println("=> Giao dịch thất bại! Đã Rollback dữ liệu.");
            }
        } else {
            System.out.println("=> Đã hủy lưu hóa đơn.");
        }
    }

    // Tìm kiếm hóa đơn theo tên khách hàng
    private void searchInvoiceByCustomer() {
        System.out.println("\n--- TÌM KIẾM HÓA ĐƠN ---");
        String customerName = InputValidator.getString("Nhập tên khách hàng cần tìm: ", "Tên không được để trống!");

        List<Invoice> invoices = invoiceService.searchByCustomerName(customerName);
        if (invoices == null || invoices.isEmpty()) {
            System.out.println("=> Không tìm thấy hóa đơn nào của khách hàng này.");
            return;
        }

        System.out.println("------------------------------------------------------------------");
        System.out.printf("| %-5s | %-12s | %-20s | %-15s |\n", "ID HĐ", "ID Khách", "Ngày tạo", "Tổng tiền");
        System.out.println("------------------------------------------------------------------");
        for (Invoice inv : invoices) {
            System.out.printf("| %-5d | %-12d | %-20s | %-15.2f |\n",
                    inv.getId(), inv.getCustomerId(), inv.getCreatedAt(), inv.getTotalAmount());
        }
        System.out.println("------------------------------------------------------------------");
    }

    // Menu tìm kiếm hóa đơn
    private void showSearchMenu() {
        while (true) {
            System.out.println("\n→ Menu tìm kiếm hóa đơn");
            System.out.println("1. Tìm theo tên khách hàng");
            System.out.println("2. Tìm theo ngày/tháng/năm");
            System.out.println("3. Quay lại menu hóa đơn");

            int choice = InputValidator.getInt("Nhập lựa chọn: ", "Lỗi: Vui lòng nhập số từ 1 đến 3!", 1, 3);

            switch (choice) {
                case 1:
                    searchInvoiceByCustomer();
                    break;
                case 2:
                    searchInvoiceByDate();
                    break;
                case 3:
                    return; // Thoát menu con, quay lại menu hóa đơn
            }
        }
    }

    // Hàm tìm kiếm theo ngày tháng năm
    private void searchInvoiceByDate() {
        System.out.println("\n--- TÌM KIẾM THEO NGÀY/THÁNG/NĂM ---");
        // Yêu cầu nhập theo định dạng chuẩn để dễ truy vấn
        String dateStr = InputValidator.getString("Nhập ngày cần tìm (Định dạng YYYY-MM-DD, vd: 2024-05-20): ", "Không được để trống!");

        // Gọi xuống service để tìm kiếm (Bạn cần bổ sung hàm này ở Service và DAO)
        List<Invoice> invoices = invoiceService.searchByDate(dateStr);

        if (invoices == null || invoices.isEmpty()) {
            System.out.println("=> Không tìm thấy hóa đơn nào trong ngày: " + dateStr);
            return;
        }

        // Tái sử dụng code in bảng danh sách
        System.out.println("------------------------------------------------------------------");
        System.out.printf("| %-5s | %-12s | %-20s | %-15s |\n", "ID HĐ", "ID Khách", "Ngày tạo", "Tổng tiền");
        System.out.println("------------------------------------------------------------------");
        for (Invoice inv : invoices) {
            System.out.printf("| %-5d | %-12d | %-20s | %-15.2f |\n",
                    inv.getId(), inv.getCustomerId(), inv.getCreatedAt(), inv.getTotalAmount());
        }
        System.out.println("------------------------------------------------------------------");
    }
}