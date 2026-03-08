import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class S16G2_ProductManagement {
    static class Product {
        private String name;
        private double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price + "}";
        }
    }

    interface ProductProcessor {
        double calculateTotalValue(List<Product> products);

        static void printProductList(List<Product> products) {
            System.out.println("Danh sách sản phẩm:");
            for (Product p : products) {
                System.out.println(p);
            }
        }

        default boolean hasExpensiveProduct(List<Product> products) {
            Predicate<Product> isExpensive = product -> product.getPrice() > 100;

            return products.stream().anyMatch(isExpensive);
        }
    }

    static class ProductProcessorImpl implements ProductProcessor {

        @Override
        public double calculateTotalValue(List<Product> products) {
            double total = 0;

            for (Product p : products) {
                total += p.getPrice();
            }
            return total;
        }
    }

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Áo thun", 50.0));
        products.add(new Product("Quần Jeans", 150.0));
        products.add(new Product("Giày thể thao", 200.0));
        products.add(new Product("Mũ", 30.0));

        ProductProcessor processor = new ProductProcessorImpl();

        ProductProcessor.printProductList(products);
        System.out.println("-------------------------");

        boolean hasExpensive = processor.hasExpensiveProduct(products);
        if (hasExpensive) {
            System.out.println("Cảnh báo: Có sản phẩm đắt tiền trong danh sách!");
        } else {
            System.out.println("Không có sản phẩm đắt tiền"); //
        }

        double totalValue = processor.calculateTotalValue(products);
        System.out.println("Tổng giá trị các sản phẩm: " + totalValue);
    }
}