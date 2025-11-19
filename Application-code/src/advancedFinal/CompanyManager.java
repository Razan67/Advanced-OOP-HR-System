package advancedFinal;

import java.util.List;
import java.util.Scanner;

public class CompanyManager {

    // The manager has the tools for its jobs: managing leaves and generating reports.
    private final LeaveDatabase leaveDatabase;
    private final ReportContext reportContext;
    
    // It needs access to data sources for report generation.
    private final EmployeeDatabase employeeDatabase;
    private final PayrollDatabase payrollDatabase;

    public CompanyManager(LeaveDatabase leaveDB, EmployeeDatabase empDB, PayrollDatabase payrollDB) {
        this.leaveDatabase = leaveDB;
        this.employeeDatabase = empDB;
        this.payrollDatabase = payrollDB;
        
        // The manager creates its own ReportContext tool.
        this.reportContext = new ReportContext();
        System.out.println("CompanyManager is ready to work.");
    }

    // --- Task 1: Approve or Decline a Leave Request ---
    public void reviewLeaveRequest(String leaveId, boolean isApproved) {
        System.out.println("\n>>> MANAGER: Reviewing leave request " + leaveId);
        LeaveStatus newStatus = isApproved ? LeaveStatus.APPROVED : LeaveStatus.REJECTED;
        leaveDatabase.updateLeaveStatus(leaveId, newStatus);
        System.out.println(">>> MANAGER: Request " + leaveId + " has been " + newStatus);
    }

    // --- Task 2: Generate a Report using the Strategy Pattern ---
    public void generateCompanyReport(String reportType) {
        System.out.println("\n>>> MANAGER: Generating a '" + reportType + "' report...");

        // 1. The manager decides which strategy to use based on the input.
        if (reportType.equalsIgnoreCase("Salary")) {
            reportContext.setReportStrategy(new SalaryDeptReport());
        } else if (reportType.equalsIgnoreCase("Tax")) {
            reportContext.setReportStrategy(new TaxSummaryReport());
        } else if (reportType.equalsIgnoreCase("PayrollLog")) {
            reportContext.setReportStrategy(new PayrollLogReport());
        } else {
            System.err.println(">>> MANAGER: Unknown report type '" + reportType + "'.");
            return;
        }

        // 2. The manager gets the latest data from the databases.
        List<Employee> allEmployees = employeeDatabase.getAllEmployees();
        List<Payroll> allPayrolls = payrollDatabase.getAllPayrolls();

        // 3. The manager tells the context to execute the report.
        String report = reportContext.executeReportGeneration(allEmployees, allPayrolls);

        // 4. The manager "prints" or displays the generated report.
        System.out.println("--- REPORT START ---");
        System.out.println(report);
        System.out.println("--- REPORT END ---");
    }

 public void manageLeaves(Scanner scanner) {
     System.out.println("\n-- Managing Pending Leave Requests --");
     
     // 1. Get the list of pending requests from the database.
     List<LeaveRequest> pendingRequests = leaveDatabase.getPendingLeaveRequests();
     
     if (pendingRequests.isEmpty()) {
         System.out.println("There are no pending leave requests to review.");
         return; // Exit the method if there's nothing to do.
     }
     
     // 2. Display all pending requests to the manager.
     System.out.println("The following leave requests are pending approval:");
     for (LeaveRequest req : pendingRequests) {
         System.out.println("  - Leave ID: " + req.getLeaveId() + " | Employee: " + req.getEmployeeId() + " | Type: " + req.getLeaveType());
     }
     
     // 3. Ask the manager which request to process.
     System.out.print("\nEnter the Leave ID to review: ");
     String leaveId = scanner.nextLine();
     
     System.out.print("Approve or Decline this request? (Enter 'approve' or 'decline'): ");
     String decision = scanner.nextLine();
     
     // 4. Call the existing reviewLeaveRequest method to finalize the action.
     if (decision.equalsIgnoreCase("approve") || decision.equalsIgnoreCase("decline")) {
         boolean isApproved = decision.equalsIgnoreCase("approve");
         reviewLeaveRequest(leaveId, isApproved);
     } else {
         System.err.println("Invalid decision. Please enter 'approve' or 'decline'.");
     }
 }

}