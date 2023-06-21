import java.sql.*;

public class partb {
    public static void calculateCreditsCompletedByYear(String year, String dbUrl, String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            // prepare the query statement
            String query = "SELECT s.first_name, s.last_name, s.id, " +
                           "GROUP_CONCAT(DISTINCT m.dname) AS major, " +
                           "GROUP_CONCAT(DISTINCT n.dname) AS minor, " +
                           "SUM(CASE " +
                           "  WHEN ht.grade = 'A' THEN 4 * c.credits " +
                           "  WHEN ht.grade = 'B' THEN 3 * c.credits " +
                           "  WHEN ht.grade = 'C' THEN 2 * c.credits " +
                           "  WHEN ht.grade = 'D' THEN 1 * c.credits " +
                           "  ELSE 0 " +
                           "END) / SUM(c.credits) AS gpa, " +
                           "SUM(c.credits) AS credits_completed " +
                           "FROM students s " +
                           "LEFT JOIN majors m ON s.id = m.sid " +
                           "LEFT JOIN minors n ON s.id = n.sid " +
                           "JOIN HasTaken ht ON s.id = ht.sid " +
                           "JOIN classes c ON ht.name = c.name " +
                           "WHERE ht.grade NOT LIKE 'f' " +
                           "GROUP BY s.id";
            Statement stmt = conn.createStatement();

            // execute the query and get the count of students found
            ResultSet rs = stmt.executeQuery(query);
            int count = 0;
            while (rs.next()) {
                int creditsCompleted = rs.getInt("credits_completed");
                String studentYear = "";
                if (creditsCompleted >= 0 && creditsCompleted <= 29) {
                    studentYear = "Fr";
                } else if (creditsCompleted >= 30 && creditsCompleted <= 59) {
                    studentYear = "So";
                } else if (creditsCompleted >= 60 && creditsCompleted <= 89) {
                    studentYear = "Ju";
                } else if (creditsCompleted >= 90) {
                    studentYear = "Sr";
                }
                if (studentYear.equalsIgnoreCase(year)) {
                    count++;
                    System.out.println(rs.getString("last_name") + ", " + rs.getString("first_name"));
                    System.out.println("ID: " + rs.getString("id"));
                    System.out.println("Major: " + rs.getString("major"));
                    System.out.println("Minor: " + rs.getString("minor"));
                    System.out.println("GPA: " + rs.getFloat("gpa"));
                    System.out.println("Credits Completed: " + rs.getInt("credits_completed"));
                    System.out.println();
                }
            }
            System.out.println(count + " students found");

            // close the resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

