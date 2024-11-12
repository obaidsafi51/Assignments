import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Double> marks;

    public Student(String name) {
        this.name = name;
        this.marks = new ArrayList<>();
    }

    public void addMark(double mark) {
        marks.add(mark);
    }

    public double calculateAverage() {
        double sum = 0;
        for (double mark : marks) {
            sum += mark;
        }
        return sum / marks.size();
    }

    public String calculateGrade() {
        double average = calculateAverage();
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numStudents = getIntegerInput(scanner, "Enter the number of students: ");
        scanner.nextLine();
        ArrayList<Student> students = new ArrayList<>();

        for (int i = 0; i < numStudents; i++) {
            String name = getStringInput(scanner, "Enter student " + (i + 1) + " name: ");

            Student std = new Student(name);

            int numSubjects = getIntegerInput(scanner, "Enter the number of subjects for " + name + ": ");
            scanner.nextLine();
            for (int j = 0; j < numSubjects; j++) {
                double mark = getMarkInput(scanner, "Enter mark for subject " + (j + 1) + ": ");
                scanner.nextLine();
                std.addMark(mark);
            }
            students.add(std);
        }

        System.out.println("\nStudent Marks Report:");
        for (Student student : students) {
            System.out.println("Name: " + student.name);
            System.out.println("Marks: " + student.marks);
            System.out.println("Average: " + student.calculateAverage());
            System.out.println("Grade: " + student.calculateGrade());
            System.out.println();
        }

        scanner.close();
    }

    private static int getIntegerInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.next(); // Clear invalid input
            }
        }
    }
    private static double getMarkInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double mark = scanner.nextDouble();
                if (mark < 0 || mark > 100) {
                    System.out.println("Invalid input. Mark must be between 0 and 100.");
                } else {
                    return mark;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a decimal number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) {
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            } else {
                return input;
            }
        }
    }
}
