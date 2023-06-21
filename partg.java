import java.sql.*;

public class partg {
    public static void executeArbitrarySqlQuery(String sqlQuery, String dbUrl, String username, String password) 
    {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            // Create a Statement object for executing SQL queries
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Get ResultSetMetaData for retrieving column names and types
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();

            // Print column names
            System.out.println("Column Names:");
            for (int i = 1; i <= numColumns; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Print column types
            System.out.println("Column Types:");
            for (int i = 1; i <= numColumns; i++) {
                System.out.print(metaData.getColumnTypeName(i) + "\t");
            }
            System.out.println();

            // Print query result
            System.out.println("Query Result:");
            while (resultSet.next()) {
                for (int i = 1; i <= numColumns; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

