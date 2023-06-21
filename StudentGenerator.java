import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentGenerator {
    public static void main(String[] args) {
        // Connect to the database
        String dbUrl = args[0];
        String username = args[1];
        String password = args[2];
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl,username,password);

            // Generate a list of 100 unique ID numbers for students
            List<Long> ids = generateIds(100);

            // Generate a list of common first and last names
            String[] firstNames = {"Emma", "Liam", "Olivia", "Noah", "Ava", "Isabella", "Sophia", "Mia", "Charlotte", "Amelia",
            "Harper", "Evelyn", "Abigail", "Emily", "Elizabeth", "Sofia", "Avery", "Ella", "Scarlett", "Grace",
            "Addison", "Victoria", "Lily", "Aubrey", "Zoey", "Penelope", "Victoria", "Madison", "Eleanor", "Hannah",
            "Savannah", "Brooklyn", "Zoe", "Nora", "Lily", "Grace", "Hazel", "Ellie", "Natalie", "Stella", "Sadie"};
            String[] lastNames = {"Smith", "Johnson", "Brown", "Taylor", "Miller", "Anderson", "Williams", "Jones", "Clark", "Rodriguez",
            "Martinez", "Lee", "Gonzalez", "Hernandez", "Wilson", "Walker", "Hall", "Allen", "Young", "King",
            "Wright", "Lee", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Garcia", "Gomez",
            "Lewis", "Long", "Moore", "Murphy", "Perez", "Rivera", "Sanchez", "Sullivan", "Taylor", "Thomas",
            "Thompson", "Torres", "Turner", "Ward", "Watson", "White", "Williams", "Wilson", "Wood", "Wright"};

            // Populate the Students table with random data
            for (Long id : ids) {
                String firstName = firstNames[new Random().nextInt(firstNames.length)];
                String lastName = lastNames[new Random().nextInt(lastNames.length)];
                insertStudent(connection, id, firstName, lastName);
            }

            // Populate the Departments table with random data
            String[] departments = {"Bio", "Chem", "CS", "Eng", "Math", "Phys"};
            String[] campuses = {"Busch", "CAC", "Livi", "CD"};
            for (String department : departments) {
                String campus = campuses[new Random().nextInt(campuses.length)];
                insertDepartment(connection, department, campus);
            }

            // Populate the Classes table with random data
            String[] classNames = {"Introduction to Whale Songs", "The Science of Coffee", "History of Board Games", 
            "Artificial Intelligence", "Dance for Everyone", 
            "Writing for the Web", "Philosophy of Love and Sex", 
            "Music Theory", "Photography 101", "Astrobiology", 
            "Introduction to Astronomy", "Environmental Science", 
            "Introduction to Psychology", "Introduction to Sociology", 
            "Introduction to Computer Science", "Calculus I", "Calculus II", 
            "Linear Algebra", "Discrete Mathematics", "Data Structures", 
            "Database Management", "Computer Networks", "Operating Systems", 
            "Software Engineering", "Web Development", "Mobile App Development", 
            "Digital Marketing", "Introduction to Business", "Financial Accounting", 
            "Managerial Accounting","Chemistry 101", "World History", 
            "Introduction to Philosophy", "Literature and Society", "Advanced Physics",
             "Spanish for Beginners", "Introduction to Anthropology", 
             "Psychology of Human Behavior", "Microeconomics", "Macroeconomics","Introduction to Geology",
             "Introduction to Linguistics",
             "History of Jazz Music",
             "Ethics in Technology",
             "Introduction to Statistics",
             "Contemporary Art History",
             "Introduction to Political Science",
             "Business Ethics",
             "Introduction to Film Studies",
             "Introduction to Gender Studies",
             "Cultural Anthropology",
             "Introduction to Human Anatomy",
             "Introduction to Nutrition" }; 
            int[] credits = {3, 4};
            for (String className : classNames) {
                int credit = credits[new Random().nextInt(credits.length)];
                insertClass(connection, className, credit);
            }

            // Populate the Majors and Minors tables with random data
            for (Long id : ids) {
                int numMajors = new Random().nextInt(3);
                List<String> majorList = generateRandomList(departments, numMajors);
                for (String major : majorList) {
                    insertMajor(connection, id, major);
                }

                int numMinors = new Random().nextInt(3);
                List<String> minorList = generateRandomList(departments, numMinors);
                for (String minor : minorList) {
                    insertMinor(connection, id, minor);
                }
            }

            // Populate the IsTaking and HasTaken tables with random data
            // Populate the IsTaking and HasTaken tables with random data
            for (Long id : ids) {
                int numCourses = new Random().nextInt(53) + 1;
                List<String> courseList = generateRandomList(classNames, numCourses);
                int cred = new Random().nextInt(151); // 
                String grade;
                if (cred > 89) { // Senior
                    for (String course : courseList) {
                        insertIsTaking(connection, id, course);
                        grade = generateRandomGrade();
                        insertHasTaken(connection, id, course, grade);
                    }
                } else if (cred > 59 && cred <= 89) { // Junior
                    for (String course : courseList) {
                        insertIsTaking(connection, id, course);
                        grade = generateRandomGrade();
                        insertHasTaken(connection, id, course, grade);
                    }
                } else if (cred > 29 && cred <= 59) { // Sophomore
                    for (String course : courseList) {
                        insertIsTaking(connection, id, course);
                        grade = generateRandomGrade();
                        insertHasTaken(connection, id, course, grade);
                    }
                } else { // Freshman
                    for (String course : courseList) {
                        insertIsTaking(connection, id, course);
                        grade = generateRandomGrade();
                        insertHasTaken(connection, id, course, grade);
                    }
                }
            }
            System.out.println("Database populated successfully.");

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}

// Helper methods to insert data into tables

private static void insertStudent(Connection connection, Long id, String firstName, String lastName) throws SQLException {
    String sql = "INSERT INTO Students (id, first_name, last_name) VALUES (?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, id);
    statement.setString(2, firstName);
    statement.setString(3, lastName);
    statement.executeUpdate();
}

private static void insertDepartment(Connection connection, String name, String campus) throws SQLException {
    String sql = "INSERT INTO Departments (name, campus) VALUES (?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setString(1, name);
    statement.setString(2, campus);
    statement.executeUpdate();
}

private static void insertClass(Connection connection, String name, int credits) throws SQLException {
    String sql = "INSERT INTO Classes (name, credits) VALUES (?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setString(1, name);
    statement.setInt(2, credits);
    statement.executeUpdate();
}

private static void insertMajor(Connection connection, Long sid, String dname) throws SQLException {
    String sql = "INSERT INTO Majors (sid, dname) VALUES (?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, sid);
    statement.setString(2, dname);
    statement.executeUpdate();
}

private static void insertMinor(Connection connection, Long sid, String dname) throws SQLException {
    String sql = "INSERT INTO Minors (sid, dname) VALUES (?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, sid);
    statement.setString(2, dname);
    statement.executeUpdate();
}

private static void insertIsTaking(Connection connection, Long sid, String name) throws SQLException {
    String sql = "INSERT INTO IsTaking (sid, name) VALUES (?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, sid);
    statement.setString(2, name);
    statement.executeUpdate();
}

private static void insertHasTaken(Connection connection, Long sid, String name, String grade) throws SQLException {
    String sql = "INSERT INTO HasTaken (sid, name, grade) VALUES (?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, sid);
    statement.setString(2, name);
    statement.setString(3, grade);
    statement.executeUpdate();
}

// Helper methods to generate random data

private static List<Long> generateIds(int numIds) {
    List<Long> ids = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < numIds; i++) {
        long id = 100000000 + random.nextInt(900000000);
        ids.add(id);
    }
    return ids;
}

private static String generateRandomGrade() {
    String[] grades = {"A", "B", "C", "D", "F"};
    return grades[new Random().nextInt(grades.length)];
}

private static <T> List<T> generateRandomList(T[] array, int length) {
    List<T> list = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
        T element = array[random.nextInt(array.length)];
        if (!list.contains(element)) {
            list.add(element);
        }
    }
    return list;
}
}

