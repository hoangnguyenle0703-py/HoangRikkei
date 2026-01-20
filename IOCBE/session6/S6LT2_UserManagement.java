import java.util.Scanner;

public class S6LT2_UserManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String name = new String();
        String email = new String();
        String password = new String();
        String phone = new String();
        while(true){
            System.out.print("""
                    *************** QUẢN LÝ NGƯỜI DÙNG ***************
                    1. Nhập thông tin người dùng
                    2. Chuẩn hóa họ tên
                    3. Kiểm tra email hợp lệ
                    4. Kiểm tra số điện thoại hợp lệ
                    5. Kiểm tra mật khẩu hợp lệ
                    6. Thoát
                    Lựa chọn của bạn:
                    """);
            int choice = sc.nextInt();
            sc.nextLine();
            if(choice == 1) {
                System.out.print("Họ và tên: ");
                name = sc.nextLine();
                System.out.print("Email: ");
                email = sc.nextLine();
                System.out.print("Password: ");
                password = sc.nextLine();
                System.out.print("Phone number: ");
                phone = sc.nextLine();
            }
            else if(choice == 2) {
                name = name.trim().toLowerCase().replaceAll("\\s+"," ");
                String[] words = name.split(" ");
                StringBuilder newName = new StringBuilder();
                for(String word : words){
                    if(!word.isEmpty()){
                        newName.append(Character.toUpperCase(word.charAt(0)))
                                .append(word.substring(1))
                                .append(" ");
                    }
                }
                name = newName.toString().trim();
                System.out.println("Tên sau khi chuẩn hóa: " + name);
            }
            else if(choice == 3) {
                email = email.trim();
                String regex = "\\b[\\w._]+@[\\w.]+.[a-zA-Z]{2,6}\\b";
                if(email.matches(regex))
                    System.out.println("Email hợp lệ");
                else System.out.println("Email không hợp lệ");
            }
            else if(choice == 4) {
                phone = phone.trim();
                String regex = "^(032|033|034|035|036|037|038|039|096|097|" +
                        "098|086|083|084|085|081|082|088|091|094|070|079|077|" +
                        "076|078|090|093|089|056|058|092|059|099)[0-9]{7}$";
                if(phone.matches(regex))
                    System.out.println("Số điện thoại hợp lệ");
                else System.out.println("Số điện thoại không hợp lệ");
            }
            else if(choice == 5) {
                password =  password.trim();
                String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[!@#$%]).{8,}$";
                if(password.matches(regex))
                    System.out.println("Mật khẩu hợp lệ");
                else System.out.println("Mật khẩu không hợp lệ");
            }
            else if(choice == 6)break;
            else System.out.println("Chọn lại.");
        }
    }
}
