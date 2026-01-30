package ra.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Appointment {
    private static Set<String> uniqueIds = new HashSet<>();

    private String appointmentId;
    private String patientName;
    private String phoneNumber;
    private LocalDate appointmentDate;
    private String doctor;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Appointment() {}

    public Appointment(String appointmentId, String patientName, String phoneNumber, LocalDate appointmentDate, String doctor) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.phoneNumber = phoneNumber;
        this.appointmentDate = appointmentDate;
        this.doctor = doctor;
        uniqueIds.add(appointmentId);
    }

    public String getAppointmentId() { return appointmentId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    public void inputData(Scanner scanner) {
        while (true) {
            System.out.print("Nhập mã lịch hẹn (6 ký tự): ");
            String idInput = scanner.nextLine().trim();
            if (idInput.length() != 6) {
                System.err.println("Mã lịch hẹn phải đúng 6 ký tự.");
            } else if (uniqueIds.contains(idInput)) {
                System.err.println("Mã lịch hẹn này đã tồn tại.");
            } else {
                this.appointmentId = idInput;
                uniqueIds.add(this.appointmentId);
                break;
            }
        }

        while (true) {
            System.out.print("Nhập tên bệnh nhân (10-50 ký tự): ");
            String nameInput = scanner.nextLine().trim();
            if (nameInput.length() >= 10 && nameInput.length() <= 50) {
                this.patientName = nameInput;
                break;
            }
            System.err.println("Tên phải từ 10 đến 50 ký tự.");
        }

        while (true) {
            System.out.print("Nhập SĐT (VD: 0912345678): ");
            String phoneInput = scanner.nextLine().trim();
            if (Pattern.matches("(0[3|5|7|8|9])+([0-9]{8})", phoneInput)) {
                this.phoneNumber = phoneInput;
                break;
            }
            System.err.println("Số điện thoại không đúng định dạng VN.");
        }

        while (true) {
            System.out.print("Nhập ngày hẹn (dd/MM/yyyy): ");
            String dateInput = scanner.nextLine().trim();
            try {
                this.appointmentDate = LocalDate.parse(dateInput, DATE_FORMATTER);
                // Có thể thêm kiểm tra ngày phải ở tương lai: if (this.appointmentDate.isAfter(LocalDate.now())) ...
                break;
            } catch (DateTimeParseException e) {
                System.err.println("Ngày tháng sai định dạng (dd/MM/yyyy). Vui lòng nhập lại.");
            }
        }

        while (true) {
            System.out.print("Nhập tên bác sĩ phụ trách: ");
            String docInput = scanner.nextLine().trim();
            if (docInput.length() > 0 && docInput.length() <= 200) {
                this.doctor = docInput;
                break;
            }
            System.err.println("Tên bác sĩ không được để trống và tối đa 200 ký tự.");
        }
    }

    public void updateInfo(Scanner scanner) {
        System.out.println("--- Cập nhật thông tin ---");
        System.out.print("Nhập tên bệnh nhân mới (Enter để giữ nguyên): ");
        String nameInput = scanner.nextLine().trim();
        if (!nameInput.isEmpty() && nameInput.length() >= 10 && nameInput.length() <= 50) {
            this.patientName = nameInput;
        }

        System.out.print("Nhập SĐT mới (Enter để giữ nguyên): ");
        String phoneInput = scanner.nextLine().trim();
        if (!phoneInput.isEmpty() && Pattern.matches("(0[3|5|7|8|9])+([0-9]{8})", phoneInput)) {
            this.phoneNumber = phoneInput;
        }

        System.out.print("Nhập ngày hẹn mới dd/MM/yyyy (Enter để giữ nguyên): ");
        String dateInput = scanner.nextLine().trim();
        if (!dateInput.isEmpty()) {
            try {
                this.appointmentDate = LocalDate.parse(dateInput, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.err.println("Ngày sai định dạng, giữ nguyên ngày cũ.");
            }
        }

        System.out.print("Nhập bác sĩ mới (Enter để giữ nguyên): ");
        String docInput = scanner.nextLine().trim();
        if (!docInput.isEmpty() && docInput.length() <= 200) {
            this.doctor = docInput;
        }
    }

    public static void removeId(String id) {
        uniqueIds.remove(id);
    }

    @Override
    public String toString() {
        return String.format("ID: %-6s | BN: %-20s | SĐT: %-10s | Ngày: %s | BS: %s",
                appointmentId, patientName, phoneNumber, appointmentDate.format(DATE_FORMATTER), doctor);
    }
}
