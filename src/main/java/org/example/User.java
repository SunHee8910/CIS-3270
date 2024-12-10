package org.example;

public abstract class User {
    protected String customerName = "Unknown";
    protected String password = "Unknown";
    protected String address = "Unknown";
    protected String zip = "Unknown";
    protected String state = "Unknown";
    protected String username = "Unknown";
    protected String email = "Unknown";
    protected int ssn = 0;
    protected String recoveryAnswer = "Unknown";

    public User() {}

    public User(String customerName, String password, String address, String zip, String state,
                String username, String email, int ssn, String recoveryAnswer) {
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

    // Common methods
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean recoverPassword(String recoveryAnswer) {
        return this.recoveryAnswer.equalsIgnoreCase(recoveryAnswer);
    }

}
