package backend.academy.services;

import backend.academy.mazegenerator.Generator;
import backend.academy.mazegenerator.KruskalGenerator;
import backend.academy.mazegenerator.PrimsGenerator;
import backend.academy.pathfinder.AstarSolver;
import backend.academy.pathfinder.BfsSolver;
import backend.academy.pathfinder.Solver;
import backend.academy.utilities.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

class ProcessingServiceTest {


    private void deleteFile(String pathnameFileDelete) throws IOException {
        File fileOutput = new File(pathnameFileDelete);
        Files.deleteIfExists(fileOutput.toPath());
    }

    @Test
    void shouldAcceptCoordinates() throws IOException {
        deleteFile("src/test/java/backend/academy/resources/processtest/shouldAcceptCoordinatesOutPut.txt");
        File fileIn = new File("src/test/java/backend/academy/resources/processtest/shouldAcceptCoordinates.txt");
        ProcessingService processingService = new ProcessingService(new InputService(fileIn),
                new PrintStream(new File("src/test/java/backend/academy/resources/processtest/shouldAcceptCoordinatesOutPut.txt"), StandardCharsets.UTF_8));
        String line;
        int counterEntering = 0;
        int counterWrongNum = 0;
        Coordinate coordinate =  processingService.getParameters(Text.HEIGHTWIDTH.getMessage(),
                Text.WRONGNUMBER.getMessage());
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/test/java/backend/academy/resources/processtest/shouldAcceptCoordinatesOutPut.txt", StandardCharsets.UTF_8))) {
            while (!Objects.equals(line = br.readLine(), null)) {
                if (line.equals("     Enter the height and width of the maze separated by a whitespace, the numbers must be greater than 1")) {
                    counterEntering++;
                } else if (line.equals("     You enter the wrong number")) {
                    counterWrongNum++;
                }
            }
        }
        Assertions.assertEquals(4, counterEntering);
        Assertions.assertEquals(3, counterWrongNum);
        Assertions.assertEquals(new Coordinate(4, 4), coordinate);
    }

    @Test
    void shouldAcceptGenerationAlgorithm() throws IOException {
        deleteFile("shouldAcceptGenerationAlgorithmOutPut.txt");
        File fileIn = new File("src/test/java/backend/academy/resources/processtest/shouldAcceptGenerationAlgorithm.txt");
        ProcessingService processingService = new ProcessingService(new InputService(fileIn),
                new PrintStream(new File("src/test/java/backend/academy/resources/processtest/shouldAcceptGenerationAlgorithmOutPut.txt"), StandardCharsets.UTF_8));
        String line;
        int counterEnteringGenerator = 0;
        int counterWrongNum = 0;
        Generator generator = processingService.getGenerationAlgorithm(Text.GENERATIONALGORITHM.getMessage(), Text.WRONGNUMBER.getMessage());
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/test/java/backend/academy/resources/processtest/shouldAcceptGenerationAlgorithmOutPut.txt", StandardCharsets.UTF_8))) {
            while (!Objects.equals(line = br.readLine(), null)) {
                if (line.equals("     There're 2 algorithms for generate a maze, enter a number")) {
                    counterEnteringGenerator++;
                } else if (line.equals("     You enter the wrong number")) {
                    counterWrongNum++;
                }
            }
        }
        Assertions.assertEquals(KruskalGenerator.class, generator.getClass());
        Assertions.assertNotEquals(PrimsGenerator.class, generator.getClass());
        Assertions.assertEquals(5, counterEnteringGenerator);
        Assertions.assertEquals(4, counterWrongNum);
    }
    @Test
    void shouldAcceptGenerationAlgorithmEmpty() throws IOException {
        deleteFile("src/test/java/backend/academy/resources/processtest/shouldAcceptGenAlgoEmptyOut.txt");
        File fileIn = new File("src/test/java/backend/academy/resources/processtest/shouldAcceptGenAlgoEmpty.txt");
        ProcessingService processingService = new ProcessingService(new InputService(fileIn),
                new PrintStream(new File("src/test/java/backend/academy/resources/processtest/shouldAcceptGenAlgoEmptyOut.txt"), StandardCharsets.UTF_8));
        String line;
        int counterEnteringGenerator = 0;
        Generator generator = processingService.getGenerationAlgorithm(Text.GENERATIONALGORITHM.getMessage(), Text.WRONGNUMBER.getMessage());
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/test/java/backend/academy/resources/processtest/shouldAcceptGenAlgoEmptyOut.txt", StandardCharsets.UTF_8))) {
            while (!Objects.equals(line = br.readLine(), null)) {
                if (line.equals("     There're 2 algorithms for generate a maze, enter a number")) {
                    counterEnteringGenerator++;
                }
            }
        }
        Assertions.assertEquals(KruskalGenerator.class, generator.getClass());
        Assertions.assertNotEquals(PrimsGenerator.class, generator.getClass());
        Assertions.assertEquals(1, counterEnteringGenerator);
    }

    @Test
    void shouldAcceptStart() throws IOException {
        deleteFile("shouldAcceptStartOutPut.txt");
        File fileIn = new File("src/test/java/backend/academy/resources/processtest/shouldAcceptStart.txt");
        ProcessingService processingService = new ProcessingService(new InputService(fileIn),
                new PrintStream(new File("src/test/java/backend/academy/resources/processtest/shouldAcceptStartOutPut.txt"), StandardCharsets.UTF_8));
        String line;
        int counterEnteringStart = 0;
        int counterWrongNum = 0;
        processingService.width(5);
        processingService.height(5);
        Coordinate coordinate = processingService.getStart(Text.START.getMessage(), Text.WRONGNUMBER.getMessage());
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/test/java/backend/academy/resources/processtest/shouldAcceptStartOutPut.txt", StandardCharsets.UTF_8))) {
            while (!Objects.equals(line = br.readLine(), null)) {
                if (line.equals("     Enter coordinates of starting point")) {
                    counterEnteringStart++;
                } else if (line.equals("     You enter the wrong number")) {
                    counterWrongNum++;
                }
            }
        }
        Assertions.assertEquals(new Coordinate(1, 3), coordinate);
        Assertions.assertEquals(4, counterEnteringStart);
        Assertions.assertEquals(3, counterWrongNum);

    }
    @Test
    void shouldAcceptEnd() throws IOException {
        deleteFile("shouldAcceptEndOutPut.txt");
        File fileIn = new File("src/test/java/backend/academy/resources/processtest/shouldAcceptEnd.txt");
        ProcessingService processingService = new ProcessingService(new InputService(fileIn),
                new PrintStream(new File("src/test/java/backend/academy/resources/processtest/shouldAcceptEndOutPut.txt"), StandardCharsets.UTF_8));
        String line;
        int counterEnteringEnd = 0;
        int counterWrongNum = 0;
        processingService.width(5);
        processingService.height(5);
        Coordinate coordinate = processingService.getEnd(Text.END.getMessage(), Text.WRONGNUMBER.getMessage());
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/test/java/backend/academy/resources/processtest/shouldAcceptEndOutPut.txt", StandardCharsets.UTF_8))) {
            while (!Objects.equals(line = br.readLine(), null)) {
                if (line.equals("     Enter coordinates of ending point")) {
                    counterEnteringEnd++;
                } else if (line.equals("     You enter the wrong number")) {
                    counterWrongNum++;
                }
            }
        }
        Assertions.assertEquals(new Coordinate(2, 2), coordinate);
        Assertions.assertEquals(4, counterEnteringEnd);
        Assertions.assertEquals(3, counterWrongNum);

    }

    @Test
    void getSolverAlgorithm() throws IOException {
        deleteFile("shouldAcceptSolverAlgorithmOutPut.txt");
        File fileIn = new File("src/test/java/backend/academy/resources/processtest/shouldAcceptSolverAlgorithm.txt");
        ProcessingService processingService = new ProcessingService(new InputService(fileIn),
                new PrintStream(new File("src/test/java/backend/academy/resources/processtest/shouldAcceptSolverAlgorithmOutPut.txt"), StandardCharsets.UTF_8));
        String line;
        int counterEnteringGenerator = 0;
        int counterWrongNum = 0;
        Solver solver = processingService.getSolverAlgorithm(Text.SOLVERALGORITHM.getMessage(), Text.WRONGNUMBER.getMessage());
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/test/java/backend/academy/resources/processtest/shouldAcceptSolverAlgorithmOutPut.txt", StandardCharsets.UTF_8))) {
            while (!Objects.equals(line = br.readLine(), null)) {
                if (line.equals("     There're 2 algorithms for find path from start to end point, enter a number")) {
                    counterEnteringGenerator++;
                } else if (line.equals("     You enter the wrong number")) {
                    counterWrongNum++;
                }
            }
        }
        Assertions.assertEquals(AstarSolver.class, solver.getClass());
        Assertions.assertNotEquals(BfsSolver.class, solver.getClass());
        Assertions.assertEquals(4, counterEnteringGenerator);
        Assertions.assertEquals(3, counterWrongNum);
    }

    @Test
    void shouldAcceptSolverAlgorithmEmpty() throws IOException {
        deleteFile("src/test/java/backend/academy/resources/processtest/shouldAcceptSolAlgoEmptyOut.txt");
        File fileIn = new File("src/test/java/backend/academy/resources/processtest/shouldAcceptSolAlgoEmpty.txt");
        ProcessingService processingService = new ProcessingService(new InputService(fileIn),
                new PrintStream(new File("src/test/java/backend/academy/resources/processtest/shouldAcceptSolAlgoEmptyOut.txt"), StandardCharsets.UTF_8));
        String line;
        int counterEnteringGenerator = 0;
        Solver solver = processingService.getSolverAlgorithm(Text.SOLVERALGORITHM.getMessage(), Text.WRONGNUMBER.getMessage());
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/test/java/backend/academy/resources/processtest/shouldAcceptSolAlgoEmptyOut.txt", StandardCharsets.UTF_8))) {
            while (!Objects.equals(line = br.readLine(), null)) {
                if (line.equals("     There're 2 algorithms for find path from start to end point, enter a number")) {
                    counterEnteringGenerator++;
                }
            }
        }
        Assertions.assertEquals(AstarSolver.class, solver.getClass());
        Assertions.assertNotEquals(BfsSolver.class, solver.getClass());
        Assertions.assertEquals(1, counterEnteringGenerator);
    }
}