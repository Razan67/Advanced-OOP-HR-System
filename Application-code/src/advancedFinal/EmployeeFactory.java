package advancedFinal;

public class EmployeeFactory {

    public Employee createEmployee(String employeeType, String... data) {
        
        // First, perform a basic check to make sure we have valid input.
        if (employeeType == null || data == null) {
            System.err.println("FACTORY: Cannot create employee with null type or data.");
            return null;
        }

        // We use a try-catch block to handle potential errors, like bad number formats,
        // which prevents the whole application from crashing.
        try {
            // The first three data elements are always the same for the constructor: ID, Name, and Title.
            if (data.length < 3) {
                System.err.println("FACTORY: Not enough data provided (needs at least ID, Name, Title).");
                return null;
            }
            String id = data[0];
            String name = data[1];
            String title = data[2];

            // --- Logic for Full-Time Employees ---
            if (employeeType.equalsIgnoreCase("FullTime")) {
                if (data.length < 4) {
                    System.err.println("FACTORY: Not enough data for FullTime employee (is salary missing?).");
                    return null;
                }
                double salary = Double.parseDouble(data[3]);
                return new FullTimeEmployee(id, name, title, salary);
            
            // --- Logic for Part-Time Employees ---
            } else if (employeeType.equalsIgnoreCase("PartTime")) {
                // A PartTime employee also needs 4 pieces of data.
                if (data.length < 4) {
                    System.err.println("FACTORY: Not enough data for PartTime employee (is rate missing?).");
                    return null;
                }
                double rate = Double.parseDouble(data[3]);
                return new PartTimeEmployee(id, name, title, rate);

            // --- Logic for Contract Employees ---
            } else if (employeeType.equalsIgnoreCase("Contract")) {
                // A Contract employee needs 5 pieces of data.
                if (data.length < 5) {
                    System.err.println("FACTORY: Not enough data for Contract employee (is payment or duration missing?).");
                    return null;
                }
                double payment = Double.parseDouble(data[3]);
                int duration = Integer.parseInt(data[4]);
                return new ContractEmployee(id, name, title, payment, duration);
            }

        } catch (NumberFormatException e) {
            // This 'catch' block runs if Double.parseDouble or Integer.parseInt fails.
            System.err.println("FACTORY: Error: A number in the data was not in the correct format.");
            return null;
        }
        
        // If the employeeType was not "FullTime", "PartTime", or "Contract", we reach this point.
        System.err.println("FACTORY: Error: Unknown employee type requested: " + employeeType);
        return null;
    }
}
