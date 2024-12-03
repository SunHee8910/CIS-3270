/*package org.example;

class Flight{

    private String departureCity = "Please select a departure city";
    private String arrivalCity = "Please select a arrival city";
    private String departureDate = "Please select a departure date";
    private String arrivalDate = "Please select a arrival date";
    public String departureTime = "Please select a departure time";
    private String arrivalTime = "Please select a arrival time";
    public boolean purchasedTicket = false;
    public int ticketsRemaining = 100;
    public int ticketID = 0;
    public static int flightsBooked;

    Flight(){
        flightsBooked ++;
    }
    // constructor for the flight object fully booked
    Flight(String departureCity, String arrivalCity,
                 String departureDate, String arrivalDate, String departureTime,String arrivalTime){
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

    }

    // getter to see flight details
    public String getFlightDetails(){
        return departureCity
    }

    // setter to book flights
    public void setBookedFlight(String departureCity, String arrivalCity,
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
*/