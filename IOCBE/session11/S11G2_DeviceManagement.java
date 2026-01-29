import java.util.ArrayList;

public class S11G2_DeviceManagement {

    public static abstract class Device{
        public static int cnt = 0;
        int id;
        String name;

        public Device(String name){
            this.id = ++cnt;
            this.name = name;
        }
        abstract void turnOn();
        abstract void turnOff();
    }

    interface Connectable{
        void connectWifi();
    }

    interface Chargeable{
        void charge();
    }

    public static class Smartphone extends Device implements Connectable,Chargeable {
        public Smartphone(String name){
            super(name);
        }

        @Override
        void turnOn(){
            System.out.println("Bật điện thoại "+ name);
        }
        @Override
        void turnOff(){
            System.out.println("Tắt điện thoại" + name);
        }
        @Override
        public void connectWifi(){
            System.out.println("Kết nối Wifi đến " + name);
        }
        @Override
        public void charge(){
            System.out.println("Đang sạc điện thoại " + name);
        }
    }

    public static class Laptop extends Device implements Connectable,Chargeable {
        public Laptop(String name){
            super(name);
        }

        @Override
        void turnOn(){
            System.out.println("Bật Laptop "+ name);
        }
        @Override
        void turnOff(){
            System.out.println("Tắt Laptop" + name);
        }
        @Override
        public void connectWifi(){
            System.out.println("Kết nối Wifi đến " + name);
        }
        @Override
        public void charge(){
            System.out.println("Đang sạc Laptop " + name);
        }
    }
    public static class Television extends Device implements Connectable {
        public Television(String name){
            super(name);
        }

        @Override
        void turnOn(){
            System.out.println("Bật Tivi "+ name);
        }
        @Override
        void turnOff(){
            System.out.println("Tắt Tivi" + name);
        }

        @Override
        public void connectWifi(){
            System.out.println("Kết nối Wifi đến " + name);
        }
    }

    public static void main(String[] args) {
        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new Smartphone("Iphone 8 plus"));
        devices.add(new Laptop("MSI"));
        devices.add(new Television("LGTV"));

        for (Device d : devices) {
            d.turnOn();
            d.turnOff();
            if(d instanceof Connectable){
                ((Connectable)d).connectWifi();
            }
            if(d instanceof Chargeable){
                ((Chargeable)d).charge();
            }
        }
    }
}
