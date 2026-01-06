import java.util.Scanner;

public class S1K2_ArithmeticOperator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập số thứ nhất: ");
        int firstNumber = input.nextInt();
        System.out.print("Nhập số thứ hai: ");
        int secondNumber = input.nextInt();

        System.out.println("\n---Kết quả---");
        System.out.println("firstNumber = "+ firstNumber);
        System.out.println("secondNumber = "+ secondNumber);
        System.out.println("Tổng = " + (firstNumber + secondNumber));
        System.out.println("Hiệu = " + (firstNumber - secondNumber));
        System.out.println("Tích = " + (firstNumber * secondNumber));
        System.out.println("Thương = " + ((float)firstNumber / (float)secondNumber));
        System.out.println("Phần dư = " + (firstNumber % secondNumber));
    }
}
