package advancedFinal;

import java.util.ArrayList;
import java.util.List;

public class Department {
 private String departmentId;
 private String departmentName;
 private String headOfDepartment;
 private String location;
 private List<Employee> staff; 

 public Department(String departmentId, String departmentName, String headOfDepartment, String location) {
     this.departmentId = departmentId;
     this.departmentName = departmentName;
     this.headOfDepartment = headOfDepartment;
     this.location = location; 
     this.staff = new ArrayList<>();
 }

 public void addEmployee(Employee employee) {
     this.staff.add(employee);
     employee.setDepartment(this); // Establish the bi-directional link
 }

 // --- Getters ---
 public String getDepartmentId() {
     return departmentId;
 }

 public String getDepartmentName() {
     return departmentName;
 }

 public String getHeadOfDepartment() {
	return headOfDepartment;
}

public void setHeadOfDepartment(String headOfDepartment) {
	this.headOfDepartment = headOfDepartment;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

@Override
 public String toString() {
     return "Department [ID=" + departmentId + ", Name=" + departmentName + "]";
 }
}