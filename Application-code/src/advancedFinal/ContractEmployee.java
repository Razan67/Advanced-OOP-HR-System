package advancedFinal;

public class ContractEmployee extends Employee {
private int durationInMonths;
private double overallPayment;

public ContractEmployee(String employeeId, String fullName, String jobTitle, double overallPayment, int durationInMonths) {
   super(employeeId, fullName, jobTitle);
   this.overallPayment = overallPayment;
   this.durationInMonths = durationInMonths;
}

public int getDurationInMonths() {
    return this.durationInMonths;
}

public double getOverallPayment() {
    return this.overallPayment;
}

@Override
public double calculateSalary() {
   // The "salary" for a contract employee might be the total payment divided by duration
   return this.overallPayment / this.durationInMonths;
}
}