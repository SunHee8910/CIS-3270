package Database;

import java.sql.*;

import static CodingLogicPackage.CodingLogic.generateUserID;

public class myJDBC {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://cis3270project.mysql.database.azure.com:3306/project3270", "username", "password123$");
        } catch (Exception e) {
            System.out.println("Cannot connect to the database: " + e.getMessage());
        }
        return null;
    }

    public static boolean getUsernameIsUniqueQuery(String username) {
        try {
            ResultSet usernameResult = myJDBC.getConnection().createStatement().executeQuery(String.format("select * from users where lower(username) = lower('%s')", username));
            return !usernameResult.next();
        } catch (Exception e) {
            System.out.println("hit exception: " + e.getMessage());
            return false;
        }
    }

    public static boolean createCustomerQuery(String firstName, String lastName, String password, String address, String zip, String state, String email, int ssn, String question, String answer, String username) {
        try {
            int customerid = generateUserID(1);
            getConnection().createStatement().executeUpdate(String.format("INSERT INTO users " +
                            "(userID, customerName, password, address, zip, state, email, ssn, recoveryAnswer, username, recoveryQuestion) VALUES " +
                            "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    customerid, firstName + " " + lastName, password, address, zip, state, email, ssn, answer, username, question));
            return true;
        } catch (Exception e) {
            System.out.printf("Unable to create user: %s\n", e.getMessage());
            return false;
        }
    }

    public static boolean userOrAdminLoginQuery(String username, String password) {
        try {
            ResultSet resultSet = myJDBC.getConnection().createStatement().executeQuery(
                    String.format(
                            "Select * from users where lower(username) = '%s' and password = '%s'", username, password
                    ));
            return resultSet.next();
        } catch (Exception e) {
            return false;
        }
    }

    public static String[] getUserRecoveryQuestionAndAnswerQuery(String username) {
        try {
            ResultSet resultSet = myJDBC.getConnection().createStatement().executeQuery(
                    String.format(
                            "Select recoveryQuestion, recoveryAnswer, password from users where lower(username) = '%s'", username
                    ));
            boolean hasUsername = resultSet.next();
            if (!hasUsername) {
                return null;
            }
            String recoveryQuestion = resultSet.getString("recoveryQuestion");
            String recoveryAnswer = resultSet.getString("recoveryAnswer");
            String password = resultSet.getString("password");

            return new String[]{recoveryQuestion, recoveryAnswer, password};
        } catch (Exception e) {
            return null;
        }
    }

}