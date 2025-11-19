package advancedFinal;

public class PartTimeEmployee extends Employee {
private int workingHoursPerMonth;
private double hourlyRate;

public PartTimeEmployee(String employeeId, String fullName, String jobTitle, double hourlyRate) {
   super(employeeId, fullName, jobTitle);
   this.hourlyRate = hourlyRate;
}

public void setWorkingHoursPerMonth(int hours) {
   this.workingHoursPerMonth = hours;
}

@Override
public double calculateSalary() {
   // Salary is calculated based on hours worked
   return this.workingHoursPerMonth * this.hourlyRate;
}
public double getHourlyRate() {
    return hourlyRate;
}

public void setHourlyRate(double hourlyRate) {
    this.hourlyRate = hourlyRate;
}
}