import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class S15LT2_SubjectManagement {

    // 1. Tạo Custom Exception để xử lý lỗi số tín chỉ
    static class InvalidCreditException extends Exception {
        public InvalidCreditException(String message) {
            super(message);
        }
    }

    // 2. Lớp Subject chứa thông tin môn học
    static class Subject {
        private String code;
        private String name;
        private int credits;
        private LocalDate startDate; // Sử dụng LocalDate

        public Subject(String code, String name, int credits, LocalDate startDate) {
            this.code = code;
            this.name = name;
            this.credits = credits;
            this.startDate = startDate;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public int getCredits() { return credits; }
        public LocalDate getStartDate() { return startDate; }

        @Override
        public String toString() {
            // Định dạng hiển thị ngày cho đẹp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return String.format("Mã: %-8s | Tên: %-20s | Tín chỉ: %-2d | Ngày BĐ: %s",
                    code, name, credits, startDate.format(formatter));
        }
    }

    // 3. Lớp SubjectManager dùng Generic <T>
    // Sử dụng <T extends Subject> để đảm bảo T luôn có các phương thức của Subject
    static class SubjectManager<T extends Subject> {
        private ArrayList<T> subjectList = new ArrayList<>(); //

        // Thêm môn học
        public void addSubject(T subject) {
            subjectList.add(subject);
            System.out.println("Đã thêm môn học thành công.");
        }

        // Hiển thị danh sách
        public void displaySubjects() {
            if (subjectList.isEmpty()) {
                System.out.println("Danh sách môn học hiện đang trống.");
            } else {
                System.out.println("--- DANH SÁCH MÔN HỌC ---");
                // Sử dụng phương thức tham chiếu (Method Reference) thay cho vòng lặp for
                subjectList.forEach(System.out::println);
            }
        }

        // Xóa môn học theo code
        public void deleteSubject(String code) {
            // Sử dụng removeIf kết hợp Lambda Expression
            boolean removed = subjectList.removeIf(s -> s.getCode().equalsIgnoreCase(code));
            if (removed) {
                System.out.println("Đã xóa môn học có mã: " + code);
            } else {
                System.out.println("Lỗi: Không tìm thấy môn học có mã '" + code + "'"); //
            }
        }

        // Tìm kiếm môn học theo tên (Sử dụng Stream + Optional)
        public void searchByName(String name) {
            Optional<T> foundSubject = subjectList.stream()
                    .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())) //
                    .findFirst();

            // ifPresentOrElse giúp xử lý rất gọn trường hợp Có/Không tìm thấy
            foundSubject.ifPresentOrElse(
                    subject -> System.out.println("Kết quả tìm kiếm: " + subject),
                    () -> System.out.println("Không có môn học phù hợp") //
            );
        }

        // Lọc môn học theo tín chỉ > 3
        public void filterByCredits() {
            System.out.println("--- CÁC MÔN HỌC CÓ TÍN CHỈ > 3 ---");
            long count = subjectList.stream()
                    .filter(s -> s.getCredits() > 3) // Lọc bằng Stream
                    .peek(System.out::println)       // In ra màn hình
                    .count();

            if (count == 0) {
                System.out.println("Không có môn nào thỏa mãn điều kiện.");
            }
        }
    }

    // 4. Lớp Main chứa Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SubjectManager<Subject> manager = new SubjectManager<>();
        // Định dạng ngày theo chuẩn Việt Nam
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println("\n========= QUẢN LÝ MÔN HỌC =========");
            System.out.println("1. Hiển thị danh sách môn học");
            System.out.println("2. Thêm môn học");
            System.out.println("3. Xóa môn học");
            System.out.println("4. Tìm kiếm môn học theo tên");
            System.out.println("5. Lọc môn học theo tín chỉ (> 3)");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> manager.displaySubjects();
                    case 2 -> {
                        System.out.print("Nhập mã môn học (code): "); //
                        String code = sc.nextLine();
                        System.out.print("Nhập tên môn học (name): "); //
                        String name = sc.nextLine();

                        int credits = 0;
                        while (true) {
                            System.out.print("Nhập số tín chỉ (credits): "); //
                            try {
                                credits = Integer.parseInt(sc.nextLine());
                                // Ném và bắt ngoại lệ nếu tín chỉ âm hoặc > 10
                                if (credits < 0 || credits > 10) {
                                    throw new InvalidCreditException("Lỗi: Số tín chỉ phải từ 0 đến 10!");
                                }
                                break; // Nhập đúng thì thoát vòng lặp
                            } catch (NumberFormatException e) {
                                System.out.println("Lỗi: Số tín chỉ phải là một con số nguyên!");
                            } catch (InvalidCreditException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        LocalDate startDate = null;
                        while (startDate == null) {
                            System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
                            try {
                                // Sử dụng DateTimeFormatter để parse ngày
                                startDate = LocalDate.parse(sc.nextLine(), dateFormatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Lỗi: Ngày bắt đầu sai định dạng (Ví dụ đúng: 01/09/2025).");
                            }
                        }

                        manager.addSubject(new Subject(code, name, credits, startDate)); //
                    }
                    case 3 -> {
                        System.out.print("Nhập mã môn học cần xóa: "); //
                        String code = sc.nextLine();
                        manager.deleteSubject(code);
                    }
                    case 4 -> {
                        System.out.print("Nhập tên môn học cần tìm: "); //
                        String name = sc.nextLine();
                        manager.searchByName(name);
                    }
                    case 5 -> manager.filterByCredits(); //
                    case 0 -> System.exit(0);
                    default -> System.out.println("Lựa chọn không hợp lệ (0-5).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số !");
            }
        }
    }
}