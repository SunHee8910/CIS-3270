package CodingLogicPackage;
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
                // Generate a random number
                generatedIDNumber = random.nextInt(Integer.MAX_VALUE);

                // Check if the generated number is unique
                if (!userIDStorage.contains(generatedIDNumber)) {
                    userIDStorage.add(generatedIDNumber); // Add to storage
                    return generatedIDNumber; // Return the new unique ID
                } else {
                    // If not unique, allow the loop to retry
                    oneLoop--; // Decrement the loop counter to retry once
                }
            }
        } catch (Exception e) {
            System.out.println("An error has occurred while generating a user ID: " + e.getMessage());
        }

        // If all attempts fail, return the provided fallback ID
        return useriD;
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 4) {
            return false;
        }

        boolean hasNumber = false;
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasSymbol = false;

        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                hasNumber = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (!Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch)) {
                hasSymbol = true;
            }
        }

        return hasNumber && hasLowercase && hasUppercase && hasSymbol;
    }
}
