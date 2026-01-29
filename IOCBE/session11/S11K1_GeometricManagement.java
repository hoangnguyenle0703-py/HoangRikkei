public class S11K1_GeometricManagement {

    public static abstract class Shape{
        protected String name;
        public Shape(String name){
            this.name = name;
        }

        abstract public double getArea();
        abstract public double getPerimeter();

        public void displayInfo(){
            System.out.println(name);
        };
    }

    interface Drawable{
        void draw();
    }

    public static class Rectangle extends Shape implements Drawable{
        private double width;
        private double height;
        public Rectangle(String name,double width,double height){
            super(name);
            this.width = width;
            this.height = height;
        }

        @Override
        public double getArea() {
            return width*height;
        }
        @Override
        public double getPerimeter() {
            return (width+height)*2;
        }
        @Override
        public void draw() {
            System.out.println("Vẽ hình chữ nhật chiều dài "+height+" chiều rộng "+width);
        }

        @Override
        public void displayInfo() {
            System.out.println(name);
            System.out.println("Diện tích: "+ this.getPerimeter());
            System.out.println("Chu vi: "+ this.getArea());
        }
    }

    public static class Circle extends Shape implements Drawable{
        private double radius;
        public Circle(String name,double radius){
            super(name);
            this.radius = radius;
        }
        @Override
        public double getArea() {
            return radius*radius*Math.PI;
        }
        @Override
        public double getPerimeter() {
            return 2*Math.PI*radius;
        }
        @Override
        public void draw() {
            System.out.println("Vẽ hình tròn bán kính "+radius);
        }

        @Override
        public void displayInfo() {
            System.out.println(name);
            System.out.println("Diện tích: "+ this.getPerimeter());
            System.out.println("Chu vi: "+ this.getArea());
        }
    }

    public static void main(String[] args) {
        Rectangle hcn = new Rectangle("Hình chữ nhật",3,6);
        Circle t = new Circle("Hình tròn",2);

        hcn.draw();
        hcn.displayInfo();
        t.draw();
        t.displayInfo();
    }
}
