import java.util.Scanner;

public class S8K2_BookClass {

    public static class Book{
        private String title;
        private String author;
        private double price;

        public Book(String title, String author, double price) {
            this.title = title;
            this.author = author;
            this.price = price;
        }

        public void printInfo(){
            System.out.println("Title: "+title+"\nAuthor: "+author+"\nPrice: "+price);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter title: ");
        String title = input.nextLine();
        System.out.print("Enter author: ");
        String author = input.nextLine();
        System.out.print("Enter price: ");
        double price = input.nextDouble();
        Book book = new Book(title,author,price);
        book.printInfo();
    }
}
