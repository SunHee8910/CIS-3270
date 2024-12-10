package org.example;

public class Admin extends User {

    public Admin(String customerName, String password, String address, String zip, String state,
                 String username, String email, int ssn, String recoveryAnswer) {
        super(customerName, password, address, zip, state, username, email, ssn, recoveryAnswer);
        this.isAdmin = true;
    }

    // Admin-specific methods
    public void createFlight(Flight flight) {
        System.out.println("Admin is creating a flight: " + flight.getTicketID());
        // Logic to create a flight
    }

    public void updateFlight(Flight flight) {
        System.out.println("Admin is updating flight: " + flight.getTicketID());
        // Logic to update a flight
    }

    public void deleteFlight(Flight flight) {
        System.out.println("Admin is deleting flight: " + flight.getTicketID());
        // Logic to delete a flight
    }

    public void viewAllFlights() {
        System.out.println("Admin is viewing all flights...");
        // Logic to retrieve and display all flights
    }
}
