import java.util.Scanner;

public class S1G2_PAndSOfRectangular {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        float width,height;
        float area,perimeter;
        System.out.print("Nhập chiều rộng: ");
        width = input.nextFloat();
        System.out.print("Nhập chiều cao: ");
        height = input.nextFloat();
        perimeter = (width + height) * 2;
        area = width * height;
        System.out.println("Diện tích: " + area);
        System.out.println("Chu vi: " + perimeter);
    }
}
