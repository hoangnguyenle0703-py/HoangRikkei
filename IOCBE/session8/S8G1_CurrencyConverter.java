import java.util.Scanner;

public class S8G1_CurrencyConverter {

    public static class CurrencyConverter{
        private static double rate;

        public static void setRate(double r){
            if(r < 0)
                System.out.println("Invalid rate");
            else rate = r;
        }

        public static double getRate(){
            return rate;
        }

        public static double toUSD(int vnd){
            return (double)vnd*rate;
        }

        public static String formatUSD(double usd){
            return "$" + String.format("%.2f",usd);
        }
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        System.out.println("Nhập số tiền vnd: ");
        int vnd = input.nextInt();
        System.out.println("Nhập tỉ giá: ");
        double r = input.nextDouble();
        CurrencyConverter.setRate(r);
        String usd = CurrencyConverter.formatUSD(CurrencyConverter.toUSD(vnd));
        System.out.println("VND->USD: " + usd);
    }
}
