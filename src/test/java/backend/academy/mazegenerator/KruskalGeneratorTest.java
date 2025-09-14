package backend.academy.mazegenerator;

import backend.academy.DIRECTION;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class KruskalGeneratorTest {

    Maze maze = new Maze(2, 2);
    KruskalGenerator kruskalGenerator = new KruskalGenerator(maze);

    @Test
    void generator() {
        Maze generatedMaze = kruskalGenerator.generator(3, 3);
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
        kruskalGenerator.breakWall(current, DIRECTION.BOTTOM);

        Cell nextCell = maze.grid()[1][0];
        kruskalGenerator.breakWall(nextCell, DIRECTION.RIGHT);

        assertFalse(current.bottom);
        assertFalse(nextCell.top);
        assertFalse(nextCell.right);
    }
}