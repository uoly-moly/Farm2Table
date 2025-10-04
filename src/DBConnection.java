import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {
    // --- IMPORTANT: SPELLING CORRECTED ON THIS LINE ---
    private static final String DB_URL = "jdbc:mysql://localhost:3306/organic_farmers_db"; 
    
    private static final String DB_USER = "root"; // Replace with your MySQL username
    private static final String DB_PASSWORD = "Sql4kar!"; // Replace with your MySQL password

    /**
     * Establishes and returns a connection to the database.
     * @return a Connection object or null if the connection fails.
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}

