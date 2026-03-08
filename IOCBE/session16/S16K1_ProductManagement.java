import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class S16K1_ProductManagement {
    static class Product {
        private int id;
        private String name;
        private double price;

        public Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }

        public void setName(String name) { this.name = name; }
        public void setPrice(double price) { this.price = price; }

        @Override
        public String toString() {
            return String.format("ID: %d, Name: %s, Price: %.1f", id, name, price);
        }
    }

    private static Map<Integer, Product> productMap = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Product Management System ---");
            System.out.println("1. Add Product");
            System.out.println("2. Edit Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Display Products");
            System.out.println("5. Filter Products (Price > 100)");
            System.out.println("6. Total Value of Products");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> editProduct();
                    case 3 -> deleteProduct();
                    case 4 -> displayProducts();
                    case 5 -> filterProducts();
                    case 6 -> calculateTotalValue();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (productMap.containsKey(id)) {
            System.out.println("Product ID already exists!");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        productMap.put(id, new Product(id, name, price));
        System.out.println("Product added successfully."); //
    }

    private static void editProduct() {
        System.out.print("Enter Product ID to edit: ");
        int id = Integer.parseInt(scanner.nextLine());

        Product product = productMap.get(id);
        if (product != null) {
            System.out.print("Enter new Product Name: ");
            product.setName(scanner.nextLine());
            System.out.print("Enter new Product Price: ");
            product.setPrice(Double.parseDouble(scanner.nextLine()));
            System.out.println("Product updated successfully."); //
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (productMap.remove(id) != null) {
            System.out.println("Product deleted successfully."); //
        } else {
            System.out.println("Product not found."); //
        }
    }

    private static void displayProducts() {
        if (productMap.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product p : productMap.values()) {
                System.out.println(p);
            }
        }
    }

    private static void filterProducts() {
        System.out.println("Products with price greater than 100:"); //
        productMap.values().stream()
                .filter(p -> p.getPrice() > 100)
                .forEach(System.out::println);
    }

    private static void calculateTotalValue() {
        double total = productMap.values().stream()
                .mapToDouble(Product::getPrice)
                .sum();

        System.out.println("Total value of products: " + total); //
    }
}