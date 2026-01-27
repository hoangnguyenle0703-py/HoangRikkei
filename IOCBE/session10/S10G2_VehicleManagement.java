public class S10G2_VehicleManagement {
    public static class Car{
        private int currentSpeed = 0;

        void accelerate(){
            currentSpeed += 10;
            System.out.println("Car accelerates by default: +10 km/h");
        }
        void accelerate(int speed){
            currentSpeed += speed;
            System.out.println("Car accelerates by " + speed + " km/h");
        }
        void accelerate(int speed,int seconds){
            int increase = speed*seconds;
            currentSpeed += increase;
            System.out.println("Car accelerates by " + increase + " km/h (speed x time)");
        }

        void printStatus(){
            System.out.println("Current speed: " + currentSpeed + " km/h");
        }
    }

    public static void main(String[] args) {
        Car car = new Car();
        car.accelerate();
        car.printStatus();
        car.accelerate(20);
        car.printStatus();
        car.accelerate(10,2);
        car.printStatus();
    }
}
