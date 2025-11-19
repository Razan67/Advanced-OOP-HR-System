package advancedFinal;

public class FullTimeEmployee extends Employee {
 private double salary; 

 public FullTimeEmployee(String id, String name, String title, double salary) {
     super(id, name, title);
     this.salary = salary;
 }
 @Override
 public double calculateSalary() {
     // For a full-time employee, the salary is fixed
     return this.salary;
 }


public double getSalary() {
    return salary;
}

public void setSalary(double salary) {
    this.salary = salary;
}
}