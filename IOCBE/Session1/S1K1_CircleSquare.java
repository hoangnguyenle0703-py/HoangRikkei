import java.util.Scanner;

public class S1K1_CircleSquare {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the radius of the circle: ");
        double radius = input.nextDouble();
        double pi = Math.PI;
        double area = pi * radius * radius;
        System.out.printf("The area of the circle is: %.2f", area);
    }
}
