import java.util.ArrayList;
import java.util.Scanner;

public class S13K2_AttendanceManagement {
    interface Manager<T>{
        void add(T item);
        void update(int index,T item);
        void delete(int index);
        void display();
    }

    public static class Student{
        private static int cnt = 0;
        private int id;
        private String name;

        public Student(String name){
            this.id = cnt++;
            this.name = name;
        }

        @Override
        public String toString(){
            return "ID: " + id + "| Tên: " + name;
        }
    }

    public static class AttendanceManager implements Manager<Student>{
        private ArrayList<Student> students = new ArrayList<>();

        @Override
        public void add(Student s) {
            students.add(s);
            System.out.println("Đã thêm sinh viên.");
        }

        private boolean checkIndex(int index) {
            if (index >= 0 && index <= students.size()) {
                return true;
            }
            System.err.println("Lỗi: Vị trí (Index) không tồn tại!");
            return false;
        }

        @Override
        public void update(int index, Student s) {
            if(checkIndex(index)){
                students.set(index, s);
                System.out.println("Đã cập nhật");
            }
        }

        @Override
        public void delete(int index) {
            if(checkIndex(index)){
                students.remove(index);
                System.out.println("Đã xóa sinh viên");
            }
        }

        @Override
        public void display() {
            if(students.isEmpty()){
                System.out.println("Danh sách trống");
            }
            else{
                for(Student s: students) {
                    System.out.println("1. " + s.toString());
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AttendanceManager manager = new AttendanceManager();

        while (true) {
            System.out.println("\n********* MENU QUẢN LÝ ĐIỂM DANH *********");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Sửa sinh viên");
            System.out.println("3. Xóa sinh viên");
            System.out.println("4. Hiển thị danh sách sinh viên");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> {
                        System.out.print("Nhập Tên: "); String name = sc.nextLine();
                        manager.add(new Student(name));
                    }
                    case 2 -> {
                        manager.display(); // Hiện danh sách để biết index
                        System.out.print("Nhập vị trí (index) cần sửa: ");
                        int idx = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập Tên mới: "); String newName = sc.nextLine();
                        manager.update(idx, new Student(newName));
                    }
                    case 3 -> {
                        manager.display();
                        System.out.print("Nhập vị trí (index) cần xóa: ");
                        int idx = Integer.parseInt(sc.nextLine());
                        manager.delete(idx);
                    }
                    case 4 -> manager.display();
                    case 5 -> System.exit(0);
                    default -> System.out.println("Vui lòng chọn từ 0-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số nguyên!");
            }
        }
    }
}
