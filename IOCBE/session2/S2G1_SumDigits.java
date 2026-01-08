import java.util.Scanner;

public class S2G1_SumDigits {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập vào một số nguyên: ");
        int n = input.nextInt();
        if(n < 0)n -= 2*n;
        int sum = 0;
        while(n != 0){
            sum += n % 10;
            n /= 10;
        }
        System.out.printf("Tổng các chữ số là: %d",sum);
    }
}
