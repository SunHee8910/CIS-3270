import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class myJDBC {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://cis3270project.mysql.database.azure.com:3306/project3270", "username", "password123$");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from name");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Firstname"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
