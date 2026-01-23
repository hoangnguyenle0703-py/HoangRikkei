import java.util.Scanner;

public class S9LT1_Rectangle {

    public static class Rectangle {
        private double width;
        private double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double getArea() {
            return width * height;
        }

        public double getPerimeter() {
            return 2*(width + height);
        }

        public String printInfo(){
            return "width="+this.width+", height="+this.height+
                    ", getPerimeter="+this.getPerimeter()+", area="+this.getArea();
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Nhập chiều dài: ");
        double height = input.nextDouble();
        System.out.print("Nhập chiều rộng: ");
        double width = input.nextDouble();
        Rectangle rectangle = new Rectangle(width, height);
        System.out.println(rectangle.printInfo());
    }
}
