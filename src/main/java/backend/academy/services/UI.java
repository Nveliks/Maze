package backend.academy.services;

import backend.academy.mazegenerator.Generator;
import backend.academy.pathfinder.Solver;
import backend.academy.utilities.Coordinate;
import java.io.PrintStream;

public class UI {

    private final PrintStream writer;
    private final ProcessingService processingService;

    public UI(PrintStream writer, ProcessingService processingService) {

        this.writer = writer;
        this.processingService = processingService;
    }

    public Coordinate inputParameters() {
        writer.println(Text.HELLO.getMessage());
        return processingService.getParameters(Text.HEIGHTWIDTH.getMessage(), Text.WRONGNUMBER.getMessage());
    }

    public Generator chooseGenerator() {
        return processingService.getGenerationAlgorithm(Text.GENERATIONALGORITHM.getMessage(),
                Text.ENTERWRONGNUMBERALGORITHM.getMessage());
    }

    public Solver chooseFindPathAlgorithm() {
        return processingService.getSolverAlgorithm(Text.SOLVERALGORITHM.getMessage(),
                Text.ENTERWRONGNUMBERALGORITHM.getMessage());
    }

    public void chooseStartEnd() {
        writer.println(Text.STARTEND.getMessage());
        processingService.restrictions();
    }

    public Coordinate chooseStartPoint() {
        return processingService.getStart(Text.START.getMessage(), Text.WRONGNUMBER.getMessage());
    }

    public Coordinate chooseEndPoint() {
        return processingService.getEnd(Text.END.getMessage(), Text.WRONGNUMBER.getMessage());
    }

    public void getMazeWithSurfaces(String maze) {
        writer.println(Text.SURFACEDESCRIPTION.getMessage());
        writer.println(maze);
        writer.println(Text.INTRODUCINGPATH.getMessage());
    }

    public void getMazeWithoutPass(String maze) {
        processingService.mazeWithoutPass(maze);
    }

    public void getMazeWithPass(String maze) {
        processingService.mazeWithPass(maze);
        writer.println(Text.PATHDESCRIPTION.getMessage());
    }

}
