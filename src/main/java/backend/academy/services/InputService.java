package backend.academy.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InputService {
    private final Scanner reader;


    public InputService(InputStream inputStream) {
        this.reader = new Scanner(inputStream, StandardCharsets.UTF_8);
    }

    public InputService(File file) throws IOException {
        this.reader = new Scanner(file, StandardCharsets.UTF_8);
    }


    public String userInput() {
        return reader.nextLine();
    }
}
