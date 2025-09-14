package backend.academy.mazegenerator;

import backend.academy.DIRECTION;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PrimsGeneratorTest {

    Maze maze = new Maze(2, 2);
    PrimsGenerator primsGenerator = new PrimsGenerator(maze);

    @Test
    void generator() {
        Maze generatedMaze = primsGenerator.generator(3,3);
        Assertions.assertTrue(generatedMaze.grid()[0][0].visited);
        Assertions.assertTrue(generatedMaze.grid()[0][1].visited);
        Assertions.assertTrue(generatedMaze.grid()[1][0].visited);
        Assertions.assertTrue(generatedMaze.grid()[1][1].visited);
        Assertions.assertTrue(generatedMaze.grid()[2][0].visited);
        Assertions.assertTrue(generatedMaze.grid()[2][1].visited);
        Assertions.assertTrue(generatedMaze.grid()[2][2].visited);
        Assertions.assertTrue(generatedMaze.grid()[0][2].visited);
        Assertions.assertTrue(generatedMaze.grid()[1][2].visited);






    }

    @Test
    void shouldBreakWall() {
        Cell current = maze.grid()[0][0];
        Cell nextCell = primsGenerator.wallBreaker(current, DIRECTION.BOTTOM);
        primsGenerator.wallBreaker(nextCell, DIRECTION.RIGHT);

        assertFalse(current.bottom);
        assertFalse(nextCell.top);
        assertFalse(nextCell.right);


    }

    @Test
    void shouldFindNeighbors() {
        Cell current = maze.grid()[0][0];
        List<DIRECTION> neighbors = primsGenerator.findNeighbors(current);
        Assertions.assertTrue(neighbors.contains(DIRECTION.RIGHT));
        Assertions.assertTrue(neighbors.contains(DIRECTION.BOTTOM));

    }
}