package advancedFinal;

import java.util.List;

//A Concrete Strategy for generating a log of all payroll transactions.
public class PayrollLogReport implements ReportGenerator {
 @Override
 public String generate(List<Employee> employees, List<Payroll> payrolls) {
     StringBuilder reportContent = new StringBuilder();
     reportContent.append("--- Full Payroll Log Report ---\n");
     reportContent.append("Payroll ID      | Employee ID | Month    | Net Salary\n");
     reportContent.append("------------------------------------------------------\n");

     for (Payroll p : payrolls) {
         reportContent.append(String.format("%-15s | %-11s | %-8s | $%.2f\n",
             p.getPayrollId(), p.getEmployeeId(), p.getMonth(), p.getNetSalary()));
     }
     return reportContent.toString();
 }
}
