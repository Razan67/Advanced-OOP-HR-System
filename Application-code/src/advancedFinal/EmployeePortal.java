package advancedFinal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class EmployeePortal {

 private final Employee loggedInEmployee;
 private final EmployeeDatabase employeeDatabase;
 private final LeaveDatabase leaveDatabase;

 public EmployeePortal(Employee employee, EmployeeDatabase empDB, LeaveDatabase leaveDB) {
     this.loggedInEmployee = employee;
     this.employeeDatabase = empDB;
     this.leaveDatabase = leaveDB;
     System.out.println("\nWelcome to your portal, " + loggedInEmployee.getFullName() + "!");
 }

 public void showMenu(Scanner scanner) {
     String choice = "";
     while (!choice.equals("0")) {
         System.out.println("\n--- Employee Portal Menu ---");
         System.out.println("1. View My Personal Data");
         System.out.println("2. Update My Personal Data");
         System.out.println("3. Request Leave");
         System.out.println("0. Log Out (Back to Main Menu)");
         System.out.print("Choose an option: ");
         choice = scanner.nextLine();

         switch (choice) {
             case "1":
                 viewPersonalData();
                 break;
             case "2":
                 updatePersonalData(scanner);
                 break;
             case "3":
                 requestLeave(scanner);
                 break;
             case "0":
                 System.out.println("Logging out...");
                 break;
             default:
                 System.err.println("Invalid choice. Please try again.");
                 break;
         }
     }
 }

 // This method shows the employee's data.
 private void viewPersonalData() {
	    System.out.println("\n--- Your Personal Data ---");
	 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	    System.out.println("Employee ID:   " + loggedInEmployee.getEmployeeId());
	    System.out.println("Full Name:     " + loggedInEmployee.getFullName());
	    System.out.println("Job Title:     " + loggedInEmployee.getJobTitle());
	    System.out.println("Email:         " + (loggedInEmployee.getEmail() != null ? loggedInEmployee.getEmail() : "N/A"));
	    System.out.println("Phone Number:  " + (loggedInEmployee.getPhoneNumber() != null ? loggedInEmployee.getPhoneNumber() : "N/A"));
	    System.out.println("Status:        " + (loggedInEmployee.getStatus() != null ? loggedInEmployee.getStatus() : "N/A"));
	    
	    if (loggedInEmployee.getDepartment() != null) {
	        System.out.println("Department:    " + loggedInEmployee.getDepartment().getDepartmentName());
	    } else {
	        System.out.println("Department:    Not Assigned");
	    }
	    
	    System.out.println("Gender:        " + (loggedInEmployee.getGender() != null ? loggedInEmployee.getGender() : "N/A"));
	    
	    System.out.println("Date of Birth: " + (loggedInEmployee.getDateOfBirth() != null ? sdf.format(loggedInEmployee.getDateOfBirth()) : "N/A"));
	    System.out.println("Hire Date:     " + (loggedInEmployee.getHireDate() != null ? sdf.format(loggedInEmployee.getHireDate()) : "N/A"));

	    System.out.println("--------------------------");
	}

 // This method handles updating info and now takes the scanner as an argument.
 private void updatePersonalData(Scanner scanner) {
	    System.out.println("\n--- Update Your Personal Information ---");
	    System.out.println("What would you like to update?");
	    System.out.println("1. My Phone Number");
	    System.out.println("2. My Password");
	    System.out.println("3. My Email Address"); 
	    System.out.println("0. Go Back");
	    System.out.print("Choose an option: ");
	    
	    String choice = scanner.nextLine();

	    switch (choice) {
	        case "1":
	            System.out.print("Enter your new phone number: ");
	            String newPhoneNumber = scanner.nextLine();
	            loggedInEmployee.setPhoneNumber(newPhoneNumber);
	            System.out.println("Phone number updated successfully!");
	            break;

	        case "2":
	            System.out.print("Enter your new password: ");
	            String newPassword = scanner.nextLine();
	            loggedInEmployee.setPassword(newPassword);
	            System.out.println("Password updated successfully!");
	            break;

	        case "3":
	            System.out.print("Enter your new email address: ");
	            String newEmail = scanner.nextLine();
	            loggedInEmployee.setEmail(newEmail); // This requires an setEmail() method in Employee.java
	            System.out.println("Email address updated successfully!");
	            break;

	        case "0":
	            return; // Exit the method immediately

	        default:
	            // Handle invalid choices
	            System.err.println("Invalid choice. Returning to the main portal menu.");
	            return; // Exit the method
	    }


	    employeeDatabase.updateEmployee(loggedInEmployee);
	    System.out.println("Your information has been saved to the system.");
 }
 // This method handles requesting leave and now takes the scanner as an argument.
 private void requestLeave(Scanner scanner) {
     System.out.println("\n--- Submit a New Leave Request ---");
     try {
         System.out.print("Enter Leave Type (e.g., SICK, VACATION, UNPAID, MATERNITY, PATERNITY): ");
         String typeStr = scanner.nextLine().toUpperCase();
         
         System.out.print("Enter Start Date (yyyy-MM-dd): ");
         String startStr = scanner.nextLine();
         
         System.out.print("Enter End Date (yyyy-MM-dd): ");
         String endStr = scanner.nextLine();

         String leaveId = "L-" + loggedInEmployee.getEmployeeId() + "-" + System.currentTimeMillis() % 1000;
         LeaveType leaveType = LeaveType.valueOf(typeStr);
         Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startStr);
         Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endStr);

         LeaveRequest newRequest = new LeaveRequest(leaveId, loggedInEmployee.getEmployeeId(), leaveType, startDate, endDate);
         leaveDatabase.addLeaveRequest(newRequest);
         System.out.println("Your leave request has been submitted successfully!");

     } catch (Exception e) {
         System.err.println("Failed to create leave request. Please check your input format.");
     }
 }
}