import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. Tạo Custom Exception theo yêu cầu
class InvalidPhoneNumberLengthException extends Exception {
    public InvalidPhoneNumberLengthException(String message) {
        super(message);
    }
}

public class S14G2_PhoneValidator {

    // 2. Phương thức kiểm tra tính hợp lệ ném ra ngoại lệ tự định nghĩa
    public static void validatePhoneNumber(String phone) throws InvalidPhoneNumberLengthException {
        if (phone.contains(" ")) {
            throw new InvalidPhoneNumberLengthException("Không được chứa khoảng trắng");
        }

        if (phone.length() != 10) {
            throw new InvalidPhoneNumberLengthException("Sai độ dài");
        }

        if (!phone.matches("[0-9]+")) {
            throw new InvalidPhoneNumberLengthException("Chứa ký tự không hợp lệ");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> validPhones = new ArrayList<>();
        List<String> invalidPhones = new ArrayList<>();

        System.out.println("Nhập danh sách số điện thoại (ngăn cách bằng dấu phẩy):");
        String input = scanner.nextLine();

        String[] phoneArray = input.split(",");

        for (String phone : phoneArray) {
            String cleanPhone = phone.trim();

            if (cleanPhone.isEmpty()) {
                continue;
            }

            try {
                validatePhoneNumber(cleanPhone);
                validPhones.add(cleanPhone);
            } catch (InvalidPhoneNumberLengthException e) {
                invalidPhones.add(cleanPhone + " : " + e.getMessage());
            }
        }

        System.out.println("\nSố điện thoại hợp lệ:");
        for (String valid : validPhones) {
            System.out.println("- " + valid);
        }

        System.out.println("\nSố điện thoại không hợp lệ:");
        for (String invalid : invalidPhones) {
            System.out.println("- " + invalid);
        }

        scanner.close();
    }
}