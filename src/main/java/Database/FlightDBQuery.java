package Database;
import Database.myJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

}
