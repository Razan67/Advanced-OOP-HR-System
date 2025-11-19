package advancedFinal;

import java.util.Scanner;

public class Main {

 public static void main(String[] args) {
     System.out.println("--- HR System Initializing ---");

     // --- 1. System Setup: Create all the backend components ---
     EmployeeFactory employeeFactory = new EmployeeFactory();
     EmployeeDatabase employeeDatabase = new EmployeeDatabase();
     DepartmentDatabase departmentDatabase = new DepartmentDatabase();
     PayrollDatabase payrollDatabase = new PayrollDatabase();
     LeaveDatabase leaveDatabase = new LeaveDatabase();
     OnboardingFacade onboardingFacade = new OnboardingFacade(employeeFactory, employeeDatabase, departmentDatabase, payrollDatabase);

     // --- 2. Create the User Interface Handler ---
     HRSystemCLI cli = new HRSystemCLI();
     Scanner scanner = new Scanner(System.in);

     // --- 3. Start the Application ---.
     cli.start(scanner, employeeDatabase, departmentDatabase, payrollDatabase, leaveDatabase, onboardingFacade);

     scanner.close();
     System.out.println("--- System Shutdown Complete ---");
 }
}