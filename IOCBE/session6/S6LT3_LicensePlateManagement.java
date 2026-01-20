import java.util.*;

public class S6LT3_LicensePlateManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Vector<String> plates = new Vector<>();
        while(true) {
            System.out.print("""
                    *************** QUẢN LÝ BIỂN SỐ XE ***************
                    1. Thêm các biển số xe
                    2. Hiển thị danh sách biển số xe
                    3. Tìm kiếm biển số xe
                    4. Tìm kiếm biển số xe theo mã tỉnh
                    5. Sắp xếp biển số xe tăng dần
                    6. Thoát
                    Lựa chọn của bạn: 
                    """);
            int choice = sc.nextInt();
            if(choice == 1) {
                System.out.println("Nhập số lượng biển số xe thêm vào: ");
                int n =  sc.nextInt();
                System.out.println("Nhập danh sách biển số thêm vào: ");
                for(int i = 0; i < n; i++){
                    String plate = sc.next();
                    plates.add(plate);
                }
            }
            else if(choice == 2) {
                System.out.println("Danh sách biển số xe: ");
                for (String plate : plates) System.out.println(plate);
            }
            else if(choice == 3) {
                System.out.print("Nhập biển số xe cần tìm: ");
                String plate = sc.next();
                boolean ok = false;
                for(int i = 0; i < plates.size(); i++){
                    if(plate.equals(plates.get(i))){
                        System.out.println("Biển số nằm ở vị trí thứ " + (i+1) + " trong danh sách");
                        ok = true;
                        break;
                    }
                }
                if(!ok) System.out.println("Biển số không nằm trong danh sách");
            }
            else if(choice == 4) {
                System.out.println("Nhập mã tỉnh cần tìm: ");
                String code =  sc.next();
                Vector<String> codePlate =  new Vector<>();
                for(String plate : plates){
                    if(plate.startsWith(code)){
                        codePlate.add(plate);
                    }
                }
                System.out.println("Biển số xe theo mã tỉnh " + code + " " + codePlate);
            }
            else if(choice == 5) {
                plates.sort(null);
                System.out.println("Danh sách sau khi sắp xếp: " + plates);
            }
            else if(choice == 6) break;
            else System.out.println("Chọn lại");
        }
    }
}
