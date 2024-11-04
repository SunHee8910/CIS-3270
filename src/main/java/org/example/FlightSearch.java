package org.example;

public class FlightSearch{

    String departureCity = "Unknown";
    String arrivalCity = "Unknown";
    String date = "Unknown";
    String flightTime = "Unknown";
    boolean purchasedTicket = false;
    int ticketsRemaining = 100;
    String ticketID = " ";

    FlightSearch(){

    }
    // constructor for a customer to search city to citys,date,times.
    FlightSearch(String departureCity, String arrivalCity,
                 String date, String flightTime){
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.date = date;
        this.flightTime = flightTime;
    }

    public void bookFlight(String departureCity, String arrivalCity,
    String date, String flightTime){
        if(!purchasedTicket){
            purchasedTicket = true;
            this.departureCity = departureCity;
            this.arrivalCity = arrivalCity;
            this.date = date;
            this.flightTime = flightTime;

        }
        else{
            System.out.println("This flight is already booked");
        }
    }



}
