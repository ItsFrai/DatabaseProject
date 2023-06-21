import java.sql.*;

public class parte {
    public static void reportStudentsAndAverageGpaByDepartment(String department, String dbUrl, String username, String password) {
        


        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            // Prepare SQL query to retrieve students from the given department and calculate their GPA
            String sql = "SELECT d.name AS department, COUNT(*) AS num_students, AVG(gpa) AS avg_gpa "
                        + "FROM ("
                        + "  SELECT s.id, SUM(CASE "
                        + "  WHEN ht.grade = 'A' THEN 4 * c.credits "
                        + "  WHEN ht.grade = 'B' THEN 3 * c.credits "
                        + "  WHEN ht.grade = 'C' THEN 2 * c.credits "
                        + "  WHEN ht.grade = 'D' THEN 1 * c.credits "
                        + "  ELSE 0 "
                        + "  END) / SUM(c.credits) AS gpa "
                        + "  FROM Students s "
                        + "  JOIN HasTaken ht ON s.id = ht.sid "
                        + "  JOIN Classes c ON ht.name = c.name "
                        + "  JOIN IsTaking it ON s.id = it.sid "
                        + "  GROUP BY s.id"
                        + ") AS department_students "
                        + "JOIN Majors m ON department_students.id = m.sid "
                        + "JOIN Departments d ON m.dname = d.name "
                        + "WHERE d.name = ? " // Filter by department name
                        + "GROUP BY d.name";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, department);
            ResultSet resultSet = statement.executeQuery();

            // Print department statistics
            System.out.println("Department: " + department);
            System.out.println("Number of Students: Average GPA:");
            while (resultSet.next()) {
                int numStudents = resultSet.getInt("num_students");
                double avgGpa = resultSet.getDouble("avg_gpa");
                System.out.printf("%-15d %-15.2f%n", numStudents, avgGpa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

