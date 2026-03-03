import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Khởi tạo đối tượng MovieManagement để gọi các hàm xử lý DB
        MovieManagement manager = new MovieManagement();
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n===== QUẢN LÝ PHIM (POSTGRESQL) =====");
            System.out.println("1. Thêm phim");
            System.out.println("2. Liệt kê phim");
            System.out.println("3. Sửa phim");
            System.out.println("4. Xóa phim");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số hợp lệ!"); // Xử lý ngoại lệ nhập sai kiểu dữ liệu
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Nhập tiêu đề phim: ");
                        String title = scanner.nextLine().trim();
                        if (title.isEmpty()) throw new Exception("Tiêu đề không được để trống!"); // Bắt lỗi nhập trống

                        System.out.print("Nhập đạo diễn: ");
                        String director = scanner.nextLine().trim();
                        if (director.isEmpty()) throw new Exception("Đạo diễn không được để trống!"); // Bắt lỗi nhập trống

                        System.out.print("Nhập năm phát hành: ");
                        int year = Integer.parseInt(scanner.nextLine().trim()); // Bắt lỗi năm không phải số

                        manager.addMovie(title, director, year);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Năm phát hành phải là số nguyên!"); // Xử lý ngoại lệ nhập sai kiểu dữ liệu
                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;

                case 2:
                    manager.listMovies();
                    break;

                case 3:
                    try {
                        System.out.print("Nhập ID phim cần sửa: ");
                        int idUpdate = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Nhập tiêu đề phim mới: ");
                        String newTitle = scanner.nextLine().trim();
                        if (newTitle.isEmpty()) throw new Exception("Tiêu đề không được để trống!"); // Bắt lỗi nhập trống

                        System.out.print("Nhập đạo diễn mới: ");
                        String newDirector = scanner.nextLine().trim();
                        if (newDirector.isEmpty()) throw new Exception("Đạo diễn không được để trống!"); // Bắt lỗi nhập trống

                        System.out.print("Nhập năm phát hành mới: ");
                        int newYear = Integer.parseInt(scanner.nextLine().trim());

                        manager.updateMovie(idUpdate, newTitle, newDirector, newYear);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: ID và Năm phát hành phải là số nguyên!"); // Xử lý ngoại lệ nhập sai kiểu dữ liệu
                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Nhập ID phim cần xóa: ");
                        int idDelete = Integer.parseInt(scanner.nextLine().trim());
                        manager.deleteMovie(idDelete);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: ID phải là một số nguyên!"); // Xử lý ngoại lệ nhập sai kiểu dữ liệu
                    }
                    break;

                case 0:
                    System.out.println("Đang thoát chương trình...");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại.");
            }
        }
        scanner.close();
    }
}