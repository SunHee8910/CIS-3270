package org.example;
import java.util.ArrayList;

class Flight extends CodingLogic{

    private String departureCity = "Please select a departure city";
    private String arrivalCity = "Please select a arrival city";
    private String departureDate = "Please select a departure date";
    private String arrivalDate = "Please select a arrival date";
    public String departureTime = "Please select a departure time";
    private String arrivalTime = "Please select a arrival time";
    public boolean purchasedTicket = false;
    public int ticketsRemaining = 100;
    public int ticketID = 0;
    public static int flightsBooked = 0;

    // default constructor
    Flight(){
        flightsBooked ++;
    }
    // constructor when user is ready to book entire flight
    Flight(String departureCity, String arrivalCity, String departureDate, String arrivalDate, String departureTime, String arrivalTime) {
        this.ticketID = CodingLogic.generateFlightID(this.ticketID);
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        flightsBooked++;
    }
    // setters and getters for all private fields
    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getDepartureDate() {
        return departureDate;
    }
//
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTicketID(){
        return ticketID;
    }
/*
    public static void main(String[] args) {
        Flight newFlight = new Flight("test","test2", "test3", "test4", "test5", "test6");
        System.out.println(        newFlight.getTicketID());
    }
    test main method to ensure the initalization of a newflight instance would have a randomly generated number through the coding logic method.
 */

}
