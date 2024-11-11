package org.example;

public class Admin {
    String FirstName = "Unknown";
    String LastName = "Unknown";
    String Address = "Unknown";
    String Zip = "Unknown";
    String State = "Unknown";
    String Username = "Unknown";
    String Password = "Unknown";
    String Email = "Unknown";
    int SSN = 0;
    String RecoveryAnswer = "Unknown";
    Boolean isAdmin = true;

    Admin(){//default constructor
    }

    Admin(String FirstName, String LastName, String Address, String Zip, String State, String Username, String Password, String Email, int SSN, String RecoveryAnswer){
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Address = Address;
        this.Zip = Zip;
        this.State = State;
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.SSN = SSN;
        this.RecoveryAnswer = RecoveryAnswer;
    }//constructor for Admin

    public class createFlight{
        String departureCity = "Unknown";
        String arrivalCity = "Unknown";
        String date = "Unknown";
        String flightTime = "Unknown";

        createFlight(){//default constructor
        }

        createFlight(String departureCity, String arrivalCity, String date, String flightTime){
            this.departureCity = departureCity;
            this.arrivalCity = arrivalCity;
            this.date = date;
            this.flightTime = flightTime;
        }//constructor for createFlight

        public void setDepartureCity(String departureCity){
            this.departureCity = departureCity;
        }//method to update departure city
        public void setArrivalCity(String arrivalCity){
            this.arrivalCity = arrivalCity;
        }//method to update arrival city
        public void setDate(String date){
            this.date = date;
        }//method to update date
        public void setFlightTime(String flightTime){
            this.flightTime = flightTime;
        }//method to update flight time
        public void deleteFlight(){
            this.departureCity = "Unknown";
            this.arrivalCity = "Unknown";
            this.date = "Unknown";
            this.flightTime = "Unknown";
        }//method to delete flight

    }
    public void createFlight(String departureCity, String arrivalCity, String date, String flightTime){
        createFlight newFlight = new createFlight(departureCity, arrivalCity, date, flightTime);
    }//method to create a new flight

}
