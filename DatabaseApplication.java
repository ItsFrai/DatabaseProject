import java.sql.*;
import java.util.Scanner;

public class DatabaseApplication {

    public static void main(String[] args) {

        try {
            // Connect to the database
            String dbUrl = args[0];
            String username = args[1];
            String password = args[2];
            Connection conn = DriverManager.getConnection(dbUrl,username,password);
            System.out.println("Connected to the database.");

            // Create a statement for executing SQL queries
            Statement stmt = conn.createStatement();

            // Loop to display menu and accept user input
            Scanner scanner = new Scanner(System.in);
            String choice;
            do {
                System.out.println("Select an option:");
                System.out.println("a) Search students by name");
                System.out.println("b) Search students by year");
                System.out.println("c) Search students by GPA above threshold");
                System.out.println("d) Search students by GPA below threshold");
                System.out.println("e) Report number of students and average GPA by department");
                System.out.println("f) Report number of students and letter grades by class");
                System.out.println("g) Execute arbitrary SQL query");
                System.out.println("q) Quit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextLine();

                switch (choice.toLowerCase()) {
                    case "a":
                        System.out.print("Enter search string: ");
                        String searchString = scanner.nextLine();
                        parta.searchStudentsByName(searchString,dbUrl, username, password);
                        break;
                    case "b":
                        System.out.print("Enter year (Fr, So, Ju, Sr): ");
                        String year = scanner.nextLine();
                        partb.calculateCreditsCompletedByYear(year, dbUrl, username, password);
                        break;
                    case "c":
                        System.out.print("Enter GPA threshold: ");
                        float gpaAboveThreshold = scanner.nextFloat();
                        scanner.nextLine();
                        partc.calculateGPAAboveThreshold(gpaAboveThreshold, dbUrl, username, password);
                        break;
                    case "d":
                        System.out.print("Enter GPA threshold: ");
                        float gpaBelowThreshold = scanner.nextFloat();
                        scanner.nextLine();
                        partd.searchStudentsByGpaBelowThreshold(gpaBelowThreshold, dbUrl, username, password);
                        break;
                    case "e":
                        System.out.print("Enter department name: ");
                        String departmentName = scanner.nextLine();
                        parte.reportStudentsAndAverageGpaByDepartment(departmentName, dbUrl, username, password);
                        break;
                    case "f":
                        System.out.print("Enter class name: ");
                        String className = scanner.nextLine();
                        partf.reportStudentsAndLetterGradesByClass(className, dbUrl, username, password);
                        break;
                    case "g":
                        System.out.print("Enter SQL query: ");
                        String sqlQuery = scanner.nextLine();
                        partg.executeArbitrarySqlQuery(sqlQuery, dbUrl, username, password);
                        break;
                    case "q":
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (!choice.toLowerCase().equals("q"));

            // Close resources
            stmt.close();
            conn.close();
            scanner.close();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}
      



