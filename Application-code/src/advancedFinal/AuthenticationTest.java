package advancedFinal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* This class tests the user authentication logic, which is primarily
* handled by finding an employee in the EmployeeDatabase.
*/
class AuthenticationTest {

 private EmployeeDatabase employeeDatabase;

 @BeforeEach
 void setUp() {
     employeeDatabase = new EmployeeDatabase();
     // We add a valid user to the database so we have someone to find.
     employeeDatabase.addEmployeeToArrayList(new FullTimeEmployee("E002", "Jerry Robinson", "Consultant", 5000));
 }

 @Test
 void testSuccessfulLogin_HappyPath() {
     // This is Test Case 5.1 from our plan.
     System.out.println("--- Running Test: testSuccessfulLogin_HappyPath ---");
     System.out.println("Objective: Verify that a valid employee ID returns an employee object.");

     // Action: Try to find an employee with a valid, existing ID.
     System.out.println("Attempting to find employee with ID: E002");
     Employee foundEmployee = employeeDatabase.findEmployeeById("E002");

     // Verification: Check that the result is what we expect.
     // assertNotNull checks that the object is not null.
     assertNotNull(foundEmployee, "Verification failed: Employee should have been found.");
     
     // assertEquals checks that the found employee has the correct name.
     assertEquals("Jerry Robinson", foundEmployee.getFullName(), "Verification failed: The employee's name is incorrect.");

     System.out.println("Result: Employee 'Jerry Robinson' found successfully.");
     System.out.println("--- Test PASSED ---");
 }

 @Test
 void testLoginWithNonExistentId_FailureCase() {
     // This is Test Case 5.2 from our plan.
     System.out.println("\n--- Running Test: testLoginWithNonExistentId_FailureCase ---");
     System.out.println("Objective: Verify that an invalid employee ID returns null.");

     // Action: Try to find an employee with an ID that does not exist.
     System.out.println("Attempting to find employee with non-existent ID: E999");
     Employee foundEmployee = employeeDatabase.findEmployeeById("E999");

     // Verification: Check that the result is null, as expected.
     // assertNull checks that the object is null.
     assertNull(foundEmployee, "Verification failed: A non-existent employee ID should return null.");

     System.out.println("Result: System correctly returned null for the invalid ID.");
     System.out.println("--- Test PASSED ---");
 }
}