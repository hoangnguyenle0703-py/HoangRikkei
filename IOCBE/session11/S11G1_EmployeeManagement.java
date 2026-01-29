import java.util.ArrayList;

public class S11G1_EmployeeManagement {

    public static abstract class Employee{
        public static int cnt = 0;
        int id = 0;
        String name;
        public Employee(String name){
            this.id = ++cnt;
            this.name = name;
        }
        abstract double calculateSalary();
        void showInfo(){
            System.out.println("Employee ID: " + this.id);
            System.out.println("Name: " + name);
        }
    }

    interface BonusEligible{
        double calculateBonus();
    }

    public static class FulltimeEmployee extends Employee implements BonusEligible{
        private double basicSalary;
        public FulltimeEmployee(String name, double basicSalary){
            super(name);
            this.basicSalary = basicSalary;
        }

        @Override
        public double calculateSalary() {
            return basicSalary;
        }

        @Override
        public double calculateBonus() {
            return basicSalary*0.1;
        }
    }

    public static class PartTimeEmployee extends Employee{
        private double salary;
        private int workingHours;
        public PartTimeEmployee(String name, double salary, int workingHours){
            super(name);
            this.salary = salary;
            this.workingHours = workingHours;
        }
        @Override
        public double calculateSalary() {
            return salary*workingHours;
        }
    }

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(new FulltimeEmployee("Nguyen",10000000));
        employees.add(new PartTimeEmployee("Le",20000000,6));
        employees.add(new PartTimeEmployee("Hoang",30000000,12));

        for (Employee e : employees){
            e.showInfo();
            System.out.println("Lương: "+ e.calculateSalary());
            if(e instanceof BonusEligible){
                System.out.println("Thưởng: "+((BonusEligible)e).calculateBonus());
            }
        }
    }
}
