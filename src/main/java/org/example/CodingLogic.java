package org.example;
import java.util.Random;
import java.util.ArrayList;
/*
super class for all coding logics, all methods will be placed into try and catch blocks
so that if any errors unexpectedly occur they can end gracefully.
 */

public class CodingLogic {
    private ArrayList<Integer> flightIDStorage = new ArrayList<>();


    public static int generateFlightID(int newBookedFlight){
        int generatedIDNumber;
        Random random = new Random();

        try{
            // verifying to ensure generatedFlightID is not already existing: if(!flightIDStorage.contains(newInteger){
            for(int oneLoop = 0; oneLoop < 1; oneLoop++){
                generatedIDNumber = random.nextInt();
                Integer newInt = Integer.valueOf(generatedIDNumber);

            }


        }catch (Exception e){
            System.out.println("An error has occured.");
        }
        return newBookedFlight;
    }

}
