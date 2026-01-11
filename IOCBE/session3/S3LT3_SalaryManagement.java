import java.util.Scanner;

public class S3LT3_SalaryManagement {
    public  static void main(String args[]) {
        Scanner input = new Scanner(System.in);

        int num = 0;
        long sum = 0,max = 0, min = (long)(5e8), reward = 0;

        while(true){
            System.out.println("********** MENU NHẬP LƯƠNG **********");
            System.out.print("1. Nhập lương nhân viên\n" +
                    "2. Hiển thị thống kê\n" +
                    "3. Tính tổng số tiền thưởng cho nhân viên\n" +
                    "4. Thoát\n" +
                    "Lựa chọn của bạn: ");
            int choice = input.nextInt();
            if(choice == 1){
                System.out.println("----- Nhập lương nhân viên (nhập -1 để kết thúc) -----");

                while(true) {
                    System.out.print("Nhập lương: ");
                    long salary = input.nextLong();
                    if (salary > (5*1e8) || salary < -1){
                        System.out.println("Lương không hợp lệ. Nhập lại.");
                        continue;
                    }
                    if(salary == -1) break;

                    sum += salary;
                    num++;
                    if (max < salary) max = salary;
                    if (min > salary) min = salary;

                    System.out.print("-> Phân loại: ");
                    if (salary > (5e7)) System.out.println("Cao.");
                    else if(salary > (15e6))System.out.println("Khá.");
                    else if (salary > (5e6)) System.out.println("Trung Bình.");
                    else System.out.println("Thấp.");

                    if(salary > (1e8))reward += (long)(salary*0.25);
                    else if(salary > (5e7))reward += (long)(salary*0.2);
                    else if(salary > (15e6))reward += (long)(salary*0.15);
                    else if(salary > (5e6))reward += (long)(salary*0.1);
                    else reward += (long)(salary*0.05);
                }
            }
            else if(choice == 2){
                System.out.println("---------- Thống kê ----------");
                System.out.println("Số nhân viên: " + num);
                System.out.println("Tổng lương: " + sum);
                System.out.println("Lương trung bình: " + (long)(sum/(double)num));
                System.out.println("Lương cao nhất: " + max);
                System.out.println("Lương thấp nhất: " + min);
            }
            else if(choice == 3){
                System.out.println("----- Tính tổng số tiền thưởng nhân viên -----");
                System.out.println("Tổng số tiền thưởng nhân viên: " + reward);
            }
            else if(choice == 4){
                System.out.println("Kết thúc chương trình.");
                break;
            }
            else System.err.println("Chọn lại.");
        }
    }
}
