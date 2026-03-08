import java.util.Scanner;

public class S14G1_ATMWithdrawal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double balance = 1000000;
        final double MIN_BALANCE = 50000;

        System.out.println("=== CHƯƠNG TRÌNH MÔ PHỎNG RÚT TIỀN ===");
        System.out.println("Tài khoản của bạn hiện có: " + String.format("%,.0f", balance) + " VNĐ");

        while (true) {
            System.out.print("\nNhập số tiền muốn rút (hoặc gõ 'exit' để thoát): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Cảm ơn bạn đã sử dụng dịch vụ!");
                break;
            }

            try {
                double withdrawAmount = Double.parseDouble(input);

                if (withdrawAmount <= 0) {
                    System.out.println("Lỗi: Số tiền rút phải lớn hơn 0!");
                    continue;
                }

                if (withdrawAmount > balance) {
                    System.out.println("Lỗi: Số tiền rút vượt quá số dư!");
                }
                else if ((balance - withdrawAmount) < MIN_BALANCE) {
                    System.out.println("Lỗi: Tài khoản phải duy trì số dư tối thiểu 50.000 đồng!");
                }
                else {
                    balance -= withdrawAmount;
                    System.out.println("Giao dịch thành công!");
                    System.out.println("- Số tiền đã rút: " + String.format("%,.0f", withdrawAmount) + " VNĐ");
                    System.out.println("- Số dư còn lại: " + String.format("%,.0f", balance) + " VNĐ");
                }

            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số hợp lệ!");
            }
        }

        scanner.close();
    }
}