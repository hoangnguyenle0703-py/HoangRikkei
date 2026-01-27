public class S10K2_ShapeToRectangleAndCircle {

    public static class Shape{
        public double area(){return 0;}
    }

    public static class Rectangle extends Shape{
        private double width;
        private double height;
        public Rectangle(double width, double height){
            this.width = width;
            this.height = height;
        }

        public double area(){return width*height;}
    }

    public static class Circle extends Shape{
        private double radius;
        public Circle(double radius){
            this.radius = radius;
        }
        public double area(){return Math.PI*radius*radius;}
    }

    public static void main(String[] args) {
        Shape s1 = new Rectangle(2,3);
        Shape s2 = new Circle(1);
        System.out.println(s1.area());
        System.out.println(s2.area());
    }
}
