package backend.academy.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InputServiceTest {

    @Test
    void shouldReadTxt1() throws IOException {
        File file = new File("src/test/java/backend/academy/resources/InputServiceTest.txt");
        InputService inputService = new InputService(file);
        Assertions.assertEquals("5 5", inputService.userInput());

    }

    @Test
    void shouldReadTxt2() throws IOException {
        File file = new File("src/test/java/backend/academy/resources/InputServiceTest2.txt");
        InputService inputService = new InputService(file);
        Assertions.assertEquals("  13     24", inputService.userInput());

    }
}