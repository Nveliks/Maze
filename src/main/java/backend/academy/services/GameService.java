package backend.academy.services;

import backend.academy.mazegenerator.Generator;
import backend.academy.mazegenerator.Maze;
import backend.academy.mazerenderer.MazeRenderer;
import backend.academy.pathfinder.AstarSolver;
import backend.academy.pathfinder.Solver;
import backend.academy.utilities.Coordinate;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameService {
    private final UI ui;
    private final InputService inputService;
    private final ProcessingService processingService;
    private final MazeRenderer mazeRenderer;
    private final PrintStream writer;

    public GameService() {
        this.writer = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        this.inputService = new InputService(System.in);
        this.processingService = new ProcessingService(inputService, writer);
        this.ui = new UI(writer, processingService);
        this.mazeRenderer = new MazeRenderer();
    }


    public GameService(File fileIn, File fileOut) throws IOException {
        this.writer = new PrintStream(fileOut, StandardCharsets.UTF_8);
        this.inputService = new InputService(fileIn);
        this.processingService = new ProcessingService(inputService, writer);
        this.ui = new UI(writer, processingService);
        this.mazeRenderer = new MazeRenderer();
    }

    public void start() throws Exception {

        Coordinate mazeHeightWidth = ui.inputParameters();
        Generator generator = ui.chooseGenerator();
        Maze maze = generator.generator(mazeHeightWidth.row(), mazeHeightWidth.col());
        ui.getMazeWithoutPass(mazeRenderer.renderer(maze));
        ui.chooseStartEnd();
        Coordinate start = ui.chooseStartPoint();
        Coordinate end = ui.chooseEndPoint();
        Solver solver = ui.chooseFindPathAlgorithm();
        if (solver.getClass() == AstarSolver.class) {
            maze.makeAnotherWay();
            maze.makeSurfaces();
            ui.getMazeWithSurfaces(mazeRenderer.renderer(maze));
        }
        List<Coordinate> path = solver.solve(maze, start, end);
        ui.getMazeWithPass(mazeRenderer.renderer(maze, path));
    }
}
