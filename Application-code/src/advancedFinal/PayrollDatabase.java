package advancedFinal;

//PayrollDatabase.java

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDatabase {
 private List<Payroll> payrolls;
 private final String fileName = "Dataset-20250902/payroll.txt";

 public PayrollDatabase() {
     this.payrolls = new ArrayList<>();
     System.out.println("PayrollDatabase initialized.");
     loadDataFromFile();
 }

private void loadDataFromFile() {
  try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      reader.readLine(); // Skip header

      while ((line = reader.readLine()) != null) {
          if (line.trim().isEmpty()) continue;
          
          String[] parts = line.split("\t"); // Split by TAB

          if (parts.length >= 4) {
              try {
                  String employeeId = parts[1];
                  String month = parts[2];
                  double baseSalary = Double.parseDouble(parts[3]);
                  Payroll p = new Payroll(employeeId, month, baseSalary);
                  this.payrolls.add(p);
              } catch (NumberFormatException e) {
                  System.err.println("DATABASE: Could not parse number in payroll.txt: " + line);
              }
          } else {
              System.err.println("DATABASE: Skipping malformed line in payroll.txt: " + line);
          }
      }
      System.out.println("DATABASE: Loaded " + payrolls.size() + " payroll records from " + fileName);
  } catch (IOException e) {
      System.out.println("DATABASE: Error reading " + fileName + ". " + e.getMessage());
  }
}




 private void saveDataToFile() {
     try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) { // Overwrite
         for (Payroll p : payrolls) {
             String line = p.getEmployeeId() + ";" + p.getMonth() + ";" + p.getMonthlyPayment();
             writer.write(line);
             writer.newLine();
         }
         System.out.println("DATABASE: Saved " + payrolls.size() + " payroll records to " + fileName);
     } catch (IOException e) {
         System.err.println("DATABASE: Error writing to payroll file: " + e.getMessage());
     }
 }

 public void addToPayrollArrayList(Payroll payroll) {
     this.payrolls.add(payroll);
     System.out.println("DATABASE: Added payroll record for employee " + payroll.getEmployeeId() + " to memory.");
     saveDataToFile(); // Save changes to the file
 }
 
 public List<Payroll> getAllPayrolls() {
     // Return a copy for safety.
     return new ArrayList<>(this.payrolls);
 }
}
