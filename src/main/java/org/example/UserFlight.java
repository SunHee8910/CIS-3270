package org.example;

public class UserFlight {
    private String username;
    private Flight flight;
    private int bookingID;

    public UserFlight(int bookingID, String username, Flight flight) {
        this.username = username;
        this.flight = flight;
        this.bookingID = bookingID;
    }

    public Flight getFlight() {
        return flight;
    }

    public String toString() {
        return flight.getDepartureCity() + " to " + flight.getArrivalCity() +
                "  Departure Date: " + flight.getDepartureDate() +
                "  Arrival Date: " + flight.getArrivalDate() +
                "  Departure Time: " + flight.getDepartureTime() +
                "  Arrival Time: " + flight.getArrivalTime() +
                "   Booking ID: " + bookingID +
                "   Ticket ID: " + flight.getTicketID();
    }

    public int getBookingID() {
        return bookingID;
    }

}
