package backend.academy.mazerenderer;

import backend.academy.mazegenerator.Maze;
import backend.academy.utilities.Coordinate;
import java.util.List;

public interface Renderer {

    String renderer(Maze maze);

    String renderer(Maze maze, List<Coordinate> path);
}
