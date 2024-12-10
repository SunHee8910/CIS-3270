package org.example;
public class Customer extends User {

    public Customer(String customerName, String password, String address, String zip, String state,
                    String username, String email, int ssn, String recoveryAnswer) {
        super(customerName, password, address, zip, state, username, email, ssn, recoveryAnswer);
    }

    }

    // Customer-specific methods
    public void bookFlight(Flight flight) {
        System.out.println("Booking flight: " + flight.getTicketID());
        // Logic to book a flight
    }

    public void viewBookings() {
        System.out.println("Viewing booked flights...");
        // Logic to retrieve and display booked flights
    }
}
