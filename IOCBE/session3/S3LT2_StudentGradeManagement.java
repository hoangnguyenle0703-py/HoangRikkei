import java.util.Scanner;

public class S3LT2_StudentGradeManagement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        float sum = 0;
        int num = 0;
        float max = 0;
        float min = 10;
        while (true) {
            System.out.println("========== MENU ==========");
            System.out.println("1. Nhập điểm học viên");
            System.out.println("2. Hiển thị thống kê");
            System.out.println("3. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = input.nextInt();
            if (choice == 1) {
                System.out.println("----- Nhập điểm học viên (nhập -1 để dừng) -----");
                while (true) {
                    System.out.print("Nhập điểm: ");
                    float grade = input.nextFloat();
                    if (grade < -1 || grade > 10) {
                        System.err.println("Điểm không hợp lệ. Nhập lại.");
                        continue;
                    }
                    if (grade == -1) break;

                    num++;
                    sum += grade;
                    if(grade < min) min = grade;
                    if(grade > max) max = grade;

                    System.out.print("Học lực: ");
                    if(grade >= 9) System.out.println("Xuất sắc.");
                    else if(grade >= 8) System.out.println("Giỏi.");
                    else if(grade >= 7) System.out.println("Khá.");
                    else if(grade >= 5) System.out.println("Trung bình.");
                    else System.out.println("Yếu.");
                }
            }
            else if (choice == 2) {
                System.out.println("---------- KẾT QUẢ ----------");
                System.out.println("Số học viên đã nhập: " + num);
                System.out.println("Điểm trung bình: " + (sum/(float)num));
                System.out.println("Điểm cao nhất: " + max);
                System.out.println("Điểm thấp nhất: " + min);
            }
            else if (choice == 3) {
                System.out.println("Kết thúc chương trình.");
                break;
            }
            else System.err.println("Chọn lại.");
        }
    }
}
