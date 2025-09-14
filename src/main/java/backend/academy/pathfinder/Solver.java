package backend.academy.pathfinder;

import backend.academy.mazegenerator.Maze;
import backend.academy.utilities.Coordinate;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) throws Exception;
}
