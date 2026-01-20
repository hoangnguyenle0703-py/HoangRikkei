import java.util.Arrays;
import java.util.Scanner;

public class S6LT1_StudentManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double[] grades = new double[10000];
        double n = 0;
        double sum = 0,max = 0,min = 11;
        int pass = 0, nopass = 0, good = 0;
        while(true){
            System.out.print("""
                    *************** QUẢN LÝ ĐIỂM SINH VIÊN **********
                    1. Nhập danh sách điểm sinh viên
                    2. In danh sách điểm
                    3. Tính điểm trung bính của các sinh viên
                    4. Tính điểm cao nhất và thấp nhất
                    5. Đếm số lượng sinh viên đạt và trượt
                    6. Sắp xếp điểm tăng dần
                    7. Thống kê số lượng sinh viên giỏi và xuất sắc
                    8. Thoát
                    Lựa chọn của bạn:
                    """);
            int choice = sc.nextInt();
            if(choice == 1) {
                sum = 0;
                max = 0;
                min = 11;
                pass = 0;
                nopass = 0;
                good = 0;
                System.out.print("Nhập số lượng sinh viên: ");
                n = sc.nextDouble();
                System.out.println("Nhập điểm: ");
                for (int i = 0; i < n; i++){
                    grades[i] = sc.nextDouble();
                    if(max < grades[i]) max = grades[i];
                    if(min > grades[i]) min = grades[i];
                    sum += grades[i];
                    if(grades[i] >= 5)pass++;
                    else nopass++;
                    if(grades[i] >= 8) good++;
                }
            }
            else if(choice == 2){
                System.out.print("Danh sách điểm: ");
                for(int i = 0; i < n; i++) System.out.print(grades[i] + " ");
                System.out.print("");
            }
            else if(choice == 3){
                System.out.println("Điểm trung bình: " + sum/n);
            }
            else if(choice == 4){
                System.out.println("Điểm cao nhất: " + max);
                System.out.println("Điểm thấp nhất: " + min);
            }
            else if(choice == 5){
                System.out.println("Số sinh viên đạt: " + pass);
                System.out.println("Số sinh viên trượt: " + nopass);
            }
            else if(choice == 6){
                Arrays.sort(grades,0,(int)n-1);
                System.out.print("Điểm sau khi sắp xếp: ");
                for(int i = 0; i < n; i++) System.out.print(grades[i] + " ");
                System.out.print("");
            }
            else if(choice == 7){
                System.out.println("Số sinh viên giỏi và xuất sắc: " + good);
            }
            else if(choice == 8)break;
            else System.out.println("Chọn lại.");
        }
    }
}
