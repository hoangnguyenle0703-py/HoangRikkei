import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookManager manager = new BookManager();
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n===== QUẢN LÝ SÁCH TRONG THƯ VIỆN =====");
            System.out.println("1. Thêm sách");
            System.out.println("2. Hiển thị tất cả sách");
            System.out.println("3. Cập nhật thông tin sách");
            System.out.println("4. Xóa sách");
            System.out.println("5. Tìm kiếm sách theo tác giả");
            System.out.println("0. Thoát");
            System.out.print("Vui lòng chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("-> Lỗi: Lựa chọn phải là một số nguyên!"); // Kiểm soát sai kiểu dữ liệu
                continue;
            }

            switch (choice) {
                case 1: // Thêm sách
                    try {
                        System.out.print("Nhập tựa sách: ");
                        String title = scanner.nextLine().trim();
                        if (title.isEmpty()) throw new Exception("Tựa sách không được để trống!"); // Kiểm soát rỗng

                        System.out.print("Nhập tác giả: ");
                        String author = scanner.nextLine().trim();
                        if (author.isEmpty()) throw new Exception("Tác giả không được để trống!"); // Kiểm soát rỗng

                        System.out.print("Nhập năm xuất bản: ");
                        int year = Integer.parseInt(scanner.nextLine().trim()); // Bắt lỗi số

                        System.out.print("Nhập giá tiền: ");
                        double price = Double.parseDouble(scanner.nextLine().trim()); // Bắt lỗi số

                        Book newBook = new Book(title, author, year, price);
                        manager.addBook(newBook);
                    } catch (NumberFormatException e) {
                        System.out.println("-> Lỗi: Năm XB và Giá phải là một con số hợp lệ!");
                    } catch (Exception e) {
                        System.out.println("-> Lỗi: " + e.getMessage());
                    }
                    break;

                case 2: // Liệt kê
                    manager.listAllBooks();
                    break;

                case 3: // Cập nhật
                    try {
                        System.out.print("Nhập ID sách cần cập nhật: ");
                        int updateId = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Nhập tựa sách mới: ");
                        String newTitle = scanner.nextLine().trim();
                        if (newTitle.isEmpty()) throw new Exception("Tựa sách không được để trống!");

                        System.out.print("Nhập tác giả mới: ");
                        String newAuthor = scanner.nextLine().trim();
                        if (newAuthor.isEmpty()) throw new Exception("Tác giả không được để trống!");

                        System.out.print("Nhập năm xuất bản mới: ");
                        int newYear = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Nhập giá tiền mới: ");
                        double newPrice = Double.parseDouble(scanner.nextLine().trim());

                        Book updateBookInfo = new Book(newTitle, newAuthor, newYear, newPrice);
                        manager.updateBook(updateId, updateBookInfo);
                    } catch (NumberFormatException e) {
                        System.out.println("-> Lỗi: ID, Năm XB và Giá phải là con số!");
                    } catch (Exception e) {
                        System.out.println("-> Lỗi: " + e.getMessage());
                    }
                    break;

                case 4: // Xóa
                    try {
                        System.out.print("Nhập ID sách cần xóa: ");
                        int deleteId = Integer.parseInt(scanner.nextLine().trim());
                        manager.deleteBook(deleteId);
                    } catch (NumberFormatException e) {
                        System.out.println("-> Lỗi: ID phải là một số nguyên!");
                    }
                    break;

                case 5: // Tìm kiếm
                    try {
                        System.out.print("Nhập tên tác giả cần tìm: ");
                        String searchAuthor = scanner.nextLine().trim();
                        if (searchAuthor.isEmpty()) throw new Exception("Tên tác giả không được để trống!");
                        manager.findBooksByAuthor(searchAuthor);
                    } catch (Exception e) {
                        System.out.println("-> Lỗi: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Đang đóng chương trình...");
                    break;

                default:
                    System.out.println("-> Lỗi: Chức năng không hợp lệ, vui lòng chọn lại.");
            }
        }
        scanner.close();
    }
}