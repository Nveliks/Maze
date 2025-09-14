package backend.academy.pathfinder;

import backend.academy.mazegenerator.Maze;
import backend.academy.mazerenderer.MazeRenderer;
import backend.academy.utilities.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class BfsSolverTest {
    MazeRenderer mazeRenderer = new MazeRenderer();
    Maze maze = createMaze3x3();
    BfsSolver bfsSolver = new BfsSolver();

    Coordinate START1 = new Coordinate(0, 0);
    Coordinate END1 = new Coordinate(2, 2);
    Coordinate START2 = new Coordinate(2, 2);
    Coordinate END2 = new Coordinate(0, 0);
    Coordinate START3 = new Coordinate(1, 1);
    Coordinate END3 = new Coordinate(2, 2);
    Coordinate START4 = new Coordinate(2, 2);
    Coordinate END4 = new Coordinate(2, 0);

    @Test
    void shouldFindPath1() throws Exception {
        List<Coordinate> answer = new ArrayList<>();
        answer.add(new Coordinate(0, 0));
        answer.add(new Coordinate(1, 0));
        answer.add(new Coordinate(1, 1));
        answer.add(new Coordinate(0, 1));
        answer.add(new Coordinate(0, 2));
        answer.add(new Coordinate(1, 2));
        answer.add(new Coordinate(2, 2));


        createMaze3x3();

        List<Coordinate> path = bfsSolver.solve(maze, START1, END1);

        Assertions.assertEquals(answer, path);
    }


    @Test
    void shouldFindPath2() throws Exception {
        List<Coordinate> answer = new ArrayList<>();

        answer.add(new Coordinate(2, 2));
        answer.add(new Coordinate(1, 2));
        answer.add(new Coordinate(0, 2));
        answer.add(new Coordinate(0, 1));
        answer.add(new Coordinate(1, 1));
        answer.add(new Coordinate(1, 0));
        answer.add(new Coordinate(0, 0));

        createMaze3x3();


        Assertions.assertEquals(answer, bfsSolver.solve(maze, START2, END2));
    }

    @Test
    void shouldFindPath3() throws Exception {
        List<Coordinate> answer = new ArrayList<>();

        answer.add(new Coordinate(1, 1));
        answer.add(new Coordinate(0, 1));
        answer.add(new Coordinate(0, 2));
        answer.add(new Coordinate(1, 2));
        answer.add(new Coordinate(2, 2));


        createMaze3x3();

        Assertions.assertEquals(answer, bfsSolver.solve(maze, START3, END3));
    }

    @Test
    void shouldFindPath4() throws Exception {
        List<Coordinate> answer = new ArrayList<>();

        answer.add(new Coordinate(2, 2));
        answer.add(new Coordinate(1, 2));
        answer.add(new Coordinate(0, 2));
        answer.add(new Coordinate(0, 1));
        answer.add(new Coordinate(1, 1));
        answer.add(new Coordinate(2, 1));
        answer.add(new Coordinate(2, 0));

        createMaze3x3();
        MazeRenderer mazeRenderer = new MazeRenderer();
        Assertions.assertEquals(answer, bfsSolver.solve(maze, START4, END4));
    }

    Maze createMaze3x3() {
        Maze maze = new Maze(3, 3);
        maze.grid()[0][0].bottom(false);
        maze.grid()[1][0].top(false);
        maze.grid()[1][0].right(false);
        maze.grid()[1][1].left(false);
        maze.grid()[2][0].right(false);
        maze.grid()[2][1].left(false);
        maze.grid()[2][1].top(false);
        maze.grid()[1][1].bottom(false);
        maze.grid()[1][1].top(false);
        maze.grid()[0][1].bottom(false);
        maze.grid()[0][1].right(false);
        maze.grid()[0][2].left(false);
        maze.grid()[0][2].bottom(false);
        maze.grid()[1][2].top(false);
        maze.grid()[1][2].bottom(false);
        maze.grid()[2][2].top(false);
        return maze;
    }

    @Test
    void solve() {
    }

    @Test
    void path() {
    }
}