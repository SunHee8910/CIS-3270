package Database;
import org.example.*;

import java.sql.*;
import java.util.ArrayList;

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


    public User getUser(String username) {
        User user = null;
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = myJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String customerName = resultSet.getString("customerName");
                String password = resultSet.getString("password");
                String address = resultSet.getString("address");
                String zip = resultSet.getString("zip");
                String state = resultSet.getString("state");
                String email = resultSet.getString("email");
                int ssn = resultSet.getInt("ssn");
                String recoveryAnswer = resultSet.getString("recoveryAnswer");
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                // Create User object based on role
                if (isAdmin) {
                    user = new Admin(customerName, password, address, zip, state, username, email, ssn, recoveryAnswer);
                } else {
                    user = new Customer(customerName, password, address, zip, state, username, email, ssn, recoveryAnswer);
                }

                // Fetch and populate booked flights
                ArrayList<Flight> bookedFlights = fetchBookedFlights(resultSet.getInt("userID"));
                user.getBookedFlights().addAll(bookedFlights);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
        return user;
    }



    private ArrayList<Flight> fetchBookedFlights(int userID) {
        ArrayList<Flight> bookedFlights = new ArrayList<>();
        String query = "SELECT flights.* FROM flights " +
                "JOIN bookings ON flights.ticketID = bookings.ticketID " +
                "WHERE bookings.userID = ?";

        try (Connection connection = myJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setDepartureCity(resultSet.getString("departureCity"));
                flight.setArrivalCity(resultSet.getString("arrivalCity"));
                flight.setDepartureDate(resultSet.getString("departureDate"));
                flight.setArrivalDate(resultSet.getString("arrivalDate"));
                flight.setdepartureTime(resultSet.getString("departureTime"));
                flight.setArrivalTime(resultSet.getString("arrivalTime"));
                flight.setTicketsRemaining(resultSet.getInt("ticketsRemaining"));
                flight.setPurchasedTicket(resultSet.getBoolean("purchasedTicket"));
                bookedFlights.add(flight);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching booked flights: " + e.getMessage());
        }
        return bookedFlights;
    }




    public ArrayList<Integer> fetchUserBookings(int userID) {
        ArrayList<Integer> bookedFlightIDs = new ArrayList<>();
        String query = "SELECT ticketID FROM bookings WHERE userID = ?";
        try (Connection connection = myJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bookedFlightIDs.add(resultSet.getInt("ticketID"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching bookings for userID " + userID + ": " + e.getMessage());
        }
        return bookedFlightIDs;
    }


}
