package advancedFinal;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class Employee {
 
 private String employeeId;
 private String fullName;
 private Date dateOfBirth;
 private Gender gender;
 private String phoneNumber;
 private String email;
 private String jobTitle;
 private Date hireDate;
 private Date lastPromotionDate;
 private String password;
 private String status; 
 private Department department; 

 public Employee(String employeeId, String fullName, String jobTitle) {
     this.employeeId = employeeId;
     this.fullName = fullName;
     this.jobTitle = jobTitle;
     this.hireDate = new Date(); // Default hire date
     this.status = "Active";     // Default status
 }

 public abstract double calculateSalary();
 public void setDateOfBirth(String dateOfBirthStr) {
     try {
         this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
     } catch (ParseException e) {
         System.err.println("Could not parse date: " + dateOfBirthStr);
     }
 }
 
 // --- Getters and Setters for all fields ---
 public String getEmployeeId() {
     return employeeId;
 }
 
 public void setGender(String genderStr) {
	    if (genderStr == null) {
	        this.gender = Gender.OTHER;
	        return;
	    }
	    
	    String cleanGender = genderStr.trim().toUpperCase();
	    
	    if (cleanGender.equals("MALE")) {
	        this.gender = Gender.MALE;
	    } else if (cleanGender.equals("FEMALE")) {
	        this.gender = Gender.FEMALE;
	    } else {
	        this.gender = Gender.OTHER;
	    }
	}

 public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public void setEmployeeId(String employeeId) {
	this.employeeId = employeeId;
}

public String getJobTitle() {
	return jobTitle;
}

public String getFullName() {
     return fullName;
 }

 public Department getDepartment() {
     return department;
 }
 

 public String getPhoneNumber() {
	return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

public Date getHireDate() {
	return hireDate;
}

public void setHireDate(Date hireDate) {
	this.hireDate = hireDate;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Date getLastPromotionDate() {
	return lastPromotionDate;
}

public Date getDateOfBirth() {
	return dateOfBirth;
}

public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
}

public Gender getGender() {
	return gender;
}

public void setGender(Gender gender) {
	this.gender = gender;
}

public void setDepartment(Department department) {
     this.department = department;
 }

 public void setJobTitle(String jobTitle) {
     this.jobTitle = jobTitle;
 }

 public void setLastPromotionDate(Date lastPromotionDate) {
     this.lastPromotionDate = lastPromotionDate;
 }

 @Override
 public String toString() {
     return "Employee [ID=" + employeeId + ", Name=" + fullName + ", Title=" + jobTitle + "]";
 }
}