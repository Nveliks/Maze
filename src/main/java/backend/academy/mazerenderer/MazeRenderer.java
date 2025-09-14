package backend.academy.mazerenderer;

import backend.academy.mazegenerator.Cell;
import backend.academy.mazegenerator.Maze;
import backend.academy.pathfinder.Weights;
import backend.academy.utilities.Coordinate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MazeRenderer implements Renderer {

/*
Class MazeRenderer
Осуществляет обработку лабиринта и путь из start в end, с помощью методов renderer
 */

    StringBuilder mazePicture;
    StringBuilder mazePicturePath;

    public MazeRenderer() {
        mazePicturePath = new StringBuilder();
    }

    /*
    Метод createTopFrame создает верхнюю границу лабиринта
     */
    public void createTopFrame(StringBuilder mazePicture, int width) {
        mazePicture.append(ConstructorOfMaze.FLOOR.sign.repeat(Math.max(0, width)));
        mazePicture.append(ConstructorOfMaze.NEXTLINE.sign);
    }

    /*
    Метод createLeftRightWalls проверяет на наличие сначала правой стены в ячейках во всю ширину лабиринта,
    а потом проверяет наличие нижней стены в ячейке
     */
    public void createLeftRightWalls(Maze maze, int i) {

        for (int j = 0; j < maze.width(); j++) {

            if (maze.grid()[i][j].right()) {
                mazePicture.append(ConstructorOfMaze.RIGHTWALL.sign);
            } else {
                mazePicture.append(ConstructorOfMaze.WHITESPACE.sign);
            }

        }
        mazePicture.append(ConstructorOfMaze.NEXTLINE.sign);

        for (int j = 0; j < maze.width(); j++) {

            if (maze.grid()[i][j].bottom()) {
                mazePicture.append(ConstructorOfMaze.FLOOR.sign);
            } else {
                mazePicture.append(ConstructorOfMaze.WHITESPACE.sign);
            }

        }
        if (i < maze.height() - 1) {
            mazePicture.append(ConstructorOfMaze.NEXTLINE.sign);
        }

    }

    @Override
    public String renderer(Maze maze) {
        mazePicture = new StringBuilder();
        createTopFrame(mazePicture, maze.width());
        for (int i = 0; i < maze.height(); i++) {
            createRightWalls(maze, mazePicture, null, null, i);
            mazePicture.append(ConstructorOfMaze.NEXTLINE.sign);
            createBottomWalls(maze, mazePicture, i);
        }
        return mazePicture.toString();
    }

    @Override
    public String renderer(Maze maze, List<Coordinate> path) {

        processForPathRenderer(maze, path);
        createTopFrame(mazePicturePath, maze.width());

        for (int i = 0; i < maze.height(); i++) {
            createLeftRightWallsWithPath(maze, mazePicturePath, path.getFirst(), path.getLast(), i);
        }
        return mazePicturePath.toString();
    }

    /*
    Метод processForPathRenderer заполняет поле partOfPath в ячейках, если они являются частью пути
     */
    private void processForPathRenderer(Maze maze, List<Coordinate> path) {
        for (Coordinate coordinate : path) {
            maze.grid()[coordinate.row()][coordinate.col()].partOfPath(true);

        }
    }

    /*
    Метод createLeftRightWallsWithPath заполняет строку с визуализацией лабиринта с путем из с точки start в точку end
    В первом цикле во всю ширину проверяет наличие правой стены, а также является ли ячейка частью пути, если да,
    то проверяет на начало или конец пути
    В следующем цикле проверка на нижнюю границу
         */
    private void createLeftRightWallsWithPath(Maze maze, StringBuilder mazePicture, Coordinate start, Coordinate end,
                                              int i) {
        createRightWalls(maze, mazePicture, start, end, i);
        mazePicture.append(ConstructorOfMaze.NEXTLINE.sign);
        createBottomWalls(maze, mazePicture, i);
    }

    private void renderPath(StringBuilder mazePicture, Cell cell, Coordinate start, Coordinate end) {

        int y = cell.coordinate().row();
        int x = cell.coordinate().col();
        if (start.row() == y && start.col() == x) {
            mazePicture.append(" @ ");
        } else if (end.row() == y && end.col() == x) {
            mazePicture.append(" # ");
        } else {
            mazePicture.append(" * ");
        }

    }

    private void renderDifferentWeights(StringBuilder mazePicture, Cell cell) {
        if ((cell.weight() == Weights.COINS)) {
            mazePicture.append(" $ ");
        } else if ((cell.weight() == Weights.SWAMP)) {
            mazePicture.append(" % ");
        } else {
            mazePicture.append("   ");
        }
    }

    private void createRightWalls(Maze maze, StringBuilder mazePicture, Coordinate start, Coordinate end,
                                  int i) {
        for (int j = 0; j < maze.width(); j++) {
            Cell cell = maze.grid()[i][j];
            if (cell.partOfPath() && start != null && end != null) {
                renderPath(mazePicture, cell, start, end);
            } else {
                renderDifferentWeights(mazePicture, cell);
            }
            if (cell.right()) {
                mazePicture.append('|');
            } else {
                mazePicture.append(' ');
            }
        }
    }

    private void createBottomWalls(Maze maze, StringBuilder mazePicture, int i) {
        for (int j = 0; j < maze.width(); j++) {

            if (maze.grid()[i][j].bottom()) {
                mazePicture.append(ConstructorOfMaze.FLOOR.sign);
            } else {
                mazePicture.append(ConstructorOfMaze.WHITESPACE.sign);
            }
        }
        if (i < maze.height() - 1) {
            mazePicture.append(ConstructorOfMaze.NEXTLINE.sign);
        }
    }

    public enum ConstructorOfMaze {
        FLOOR("----"),
        RIGHTWALL("   |"),
        WHITESPACE("    "),
        NEXTLINE("\n|");

        private final String sign;

        ConstructorOfMaze(String sign) {
            this.sign = sign;
        }
    }
}
