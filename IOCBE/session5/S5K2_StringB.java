public class S5K2_StringB {
    public static void main(String[] args) {

        long start,end;
        String str1 = "Hello";
        StringBuffer str2 = new StringBuffer("Hello");
        StringBuilder str3 = new StringBuilder("Hello");

        start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++)
            str1 += " World";
        end = System.currentTimeMillis();
        System.out.println("Thời gian thực hiện với String: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++)
            str2.append(" World");
        end = System.currentTimeMillis();
        System.out.println("Thời gian thực hiện với StringBuffer: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++)
            str3.append(" World");
        end = System.currentTimeMillis();
        System.out.println("Thời gian thực hiện với StringBuilder: " + (end - start) + "ms");

        System.out.print("""
                Nhận xét:
                - String: Không hiệu quả cho phép nối chuỗi nhiều lần do tạo ra nhiều đối tượng mới.
                - StringBuilder: Hiệu quả và nhanh chóng, thích hợp cho nhiều thao tác nối chuỗi trong một luồng.
                - StringBuffer: Tương tự như StringBuilder nhưng an toàn với đa luồng, có thể chậm hơn một chút do đồng bộ hóa. 
                """);
    }
}
