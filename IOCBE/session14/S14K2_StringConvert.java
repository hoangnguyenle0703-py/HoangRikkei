import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class S14K2_StringConvert {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> validIntegers = new ArrayList<>();
        int invalidCount = 0;

        System.out.println("=== CHƯƠNG TRÌNH LỌC VÀ CHUYỂN ĐỔI SỐ NGUYÊN ===");
        System.out.println("Vui lòng nhập lần lượt các chuỗi (nhập 'exit' để dừng và xem kết quả).");

        while (true) {
            System.out.print("Nhập chuỗi: ");
            String input = scanner.nextLine().trim();

            // Kiểm tra điều kiện thoát (không phân biệt hoa thường)
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int number = Integer.parseInt(input);

                validIntegers.add(number);

            } catch (NumberFormatException e) {
                invalidCount++;
                System.out.println("   -> Lỗi: '" + input + "' không phải là số nguyên. Bỏ qua!");
            }
        }

        // --- THỐNG KÊ VÀ HIỂN THỊ KẾT QUẢ ---
        System.out.println("\n================ KẾT QUẢ ================");
        System.out.println("- Số lượng chuỗi hợp lệ: " + validIntegers.size() + "");
        System.out.println("- Số lượng chuỗi không hợp lệ: " + invalidCount + "");

        System.out.print("- Danh sách các số nguyên đã chuyển đổi: ");
        if (validIntegers.isEmpty()) {
            System.out.println("[Trống]");
        } else {
            System.out.println(validIntegers + "");
        }

        scanner.close();
    }
}