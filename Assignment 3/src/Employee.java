public class Employee {
    private String name;
    private double hoursWorked;
    private double hourlyWage;

    public Employee(String name, double hoursWorked, double hourlyWage) {
        this.name = name;
        this.hoursWorked = hoursWorked;
        this.hourlyWage = hourlyWage;
    }

    public double calculateWeeklyPay() {
        return hoursWorked * hourlyWage;
    }

    // Getters
    public String getName() { return name; }
}
