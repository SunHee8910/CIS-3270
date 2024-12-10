package Database;
import org.example.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDBQuery {

    public boolean registerUser(User user) {
        try (Connection connection = myJDBC.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "INSERT INTO users (userID, customerName, password, address, zip, state, username, email, ssn, recoveryAnswer) VALUES (" +
                    user.getUserID() + ", '" +
                    user.getCustomerName() + "', '" +
                    user.getPassword() + "', '" +
                    user.getAddress() + "', '" +
                    user.getZip() + "', '" +
                    user.getState() + "', '" +
                    user.getUsername() + "', '" +
                    user.getEmail() + "', " +
                    user.getSsn() + ", '" +
                    user.getRecoveryAnswer() + "')";
            statement.executeUpdate(sql);
            return true; // Registration successful
        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
        return false; // Registration failed
    }



    public boolean validateLogin(String username, String password) {
        try (Connection connection = myJDBC.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "SELECT COUNT(*) AS count FROM users WHERE " +
                    "username = '" + username + "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next() && resultSet.getInt("count") > 0) {
                return true; // Login successful
            }
        } catch (Exception e) {
            System.out.println("Error validating login: " + e.getMessage());
        }
        return false; // Login failed
    }


    public boolean isUsernameUnique(String username) {
        try (Connection connection = myJDBC.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "SELECT COUNT(*) AS count FROM users WHERE username = '" + username + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next() && resultSet.getInt("count") == 0) {
                return true; // Username is unique
            }
        } catch (Exception e) {
            System.out.println("Error checking username uniqueness: " + e.getMessage());
        }
        return false; // Username is not unique
    }



    public String retrievePassword(String username, String recoveryAnswer) {
        try (Connection connection = myJDBC.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "SELECT password FROM users WHERE " +
                    "username = '" + username + "' AND " +
                    "recoveryAnswer = '" + recoveryAnswer + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                // Return the password if a match is found
                return resultSet.getString("password");
            } else {
                System.out.println("Invalid username or recovery answer.");
                return null; // No match found
            }

        } catch (Exception e) {
            System.out.println("Error retrieving password: " + e.getMessage());
            return null; // Return null on error
        }
    }

}
