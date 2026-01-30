package ra.presentation;

import ra.business.AppointmentBusiness;
import java.util.Scanner;

public class AppointmentManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AppointmentBusiness business = new AppointmentBusiness();

        while (true) {
            System.out.println("\n**************** QUẢN LÝ LỊCH HẸN ****************");
            System.out.println("1. Thêm lịch hẹn");
            System.out.println("2. Hiển thị danh sách lịch hẹn");
            System.out.println("3. Tìm kiếm lịch hẹn theo tên bệnh nhân");
            System.out.println("4. Cập nhật lịch hẹn theo mã lịch hẹn");
            System.out.println("5. Xóa lịch hẹn theo mã lịch hẹn");
            System.out.println("6. Thống kê");
            System.out.println("7. Thoát");
            System.out.println("**************************************************");
            System.out.print("Lựa chọn của bạn: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> business.addAppointment(scanner);
                    case 2 -> business.displayAppointments();
                    case 3 -> business.searchByPatientName(scanner);
                    case 4 -> business.updateAppointment(scanner);
                    case 5 -> business.deleteAppointment(scanner);
                    case 6 -> business.showStatistics();
                    case 7 -> {
                        System.out.println("Kết thúc chương trình.");
                        System.exit(0);
                    }
                    default -> System.err.println("Lựa chọn không hợp lệ (1-7).");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số.");
            } catch (Exception e) {
                System.err.println("Lỗi hệ thống: " + e.getMessage());
            }
        }
    }
}