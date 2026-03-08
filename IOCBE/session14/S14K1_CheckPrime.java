import java.util.Scanner;

public class S14K1_CheckPrime {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập vào một số nguyên: ");

        try {
            int number = Integer.parseInt(scanner.nextLine().trim());
            if (number <= 0) {
                System.out.println("Lỗi: Số nhập vào (" + number + ") không hợp lệ. Vui lòng nhập số nguyên dương (>0) để kiểm tra số nguyên tố.");
            } else {
                if (isPrime(number)) {
                    System.out.println(number + " là số nguyên tố.");
                } else {
                    System.out.println(number + " không phải là số nguyên tố.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Dữ liệu nhập vào không phải là số nguyên hợp lệ!");
        } finally {
            scanner.close();
        }
    }

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}