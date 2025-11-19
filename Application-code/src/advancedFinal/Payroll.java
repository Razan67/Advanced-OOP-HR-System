package advancedFinal;

//Payroll.java
public class Payroll {
 private String payrollId;
 private String employeeId;
 private String month;
 private double monthlyPayment;
 private double taxDeducted;
 private double netSalary;

 public Payroll(String employeeId, String month, double monthlyPayment) {
     this.payrollId = "PAY-" + employeeId + "-" + month;
     this.employeeId = employeeId;
     this.month = month;
     this.monthlyPayment = monthlyPayment;
     this.taxDeducted = monthlyPayment * 0.20;
     this.netSalary = monthlyPayment - this.taxDeducted;
 }

 // --- Getters ---
 public String getPayrollId() {
     return this.payrollId;
 }

 public String getEmployeeId() {
     return this.employeeId;
 }
 
 public String getMonth() {
     return month;
 }
 
 public double getMonthlyPayment() {
     return monthlyPayment;
 }

 public double getTaxDeducted() {
     return this.taxDeducted;
 }

 public double getNetSalary() {
     return this.netSalary;
 }

 @Override
 public String toString() {
     return "Payroll [ID=" + payrollId + ", EmployeeID=" + employeeId + ", NetSalary=" + netSalary + "]";
 }
}