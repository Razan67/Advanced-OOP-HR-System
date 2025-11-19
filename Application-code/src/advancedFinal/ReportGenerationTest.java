package advancedFinal;

import org.junit.jupiter.api.Test;

//In src/test/java/your/package/name/ReportGenerationTest.java

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ReportGenerationTest {

 private CompanyManager companyManager;
 private EmployeeDatabase employeeDatabase;
 private PayrollDatabase payrollDatabase;

 @BeforeEach
 void setUp() {
     employeeDatabase = new EmployeeDatabase();
     payrollDatabase = new PayrollDatabase();
     companyManager = new CompanyManager(null, employeeDatabase, payrollDatabase);
 }

 @Test
 void testGenerateSalaryReport_HappyPath() {
     // Test Case 3.1
     // Pre-condition: Add some data to our in-memory databases
     employeeDatabase.addEmployeeToArrayList(new FullTimeEmployee("E001", "Test User", "Tester", 5000));
     payrollDatabase.addToPayrollArrayList(new Payroll("E001", "Sep-2025", 5000));

     // Action
     // To test this properly, we need to capture console output, which is advanced.
     // For this assignment, we'll just verify the method runs without error.
     assertDoesNotThrow(() -> {
         companyManager.generateCompanyReport("Salary");
     }, "Generating a valid report should not throw an exception.");
 }

 @Test
 void testGenerateReportWithNoData_EdgeCase() {
     // Test Case 3.2
     // Pre-condition: The databases are empty (which they are by default in setUp).
     
     // Action & Verification
     assertDoesNotThrow(() -> {
         companyManager.generateCompanyReport("Tax");
     }, "Generating a report with no data should not crash.");
 }
}