import java.sql.SQLOutput;
import java.util.Scanner;

public class S2K2_DaysInMonth {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập vào số tháng: ");
        int month = input.nextInt();
        if (month < 1 || month > 12) {
            System.out.println("Số tháng không hợp lệ.");
        }
        else{
            int days = 0;
            switch (month) {
                case 4,6,9,11:
                    days = 30;
                    break;
                case 2:
                    System.out.println("Tháng 2 có 28 hoặc 29 ngày.");
                    break;
                default:
                    days = 31;
                    break;
            }
            if(month != 2)
                System.out.printf("Tháng %d có %d ngày.",month,days);
        }
    }
}
