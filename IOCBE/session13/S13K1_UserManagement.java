import java.util.LinkedList;
import java.util.Scanner;

public class S13K1_UserManagement {
    public static class Person {
        private String name;
        private String email;
        private String phone;
        public Person(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return String.format("Name: %-15s | Email: %-20s | Phone: %s", name, email, phone);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Person> persons = new LinkedList<>();
        while (true) {
            System.out.println("\n=== MENU QUẢN LÝ NGƯỜI DÙNG===");
            System.out.println("1. Thêm người dùng");
            System.out.println("2. Xóa người dùng");
            System.out.println("3. Hiển thị danh sách người dùng");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");

            try {
                int chon = Integer.parseInt(sc.nextLine());
                switch (chon) {
                    case 1 -> {
                        System.out.print("Nhập tên: "); String n = sc.nextLine();
                        System.out.print("Nhập email: "); String e = sc.nextLine();
                        System.out.print("Nhập SĐT: "); String p = sc.nextLine();
                        persons.add(new Person(n, e, p));
                        System.out.println("Thêm thành công!");
                    }
                    case 2 -> {
                        System.out.print("Nhập email cần xóa: "); String delEmail = sc.nextLine();
                        // Dùng removeIf để xóa gọn trong 1 dòng
                        boolean isDeleted = persons.removeIf(person -> person.email.equalsIgnoreCase(delEmail));
                        System.out.println(isDeleted ? "Đã xóa xong." : "Không tìm thấy email.");
                    }
                    case 3 -> {
                        if (persons.isEmpty()) System.out.println("Danh sách trống.");
                        else persons.forEach(System.out::println);
                    }
                    case 4 -> System.exit(0);
                    default -> System.out.println("Chọn sai rồi!");
                }
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số!");
            }
        }
    }
}
