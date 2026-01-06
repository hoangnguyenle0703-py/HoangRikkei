import java.util.Scanner;

public class S1G1_SumFraction {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int numer1, numer2, deno1, deno2;
        System.out.print("Nhập tử số 1: ");
        numer1 = input.nextInt();
        System.out.print("Nhập mẫu số 1: ");
        deno1 = input.nextInt();
        System.out.print("Nhập tử số 2: ");
        numer2 = input.nextInt();
        System.out.print("Nhập mẫu số 2: ");
        deno2 = input.nextInt();

        System.out.printf("Tổng hai phân số: %d/%d",numer1*deno2+numer2*deno1, deno1*deno2);
    }
}
