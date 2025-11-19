package advancedFinal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OnboardingFacadeTest {

 private EmployeeDatabase employeeDatabase;
 private DepartmentDatabase departmentDatabase;
 private PayrollDatabase payrollDatabase;
 private EmployeeFactory employeeFactory;
 private OnboardingFacade onboardingFacade;

 @BeforeEach
 void setUp() {
     employeeDatabase = new EmployeeDatabase(); 
     departmentDatabase = new DepartmentDatabase();
     payrollDatabase = new PayrollDatabase();
     employeeFactory = new EmployeeFactory();
     onboardingFacade = new OnboardingFacade(employeeFactory, employeeDatabase, departmentDatabase, payrollDatabase);
 }

 @Test
 void testSuccessfulOnboarding_HappyPath() {
     // Test Case 1.1
     // Pre-condition: Department "2" exists. We assume the DepartmentDatabase loads it.
     
     // Action
     String[] data = {"E101", "John Doe", "Engineer", "50000.00", "2"};
     onboardingFacade.onboardNewEmployee("FullTime", data);

     // Verification
     Employee newEmployee = employeeDatabase.findEmployeeById("E101");
     assertNotNull(newEmployee, "Employee should be created and found in the database.");
     assertEquals("John Doe", newEmployee.getFullName(), "Employee name should match.");
     assertEquals("2", newEmployee.getDepartment().getDepartmentId(), "Employee should be assigned to the correct department.");
     
     // We would also need a way to check the payroll database, e.g., a find method.
     // assertTrue(payrollDatabase.hasRecordFor("E101"), "Payroll record should be created.");
 }

 @Test
 void testOnboardingWithInvalidDepartment_FailureCase() {
     // Test Case 1.2
     // Pre-condition: Department "99" does not exist.
     
     // Action
     String[] data = {"E102", "Jane Smith", "Analyst", "60000.00", "99"};
     onboardingFacade.onboardNewEmployee("FullTime", data);

     // Verification
     Employee newEmployee = employeeDatabase.findEmployeeById("E102");
     assertNull(newEmployee, "Employee should NOT be created with an invalid department ID.");
 }
}