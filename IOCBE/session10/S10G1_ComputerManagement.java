public class S10G1_ComputerManagement {

    public static class Computer {
        public double calculatePrice(double basePrice){return basePrice;}
        public double calculatePrice(double basePrice,double tax){return basePrice+tax;}
        public double calculatePrice(double basePrice,double tax,double discount){return basePrice+tax-discount;}
    }

    public static void main(String[] args) {
        Computer computer = new Computer();
        System.out.println("[Using basePrice only]");
        System.out.println("Final price = " + computer.calculatePrice(1000));
        System.out.println("[Using basePrice + tax]");
        System.out.println("Final price = " + computer.calculatePrice(1000,100));
        System.out.println("[Using basePrice + tax + discount]");
        System.out.println("Final price = " + computer.calculatePrice(1000,100,50));
    }
}
