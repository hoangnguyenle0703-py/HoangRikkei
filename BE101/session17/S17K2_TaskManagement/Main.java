import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManagement manager = new TaskManagement();
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n===== TO-DO LIST (POSTGRESQL) =====");
            System.out.println("1. Thêm công việc");
            System.out.println("2. Liệt kê công việc");
            System.out.println("3. Cập nhật trạng thái");
            System.out.println("4. Xóa công việc");
            System.out.println("5. Tìm kiếm công việc");
            System.out.println("6. Thống kê công việc");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Nhập tên công việc: ");
                        String name = scanner.nextLine().trim();
                        if (name.isEmpty()) throw new Exception("Tên công việc không được để trống!");

                        System.out.print("Nhập trạng thái ('chưa hoàn thành' hoặc 'đã hoàn thành'): ");
                        String status = scanner.nextLine().trim();
                        if (status.isEmpty()) throw new Exception("Trạng thái không được để trống!");

                        manager.addTask(name, status);
                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;

                case 2:
                    manager.listTasks();
                    break;

                case 3:
                    try {
                        System.out.print("Nhập ID công việc cần cập nhật: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Nhập trạng thái mới ('chưa hoàn thành' hoặc 'đã hoàn thành'): ");
                        String newStatus = scanner.nextLine().trim();
                        if (newStatus.isEmpty()) throw new Exception("Trạng thái không được để trống!");

                        manager.updateTaskStatus(id, newStatus);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: ID phải là một số nguyên!");
                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Nhập ID công việc cần xóa: ");
                        int delId = Integer.parseInt(scanner.nextLine().trim());
                        manager.deleteTask(delId);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: ID phải là một số nguyên!");
                    }
                    break;

                case 5:
                    try {
                        System.out.print("Nhập tên công việc cần tìm: ");
                        String searchName = scanner.nextLine().trim();
                        if (searchName.isEmpty()) throw new Exception("Vui lòng nhập từ khóa tìm kiếm!");

                        manager.searchTaskByName(searchName);
                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;

                case 6:
                    manager.taskStatistics();
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