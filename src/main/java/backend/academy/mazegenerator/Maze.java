package backend.academy.mazegenerator;

import backend.academy.pathfinder.Weights;
import java.security.SecureRandom;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public final class Maze {
    private final static double SURFACEOCCUPANCY = 0.1;
    private final int height;
    private final int width;
    private final SecureRandom random = new SecureRandom();
    private Cell[][] grid;

    /*
    Класс Maze
    Лабиринт представлен в виде 2d матрицы состоящей из ячеек класса Cell
     */
    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = createMaze();
    }

    public Cell[][] createMaze() {
        grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        return grid;
    }

    public void makeAnotherWay() {
        while (true) {
            int y = random.nextInt(height);
            int x = random.nextInt(width);

            if (y > 0 && grid[y][x].top()) {
                grid[y][x].top(false);
                grid[y - 1][x].bottom(false);
                break;
            }
            if (y < grid.length - 1 && grid[y][x].bottom()) {
                grid[y][x].bottom(false);
                grid[y + 1][x].top(false);
                break;
            }
            if (x > 0 && grid[y][x].left()) {
                grid[y][x].left(false);
                grid[y][x - 1].right(false);
                break;
            }
            if (x < grid[0].length - 1 && grid[y][x].right()) {
                grid[y][x].right(false);
                grid[y][x + 1].left(false);
                break;
            }
        }
    }

    public void makeSurfaces() {
        int numberOfCellsForSurface = Math.max((int) (height * width * SURFACEOCCUPANCY), 1);
        createSwamp(numberOfCellsForSurface);
        createCoins(numberOfCellsForSurface);
    }

    private void createSwamp(int numberOfSwamp) {
        int counter = 0;
        while (counter < numberOfSwamp) {
            int y = random.nextInt(height);
            int x = random.nextInt(width);
            if (grid[y][x].weight() == Weights.AVERAGE) {
                grid[y][x].weight(Weights.SWAMP);
                counter++;
            }
        }
    }

    private void createCoins(int numberOfCoins) {
        int counter = 0;
        while (counter < numberOfCoins) {
            int y = random.nextInt(height);
            int x = random.nextInt(width);
            if (grid[y][x].weight() == Weights.AVERAGE) {
                grid[y][x].weight(Weights.COINS);
                counter++;
            }
        }
    }


}
