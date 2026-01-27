public class S10K1_AnimalToDog {

    public static class Animal {
        protected String name;

        public void makeSound() {
            System.out.println("Some animal sound");
        }
    }

    public static class Dog extends Animal {
        public Dog(String name) {
            this.name = name;
        }
        public void makeSound() {
            System.out.println("baubau");
        }
    }

    public static void main(String[] args) {
        Dog d = new Dog("Buddy");
        d.makeSound();
    }
}
