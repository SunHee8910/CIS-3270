package org.example;
import java.util.ArrayList;

class Flight{

    private String departureCity = "Please select a departure city";
    private String arrivalCity = "Please select a arrival city";
    private String departureDate = "Please select a departure date";
    private String arrivalDate = "Please select a arrival date";
    public String departureTime = "Please select a departure time";
    private String arrivalTime = "Please select a arrival time";
    public boolean purchasedTicket = false;
    public int ticketsRemaining = 100;
    public int [] ticketID = new int [100];
    public static int flightsBooked = 0;
    // default constructor
    Flight(){
        flightsBooked ++;
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


}
