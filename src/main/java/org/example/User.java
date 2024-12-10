package org.example;

import CodingLogicPackage.CodingLogic;

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

    public User() {}

    public User(String customerName, String password, String address, String zip, String state,
                String username, String email, int ssn, String recoveryAnswer) {
        this.userID = CodingLogic.generateUserID(); // Assign unique ID here
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

    public boolean validateRecoveryAnswer(String answer) {
        return this.recoveryAnswer.equalsIgnoreCase(answer);
    }

    // Method to retrieve password (if the recovery answer is correct)
    public String retrievePassword(String answer) {
        if (validateRecoveryAnswer(answer)) {
            return this.password;
        }
        return "incorrect password, try again";
    }
}
