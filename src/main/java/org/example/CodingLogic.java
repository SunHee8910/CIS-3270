package org.example;
import java.util.Random;
import java.util.ArrayList;
/*
super class for all coding logics, all methods will be placed into try and catch blocks
so that if any errors unexpectedly occur they can end gracefully.
 */

public class CodingLogic {
    private static ArrayList<Integer> flightIDStorage = new ArrayList<>();

    public static int generateFlightID(int newBookedFlight) {
        int generatedIDNumber;
        Random random = new Random();
        try {
            // Generate a unique flight ID
            for (int oneLoop = 0; oneLoop < 1; oneLoop++) {
                generatedIDNumber = random.nextInt(Integer.MAX_VALUE); // Generate a positive random integer
                Integer newInt = Integer.valueOf(generatedIDNumber);

                // verifying to ensure flightID is not already existing
                if (!flightIDStorage.contains(newInt)) {
                    flightIDStorage.add(newInt); // Add to the list
                    return generatedIDNumber; // Return the unique ID
                } else {
                    oneLoop--; // Retry if duplicate is found
                }
            }
        } catch (Exception e) {
            System.out.println("An error has occurred while generating a flight ID.");
        }

        return newBookedFlight; // Return fallback if an error occurs
    }
}
