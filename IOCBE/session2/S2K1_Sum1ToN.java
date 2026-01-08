import java.util.Scanner;

public class S2K1_Sum1ToN {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        if(n <= 0)
            System.out.println("Số nhập vào không hợp lệ");
        else {
            int sum = 0;
            for (int i = 1; i <= n; i++)
                sum += i;
            System.out.printf("Tổng các số từ 1 đến %d là: %d",n,sum);
        }
    }
}
