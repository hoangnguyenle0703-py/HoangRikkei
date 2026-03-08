package utils;

import java.util.Scanner;

public class InputValidator {
    // Sử dụng chung 1 đối tượng Scanner cho toàn bộ chương trình
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.err.println(errorMessage);
            } else {
                return input;
            }
        }
    }

    public static int getInt(String prompt, String errorMessage) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println(errorMessage);
            }
        }
    }

    public static int getInt(String prompt, String errorMessage, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.err.println("Vui lòng nhập số từ " + min + " đến " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.err.println(errorMessage);
            }
        }
    }

    public static double getDouble(String prompt, String errorMessage, double min) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);
                if (value < min) {
                    System.err.println("Giá trị không được nhỏ hơn " + min + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.err.println(errorMessage);
            }
        }
    }
}