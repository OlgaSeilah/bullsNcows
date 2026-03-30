import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Logic {

    public static String validateAndConvertGivenData(String givenString) {
        if (givenString == null || givenString.isBlank()) {
            throw new RuntimeException("givenString should not be null or blank.");
        } // сюда дошла строка 100% не пустая, содержащая что угодно, длина любая
        // из этой строки вытянем число - первые 4
        int usersNumber = 0;
        int counter = 0;
        for (int i = 0; i < givenString.length(); i++) {
            if (Character.isDigit(givenString.charAt(i))) {
                usersNumber = usersNumber + Character.getNumericValue(givenString.charAt(i)) * (int) Math.pow(10, 3 - counter);
                counter++;
            } // todo add check that all digits are unique
        }
        if (counter < 4) {
            throw new RuntimeException("Number should not contain < 4 digits.");
        }
        return String.valueOf(usersNumber);
    }

    public static HashMap<Animal, Integer> countBullsNCows(String givenNumber, String secretNumber) { // here we get 2 strings of numbers of 4 digits each
        // compare 2 numbers by digits in them.
        HashMap<Animal, Integer> result = new HashMap<>();
        result.put(Animal.COWS, 0); //  cows (existing digit at an incorrect place)
        result.put(Animal.BULLS, 0); // bulls (existing digit at a correct position)
        // ex: givenNumber = 1 2 3 4
        //    secretNumber = 9 2 1 4   => 2 bulls; 1 cows.

        for (int i = 0; i < givenNumber.length(); i++) {
            if (givenNumber.charAt(i) == secretNumber.charAt(i)) {
                result.put(Animal.BULLS, result.get(Animal.BULLS) + 1);
            } else if (secretNumber.contains(
                    String.valueOf(givenNumber.charAt(i)))
            ) {
                result.put(Animal.COWS, result.get(Animal.COWS) + 1);
            }
        }

        return result;
    }

    public static String generateRandomNumber() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            numbers.add(i);
        }
        int secretNumber = 0;
        Collections.shuffle(numbers);

        for (int i = 0; i < 4; i++) {
            secretNumber = secretNumber + numbers.get(i) * (int) Math.pow(10, 3 - i);
        }

        return String.valueOf(secretNumber);
    }

    public static String generateFileName(int numberOfMoves) {
        LocalDateTime ldtNow = LocalDateTime.now();
        //2020  - 12  - 31  _ 23 _ 59 _ 1000000
        //year  month  day   hour  min  count of movements
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm");
        String fileName = ldtNow.format(dtf);
        System.out.printf("Generated file name: %s\n", fileName);

        return fileName + "_" + numberOfMoves + ".txt";
    }

    public static String renameFile(String fileName, int numberOfMoves) {
        StringBuilder sb = new StringBuilder(fileName);
        sb.replace(17,18, String.valueOf(numberOfMoves));
        return sb.toString();
    }
}
