package backend.academy.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

class GameServiceTest {

    private void deleteFile(String pathnameFileDelete) throws IOException {
        File fileOutput = new File(pathnameFileDelete);
        Files.deleteIfExists(fileOutput.toPath());
    }

    @Test
    void shouldStartGame() throws Exception {
        deleteFile("src/test/java/backend/academy/resources/shouldStartGameOutPut.txt");
        File fileIn = new File("src/test/java/backend/academy/resources/shouldStartGame.txt");
        GameService gameService = new GameService(fileIn, new File("src/test/java/backend/academy/resources/shouldStartGameOutPut.txt"));
        gameService.start();
        String line;
        int counterEnteringParameters = 0;
        int counterWrongAlgo = 0;
        int counterWrongNum = 0;
        int lastLine = 0;
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/test/java/backend/academy/resources/shouldStartGameOutPut.txt", StandardCharsets.UTF_8))) {
            while (!Objects.equals(line = br.readLine(), null)) {

                if (line.equals("     Enter the height and width of the maze separated by a whitespace, the numbers must be greater than 1")) {
                    counterEnteringParameters++;
                }
                if (line.equals("     You enter the wrong number")) {
                    counterWrongNum++;
                }
                if (line.equals("     You enter the wrong number of algorithm")) {
                    counterWrongAlgo++;
                }
                if (line.equals("     Have a nice day!")) {
                    lastLine++;
                }


            }
        }
        Assertions.assertEquals(2, counterEnteringParameters);
        Assertions.assertEquals(3, counterWrongAlgo);
        Assertions.assertEquals(2, counterWrongNum);
        Assertions.assertEquals(1, lastLine);


    }

}