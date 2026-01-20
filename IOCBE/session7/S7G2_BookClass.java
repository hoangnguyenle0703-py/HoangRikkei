import java.util.Scanner;

public class S7G2_BookClass {
    public static class Book{
        public String title;
        public String author;
        public double price;

        public Book(String title, String author, double price) {
            this.title = title;
            this.author = author;
            this.price = price;
        }

        public void printInfo(){
            System.out.println("title: " + this.title);
            System.out.println("author: " + this.author);
            System.out.println("price: " + this.price);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập nhan đề: ");
        String title = sc.nextLine();
        System.out.print("Nhập tên tác giả: ");
        String author = sc.nextLine();
        System.out.print("Nhập giá: ");
        double price = sc.nextDouble();

        Book b = new Book(title,author,price);
        b.printInfo();
    }
}
