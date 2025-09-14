package backend.academy.services;

import backend.academy.mazegenerator.Generator;
import backend.academy.mazegenerator.KruskalGenerator;
import backend.academy.mazegenerator.PrimsGenerator;
import backend.academy.pathfinder.AstarSolver;
import backend.academy.pathfinder.BfsSolver;
import backend.academy.pathfinder.Solver;
import backend.academy.utilities.Coordinate;
import java.io.PrintStream;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class ProcessingService {
    private final InputService reader;
    private final PrintStream writer;
    private final Coordinate[] coordinates = new Coordinate[2];
    @Setter
    private int height;
    @Setter
    private int width;
    private static final String ONETOTWO = "^[1-2]";



    public ProcessingService(InputService reader, PrintStream writer) {
        this.reader = reader;
        this.writer = writer;
    }


    public Coordinate getParameters(String parametersText, String wrongNumber) {

        writer.println(parametersText);
        String input = reader.userInput();
        String[] inputAr = input.split(" ");
        if (inputAr.length == 2) {
            try {
                height = Integer.parseInt(inputAr[0]);
                width = Integer.parseInt(inputAr[1]);
                if (height >= 2 && width >= 2) {
                    return new Coordinate(height, width);
                } else {
                    height = 0;
                    width = 0;
                    writer.println(wrongNumber);
                    return getParameters(parametersText, wrongNumber);
                }
            } catch (NumberFormatException e) {
                writer.println(wrongNumber);
                return getParameters(parametersText, wrongNumber);
            }
        } else {
            writer.println(wrongNumber);
            return getParameters(parametersText, wrongNumber);
        }
    }


    public Generator getGenerationAlgorithm(String chooseAlgo, String wrongNumber) {
        writer.println(chooseAlgo);
        String input = reader.userInput();
        if (input.isEmpty()) {
            return new KruskalGenerator();
        }
        input = input.replaceAll(" +", "");
        if (input.length() == 1 && input.matches(ONETOTWO)) {
            int numOfAlgorithm = Integer.parseInt(input);
            if (numOfAlgorithm == 1) {
                return new PrimsGenerator();
            } else {
                return new KruskalGenerator();
            }
        } else {
            writer.println(wrongNumber);
            return getGenerationAlgorithm(chooseAlgo, wrongNumber);
        }
    }

    public void restrictions() {
        writer.println("The Y coordinate should be greater then 0 and be less than the height (" + (height + 1) + ")\n"
                + " and the X coordinate should be greater then 0 and be"
                +
                " less than the width of the maze (" + (width + 1) + ")");
    }

    public Coordinate getStart(String startText, String wrongNumber) {
        writer.println(startText);
        String input = reader.userInput();
        String[] inputAr = input.split(" ");
        if (inputAr.length == 2) {
            int row = Integer.parseInt(inputAr[0]);
            int col = Integer.parseInt(inputAr[1]);
            if (row < height + 1 && row > 0 && col > 0 && col < width + 1) {
                return new Coordinate(row - 1, col - 1);
            } else {
                writer.println(wrongNumber);
                return getStart(startText, wrongNumber);
            }
        } else {
            writer.println(wrongNumber);
            return getStart(startText, wrongNumber);
        }
    }

    public Coordinate getEnd(String endText, String wrongNumber) {
        writer.println(endText);
        String input = reader.userInput();
        input = input.trim();
        String[] inputAr = input.split(" ");
        if (inputAr.length == 2) {
            int row = Integer.parseInt(inputAr[0]);
            int col = Integer.parseInt(inputAr[1]);
            if (row < height + 1 && row > 0 && col > 0 && col < width + 1) {
                return new Coordinate(row - 1, col - 1);
            } else {
                writer.println(wrongNumber);
                return getEnd(endText, wrongNumber);
            }
        } else {
            writer.println(wrongNumber);
            return getEnd(endText, wrongNumber);
        }
    }


    public Solver getSolverAlgorithm(String chooseSolver, String wrongNumber) {
        writer.println(chooseSolver);
        String input = reader.userInput();
        if (input.isEmpty()) {
            return new AstarSolver();
        }
        input = input.trim();
        if (input.length() == 1 && input.matches(ONETOTWO)) {
            int numOfAlgorithm = Integer.parseInt(input);
            if (numOfAlgorithm == 1) {
                return new AstarSolver();
            } else {
                return new BfsSolver();
            }
        } else {
            writer.println(wrongNumber);
            return getSolverAlgorithm(chooseSolver, wrongNumber);
        }
    }


    public void mazeWithPass(String maze) {
        writer.println(maze);
    }

    public void mazeWithoutPass(String maze) {
        writer.println(maze);
    }


}
