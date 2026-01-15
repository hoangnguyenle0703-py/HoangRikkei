import java.util.Random;
import java.util.Scanner;

public class S5G2_RandString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        StringBuilder base = new StringBuilder();
        for(char c = 'a'; c <= 'z'; c++)
            base.append(c);
        for(char C = 'A';C <= 'Z';C++)
            base.append(C);
        for(char i = '0'; i <= '9';i++)
            base.append(i);

        StringBuilder res = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < n; i++){
            int id = rand.nextInt(base.length()-1);
            res.append(base.charAt(id));
        }
        System.out.println(res);
    }
}
