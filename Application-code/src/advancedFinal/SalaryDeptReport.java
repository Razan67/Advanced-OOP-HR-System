package advancedFinal;

import java.util.List;

//A Concrete Strategy for generating a salary report.
public class SalaryDeptReport implements ReportGenerator {
 @Override
 public String generate(List<Employee> employees, List<Payroll> payrolls) {
     StringBuilder reportContent = new StringBuilder();
     reportContent.append("--- Salary Department Report ---\n");
     reportContent.append("Employee ID | Employee Name | Job Title | Monthly Salary\n");
     reportContent.append("--------------------------------------------------------\n");

     for (Employee emp : employees) {
         // Find the latest payroll for this employee to get their salary
         double salary = payrolls.stream()
             .filter(p -> p.getEmployeeId().equals(emp.getEmployeeId()))
             .map(Payroll::getMonthlyPayment)
             .findFirst()
             .orElse(0.0);

         reportContent.append(String.format("%-11s | %-13s | %-10s | $%.2f\n",
             emp.getEmployeeId(), emp.getFullName(), emp.getJobTitle(), salary));
     }
     return reportContent.toString();
 }
}

