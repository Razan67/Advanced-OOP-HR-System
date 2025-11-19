package advancedFinal;

import java.util.List;
import java.util.Scanner;


public class HROfficer {

 
 private final OnboardingFacade onboardingFacade;
 private final EmployeeDatabase employeeDatabase;
 private final PayrollDatabase payrollDatabase; 
 private final DepartmentDatabase departmentDatabase; 
 
 public HROfficer(OnboardingFacade facade, EmployeeDatabase empDB, PayrollDatabase payrollDB, DepartmentDatabase deptDB) {
     this.onboardingFacade = facade;
     this.employeeDatabase = empDB;
     this.payrollDatabase = payrollDB; 
     this.departmentDatabase = deptDB;
     System.out.println("HROfficer is ready to work.");
 }

 // --- Task 1: Onboard a New Employee --- here is where i used the facade
 public void createNewEmployee(String employeeType, String... employeeDataWithDept) {
     System.out.println("\n>>> HROFFICER: Initiating request to onboard a new " + employeeType + " employee.");
     boolean success = onboardingFacade.onboardNewEmployee(employeeType, employeeDataWithDept);
     if (success) {
         System.out.println(">>> HROFFICER: Facade confirmed successful onboarding.");
     } else {
         System.err.println(">>> HROFFICER: Facade reported a failure during onboarding.");
     }
 }

 // --- Task 2: Update an Employee's Info ---
 public void updateEmployeeInfo(Scanner scanner) {
	    System.out.println("\n-- Updating Employee Information --");
	    System.out.print("Enter the Employee ID to update: ");
	    String empId = scanner.nextLine();

	    Employee employee = employeeDatabase.findEmployeeById(empId);

	    if (employee == null) {
	        System.err.println(">>> HROFFICER: Employee with ID " + empId + " not found.");
	        return;
	    }

	    System.out.println("\nWhat would you like to update for " + employee.getFullName() + "?");
	    System.out.println("1. Job Title");
	    System.out.println("2. Phone Number");
	    System.out.println("3. Status (e.g., Active, On Leave, Terminated)");
	    System.out.println("4. Salary / Hourly Rate");
	    System.out.println("5. Department Assignment");
	    System.out.println("0. Go Back");
	    System.out.print("Choose an option: ");
	    String choice = scanner.nextLine();

	    switch (choice) {
	        case "1":
	            System.out.print("Enter new Job Title: ");
	            String newTitle = scanner.nextLine();
	            employee.setJobTitle(newTitle);
	            break;
	        case "2":
	            System.out.print("Enter new Phone Number: ");
	            String newPhone = scanner.nextLine();
	            employee.setPhoneNumber(newPhone);
	            break;
	        case "3":
	            System.out.print("Enter new Status: ");
	            String newStatus = scanner.nextLine();
	            employee.setStatus(newStatus);

	            break;
	        case "4":
	            // Logic to update pay, which depends on the employee type
	            if (employee instanceof FullTimeEmployee) {
	                System.out.print("Enter new Monthly Salary: ");
	                double newSalary = Double.parseDouble(scanner.nextLine());
	                ((FullTimeEmployee) employee).setSalary(newSalary);
	            } else if (employee instanceof PartTimeEmployee) {
	                System.out.print("Enter new Hourly Rate: ");
	                double newRate = Double.parseDouble(scanner.nextLine());
	                ((PartTimeEmployee) employee).setHourlyRate(newRate);
	            } else {
	                System.out.println("Salary for Contract employees cannot be updated this way.");
	            }
	            break;
	        case "5":
	            // Logic to change department
	            System.out.print("Enter new Department ID: ");
	            String newDeptId = scanner.nextLine();
	             Department newDept = departmentDatabase.findDepartmentById(newDeptId);
	             if (newDept != null) {
	                 employee.setDepartment(newDept);
	             } else {
	                 System.err.println("Department ID not found.");
	             }
	            System.out.println("Department change functionality not fully implemented yet.");
	            break;
	        case "0":
	            return; // Exit without saving
	        default:
	            System.err.println("Invalid update choice.");
	            return; // Exit without saving
	    }

	    // After making the change, save the updated employee object back to the database.
	    employeeDatabase.updateEmployee(employee);
	    System.out.println(">>> HROFFICER: Information for " + employee.getFullName() + " has been updated.");
	}
	
	    public void processMonthlyPayroll() {
	    System.out.println("\n>>> HROFFICER: Initiating monthly payroll processing...");
	    List<Employee> allEmployees = employeeDatabase.getAllEmployees();
	    String currentMonth = "AUG-2025"; 

	    for (Employee emp : allEmployees) {
	        double salary = emp.calculateSalary();
	        Payroll payrollRecord = new Payroll(emp.getEmployeeId(), currentMonth, salary);
	        payrollDatabase.addToPayrollArrayList(payrollRecord);
	    }
	    System.out.println(">>> HROFFICER: Monthly payroll processed for " + allEmployees.size() + " employees.");
	}
	    
 public void promoteEmployee(String employeeId, String newTitle, double salaryIncrease) {
	    System.out.println("\n>>> HROFFICER: Initiating promotion for employee " + employeeId);
	    Employee employee = employeeDatabase.findEmployeeById(employeeId);

	    if (employee != null) {
	        employee.setJobTitle(newTitle);
	        
	        if (employee instanceof FullTimeEmployee) {
	            double currentSalary = ((FullTimeEmployee) employee).getSalary();
	            ((FullTimeEmployee) employee).setSalary(currentSalary + salaryIncrease);
	        } else if (employee instanceof PartTimeEmployee) {
	             double currentRate = ((PartTimeEmployee) employee).getHourlyRate();
	             ((PartTimeEmployee) employee).setHourlyRate(currentRate + salaryIncrease);
	        }
	        
	        employeeDatabase.updateEmployee(employee);
	        System.out.println(">>> HROFFICER: Promotion processed successfully.");
	    } else {
	        System.err.println(">>> HROFFICER: Could not find employee with ID " + employeeId + ". Promotion failed.");
	    }
	}
}