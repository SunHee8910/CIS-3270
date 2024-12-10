package org.example;

import java.util.ArrayList;
import CodingLogicPackage.CodingLogic;

public class Flight {
    private static ArrayList<Integer> bookedFlightIDs = new ArrayList<>();
    private String departureCity = "Please select a departure city";
    private String arrivalCity = "Please select a arrival city";
    private String departureDate = "Please select a departure date";
    private String arrivalDate = "Please select an arrival date";
    public String departureTime = "Please select a departure time";
    private String arrivalTime = "Please select an arrival time";
    private boolean purchasedTicket = false;
    private int ticketsRemaining = 100;
    private int ticketID = 0;
    public static int flightsBooked = 0;
    private ArrayList<User> passengers = new ArrayList<>();

    // Default constructor
    public Flight() {
        flightsBooked++;
    }

    // Constructor when the user is ready to book the entire flight
    public Flight(String departureCity, String arrivalCity, String departureDate, String arrivalDate, String departureTime, String arrivalTime) {
        this.ticketID = CodingLogic.generateFlightID(50);
        this.purchasedTicket = true;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        flightsBooked++;
    }

    // Getters and Setters
    public boolean hasPurchasedTicket() {
        return purchasedTicket;
    }

    public void setPurchasedTicket(boolean purchasedTicket) {
        this.purchasedTicket = purchasedTicket;
    }

    public int getTicketsRemaining() {
        return ticketsRemaining;
    }

    public void setTicketsRemaining(int ticketsRemaining) {
        this.ticketsRemaining = ticketsRemaining;
    }

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

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public ArrayList<User> getPassengers() {
        return passengers;
    }

    public void addPassenger(User user) {
        this.passengers.add(user);
    }

    public static ArrayList<Integer> getBookedFlightIDs() {
        return bookedFlightIDs;
    }

    public static void setBookedFlightIDs(ArrayList<Integer> bookedFlightIDs) {
        Flight.bookedFlightIDs = bookedFlightIDs;
    }

    public static int getFlightsBooked() {
        return flightsBooked;
    }

    public static void setFlightsBooked(int flightsBooked) {
        Flight.flightsBooked = flightsBooked;
    }

    public void setdepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
