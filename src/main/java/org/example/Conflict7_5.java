package org.example;

import Database.myJDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.example.Flight;


//public class conflict75 {
//    for(int i = 0; i < bookedFlightsIDs.length; i++) {
//        boolean timeConflict = false;
//        try{
//            Connection connection = myJDBC.getConnection();
//            Statement statement = connection.createStatement();
//
//            ResultSet resultSet = "SELECT * FROM flights WHERE flightID = " + bookedFlightsIDs[i];
//            while(resultSet.next()){
//                if (Integer.parseInt(resultSet.getString("departureTime")) - Integer.parseInt(resultSet.getString("arrivalTime")) < 10){
//                    Label label = new Label("Time Conflict");
//                    timeConflict = true;
//                }
//                if (resultSet.getString("arrivalDate").equals(departureDate) && resultSet.getString("departureCity").equals(departureCity) && resultSet.getString("arrivalCity").equals(arrivalCity)){
//                    Label label = new Label("Location Conflict");
//                    timeConflict = true;
//                }
//                if (resultSet.getString("departureDate").equals(departureDate) && resultSet.getString("departureCity").equals(departureCity) && resultSet.getString("arrivalCity").equals(arrivalCity)){
//                    Label label = new Label("Location Conflict");
//                    timeConflict = true;
//                }
//
//            }
//        }
//        catch(Exception e){
//            System.out.println("Error: " + e);
//        }
//    }
//
//
//
//}