package advancedFinal;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaveDatabase {
 private List<LeaveRequest> leaveRequests;
 private final String fileName = "Dataset-20250902/leaves.txt";
 private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

 public LeaveDatabase() {
     this.leaveRequests = new ArrayList<>();
     System.out.println("LeaveDatabase initialized.");
     loadDataFromFile();
 }

private void loadDataFromFile() {
  try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      // Skip the header line
      reader.readLine(); 

      while ((line = reader.readLine()) != null) {
          if (line.trim().isEmpty()) { // Skip blank lines
              continue;
          }
          
          // Split by one or more spaces/tabs
          String[] parts = line.split("\\s+"); 
          
          if (parts.length >= 6) {
              try {
                  String leaveId = parts[0];
                  String employeeId = parts[1];
                  
                 
                  LeaveType leaveType = LeaveType.valueOf(parts[2].toUpperCase());
                  
                  Date startDate = dateFormat.parse(parts[3]);
                  Date endDate = dateFormat.parse(parts[4]);
                  
                  // Convert file text to our Enum values
                  LeaveStatus status = LeaveStatus.valueOf(parts[5].toUpperCase());

                  LeaveRequest req = new LeaveRequest(leaveId, employeeId, leaveType, startDate, endDate, status);
                  this.leaveRequests.add(req);

              } catch (ParseException | IllegalArgumentException e) {
                  // This will catch errors from bad dates or unknown enum types (like "Paternity" before we added it)
                  System.err.println("DATABASE: Skipping malformed leave record: " + line + " -> " + e.getMessage());
              }
          } else {
              System.err.println("DATABASE: Skipping malformed line in leaves.txt: " + line);
          }
      }
      System.out.println("DATABASE: Loaded " + leaveRequests.size() + " leave records from " + fileName);
  } catch (IOException e) {
      System.out.println("DATABASE: Error reading " + fileName + ". " + e.getMessage());
  }
}

 private void saveDataToFile() {
     try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
         for (LeaveRequest req : leaveRequests) {
             String line = req.getLeaveId() + ";" +
                           req.getEmployeeId() + ";" +
                           req.getLeaveType().name() + ";" +
                           dateFormat.format(req.getStartDate()) + ";" +
                           dateFormat.format(req.getEndDate()) + ";" +
                           req.getApprovalStatus().name();
             writer.write(line);
             writer.newLine();
         }
         System.out.println("DATABASE: Saved " + leaveRequests.size() + " leave records to " + fileName);
     } catch (IOException e) {
         System.err.println("DATABASE: Error writing to leave file: " + e.getMessage());
     }
 }
 public void addLeaveRequest(LeaveRequest request) {
     this.leaveRequests.add(request);
     System.out.println("DATABASE: Added new leave request for employee " + request.getEmployeeId());
     saveDataToFile();
 }
 
 // Method to update the status of a leave request (for CompanyManager)
 public void updateLeaveStatus(String leaveId, LeaveStatus newStatus) {
     for (LeaveRequest req : leaveRequests) {
         if (req.getLeaveId().equals(leaveId)) {
             req.setApprovalStatus(newStatus);
             System.out.println("DATABASE: Updated status for leave " + leaveId + " to " + newStatus);
             saveDataToFile();
             return;
         }
     }
     System.out.println("DATABASE: Could not find leave request with ID: " + leaveId);
 }
 public List<LeaveRequest> getPendingLeaveRequests() {
	    List<LeaveRequest> pendingRequests = new ArrayList<>();
	    for (LeaveRequest req : this.leaveRequests) {
	        if (req.getApprovalStatus() == LeaveStatus.PENDING) {
	            pendingRequests.add(req);
	        }
	    }
	    return pendingRequests;
	}


public LeaveRequest findLeaveRequestById(String leaveId) {
    // We loop through every request currently stored in the 'leaveRequests' list.
    for (LeaveRequest request : this.leaveRequests) {
        // For each request, we check if its ID matches the ID we are searching for.
        if (request.getLeaveId().equals(leaveId)) {
            // If we find a match, we return that LeaveRequest object right away.
            return request;
        }
    }
    // If the loop finishes without finding any match, it means the request
    // is not in our list. In that case, we return null to signal "not found".
    return null;
}}