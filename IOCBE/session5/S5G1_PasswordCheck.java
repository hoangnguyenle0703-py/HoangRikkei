import java.util.Scanner;

public class S5G1_PasswordCheck {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String password = input.nextLine();
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[!@#$%]).{8,}$";

        if(password.matches(regex))
            System.out.println("Mật khẩu hợp lệ");
        else System.out.println("Mật khẩu không hợp lệ");
    }
}
