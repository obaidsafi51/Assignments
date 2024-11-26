
public class Employee {
    private String name;
    private double hoursWorked;
    private double hourlyWage;

    public Employee(String name, double hoursWorked, double hourlyWage) {
        this.name = name;
        this.hoursWorked = hoursWorked;
        this.hourlyWage = hourlyWage;
    }

    public String getName() {
        return name;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public double calculateWeeklyPay() {
        return hoursWorked * hourlyWage;
    }
}
