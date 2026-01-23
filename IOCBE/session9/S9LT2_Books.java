import java.util.ArrayList;

public class S9LT2_Books {

    public static class Book{
        private String title;
        private String author;
        private double price;

        public Book(String title, String author, double price){
            this.title = title;
            this.author = author;
            this.price = price;
        }

        public void printInfo(){
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Price: " + price);
        }
    }

    public static void main(String[] args) {
        ArrayList<Book> b = new ArrayList<>(5);
        b.add(new Book("Bí quyết làm giàu", "Nguyễn Lê Hoàng", 10000000.00));
        b.add(new Book("Top những cái bẫy lừa đảo chết người", "Hoàng Nguyễn Lê", 20000000.00));
        b.add(new Book("Hướng dẫn mua hàng hiệu quả", "Lê Hoàng Nguyễn", 30000000.00));

        for(int i = 0; i < b.size(); i++){
            System.out.println("Book "+(i+1)+":");
            b.get(i).printInfo();
        }

    }
}
