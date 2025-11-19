package advancedFinal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

//In src/test/java/your/package/name/CompanyManagerTest.java

import org.junit.jupiter.api.BeforeEach;
import java.util.Date;


class CompanyManagerTest {

 private LeaveDatabase leaveDatabase;
 private CompanyManager companyManager;

 @BeforeEach
 void setUp() {
     leaveDatabase = new LeaveDatabase();
     companyManager = new CompanyManager(leaveDatabase, null, null); 
     
     LeaveRequest pendingRequest = new LeaveRequest("L-E051-123", "E051", LeaveType.VACATION, new Date(), new Date());
     pendingRequest.setApprovalStatus(LeaveStatus.PENDING);
     leaveDatabase.addLeaveRequest(pendingRequest);
 }

 @Test
 void testApproveLeaveRequest_HappyPath() {
     // Test Case 2.1
     
     // Action
     companyManager.reviewLeaveRequest("L-E051-123", true); // true = approve

     // Verification
     LeaveRequest updatedRequest = leaveDatabase.findLeaveRequestById("L-E051-123");
     assertNotNull(updatedRequest);
     assertEquals(LeaveStatus.APPROVED, updatedRequest.getApprovalStatus(), "Leave status should be updated to APPROVED.");
 }

 @Test
 void testManageNonExistentLeaveRequest_FailureCase() {
     // Test Case 2.2
     
     // Action & Verification
     // We want to ensure that calling reviewLeaveRequest with a bad ID doesn't crash the program.
     // The method should handle the null case gracefully.
     assertDoesNotThrow(() -> {
         companyManager.reviewLeaveRequest("L-INVALID-999", true);
     }, "Method should not throw an exception for a non-existent ID.");
 }
}
