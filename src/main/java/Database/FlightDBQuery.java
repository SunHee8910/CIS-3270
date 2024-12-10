package Database;
import java.sql.*;
import java.util.HashMap;
import org.example.Flight;
import static Database.myJDBC.getConnection;
public class FlightDBQuery {

    public static boolean addFlight(Flight flight) {
        Connection connection = null;

        connection = getConnection();
        Statement statement = null;

        try {
            // Establish the connection
            connection = getConnection();

            // Construct the SQL query using getters from the Flight object
            String sql = "INSERT INTO Flights " +
                    "(ticketID, departureCity, arrivalCity, departureDate, arrivalDate, departureTime, arrivalTime, ticketsRemaining, purchasedTicket) " +
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
            return true;

        } catch (Exception e) {
            System.out.println("Error adding flight: " + e.getMessage());
            return false;
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
            String sql = "DELETE FROM flights WHERE " +
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

            // Step 1: Check flight capacity
            String capacityCheckSql = "SELECT ticketsRemaining FROM flights WHERE ticketID = " + flight.getTicketID();
            ResultSet capacityResult = statement.executeQuery(capacityCheckSql);

            if (capacityResult.next()) {
                int ticketsRemaining = capacityResult.getInt("ticketsRemaining");

                if (ticketsRemaining <= 0) {
                    System.out.println("This flight is fully booked. Cannot book this flight.");
                    return false;
                }
            } else {
                System.out.println("Flight not found. Please check the flight ID.");
                return false;
            }

            // Step 2: Prevent duplicate bookings (assuming username is stored in another tracking table)
            String duplicateCheckSql = "SELECT COUNT(*) AS total FROM flights WHERE ticketID = " +
                    flight.getTicketID() + " AND bookedByUsername = '" + username + "'";
            ResultSet duplicateResult = statement.executeQuery(duplicateCheckSql);

            if (duplicateResult.next() && duplicateResult.getInt("total") > 0) {
                System.out.println("You have already booked this flight.");
                return false;
            }

            // Step 3: Update flight to mark it as booked by the user
            String bookFlightSql = "UPDATE flights SET ticketsRemaining = ticketsRemaining - 1, " +
                    "bookedByUsername = '" + username + "' WHERE ticketID = " + flight.getTicketID();
            statement.executeUpdate(bookFlightSql);

            System.out.println("Flight successfully booked for user: " + username);
            return true;

        } catch (Exception e) {
            System.err.println("Error booking flight: " + e.getMessage());
            return false;
        }
    }

    // method to get the users bookedflights
    public HashMap<Integer, Flight> getUserBookedFlights(String username) {
        HashMap<Integer, Flight> bookedFlights = new HashMap<>();

        try {
            Connection connection = myJDBC.getConnection();
            Statement statement = connection.createStatement();

            // SQL query to fetch flights associated with the user
            String sql = "SELECT * FROM flights WHERE userID = (SELECT userID FROM users WHERE username = '" + username + "')";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int ticketID = resultSet.getInt("ticketID");
                Flight flight = new Flight(
                        resultSet.getString("departureCity"),
                        resultSet.getString("arrivalCity"),
                        resultSet.getString("departureDate"),
                        resultSet.getString("arrivalDate"),
                        resultSet.getString("departureTime"),
                        resultSet.getString("arrivalTime")
                );
                flight.setTicketsRemaining(resultSet.getInt("ticketsRemaining"));
                bookedFlights.put(ticketID, flight);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error while getting booked flights: " + e.getMessage());
        }

        return bookedFlights;
    }



    // Search for flights based on criteria
    public HashMap<String, Flight> searchFlights(String departureCity, String arrivalCity, String departureDate, String departureTime) {
        HashMap<String, Flight> flights = new HashMap<>();
        String sql = "SELECT * FROM Flights WHERE " +
                "(departureCity = '" + departureCity + "' OR '" + departureCity + "' IS NULL OR departureCity IS NULL) AND " +
                "(arrivalCity = '" + arrivalCity + "' OR '" + arrivalCity + "' IS NULL OR arrivalCity IS NULL) AND " +
                "(departureDate = '" + departureDate + "' OR '" + departureDate + "' IS NULL OR departureDate IS NULL) AND " +
                "(departureTime = '" + departureTime + "' OR '" + departureTime + "' IS NULL OR departureTime IS NULL)";

        try (Connection connection = myJDBC.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Flight flight = new Flight(
                        resultSet.getString("departureCity"),
                        resultSet.getString("arrivalCity"),
                        resultSet.getString("departureDate"),
                        resultSet.getString("arrivalDate"),
                        resultSet.getString("departureTime"),
                        resultSet.getString("arrivalTime")
                );
                flights.put(resultSet.getString("ticketID"), flight); // Use ticketID as the key
            }

            if (flights.isEmpty()) {
                System.out.println("No flights found matching the criteria.");
            } else {
                System.out.println("Flights found:");
                for (String ticketID : flights.keySet()) {
                    Flight f = flights.get(ticketID);
                    System.out.printf("ID: %s | From: %s | To: %s | Date: %s | Time: %s\n",
                            ticketID, f.getDepartureCity(), f.getArrivalCity(), f.getDepartureDate(), f.departureTime);
                }
            }

        } catch (Exception e) {
            System.err.println("Error searching flights: " + e.getMessage());
        }

        return flights;
    }


}
