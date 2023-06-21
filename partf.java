import java.sql.*;

public class partf {
    public static void reportStudentsAndLetterGradesByClass(String className, String dbUrl, String username, String password) {
       

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(dbUrl, username, password);

            // Prepare SQL statement with parameter for class name
            String sql = "SELECT c.name AS class_name, "
                    + "(SELECT COUNT(*) FROM IsTaking it WHERE it.name = c.name) AS num_students_taking, "
                    + "(SELECT COUNT(*) FROM HasTaken ht WHERE ht.name = c.name AND ht.grade = 'A') AS num_students_grade_A, "
                    + "(SELECT COUNT(*) FROM HasTaken ht WHERE ht.name = c.name AND ht.grade = 'B') AS num_students_grade_B, "
                    + "(SELECT COUNT(*) FROM HasTaken ht WHERE ht.name = c.name AND ht.grade = 'C') AS num_students_grade_C, "
                    + "(SELECT COUNT(*) FROM HasTaken ht WHERE ht.name = c.name AND ht.grade = 'D') AS num_students_grade_D, "
                    + "(SELECT COUNT(*) FROM HasTaken ht WHERE ht.name = c.name AND ht.grade = 'F') AS num_students_grade_F "
                    + "FROM Classes c "
                    + "WHERE c.name = ?";
                    
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, className); // Set the parameter value for class name

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve and display the results
            if (resultSet.next()) {
                String class_name = resultSet.getString("class_name");
                int num_students_taking = resultSet.getInt("num_students_taking");
                int num_students_grade_A = resultSet.getInt("num_students_grade_A");
                int num_students_grade_B = resultSet.getInt("num_students_grade_B");
                int num_students_grade_C = resultSet.getInt("num_students_grade_C");
                int num_students_grade_D = resultSet.getInt("num_students_grade_D");
                int num_students_grade_F = resultSet.getInt("num_students_grade_F");

                System.out.println("Class Name: " + class_name);
                System.out.println("Number of Students Taking: " + num_students_taking);
                System.out.println("Number of Students with Grade A: " + num_students_grade_A);
                System.out.println("Number of Students with Grade B: " + num_students_grade_B);
                System.out.println("Number of Students with Grade C: " + num_students_grade_C);
                System.out.println("Number of Students with Grade D: " + num_students_grade_D);
                System.out.println("Number of Students with Grade F: " + num_students_grade_F);
            } else {
                System.out.println("No results found for the given class name.");
            }

            // Close connections and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
}


