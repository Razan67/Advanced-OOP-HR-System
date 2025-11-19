package advancedFinal;


import java.util.List;

//A Concrete Strategy for generating a tax summary report.
public class TaxSummaryReport implements ReportGenerator {
 @Override
 public String generate(List<Employee> employees, List<Payroll> payrolls) {
     StringBuilder reportContent = new StringBuilder();
     reportContent.append("--- Tax Summary Report ---\n");
     
     double totalTaxDeducted = payrolls.stream()
         .mapToDouble(Payroll::getTaxDeducted)
         .sum();
         
     reportContent.append(String.format("Total tax deducted across all employees this period: $%.2f\n", totalTaxDeducted));
     reportContent.append("----------------------------------\n");
     
     return reportContent.toString();
 }
}
