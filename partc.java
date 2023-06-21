import java.sql.*;

public class partc {
    public static void calculateGPAAboveThreshold(float gpaThreshold, String dbUrl, String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            // prepare the query statement
            String query = "SELECT students.*, " +
                           "GROUP_CONCAT(DISTINCT majors.dname) AS major, " +
                           "GROUP_CONCAT(DISTINCT minors.dname) AS minor, " +
                           "SUM(CASE " +
                           "  WHEN ht.grade = 'A' THEN 4 * c.credits " +
                           "  WHEN ht.grade = 'B' THEN 3 * c.credits " +
                           "  WHEN ht.grade = 'C' THEN 2 * c.credits " +
                           "  WHEN ht.grade = 'D' THEN 1 * c.credits " +
                           "  ELSE 0 " +
                           "END) / SUM(c.credits) AS gpa, " +
                           "SUM(c.credits) AS credits_completed " +
                           "FROM students " +
                           "LEFT JOIN majors ON students.id = majors.sid " +
                           "LEFT JOIN minors ON students.id = minors.sid " +
                           "LEFT JOIN HasTaken ht ON students.id = ht.sid " +
                           "LEFT JOIN classes c ON ht.name = c.name " +
                           "GROUP BY students.id " +
                           "HAVING gpa >= ?";

            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // set the parameter value for GPA threshold in the query statement
            stmt.setFloat(1, gpaThreshold);

            // execute the query and get the count of students found
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            System.out.println(count + " students found");

            // execute the query again to print the details of each student
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("last_name") + ", " + rs.getString("first_name"));
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Major: " + rs.getString("major"));
                System.out.println("Minor: " + rs.getString("minor"));
                System.out.println("GPA: " + rs.getFloat("gpa"));
                System.out.println("Credits Completed: " + rs.getInt("credits_completed"));
                System.out.println();
            }

            // close the resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
