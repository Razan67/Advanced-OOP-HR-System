package advancedFinal;

import java.util.Scanner;

public class HRSystemCLI {

 // --- Main Menu Handler ---
 public void start(Scanner scanner, EmployeeDatabase empDB, DepartmentDatabase deptDB, PayrollDatabase payrollDB, LeaveDatabase leaveDB, OnboardingFacade facade) {
     String choice = "";
     while (!choice.equals("0")) {
         System.out.println("\n===== Main Menu =====");
         System.out.println("1. Log in as HR Officer");
         System.out.println("2. Log in as Company Manager");
         System.out.println("3. Log in as Employee");
         System.out.println("0. Exit System");
         System.out.print("Enter your choice: ");
         choice = scanner.nextLine();

         switch (choice) {
             case "1":
                 showHROfficerMenu(scanner, facade, empDB, payrollDB, deptDB);
                 break;
             case "2":
                 showCompanyManagerMenu(scanner, leaveDB, empDB, payrollDB);
                 break;
             case "3":
                 showEmployeeLogin(scanner, empDB, leaveDB);
                 break;
             case "0":
                 System.out.println("Exiting system. Goodbye!");
                 break;
             default:
                 System.err.println("Invalid choice. Please try again.");
                 break;
         }
     }
 }

 // --- Menu for HR Officer ---
 private void showHROfficerMenu(Scanner scanner, OnboardingFacade facade, EmployeeDatabase empDB, PayrollDatabase payrollDB, DepartmentDatabase deptDB) {
     HROfficer hrOfficer = new HROfficer(facade, empDB, payrollDB, deptDB);
     String choice = "";

     while (!choice.equals("0")) {
         System.out.println("\n--- HR Officer Menu (Full) ---");
         System.out.println("1. Onboard New Employee");
         System.out.println("2. Update Employee Info");
         System.out.println("3. Process Monthly Payroll for All Employees");
         System.out.println("4. Promote an Employee");
         System.out.println("0. Back to Main Menu");
         System.out.print("Choose an action: ");
         choice = scanner.nextLine();

         switch (choice) {
         case "1":
        	    System.out.println("\n-- Onboarding a New Employee --");
        	    try {
        	        // --- 1. Gather All Data ---
        	        System.out.print("Enter Employee Type (FullTime, PartTime, Contract): ");
        	        String type = scanner.nextLine();
        	        System.out.print("Enter new Employee ID: e.g, EMP-101 ");
        	        String id = scanner.nextLine();
        	        System.out.print("Enter Full Name: ");
        	        String name = scanner.nextLine();
        	        System.out.print("Enter Job Title: ");
        	        String title = scanner.nextLine();
        	        System.out.print("Enter Department ID: 1-5 ");
        	        String deptId = scanner.nextLine();
        	        System.out.print("Enter Email: ");
        	        String email = scanner.nextLine();
        	        System.out.print("Enter Phone Number: ");
        	        String phone = scanner.nextLine();
        	        System.out.print("Enter Password: ");
        	        String password = scanner.nextLine();
        	        System.out.print("Enter Status: (e.g., Active, On Leave, Terminated)");
        	        String status = scanner.nextLine();
        	        System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
        	        String dob = scanner.nextLine();
        	        System.out.print("Enter Gender: MALE or FEMALE ");
        	        String gender = scanner.nextLine();

        	        // --- 2. Create the Employee with Essential Data ---
        	        String[] factoryData;
        	        if (type.equalsIgnoreCase("FullTime")) {
        	            System.out.print("Enter Monthly Salary: ");
        	            String salary = scanner.nextLine();
        	            factoryData = new String[]{id, name, title, salary};
        	        } // ... (add else-if for PartTime and Contract)
        	        else {
        	            System.err.println("Invalid type.");
        	            break;
        	        }
        	        
        	        String[] facadeData = new String[factoryData.length + 1];
        	        System.arraycopy(factoryData, 0, facadeData, 0, factoryData.length);
        	        facadeData[factoryData.length] = deptId;
        	        hrOfficer.createNewEmployee(type, facadeData);

        	        Employee newEmployee = empDB.findEmployeeById(id);
        	        if (newEmployee != null) {
        	            newEmployee.setEmail(email);
        	            newEmployee.setPhoneNumber(phone);
        	            newEmployee.setPassword(password);
        	            newEmployee.setStatus(status);
        	            newEmployee.setDateOfBirth(dob);
        	            newEmployee.setGender(gender);
        	            
        	            // 4. Save the final, complete record
        	            empDB.updateEmployee(newEmployee);
        	            System.out.println("Successfully set all details for " + name);
        	        }
        	    } catch (Exception e) {
        	        System.err.println("An error occurred: " + e.getMessage());
        	    }
        	    break;
             case "2":
                 hrOfficer.updateEmployeeInfo(scanner);
                 break;
             case "3":
                 hrOfficer.processMonthlyPayroll();
                 break;
             case "4":
                 System.out.println("\n-- Promoting an Employee --");
                 System.out.print("Enter the Employee ID to promote: ");
                 String empIdPromote = scanner.nextLine();
                 System.out.print("Enter their new Job Title: ");
                 String newTitlePromote = scanner.nextLine();
                 System.out.print("Enter their salary/rate increase: ");
                 double increase = Double.parseDouble(scanner.nextLine());
                 hrOfficer.promoteEmployee(empIdPromote, newTitlePromote, increase);
                 break;
             case "0":
                 System.out.println("Returning to Main Menu...");
                 break;
             default:
                 System.err.println("Invalid choice. Please try again.");
                 break;
         }
     }
 }

 

 // --- Menu for Company Manager ---
 private static void showCompanyManagerMenu(Scanner scanner, LeaveDatabase leaveDB, EmployeeDatabase empDB, PayrollDatabase payrollDB) {
     CompanyManager manager = new CompanyManager(leaveDB, empDB, payrollDB);
     System.out.println("1. Manage Pending Leave Requests"); // New option
     System.out.println("2. Generate Salary Report");
     System.out.println("3. Generate Tax Summary Report");
     System.out.println("4. Generate Payroll Log Report");
     System.out.println("0. Back to Main Menu");
     System.out.print("Choose an action: ");
     String choice = scanner.nextLine();
     switch (choice) {
     case "1":
         manager.manageLeaves(scanner);
         break;
     case "2":
         manager.generateCompanyReport("Salary");
         break;
     case "3":
         manager.generateCompanyReport("Tax");
         break;
     case "4":
         manager.generateCompanyReport("PayrollLog");
         break;
     case "0":
         System.out.println("Returning to Main Menu...");
         break;
     default:
         System.err.println("Invalid choice. Please try again.");
         break;
 }
}

 // --- Menu for Employee Login ---
 private void showEmployeeLogin(Scanner scanner, EmployeeDatabase empDB, LeaveDatabase leaveDB) {
     System.out.print("\nEnter your Employee ID to log in: ");
     String empId = scanner.nextLine();
     Employee employee = empDB.findEmployeeById(empId);
     
     if (employee != null) {
         EmployeePortal portal = new EmployeePortal(employee, empDB, leaveDB);
         portal.showMenu(scanner);
     } else {
         System.err.println("Login failed. Employee ID not found.");
     }
 }
}