package org.example;

import CodingLogicPackage.CodingLogic;

import java.util.ArrayList;

public abstract class User {
    protected int userID = 0;
    protected String customerName = "Unknown";
    protected String password = "Unknown";
    protected String address = "Unknown";
    protected String zip = "Unknown";
    protected String state = "Unknown";
    protected String username = "Unknown";
    protected String email = "Unknown";
    protected int ssn = 0;
    protected String recoveryAnswer = "Unknown";
    protected boolean isAdmin = false;
    private ArrayList<Flight> bookedFlights;


    public User() {}

    public User(String customerName, String password, String address, String zip, String state,
                String username, String email, int ssn, String recoveryAnswer) {
        this.userID = CodingLogic.generateUserID(userID); // Assign unique ID here
        this.customerName = customerName;
        this.password = password;
        this.address = address;
        this.zip = zip;
        this.state = state;
        this.username = username;
        this.email = email;
        this.ssn = ssn;
        this.recoveryAnswer = recoveryAnswer;
    }

    public int getUserID() {
        return userID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getState() {
        return state;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getSsn() {
        return ssn;
    }

    public String getRecoveryAnswer() {
        return recoveryAnswer;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void addFlight(Flight flight) {
        bookedFlights.add(flight);
    }

    public ArrayList<Flight> getBookedFlights() {
        return bookedFlights;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public void setRecoveryAnswer(String recoveryAnswer) {
        this.recoveryAnswer = recoveryAnswer;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // Recovery Answer Validation
    public boolean validateRecoveryAnswer(String answer) {
        return this.recoveryAnswer.equalsIgnoreCase(answer);
    }

    // Retrieve Password
    public String retrievePassword(String answer) {
        if (validateRecoveryAnswer(answer)) {
            return this.password;
        }
        return "Incorrect recovery answer. Try again.";
    }

}



