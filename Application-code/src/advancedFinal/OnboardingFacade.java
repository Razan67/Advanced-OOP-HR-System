package advancedFinal;

//Its purpose is to make the process easy for the client (HROfficer).
public class OnboardingFacade {

 // The facade needs references to all the subsystem parts it coordinates.
 private EmployeeFactory employeeFactory;
 private EmployeeDatabase employeeDatabase;
 private DepartmentDatabase departmentDatabase;
 private PayrollDatabase payrollDatabase;

 // The constructor receives all the necessary subsystem components.
 // This is how we connect the facade to the parts it needs to manage.
 public OnboardingFacade(EmployeeFactory factory, EmployeeDatabase empDB, DepartmentDatabase deptDB, PayrollDatabase payrollDB) {
     this.employeeFactory = factory;
     this.employeeDatabase = empDB;
     this.departmentDatabase = deptDB;
     this.payrollDatabase = payrollDB;
     System.out.println("OnboardingFacade created and ready.");
 }

 public boolean onboardNewEmployee(String employeeType, String... employeeData) {
     if (employeeData.length < 4) { 
         System.err.println("FACADE: Not enough data provided to onboard employee. Aborting.");
         return false;
     }
     
     String employeeId = employeeData[0];
     String fullName = employeeData[1];
     String departmentId = employeeData[employeeData.length - 1]; 
     System.out.println("\n--- FACADE: Starting onboarding process for " + fullName + " ---");

     // Step 1: Use the EmployeeFactory to create the new employee object.
     System.out.println("FACADE: Calling EmployeeFactory to create employee object...");
     Employee newEmployee = employeeFactory.createEmployee(employeeType, employeeData);
     if (newEmployee == null) {
         System.err.println("FACADE: Failed to create employee from factory. Aborting process.");
         return false;
     }
     System.out.println("FACADE: Employee object created successfully: " + newEmployee.getFullName());

     // Step 2: Use the DepartmentDatabase to find the correct department object.
     System.out.println("FACADE: Calling DepartmentDatabase to find department " + departmentId + "...");
     Department department = departmentDatabase.findDepartmentById(departmentId);
     if (department == null) {
         System.err.println("FACADE: Department not found. Aborting process.");
         return false;
     }
     System.out.println("FACADE: Department found: " + department.getDepartmentName());

     // Step 3: Link the employee to their department.
     department.addEmployee(newEmployee); // This also sets the department on the employee object.
     System.out.println("FACADE: Assigned " + fullName + " to the " + department.getDepartmentName() + " department.");

     // Step 4: Use the EmployeeDatabase to save the new employee record.
     // This will trigger writing to the correct employees_....txt file.
     System.out.println("FACADE: Calling EmployeeDatabase to save the new employee...");
     employeeDatabase.addEmployeeToArrayList(newEmployee);
     System.out.println("FACADE: Employee saved to database.");

     // Step 5: Use the PayrollDatabase to create a new payroll record for the employee.
     // This will also trigger writing to the payroll.txt file.
     System.out.println("FACADE: Calling PayrollDatabase to create payroll record...");
     String currentMonth = "SEP-2025"; // Example month
     Payroll newPayroll = new Payroll(employeeId, currentMonth, newEmployee.calculateSalary());
     payrollDatabase.addToPayrollArrayList(newPayroll);
     System.out.println("FACADE: Payroll record created.");

     System.out.println("--- FACADE: Onboarding process completed successfully for " + fullName + "! ---");
     return true;
 }
}