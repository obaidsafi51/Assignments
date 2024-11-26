import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollSystem {

    // Define a regex pattern for the employee data format: Name,HoursWorked,HourlyWage
    private static final String EMPLOYEE_DATA_PATTERN = "^[a-zA-Z ]+,[0-9]+,[0-9]+(\\.[0-9]+)?$";

    // Load employees from file with strict validation
    public List<Employee> loadEmployeesFromFile(String fileName) throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                // Check if the line matches the required pattern
                if (!line.matches(EMPLOYEE_DATA_PATTERN)) {
                    throw new IOException("Invalid data format on line " + lineNumber + ": " + line);
                }

                String[] parts = line.split(",");
                String name = parts[0].trim();
                int hoursWorked;
                double hourlyWage;

                try {
                    hoursWorked = Integer.parseInt(parts[1].trim());
                    hourlyWage = Double.parseDouble(parts[2].trim());
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid number format on line " + lineNumber + ": " + line);
                }

                employees.add(new Employee(name, hoursWorked, hourlyWage));
                lineNumber++;
            }
        }
        return employees;
    }


    // Calculate and save payroll to output file
    public void calculateAndSavePayroll(String outputFile, List<Employee> employees) throws IOException {
        if (employees.isEmpty()) {
            throw new IOException("No valid employee data to process.");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            for (Employee employee : employees) {
                writer.printf("%s: $%.2f%n", employee.getName(), employee.calculateWeeklyPay());
            }
        } catch (IOException e) {
            throw new IOException("Error while writing payroll to file: " + e.getMessage());
        }
    }

}
