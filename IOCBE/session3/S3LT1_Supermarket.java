import java.util.Scanner;

public class S3LT1_Supermarket {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String customer,product;
        float price,quantity;
        boolean membership;

        System.out.println("========== NHẬP THÔNG TIN HÓA ĐƠN ==========");
        System.out.print("Nhập tên khách hàng: ");
        customer = input.nextLine();
        System.out.print("Nhập tên sản phẩm: ");
        product = input.nextLine();
        System.out.print("Nhập giá sản phẩm: ");
        price = Integer.parseInt(input.nextLine());
        System.out.print("Nhập số lượng mua: ");
        quantity = Integer.parseInt(input.nextLine());
        System.out.print("Khách có thẻ thành viên? (true/false): ");
        membership = input.nextBoolean();

        float total_price = price * quantity;
        float discount = total_price * (membership ? 1 : 0) * (float)0.1;
        float VAT = total_price * (float)0.08;
        float total =  total_price + VAT - discount;

        System.out.println("=============== HÓA ĐƠN ===============");
        System.out.println("Khách hàng: " + customer);
        System.out.println("Sản phẩm: " + product);
        System.out.println("Đơn giá: " + price + " VND");
        System.out.println("Thành tiền: " + total_price + " VND");
        System.out.println("Giảm giá thành viên (10%): " + discount + " VND");
        System.out.println("Tiền VAT: " + VAT + " VND");
        System.out.println("Tổng thanh toán: " + total + " VND");
        System.out.println("========================================");
    }
}
