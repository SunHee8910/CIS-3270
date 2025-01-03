package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.example.Flight;
import org.example.User;
import org.example.UserFlight;

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
            String sql = "INSERT INTO Flights " + "(ticketID, departureCity, arrivalCity, departureDate, arrivalDate, departureTime, arrivalTime, ticketsRemaining, purchasedTicket) " + "VALUES (" + "'" + flight.getTicketID() + "', " + "'" + flight.getDepartureCity() + "', " + "'" + flight.getArrivalCity() + "', " + "'" + flight.getDepartureDate() + "', " + "'" + flight.getArrivalDate() + "', " + "'" + flight.departureTime + "', " + "'" + flight.getArrivalTime() + "', " + flight.getTicketsRemaining() + ", " + flight.hasPurchasedTicket() + ")";

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
    public static boolean deleteFlight(int bookingID) {
        try (Connection connection = myJDBC.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Get the ticketID from the booking
            String getTicketIDSql = "SELECT ticketID FROM bookings WHERE bookingID = ?";
            int ticketID = -1;
            try (PreparedStatement getTicketStmt = connection.prepareStatement(getTicketIDSql)) {
                getTicketStmt.setInt(1, bookingID);
                ResultSet resultSet = getTicketStmt.executeQuery();
                if (resultSet.next()) {
                    ticketID = resultSet.getInt("ticketID");
                } else {
                    System.out.println("Booking not found.");
                    return false;
                }
            }

            // Delete the booking
            String deleteBookingSql = "DELETE FROM bookings WHERE bookingID = ?";
            try (PreparedStatement deleteBookingStmt = connection.prepareStatement(deleteBookingSql)) {
                deleteBookingStmt.setInt(1, bookingID);
                int rowsAffected = deleteBookingStmt.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("Failed to delete booking.");
                    return false;
                }
            }

            // Increase the tickets remaining for the associated flight
            String updateCapacitySql = "UPDATE flights SET ticketsRemaining = ticketsRemaining + 1 WHERE ticketID = ?";
            try (PreparedStatement updateCapacityStmt = connection.prepareStatement(updateCapacitySql)) {
                updateCapacityStmt.setInt(1, ticketID);
                updateCapacityStmt.executeUpdate();
            }

            connection.commit(); // Commit transaction
            System.out.printf("Flight booking with ID %d deleted and tickets remaining updated.%n", bookingID);
            return true;

        } catch (Exception e) {
            System.err.println("Error deleting flight booking: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<UserFlight> getUserFlights(String username) {
        try {
            ResultSet results = myJDBC.getConnection().createStatement().executeQuery("SELECT bookings.bookingID, bookings.ticketID, flights.departureCity, flights.arrivalCity, flights.departureTime, flights.arrivalTime, flights.departureDate, flights.arrivalDate " + "FROM bookings JOIN flights ON bookings.ticketID = flights.ticketID " + "JOIN users ON bookings.userID = users.userID " + "WHERE users.username = '" + username + "'");

            ArrayList<UserFlight> result = new ArrayList<>();

            while (results.next()) {
                int ticketID = results.getInt("ticketID");
                String departureCity = results.getString("departureCity");
                String arrivalCity = results.getString("arrivalCity");
                String departureTime = results.getString("departureTime");
                String arrivalTime = results.getString("arrivalTime");
                String departureDate = results.getString("departureDate");
                String arrivalDate = results.getString("arrivalDate");
                Flight flight = new Flight(departureCity, arrivalCity, departureDate, arrivalDate, departureTime, arrivalTime, ticketID);
                int bookingID = results.getInt("bookingID");
                UserFlight userFlight = new UserFlight(bookingID, username, flight);
                result.add(userFlight);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

    }


    private boolean isTimeAvailable(String arrivalTime, String departureTime) {
        arrivalTime = normalizeTime(arrivalTime);
        departureTime = normalizeTime(departureTime);

        if (arrivalTime == null || departureTime == null) {
            System.out.println("Invalid time format. Accepted formats: HH:mm or HH mm.");
            return false;
        }

        int arrival = Integer.parseInt(arrivalTime.replace(":", ""));
        int departure = Integer.parseInt(departureTime.replace(":", ""));
        return departure > arrival; // Departure must be after arrival
    }


    private String normalizeTime(String time) {
        String cleanedTime = time.replace(" ", "").replace(":", "");
        if (cleanedTime.matches("\\d{4}")) {
            return cleanedTime.substring(0, 2) + ":" + cleanedTime.substring(2);
        }
        return null;
    }


    private boolean hasTimeConflict(int userID, Flight newFlight) {
        String query = "SELECT flights.arrivalDate, flights.arrivalTime, flights.arrivalCity " + "FROM flights " + "JOIN bookings ON flights.ticketID = bookings.ticketID " + "WHERE bookings.userID = ?";
        try (Connection connection = myJDBC.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bookedArrivalDate = resultSet.getString("arrivalDate");
                String bookedArrivalTime = resultSet.getString("arrivalTime");
                String bookedArrivalCity = resultSet.getString("arrivalCity");

                // Ensure the arrival city and new flight's departure city are different
                if (bookedArrivalCity.equals(newFlight.getDepartureCity())) {

                    if (bookedArrivalDate.equals(newFlight.getDepartureDate()) && !isTimeAvailable(bookedArrivalTime, newFlight.getDepartureTime())) {
                        return true; // Conflict detected
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking for time conflicts: " + e.getMessage());
        }

        return false; // No conflicts detected
    }


    public static boolean bookFlight(String username, Flight flight) {
        try (Connection connection = myJDBC.getConnection()) {
            // Get the userID from username
            String getUserIDSql = "SELECT userID FROM users WHERE username = ?";
            try (PreparedStatement userStmt = connection.prepareStatement(getUserIDSql)) {
                userStmt.setString(1, username);
                ResultSet userResult = userStmt.executeQuery();

                if (!userResult.next()) {
                    System.out.println("User not found.");
                    return false;
                }

                int userID = userResult.getInt("userID");

                // Check flight capacity
                String capacityCheckSql = "SELECT ticketsRemaining FROM flights WHERE ticketID = ?";
                try (PreparedStatement capacityStmt = connection.prepareStatement(capacityCheckSql)) {
                    capacityStmt.setInt(1, flight.getTicketID());
                    ResultSet capacityResult = capacityStmt.executeQuery();

                    if (!capacityResult.next()) {
                        System.out.println("Flight not found.");
                        return false;
                    }

                    int ticketsRemaining = capacityResult.getInt("ticketsRemaining");

                    if (ticketsRemaining <= 0) {
                        System.out.println("This flight is fully booked.");
                        return false;
                    }
                }

                // Prevent duplicate bookings
                String duplicateCheckSql = "SELECT COUNT(*) AS total FROM bookings WHERE userID = ? AND ticketID = ?";
                try (PreparedStatement duplicateStmt = connection.prepareStatement(duplicateCheckSql)) {
                    duplicateStmt.setInt(1, userID);
                    duplicateStmt.setInt(2, flight.getTicketID());
                    ResultSet duplicateResult = duplicateStmt.executeQuery();

                    if (duplicateResult.next() && duplicateResult.getInt("total") > 0) {
                        System.out.println("You have already booked this flight.");
                        return false;
                    }
                }

                // Insert booking and update flight capacity
                connection.setAutoCommit(false); // Start transaction

                String bookFlightSql = "INSERT INTO bookings (userID, ticketID) VALUES (?, ?)";
                try (PreparedStatement bookStmt = connection.prepareStatement(bookFlightSql)) {
                    bookStmt.setInt(1, userID);
                    bookStmt.setInt(2, flight.getTicketID());
                    bookStmt.executeUpdate();
                }

                String updateCapacitySql = "UPDATE flights SET ticketsRemaining = ticketsRemaining - 1 WHERE ticketID = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateCapacitySql)) {
                    updateStmt.setInt(1, flight.getTicketID());
                    updateStmt.executeUpdate();
                }

                connection.commit(); // Commit transaction
                System.out.println("Flight successfully booked for user: " + username);
                return true;

            } catch (Exception e) {
                connection.rollback(); // Rollback transaction on error
                throw e;
            }

        } catch (Exception e) {
            System.err.println("Error booking flight: " + e.getMessage());
            return false;
        }
    }


    public static int getTicketsRemaining(int ticketID) {
        try (Connection connection = myJDBC.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT ticketsRemaining FROM flights WHERE ticketID = ?")) {
            statement.setInt(1, ticketID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ticketsRemaining");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving tickets remaining: " + e.getMessage());
        }
        return -1; // Return -1 if an error occurs
    }

    public static String getConflictingFlights(int ticketID, String username) {
        ArrayList<String> conflictingFlights = new ArrayList<>();
        try {
            ResultSet results = myJDBC.getConnection().createStatement().executeQuery(String.format(
                    "SELECT bookings.bookingID, booked_flight.departureCity, booked_flight.arrivalCity FROM bookings\n" +
                            "\tJOIN users ON users.userID = bookings.userID\n" +
                            "\tJOIN flights selected_flight ON selected_flight.ticketID = %d\n" +
                            "\tJOIN flights booked_flight ON booked_flight.ticketID = bookings.ticketID\n" +
                            "WHERE\n" +
                            "\tlower(users.username) = lower('%s')\n" +
                            "\tand (timestamp(convert(selected_flight.departureDate, DATE), convert(selected_flight.departureTime, TIME)) BETWEEN timestamp(convert(booked_flight.departureDate, DATE), convert(booked_flight.departureTime, TIME)) AND timestamp(convert(booked_flight.arrivalDate, DATE), convert(booked_flight.arrivalTime, TIME))\n" +
                            "\tOR timestamp(convert(selected_flight.arrivalDate, DATE), convert(selected_flight.arrivalTime, TIME)) BETWEEN timestamp(convert(booked_flight.departureDate, DATE), convert(booked_flight.departureTime, TIME)) AND timestamp(convert(booked_flight.arrivalDate, DATE), convert(booked_flight.arrivalTime, TIME)))",
                     ticketID, username));

            while (results.next()) {
                conflictingFlights.add(String.format("The flight you are trying to book conflicts with a flight you already have booked, booking ID %d, from %s to %s", results.getInt("bookingID"), results.getString("departureCity"), results.getString("arrivalCity")));
            }

            return String.join("\n", conflictingFlights);
        } catch (Exception e) {
            System.out.printf("Error when searching for conflicting flights: %s\n", e.getMessage() );
            return String.join("\n", conflictingFlights);
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
                Flight flight = new Flight(resultSet.getString("departureCity"), resultSet.getString("arrivalCity"), resultSet.getString("departureDate"), resultSet.getString("arrivalDate"), resultSet.getString("departureTime"), resultSet.getString("arrivalTime"));
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
        String sql = "SELECT * FROM Flights WHERE " + "(departureCity = '" + departureCity + "' OR '" + departureCity + "' IS NULL OR departureCity IS NULL) AND " + "(arrivalCity = '" + arrivalCity + "' OR '" + arrivalCity + "' IS NULL OR arrivalCity IS NULL) AND " + "(departureDate = '" + departureDate + "' OR '" + departureDate + "' IS NULL OR departureDate IS NULL) AND " + "(departureTime = '" + departureTime + "' OR '" + departureTime + "' IS NULL OR departureTime IS NULL)";

        try (Connection connection = myJDBC.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Flight flight = new Flight(resultSet.getString("departureCity"), resultSet.getString("arrivalCity"), resultSet.getString("departureDate"), resultSet.getString("arrivalDate"), resultSet.getString("departureTime"), resultSet.getString("arrivalTime"));
                flights.put(resultSet.getString("ticketID"), flight); // Use ticketID as the key
            }

            if (flights.isEmpty()) {
                System.out.println("No flights found matching the criteria.");
            } else {
                System.out.println("Flights found:");
                for (String ticketID : flights.keySet()) {
                    Flight f = flights.get(ticketID);
                    System.out.printf("ID: %s | From: %s | To: %s | Date: %s | Time: %s\n", ticketID, f.getDepartureCity(), f.getArrivalCity(), f.getDepartureDate(), f.departureTime);
                }
            }

        } catch (Exception e) {
            System.err.println("Error searching flights: " + e.getMessage());
        }

        return flights;
    }


}
