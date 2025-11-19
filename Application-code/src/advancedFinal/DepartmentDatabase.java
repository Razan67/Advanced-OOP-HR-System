package advancedFinal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDatabase {
 private List<Department> departments;
 private final String fileName = "Dataset-20250902/departments.txt";

 public DepartmentDatabase() {
     this.departments = new ArrayList<>();
     System.out.println("DepartmentDatabase initialized.");
     loadDataFromFile();
 }

private void loadDataFromFile() {
  try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
          if (line.trim().isEmpty()) continue;

          // This regex splits by semicolon OR one-or-more spaces/tabs
          String[] parts = line.split("[;\\s]+"); 

          // We need at least 4 parts: ID, Name, HeadID, Location
          if (parts.length >= 4) {
              if (parts.length >= 6) {
                   Department dept = new Department(parts[0], parts[1], parts[2], parts[5]);
                   this.departments.add(dept);
              } else {
                   Department dept = new Department(parts[0], parts[1], parts[2], "N/A");
                   this.departments.add(dept);
              }
          } else {
              System.err.println("DATABASE: Skipping malformed line in departments.txt: " + line);
          }
      }
      System.out.println("DATABASE: Loaded " + departments.size() + " departments from " + fileName);
  } catch (IOException e) {
      System.out.println("DATABASE: Error reading " + fileName + ". " + e.getMessage());
  }
}

 public Department findDepartmentById(String departmentId) {
     for (Department dept : departments) {
         if (dept.getDepartmentId().equals(departmentId)) {
             return dept;
         }
     }
     return null;
 }
}