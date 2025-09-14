package backend.academy.pathfinder;

import backend.academy.mazegenerator.Maze;
import backend.academy.mazerenderer.MazeRenderer;
import backend.academy.utilities.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AstarSolverTest {
    MazeRenderer mazeRenderer = new MazeRenderer();
    Maze maze3x3 = createMaze3x3();
    Maze maze2x2 = createMaze2x2();
    AstarSolver astarSolver = new AstarSolver();

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
        var path = astarSolver.solve(maze3x3, START1, END1);
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
        Assertions.assertEquals(answer, astarSolver.solve(maze3x3, START2, END2));
    }

    @Test
    void shouldFindPath3() throws Exception {
        List<Coordinate> answer = new ArrayList<>();

        answer.add(new Coordinate(1, 1));
        answer.add(new Coordinate(0, 1));
        answer.add(new Coordinate(0, 2));
        answer.add(new Coordinate(1, 2));
        answer.add(new Coordinate(2, 2));
        Assertions.assertEquals(answer, astarSolver.solve(maze3x3, START3, END3));
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
        Assertions.assertEquals(answer, astarSolver.solve(maze3x3, START4, END4));
    }

    /*
Создаем лабиринт 2х2 без стен и ищем путь из клетки с индексами (0,0) в (1,1)
В 1 находим путь без поверхностей
     */

    @Test
    void shouldFindPathWithSurfaces1() throws Exception {
        List<Coordinate> answer = new ArrayList<>();
        answer.add(new Coordinate(0, 0));
        answer.add(new Coordinate(1, 0));
        answer.add(new Coordinate(1, 1));
        Assertions.assertEquals(answer, astarSolver.solve(maze2x2, new Coordinate(0, 0), new Coordinate(1, 1)));
    }

    /*
    В 2 добавляем монетки в (0,1) и путь будет проходить через эту ячейку
     */

    @Test
    void shouldFindPathWithSurfaces2() throws Exception {
        List<Coordinate> answer = new ArrayList<>();
        answer.add(new Coordinate(0, 0));
        answer.add(new Coordinate(0, 1));
        answer.add(new Coordinate(1, 1));
        maze2x2.grid()[0][1].weight(Weights.COINS);
        Assertions.assertEquals(answer, astarSolver.solve(maze2x2, new Coordinate(0, 0), new Coordinate(1, 1)));
        maze2x2.grid()[0][1].weight(Weights.AVERAGE);
    }

    /*
    В 3 добавляем болото в (1,0) и путь будет проходить через (0,1)
     */

    @Test
    void shouldFindPathWithSurfaces3() throws Exception {
        List<Coordinate> answer = new ArrayList<>();
        answer.add(new Coordinate(0, 0));
        answer.add(new Coordinate(0, 1));
        answer.add(new Coordinate(1, 1));
        maze2x2.grid()[1][0].weight(Weights.SWAMP);
        Assertions.assertEquals(answer, astarSolver.solve(maze2x2, new Coordinate(0, 0), new Coordinate(1, 1)));
        maze2x2.grid()[1][0].weight(Weights.AVERAGE);
    }

    /*
    В 4 добавляем добавляем болото в (0,1) и монетки в (1,0) путь будет проходить через (1,0)
     */

    @Test
    void shouldFindPathWithSurfaces4() throws Exception {
        List<Coordinate> answer = new ArrayList<>();
        answer.add(new Coordinate(0, 0));
        answer.add(new Coordinate(1, 0));
        answer.add(new Coordinate(1, 1));
        maze2x2.grid()[0][1].weight(Weights.SWAMP);
        maze2x2.grid()[1][0].weight(Weights.COINS);
        Assertions.assertEquals(answer, astarSolver.solve(maze2x2, new Coordinate(0, 0), new Coordinate(1, 1)));
    }


    Maze createMaze2x2() {
        Maze maze = new Maze(2, 2);
        maze.grid()[0][0].right(false);
        maze.grid()[0][0].bottom(false);
        maze.grid()[0][1].left(false);
        maze.grid()[0][1].bottom(false);
        maze.grid()[1][0].top(false);
        maze.grid()[1][0].right(false);
        maze.grid()[1][1].top(false);
        maze.grid()[1][1].left(false);
        return maze;
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


}