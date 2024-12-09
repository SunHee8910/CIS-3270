package org.example;
import java.util.Random;
import java.util.ArrayList;
/*
super class for all coding logics, all methods will be placed into try and catch blocks
so that if any errors unexpectedly occur they can end gracefully.
 */

public class CodingLogic {
    // array list for creating a list of objects with unlimited length
    private static ArrayList<Integer> flightIDStorage = new ArrayList<>();
    private static ArrayList<Integer> userIDStorage = new ArrayList<>();

    public static int generateFlightID(int newBookedFlight) {
        int generatedIDNumber;
        Random random = new Random();
        try {
            for (int oneLoop = 0; oneLoop < 1; oneLoop++) {
                generatedIDNumber = random.nextInt(Integer.MAX_VALUE);
                Integer newInt = Integer.valueOf(generatedIDNumber); //upcasting the int to an Integer object for arraylist use

                if (!flightIDStorage.contains(newInt)) {
                    flightIDStorage.add(newInt); // add to the list
                    return generatedIDNumber;
                } else {
                    oneLoop--; // Retry if duplicate is found
                }
            }
        } catch (Exception e) {
            System.out.println("An error has occurred while generating a flight ID.");
        }

        return newBookedFlight;
    }

    public static int generateUserID(int useriD) {
        int generatedIDNumber;
        Random random = new Random();
        try {
            for (int oneLoop = 0; oneLoop < 1; oneLoop++) {
                generatedIDNumber = random.nextInt(Integer.MAX_VALUE);
                Integer newInt = Integer.valueOf(generatedIDNumber);

                if (!userIDStorage.contains(newInt)) {
                    userIDStorage.add(newInt);
                    return generatedIDNumber;
                } else {
                    oneLoop--;
                }
            }
        } catch (Exception e) {
            System.out.println("An error has occurred while generating a user ID.");
        }
        return useriD;
    }
}
