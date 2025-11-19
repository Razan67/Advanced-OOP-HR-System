package advancedFinal;

import java.util.List;

//The Context class that uses a strategy.
public class ReportContext {
 private ReportGenerator reportStrategy;

 // The context can be configured with a different strategy at runtime.
 public void setReportStrategy(ReportGenerator reportStrategy) {
     this.reportStrategy = reportStrategy;
 }

 // The context delegates the report generation task to the strategy object.
 public String executeReportGeneration(List<Employee> employees, List<Payroll> payrolls) {
     if (reportStrategy == null) {
         return "Error: No report strategy has been set.";
     }
     return reportStrategy.generate(employees, payrolls);
 }
}
