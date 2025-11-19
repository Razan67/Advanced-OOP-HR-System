package advancedFinal;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabase {
 private List<Employee> employees;
 private final String folderPath = "Dataset-20250902/";

 // Define all the files with the path
 private final String fullTimeFile = folderPath + "employees_full_time.txt";
 private final String partTimeFile = folderPath + "employees_part_time.txt";
 private final String contractFile = folderPath + "employees_contract.txt";

 public EmployeeDatabase() {
     this.employees = new ArrayList<>();
     System.out.println("EmployeeDatabase initialized.");
     loadAllDataFromFiles();
 }

 private void loadAllDataFromFiles() {
     EmployeeFactory loaderFactory = new EmployeeFactory();
     loadFromFile(fullTimeFile, "FullTime", loaderFactory);
     loadFromFile(partTimeFile, "PartTime", loaderFactory);
     loadFromFile(contractFile, "Contract", loaderFactory);
     System.out.println("DATABASE: Total employees loaded into memory: " + employees.size());
 }

//This helper method loads data from a specific employee file.
 private void loadFromFile(String fileName, String employeeType, EmployeeFactory factory) {
	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	        String line;
	        reader.readLine(); // Skip header

	        while ((line = reader.readLine()) != null) {
	            if (line.trim().isEmpty()) continue;
	            
	            String[] parts = line.split("\t");
	            
	            try {
	                // --- 1. Create the basic employee object using the factory ---
	                String[] factoryData = null;
	                if (employeeType.equalsIgnoreCase("FullTime") && parts.length >= 14) {
	                    factoryData = new String[]{ parts[0], parts[1], parts[7], parts[13] };
	                } else if (employeeType.equalsIgnoreCase("PartTime") && parts.length >= 15) {
	                    factoryData = new String[]{ parts[0], parts[1], parts[7], parts[14] };
	                } else if (employeeType.equalsIgnoreCase("Contract") && parts.length >= 15) {
	                    factoryData = new String[]{ parts[0], parts[1], parts[7], parts[14], parts[13] };
	                }

	                if (factoryData != null) {
	                    Employee emp = factory.createEmployee(employeeType, factoryData);
	                    if (emp != null) {
	                        // --- 2. Set all the other details using setters ---
	                        if (parts.length > 12) { // Check we have enough columns
	                            emp.setDateOfBirth(parts[2]);
//	                            emp.setGender(parts[3]);
	                            emp.setEmail(parts[4]);
	                            emp.setPhoneNumber(parts[5]);
	                            emp.setStatus(parts[10]);
	                            emp.setPassword(parts[12]);
	                        }
	                        if (parts.length > 12) { 
	                            emp.setDateOfBirth(parts[2]);
	                            emp.setGender(parts[3]); 
	                        }
	                        this.employees.add(emp);
	                    }
	                } else {
	                    System.err.println("DATABASE: Skipping line due to wrong column count in " + fileName + ": " + line);
	                }

	            } catch (Exception e) {
	                System.err.println("DATABASE: Critical error processing line: " + line + " -> " + e.getMessage());
	            }
	        }
	        System.out.println("DATABASE: Finished loading from " + fileName);
	    } catch (IOException e) {
	        System.out.println("DATABASE: Could not find or read file: " + fileName);
	    }
	}
 private void saveDataToFile(String fileName, String employeeType) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) { // false = overwrite the file
	        
	        if (employeeType.equalsIgnoreCase("FullTime")) {
	            writer.write("employee_id\tfull_name\tdate_of_birth\tgender\temail\tphone_number\thire_date\tjob_title\temployment_type\tdepartment_id\tstatus\tlast_promotion_date\tPassword\tsalary_base");
	        } else if (employeeType.equalsIgnoreCase("PartTime")) {
	            writer.write("employee_id\tfull_name\tdate_of_birth\tgender\temail\tphone_number\thire_date\tjob_title\temployment_type\tdepartment_id\tstatus\tlast_promotion_date\tPassword\tworking_hours_per_month\trate_per_hour");
	        } else if (employeeType.equalsIgnoreCase("Contract")) {
	            writer.write("employee_id\tfull_name\tdate_of_birth\tgender\temail\tphone_number\thire_date\tjob_title\tEmployee_type\tdepartment_id\tstatus\tlast_promotion_date\tpassword\tduration\toverall_payment");
	        }
	        writer.newLine();

	        // Now, loop through ALL employees in memory
	        for (Employee emp : this.employees) {
	            // Check if the current employee matches the type we are saving
	            if ((employeeType.equalsIgnoreCase("FullTime") && emp instanceof FullTimeEmployee) ||
	                (employeeType.equalsIgnoreCase("PartTime") && emp instanceof PartTimeEmployee) ||
	                (employeeType.equalsIgnoreCase("Contract") && emp instanceof ContractEmployee)) {

	                // Use a helper to format the date nicely, or use default
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                String dob = emp.getDateOfBirth() != null ? sdf.format(emp.getDateOfBirth()) : "N/A";
	                String hireDate = emp.getHireDate() != null ? sdf.format(emp.getHireDate()) : "N/A";
	                String promoDate = emp.getLastPromotionDate() != null ? sdf.format(emp.getLastPromotionDate()) : "N/A";

	                // Build the basic, common part of the line
	                String commonData = String.join("\t",
	                    emp.getEmployeeId(),
	                    emp.getFullName(),
	                    dob,
	                    emp.getGender() != null ? emp.getGender().toString() : "N/A",
	                    emp.getEmail() != null ? emp.getEmail() : "N/A",
	                    emp.getPhoneNumber() != null ? emp.getPhoneNumber() : "N/A",
	                    hireDate,
	                    emp.getJobTitle(),
	                    employeeType, 
	                    emp.getDepartment() != null ? emp.getDepartment().getDepartmentId() : "N/A",
	                    emp.getStatus() != null ? emp.getStatus() : "N/A",
	                    promoDate,
	                    emp.getPassword() != null ? emp.getPassword() : "N/A"
	                );

	                String finalLine = "";
	                if (emp instanceof FullTimeEmployee) {
	                    finalLine = commonData + "\t" + ((FullTimeEmployee) emp).getSalary();
	                } else if (emp instanceof PartTimeEmployee) {
	                    finalLine = commonData + "\t" + "0" + "\t" + ((PartTimeEmployee) emp).getHourlyRate(); // Assuming 0 for working_hours
	                } else if (emp instanceof ContractEmployee) {
	                    finalLine = commonData + "\t" + ((ContractEmployee) emp).getDurationInMonths() + "\t" + ((ContractEmployee) emp).getOverallPayment();
	                }
	                
	                writer.write(finalLine);
	                writer.newLine();
	            }
	        }
	        System.out.println("DATABASE: Successfully saved data to " + fileName);
	    } catch (IOException e) {
	        System.err.println("DATABASE: Error writing to file: " + e.getMessage());
	    }
	}
 public void addEmployeeToArrayList(Employee employee) {
	    this.employees.add(employee);
	    saveAllFiles();
	}

	public void updateEmployee(Employee updatedEmployee) {
	    saveAllFiles(); 
	}

	// A new helper method to save everything
	private void saveAllFiles() {
	    saveDataToFile("Dataset-20250902/employees_full_time.txt", "FullTime");
	    saveDataToFile("Dataset-20250902/employees_part_time.txt", "PartTime");
	    saveDataToFile("Dataset-20250902/employees_contract.txt", "Contract");
	}
 public Employee findEmployeeById(String employeeId) {
     for (Employee emp : employees) {
         if (emp.getEmployeeId().equals(employeeId)) {
             return emp;
         }
     }
     return null;
 }
 public List<Employee> getAllEmployees() {
     return new ArrayList<>(this.employees);
 }
}