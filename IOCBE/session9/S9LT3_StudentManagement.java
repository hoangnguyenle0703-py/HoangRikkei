import java.util.ArrayList;

public class S9LT3_StudentManagement {
    public static class Student{
        private int id;
        private String fullName;
        private int age;
        private double gpa;
        private static int count = 0;
        private static final double MIN_GPA = 0;
        private static final double MAX_GPA = 4.0;

        public Student(String fullName, int age, double gpa) {
            if(gpa < MIN_GPA || gpa > MAX_GPA)
                throw new IllegalArgumentException();
            this.id = ++count;
            this.fullName = fullName;
            this.age = age;
            this.gpa = gpa;
        }

        void printInfo(){
            System.out.println("ID: " + id);
            System.out.println("Full name: " + fullName);
            System.out.println("Age: " + age);
            System.out.println("GPA: " + gpa);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();

        students.add(new Student("Nguyễn Lê Hoàng",18,4.0));
        students.add(new Student("Hoàng Nguyễn Lê",19,3.95));
        students.add(new Student("Lê Hoàng Nguyễn",20,3.6));

        for(Student student : students){
            student.printInfo();
        }
    }
}
