package ra.entity;

import java.util.*;
public class Product{
    private static int cnt = 0;
    private static Set<String> nameList = new HashSet<>();
    private final int productId;
    private String productName;
    private double price;
    private String category;
    private int quantity;

    public Product(){
        this.productId = ++cnt;
    }
    public Product(String productName, double price, String category, int quantity){
        this();
        setInfo("product", productName);
        setInfo("price",String.valueOf(price));
        setInfo("category", category);
        setInfo("quantity", String.valueOf(quantity));
    }
    public String getInfo(String type){
        if(type.equalsIgnoreCase("Product"))
            return this.productName;
        else if(type.equalsIgnoreCase("Category"))
            return this.category;
        else if(type.equalsIgnoreCase("Quantity"))
            return this.quantity + "";
        else if(type.equalsIgnoreCase("Price"))
            return this.price + "";
        else if(type.equalsIgnoreCase("Id"))
            return this.productId + "";
        else return "";
    }
    public void setInfo(String type, String info){
        if(type.equalsIgnoreCase("Product")){
            if (info == null || info.length() < 10 || info.length() > 50)
                throw new IllegalArgumentException("Tên sản phẩm phải từ 10-50 ký tự.");
            if (!info.equals(this.productName) && nameList.contains(info))
                throw new IllegalArgumentException("Tên sản phẩm '" + info + "' đã tồn tại.");
            nameList.remove(this.productName);
            this.productName = info;
            nameList.add(this.productName);
        }
        else if(type.equalsIgnoreCase("Category")){
            if (info != null && info.length() > 200)
                throw new IllegalArgumentException("Loại sản phẩm tối đa 200 ký tự.");
            this.category = info;
        }
        else if(type.equalsIgnoreCase("Quantity")){
            int q = Integer.parseInt(info);
            if (q < 0)
                throw new IllegalArgumentException("Số lượng tồn kho phải lớn hơn hoặc bằng 0.");
            this.quantity = q;
        }
        else if(type.equalsIgnoreCase("Price")){
            double p = Double.parseDouble(info);
            if (p <= 0)
                throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0.");
            this.price = p;
        }
    }
    public void inputData(Scanner scanner) {
        System.out.println("Nhập thông tin cho sản phẩm mã " + this.productId + ":");
        while (true) {
            try {
                System.out.print("Nhập tên sản phẩm (10-50 ký tự): ");
                setInfo("product", scanner.nextLine());
                break;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Nhập giá sản phẩm (>0): ");
                setInfo("price", scanner.nextLine());
                break;
            } catch (Exception e) {
                System.err.println("Giá không hợp lệ. Vui lòng nhập số thực lớn hơn 0.");
            }
        }
        while (true) {
            try {
                System.out.print("Nhập loại sản phẩm (tối đa 200 ký tự): ");
                setInfo("category", scanner.nextLine());
                break;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Nhập số lượng (>=0): ");
                setInfo("quantity", scanner.nextLine());
                break;
            } catch (Exception e) {
                System.err.println("Số lượng không hợp lệ.");
            }
        }
    }
    @Override
    public String toString() {
        return String.format(
                "Product [ID: %d | Tên: %s | Giá: %.2f | Loại: %s | Số lượng: %d]",
                productId, productName, price, category, quantity
        );
    }
}

