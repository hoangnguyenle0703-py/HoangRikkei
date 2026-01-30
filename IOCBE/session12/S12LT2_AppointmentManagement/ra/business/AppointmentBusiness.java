package ra.business;

import ra.entity.Appointment;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentBusiness {
    private List<Appointment> appointmentList = new ArrayList<>();

    public void addAppointment(Scanner scanner) {
        System.out.print("Nhập số lượng lịch hẹn muốn thêm: ");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                System.out.println("Nhập thông tin lịch hẹn thứ " + (i + 1) + ":");
                Appointment app = new Appointment();
                app.inputData(scanner);
                appointmentList.add(app);
            }
            System.out.println("Thêm mới hoàn tất.");
        } catch (NumberFormatException e) {
            System.err.println("Số lượng phải là số nguyên.");
        }
    }

    public void displayAppointments() {
        if (appointmentList.isEmpty()) {
            System.out.println("Danh sách trống.");
            return;
        }
        System.out.println("--- DANH SÁCH LỊCH HẸN (Xếp theo ngày tăng dần) ---");
        appointmentList.sort(Comparator.comparing(Appointment::getAppointmentDate));
        appointmentList.forEach(System.out::println);
    }

    public void searchByPatientName(Scanner scanner) {
        System.out.print("Nhập tên bệnh nhân cần tìm: ");
        String keyword = scanner.nextLine().toLowerCase();

        List<Appointment> results = appointmentList.stream()
                .filter(a -> a.getPatientName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("Không tìm thấy lịch hẹn nào.");
        } else {
            System.out.println("Kết quả tìm kiếm:");
            results.forEach(System.out::println);
        }
    }

    public void updateAppointment(Scanner scanner) {
        System.out.print("Nhập mã lịch hẹn cần cập nhật: ");
        String id = scanner.nextLine();

        Optional<Appointment> optApp = appointmentList.stream()
                .filter(a -> a.getAppointmentId().equals(id))
                .findFirst();

        optApp.ifPresentOrElse(
                app -> {
                    System.out.println("Tìm thấy lịch hẹn: " + app);
                    app.updateInfo(scanner); // Gọi hàm update riêng trong Entity
                    System.out.println("Cập nhật thành công!");
                },
                () -> System.out.println("Không tìm thấy mã lịch hẹn: " + id)
        );
    }

    public void deleteAppointment(Scanner scanner) {
        System.out.print("Nhập mã lịch hẹn cần xóa: ");
        String id = scanner.nextLine();

        Optional<Appointment> optApp = appointmentList.stream()
                .filter(a -> a.getAppointmentId().equals(id))
                .findFirst();

        if (optApp.isPresent()) {
            System.out.print("Bạn có chắc muốn xóa lịch hẹn này? (Y/N): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                Appointment appToDelete = optApp.get();
                Appointment.removeId(appToDelete.getAppointmentId()); // Xóa ID khỏi Set check trùng
                appointmentList.remove(appToDelete);
                System.out.println("Đã xóa thành công.");
            } else {
                System.out.println("Đã hủy thao tác xóa.");
            }
        } else {
            System.out.println("Không tìm thấy mã lịch hẹn để xóa.");
        }
    }

    public void showStatistics() {
        System.out.println("--- THỐNG KÊ ---");
        System.out.println("1. Tổng số lịch hẹn: " + appointmentList.size());

        System.out.println("2. Số lịch hẹn theo từng bác sĩ:");
        Map<String, Long> countByDoctor = appointmentList.stream()
                .collect(Collectors.groupingBy(Appointment::getDoctor, Collectors.counting()));

        countByDoctor.forEach((doc, count) ->
                System.out.printf("   - Bác sĩ %s: %d lịch hẹn\n", doc, count)
        );
    }
}