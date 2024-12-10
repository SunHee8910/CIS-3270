package Database;

import java.sql.*;

import static CodingLogicPackage.CodingLogic.generateUserID;

public class myJDBC {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://cis3270project.mysql.database.azure.com:3306/project3270",
                    "username",
                    "password123$"
            );
        } catch (Exception e) {
            System.out.println("Cannot connect to the database: " + e.getMessage());
        }
        return null;
    }

    public static boolean getIsUsernameUniqueQuery(String username){
        try {
            ResultSet usernameResult = myJDBC.getConnection()
                    .createStatement()
                    .executeQuery(String.format("select * from customers where lower(username) = lower('%s')",username));

            return !usernameResult.next();
        } catch (Exception e){
            return false;
        }

    }

public static boolean createCustomerQuery(String firstName, String lastName, String password, String address, String zip, String state, String email, int ssn, String question, String username){
    try {
        int customerid = generateUserID(1);
    getConnection().createStatement().executeUpdate(
    String.format(
            "INSERT INTO customers (customerID, customerName, password, address, zip, state, email, ssn, recoveryAnswer, username) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
            customerid,
            firstName + " " + lastName,
            password,
            address,
            zip,
            state,
            email,
            ssn,
            question,
            username
    ));
    return true;
    } catch (Exception e){
        System.out.printf("Unable to create user");
        return false;
    }
}

}