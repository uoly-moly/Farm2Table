import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Farm2TableBackend {

    /**
     * Searches the database for producers matching a specific product type.
     * @param productType The product to search for.
     * @return A list of formatted strings, each containing a producer's details.
     */
    public static List<String> searchProduce(String productType) {
        List<String> results = new ArrayList<>();
        // SQL query now selects the new 'email' column
        String sql = "SELECT * FROM producers WHERE product_type LIKE ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + productType + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StringBuilder farmDetails = new StringBuilder();
                    farmDetails.append("Name: ").append(rs.getString("name")).append("\n");
                    farmDetails.append("Product: ").append(rs.getString("product_type")).append("\n");
                    farmDetails.append("Phone: ").append(rs.getString("phone")).append("\n");
                    farmDetails.append("Location: ").append(rs.getString("location")).append("\n");
                    // Appending the email to the search results
                    farmDetails.append("Email: ").append(rs.getString("email"));
                    results.add(farmDetails.toString());
                }
            }
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database search error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Adds a new producer to the database, now including their email.
     * @param name The producer's name.
     * @param productType The type of product.
     * @param phone The producer's phone number.
     * @param location The producer's location.
     * @param email The producer's email address.
     * @return true if added successfully, false otherwise.
     */
    public static boolean addFarmer(String name, String productType, String phone, String location, String email) {
        // SQL query now includes the 'email' column
        String sql = "INSERT INTO producers(name, product_type, phone, location, email) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, productType);
            pstmt.setString(3, phone);
            pstmt.setString(4, location);
            // Setting the value for the new email parameter
            pstmt.setString(5, email);

            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;

        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database insert error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}

