import java.util.ArrayList;
import java.util.Scanner;

public class S13G1_OrderMangement {

    interface Manage<T> {
        void add(T item);
        void update(int index, T item);
        void delete(int index);
        void display();
    }

    static class Invoice {
        private String invoiceId;
        private double amount;

        public Invoice(String invoiceId, double amount) {
            this.invoiceId = invoiceId;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return String.format("Mã HĐ: %-10s | Số tiền: %,.0f VNĐ", invoiceId, amount);
        }
    }

    static class InvoiceManager implements Manage<Invoice> {
        private ArrayList<Invoice> invoiceList = new ArrayList<>();

        @Override
        public void add(Invoice item) {
            invoiceList.add(item);
            System.out.println("Thêm hóa đơn thành công!");
        }

        @Override
        public void update(int index, Invoice item) {
            if (index >= 0 && index < invoiceList.size()) {
                invoiceList.set(index, item);
                System.out.println("Cập nhật hóa đơn tại vị trí " + index + " thành công!");
            } else {
                System.err.println("Lỗi: Vị trí " + index + " không tồn tại.");
            }
        }

        @Override
        public void delete(int index) {
            if (index >= 0 && index < invoiceList.size()) {
                Invoice removed = invoiceList.remove(index);
                System.out.println("Đã xóa hóa đơn: " + removed);
            } else {
                System.err.println("Lỗi: Vị trí " + index + " không tồn tại.");
            }
        }

        @Override
        public void display() {
            if (invoiceList.isEmpty()) {
                System.out.println("Danh sách hóa đơn trống.");
            } else {
                System.out.println("--- DANH SÁCH HÓA ĐƠN ---");
                for (int i = 0; i < invoiceList.size(); i++) {
                    System.out.println("Vị trí [" + i + "] - " + invoiceList.get(i));
                }
            }
        }

        public int getSize() {
            return invoiceList.size();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InvoiceManager manager = new InvoiceManager();

        while (true) {
            System.out.println("\n=== QUẢN LÝ HÓA ĐƠN ===");
            System.out.println("1. Thêm hóa đơn");
            System.out.println("2. Sửa hóa đơn (theo Index)");
            System.out.println("3. Xóa hóa đơn (theo Index)");
            System.out.println("4. Hiển thị danh sách");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> {
                        System.out.print("Nhập mã hóa đơn: ");
                        String code = sc.nextLine();
                        System.out.print("Nhập số tiền: ");
                        double amt = Double.parseDouble(sc.nextLine());
                        manager.add(new Invoice(code, amt));
                    }
                    case 2 -> {
                        manager.display();
                        if (manager.getSize() > 0) {
                            System.out.print("Nhập vị trí (index) cần sửa: ");
                            int idx = Integer.parseInt(sc.nextLine());

                            System.out.print("Nhập mã hóa đơn mới: ");
                            String code = sc.nextLine();
                            System.out.print("Nhập số tiền mới: ");
                            double amt = Double.parseDouble(sc.nextLine());

                            manager.update(idx, new Invoice(code, amt));
                        }
                    }
                    case 3 -> {
                        manager.display();
                        if (manager.getSize() > 0) {
                            System.out.print("Nhập vị trí (index) cần xóa: ");
                            int idx = Integer.parseInt(sc.nextLine());
                            manager.delete(idx);
                        }
                    }
                    case 4 -> manager.display();
                    case 5 -> System.exit(0);
                    default -> System.out.println("Vui lòng chọn từ 0-4");
                }
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu! Vui lòng nhập đúng định dạng.");
            }
        }
    }
}
