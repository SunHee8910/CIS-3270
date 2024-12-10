package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Database.myJDBC;

public class Conflict7_5 {

    public boolean checkFlightConflict(int customerId, String newDepartureDate, String newDepartureTime, String newArrivalDate, String newArrivalTime) {
        try {
            Connection connection = myJDBC.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM flights WHERE customerId = " + customerId;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String bookedDepartureDate = resultSet.getString("departureDate");
                String bookedDepartureTime = resultSet.getString("departureTime");
                String bookedArrivalDate = resultSet.getString("arrivalDate");
                String bookedArrivalTime = resultSet.getString("arrivalTime");

                if (isConflict(newDepartureDate, newDepartureTime, newArrivalDate, newArrivalTime, bookedDepartureDate, bookedDepartureTime, bookedArrivalDate, bookedArrivalTime)) {
                    System.out.println("Time Conflict: The new flight conflicts with an existing booking.");
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return false;
    }

    private boolean isConflict(String newDepartureDate, String newDepartureTime, String newArrivalDate, String newArrivalTime, String bookedDepartureDate, String bookedDepartureTime, String bookedArrivalDate, String bookedArrivalTime) {
        // Implement the logic to check for date and time conflicts
        // This is a placeholder implementation
        if (newDepartureDate.equals(bookedDepartureDate) && newDepartureTime.equals(bookedDepartureTime)) {
            return true;
        }
        if (newArrivalDate.equals(bookedArrivalDate) && newArrivalTime.equals(bookedArrivalTime)) {
            return true;
        }
        return false;
    }
}