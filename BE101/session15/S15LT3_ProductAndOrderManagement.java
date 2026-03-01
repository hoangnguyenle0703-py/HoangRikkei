import java.util.*;

class InvalidBusinessException extends Exception {
    public InvalidBusinessException(String message) {
        super(message);
    }
}

class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}

// Lớp Product
class Product {
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

    @Override
    public String toString() {
        return String.format("SP[ID: %d | Tên: %-15s | Giá: %,.0f VNĐ]", id, name, price); //
    }
}

// Lớp Order
class Order {
    private int orderId;
    private List<Product> products; //

    public Order(int orderId) {
        this.orderId = orderId;
        this.products = new ArrayList<>();
    }

    public int getOrderId() { return orderId; }

    // Thêm sản phẩm vào đơn hàng
    public void addProduct(Product p) {
        products.add(p);
    }

    // Tính tổng tiền đơn hàng
    public double calculateTotal() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void displayOrderInfo() {
        System.out.println("--- Chi tiết Đơn hàng ID: " + orderId + " ---");
        if (products.isEmpty()) {
            System.out.println("Đơn hàng chưa có sản phẩm nào.");
        } else {
            products.forEach(System.out::println);
            System.out.printf(">> TỔNG TIỀN: %,.0f VNĐ\n", calculateTotal());
        }
    }
}

public class S15LT3_ProductAndOrderManagement {
    // Sử dụng List để quản lý sản phẩm
    private static List<Product> productList = new ArrayList<>();

    // Sử dụng Map để quản lý đơn hàng (Key: String mã đơn, Value: Order)
    private static Map<String, Order> orderMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n================ MENU ================"); //
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Hiển thị sản phẩm");
            System.out.println("4. Tạo đơn hàng");
            System.out.println("5. Thêm sản phẩm vào đơn hàng");
            System.out.println("6. Hiển thị đơn hàng");
            System.out.println("0. Thoát");
            System.out.println("======================================"); //
            System.out.print("Lựa chọn của bạn: "); //

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> addProduct(sc);
                    case 2 -> deleteProduct(sc);
                    case 3 -> displayProducts();
                    case 4 -> createOrder(sc);
                    case 5 -> addProductToOrder(sc);
                    case 6 -> displayOrder(sc);
                    case 0 -> {
                        System.out.println("Hệ thống kết thúc."); //
                        System.exit(0);
                    }
                    default -> System.out.println("Vui lòng chọn từ 0-6.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Vui lòng nhập số hợp lệ!");
            } catch (InvalidBusinessException | ItemNotFoundException e) {
                System.err.println("Lỗi nghiệp vụ: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Lỗi hệ thống: " + e.getMessage());
            }
        }
    }

    private static void addProduct(Scanner sc) throws InvalidBusinessException {
        System.out.print("Nhập ID sản phẩm (số nguyên): ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nhập tên sản phẩm: ");
        String name = sc.nextLine();
        System.out.print("Nhập giá sản phẩm: ");
        double price = Double.parseDouble(sc.nextLine());

        if (price <= 0) {
            throw new InvalidBusinessException("Giá sản phẩm phải lớn hơn 0."); //
        }

        productList.add(new Product(id, name, price));
        System.out.println("Thêm sản phẩm thành công!"); //
    }

    private static void deleteProduct(Scanner sc) {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());

        // Tìm sản phẩm
        Product found = productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (found == null) {
            throw new ItemNotFoundException("Không tìm thấy sản phẩm có ID: " + id); //
        }

        productList.remove(found);
        System.out.println("Đã xóa sản phẩm thành công."); //
    }

    private static void displayProducts() {
        if (productList.isEmpty()) {
            System.out.println("Kho hàng trống.");
        } else {
            System.out.println("--- DANH SÁCH SẢN PHẨM ---");
            productList.forEach(System.out::println);
        }
    }

    private static void createOrder(Scanner sc) {
        System.out.print("Nhập Mã đơn hàng (VD: ORD01) dùng làm Key: ");
        String key = sc.nextLine();

        if (orderMap.containsKey(key)) {
            System.out.println("Lỗi: Mã đơn hàng này đã tồn tại!");
            return;
        }

        System.out.print("Nhập số ID nội bộ của đơn hàng (số nguyên): ");
        int orderId = Integer.parseInt(sc.nextLine());

        orderMap.put(key, new Order(orderId));
        System.out.println("Tạo đơn hàng thành công với mã: " + key);
    }

    private static void addProductToOrder(Scanner sc) {
        System.out.print("Nhập Mã đơn hàng (Key) muốn thêm SP: ");
        String orderKey = sc.nextLine();

        if (!orderMap.containsKey(orderKey)) {
            throw new ItemNotFoundException("Đơn hàng '" + orderKey + "' không tồn tại trong hệ thống."); //
        }

        System.out.print("Nhập ID sản phẩm muốn thêm: ");
        int prodId = Integer.parseInt(sc.nextLine());

        // Tìm sản phẩm trong kho
        Product prod = productList.stream()
                .filter(p -> p.getId() == prodId)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Sản phẩm ID " + prodId + " không có trong kho."));

        // Thêm vào đơn
        orderMap.get(orderKey).addProduct(prod);
        System.out.println("Đã thêm '" + prod.getName() + "' vào đơn hàng " + orderKey);
    }

    private static void displayOrder(Scanner sc) {
        System.out.print("Nhập Mã đơn hàng (Key) cần xem (hoặc 'all' để xem tất cả): ");
        String key = sc.nextLine();

        if (key.equalsIgnoreCase("all")) {
            if (orderMap.isEmpty()) {
                System.out.println("Chưa có đơn hàng nào.");
            } else {
                orderMap.forEach((k, order) -> {
                    System.out.println("\n[Mã đơn Map: " + k + "]");
                    order.displayOrderInfo();
                });
            }
        } else {
            Order order = orderMap.get(key);
            if (order == null) {
                throw new ItemNotFoundException("Không tìm thấy đơn hàng mã: " + key); //
            }
            order.displayOrderInfo();
        }
    }
}