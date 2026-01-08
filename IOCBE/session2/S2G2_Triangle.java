import java.util.Scanner;

public class S2G2_Triangle {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập vào 3 cạnh tam giác: ");
        int a = input.nextInt();
        int b = input.nextInt();
        int c = input.nextInt();
        if(a <= b && b > c){
            int temp = b;
            b = c;
            c = temp;
        }
        else if(a >= b && a > c){
            int temp = a;
            a = c;
            c = temp;
        }
        //System.out.printf("%d %d %d",a,b,c);
        if(a <= 0 || b <= 0 || a + b <= c)
            System.err.println("Ba cạnh không tạo thành tam giác");
        else if(a == b && b == c)
            System.out.println("Đây là tam giác đều.");
        else if(a == b || a == c || b == c)
            System.out.println("Đây là tam giác cân.");
        else if(a*a + b*b == c*c)
            System.out.println("Đây là tam giác vuông.");
        else
            System.out.println("Đây là tam giác thường");
    }
}
