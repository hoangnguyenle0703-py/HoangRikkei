import java.util.Scanner;

public class S8K1_StudentClass {

    public static class Student {
        private String name;
        private int age;
        private final String id;
        public Student(String id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
        public void HienThiThongTin(){
            System.out.println("ID: "+ this.id);
            System.out.println("Name: "+ this.name);
            System.out.println("Age: "+ this.age);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter your name: ");
        String name = input.nextLine();
        System.out.println("Enter your age: ");
        int age = input.nextInt();
        input.nextLine();
        System.out.println("Enter your ID: ");
        String id = input.nextLine();
        Student st1 = new Student(id,name,age);
        st1.HienThiThongTin();
    }
}
