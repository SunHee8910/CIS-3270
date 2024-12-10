package Database;
import Database.myJDBC;
import java.sql.*;

import org.example.Flight;
import static Database.myJDBC.getConnection;
public class FlightDBQuery {

    public static void addFlight(Flight flight) {
        Connection connection = null;

        connection = getConnection();
        Statement statement = null;

        try {
            // Establish the connection
            connection = getConnection();

            // Construct the SQL query using getters from the Flight object
            String sql = "INSERT INTO Flights " +
                    "(flightID, departureCity, arrivalCity, departureDate, arrivalDate, departureTime, arrivalTime, ticketsRemaining, purchasedTicket) " +
                    "VALUES (" +
                    "'" + flight.getTicketID() + "', " +
                    "'" + flight.getDepartureCity() + "', " +
                    "'" + flight.getArrivalCity() + "', " +
                    "'" + flight.getDepartureDate() + "', " +
                    "'" + flight.getArrivalDate() + "', " +
                    "'" + flight.departureTime + "', " +
                    "'" + flight.getArrivalTime() + "', " +
                    flight.getTicketsRemaining() + ", " +
                    flight.hasPurchasedTicket() + ")";

            // Create and execute the statement
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            System.out.println("Flight added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding flight: " + e.getMessage());
        } finally {
            try {
                // Close the statement and connection
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    // Delete a flight from a user's account
    public boolean deleteFlight(String username, Flight flight) {
        try (Connection connection = myJDBC.getConnection();
             Statement statement = connection.createStatement()) {

            // Construct SQL to delete the flight booking
            String sql = "DELETE FROM bookings WHERE " +
                    "username = '" + username + "' AND " +
                    "ticketID = '" + flight.getTicketID() + "'";

            int rowsAffected = statement.executeUpdate(sql);

            if (rowsAffected > 0) {
                System.out.println("Flight with ticket ID " + flight.getTicketID() + " deleted from user " + username + "'s account.");
                return true;
            } else {
                System.out.println("Flight with ticket ID " + flight.getTicketID() + " not found in user " + username + "'s account.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error deleting flight: " + e.getMessage());
            return false;
        }
    }


    public boolean bookFlight(String username, Flight flight) {
        try (Connection connection = myJDBC.getConnection();
             Statement statement = connection.createStatement()) {

            // Check if flight is already booked
            String checkSql = "SELECT COUNT(*) AS total FROM bookings WHERE " +
                    "username = '" + username + "' AND " +
                    "ticketID = '" + flight.getTicketID() + "'";
            ResultSet resultSet = statement.executeQuery(checkSql);

            resultSet.next();
            int count = resultSet.getInt("total");

            if (count > 0) {
                System.out.println("Flight with ticket ID " + flight.getTicketID() + " is already booked by user " + username + ".");
                return false;
            }

            // Insert the booking
            String insertSql = "INSERT INTO bookings (username, ticketID) VALUES (" +
                    "'" + username + "', " +
                    "'" + flight.getTicketID() + "')";
            statement.executeUpdate(insertSql);

            System.out.println("Flight with ticket ID " + flight.getTicketID() + " successfully booked for user " + username + ".");
            return true;

        } catch (Exception e) {
            System.err.println("Error booking flight: " + e.getMessage());
            return false;
        }
    }


}
