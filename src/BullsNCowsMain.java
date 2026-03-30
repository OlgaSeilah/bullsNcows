import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashMap;

public class BullsNCowsMain {

    static void main(String[] args) {
        String secretNumber = Logic.generateRandomNumber();
        System.out.printf("Secret number is %s \n", secretNumber);

        boolean win = false;

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        String givenByUserString = "";
        String validatedUserString = "";
        HashMap<Integer, HashMap<Animal, Integer>> result = new HashMap<>();
        int numberOfMoves = 0;

        String fileName = Logic.generateFileName(numberOfMoves); System.out.printf("File name: %s\n", fileName);
        Path pathToFile = Path.of("src", "files", fileName); System.out.printf("pathToFile: %s\n", pathToFile);

        if (!Files.exists(pathToFile)) {
            try {
                Files.createDirectories(pathToFile.getParent());
                Files.createFile(pathToFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

////todo rename file (add moves number)
        try {
            while (win == false) {
                numberOfMoves++;

// =====================================================================================================================


// =====================================================================================================================

                givenByUserString = input.readLine();
                System.out.printf("User's string: %s\n", givenByUserString);

                validatedUserString = Logic.validateAndConvertGivenData(givenByUserString);
                System.out.printf("User's number: %s\n", validatedUserString);


                result.put(numberOfMoves, Logic.countBullsNCows(validatedUserString, secretNumber));
                result.get(numberOfMoves).forEach((key, value) -> System.out.println(key + ": "
                        + value));


                try (BufferedWriter bufferedOutputStream = Files.newBufferedWriter(pathToFile)) {
                    bufferedOutputStream.write(result.toString());
                }

                if (validatedUserString.equals(secretNumber)) {
                    System.out.println("The end!");
                    win = true;
                }


                if (win == true) {
                    Files.copy(pathToFile, Path.of("src", "files",Logic.renameFile(fileName, numberOfMoves)),
                            StandardCopyOption.REPLACE_EXISTING);
                    Files.delete(pathToFile);
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

// before every new move! -> show the whole history of the round (all prev data)
//              - LinkedList of objects "Result" where fields:
//                      int bullsCount = 0 (at start) counts every move in a loop
//                      int cowsCount = 0 (at start) counts every move in a loop

// System does not show to User if User prints not Numbers (User prints Char & Letters & other...)
//       and in step 3. shows "? cows, ? bulls" instead.

// When Game finished -> generate a txt file. file name = [date, hours, minutes and number of moves]
//     (example: 2020-12-31_23_59_1000000)
// inside file:
//          ? attempt -> given(cleaned) number : ? cows, ? bulls
//     Ask user where to place log file. User should set the Path
//
