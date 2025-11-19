package advancedFinal;

import java.util.Date;


public class LeaveRequest {
 private String leaveId;
 private String employeeId;
 private LeaveType leaveType;
 private Date startDate;
 private Date endDate;
 private LeaveStatus approvalStatus;


 public LeaveRequest(String leaveId, String employeeId, LeaveType leaveType, Date startDate, Date endDate) {
     this.leaveId = leaveId;
     this.employeeId = employeeId;
     this.leaveType = leaveType;
     this.startDate = startDate;
     this.endDate = endDate;
     this.approvalStatus = LeaveStatus.PENDING; // All new requests start as PENDING
 }

 public LeaveRequest(String leaveId, String employeeId, LeaveType leaveType, Date startDate, Date endDate, LeaveStatus status) {
     this.leaveId = leaveId;
     this.employeeId = employeeId;
     this.leaveType = leaveType;
     this.startDate = startDate;
     this.endDate = endDate;
     this.approvalStatus = status;
 }
 
 // --- Getters and Setters ---
 public String getLeaveId() {
     return leaveId;
 }

 public String getEmployeeId() {
     return employeeId;
 }

 public LeaveStatus getApprovalStatus() {
     return approvalStatus;
 }

 public void setApprovalStatus(LeaveStatus approvalStatus) {
     this.approvalStatus = approvalStatus;
 }
 
 public Date getStartDate() {
     return startDate;
 }

 public Date getEndDate() {
     return endDate;
 }
 
 public LeaveType getLeaveType() {
     return leaveType;
 }
}
