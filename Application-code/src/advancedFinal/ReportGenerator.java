package advancedFinal;

import java.util.List;

//The Strategy Interface
public interface ReportGenerator {
 // Each report strategy will generate a list of strings representing the report's content.
 String generate(List<Employee> employees, List<Payroll> payrolls);
}
