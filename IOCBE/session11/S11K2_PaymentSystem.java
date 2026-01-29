import java.util.ArrayList;

public class S11K2_PaymentSystem {

    public static abstract class Payment {
        double amount;
        public Payment(double amount) {
            this.amount = amount;
        }

        abstract void pay();

        void printAmount(){
            System.out.println(this.amount);
        }
    }

    interface Refundable{
        void refund();
    }

    public static class CashPayment extends Payment{
        public CashPayment(double amount) {
            super(amount);
        }
        @Override
        void pay() {
            System.out.println("Trả bằng tiền mặt số tiền "+ amount);
        }
    }

    public static class CreditCardPayment extends Payment implements Refundable{
        public CreditCardPayment(double amount) {
            super(amount);
        }
        @Override
        public void pay(){
            System.out.println("Trả bằng thẻ ngân hàng số tiền "+ amount);
        }
        @Override
        public void refund(){
            System.out.println("Hoàn trả lại số tiền "+amount);
        }
    }

    public static class EWalletPayment extends Payment implements Refundable{
        public EWalletPayment(double amount) {
            super(amount);
        }
        @Override
        public void pay(){
            System.out.println("Trả bằng ví điện tử số tiền "+ amount);
        }
        @Override
        public void refund(){
            System.out.println("Hoàn trả lại số tiền "+ amount);
        }
    }

    public static void main(String[] args) {
        ArrayList<Payment> payments = new ArrayList<Payment>();
        payments.add(new CashPayment(100000));
        payments.add(new CreditCardPayment(2000000));
        payments.add(new EWalletPayment(50000000));
        for (Payment payment : payments) {
            payment.pay();
            if(payment instanceof Refundable){
                ((Refundable)payment).refund();
            }
        }
    }
}
