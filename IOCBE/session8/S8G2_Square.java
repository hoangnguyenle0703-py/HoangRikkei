import java.util.Scanner;

public class S8G2_Square {
    public static class Rectangular{
        private double width;
        private double height;
        private static double maxArea = 0;

        public Rectangular(double width,double height){
            this.width = width;
            this.height = height;
        }

        public double getArea(){
            if(maxArea<width*height)maxArea=width*height;
            return width*height;
        }

        public double getPerimeter(){
            return 2*(width+height);
        }

        public double getWidth(){
            return width;
        }
        public double getHeight(){
            return height;
        }

        public String toString(int i){
            return "Rectangle "+i+" (width="+this.width+", height="+this.height+
                    ", getPerimeter="+this.getPerimeter()+", area="+this.getArea()+")";
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Rectangular[] rec = new Rectangular[3];
        for(int i = 0; i < 3; i++){
            System.out.printf("Nhập chiều dài HCN%d: ",i+1);
            double h =  input.nextDouble();
            System.out.printf("Nhập chiều rộng HCN%d: ",i+1);
            double r = input.nextDouble();
            rec[i] = new Rectangular(h,r);
            System.out.println(rec[i].toString(i+1));
        }
        System.out.print("Diện tích lớn nhất: " + Rectangular.maxArea);
    }
}
