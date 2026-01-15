import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S5K1_EmailCheck {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String  str = input.nextLine();
        str = str.trim();

        String regex = "\\b[\\w._]+@[\\w.]+.[a-zA-Z]{2,6}\\b";

        /*Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);*/
        if(str.matches(regex))
            System.out.println("Email hợp lệ");
        else System.out.println("Email không hợp lệ");
    }
}
