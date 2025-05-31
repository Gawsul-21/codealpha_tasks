import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    // Class to store student name and grade
    static class Student {
        String name;
        double grade;

        Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("===== Student Grade Tracker =====");
        System.out.print("Enter number of students: ");
        int totalStudents = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < totalStudents; i++) {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            System.out.print("Enter grade for " + name + ": ");
            double grade = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            students.add(new Student(name, grade));
        }

        // Display all student data
        System.out.println("\n--- Student Grades ---");
        for (Student s : students) {
            System.out.println(s.name + " - " + s.grade);
        }

        // Calculate average, highest and lowest
        double total = 0;
        double highest = students.get(0).grade;
        double lowest = students.get(0).grade;

        for (Student s : students) {
            total += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
            }
        }

        double average = total / students.size();

        System.out.println("\n--- Statistics ---");
        System.out.println("Average Grade: " + average);
        System.out.println("Highest Grade: " + highest);
        System.out.println("Lowest Grade: " + lowest);

        scanner.close();
    }
}
