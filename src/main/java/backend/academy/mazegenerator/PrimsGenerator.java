package backend.academy.mazegenerator;


import backend.academy.DIRECTION;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrimsGenerator implements Generator {

    /*
    Class PrimsGenerator Осуществляет генерацию лабиринта на основании алгоритма Прима
Реализация представляет из себя следующее:
Создается HashMap walls где ключ это ячейка, а значение List  с направлениями на непосещенных соседей
Также создается ArrayList wayList с ключами для walls
Выбирается рандомная ячейка, для которой, она вносится в walls, также в  wayList
Пока wayList не пустой
Из walls достаем направления для текущей ячейки, выбираем рандомное и если по данному направлению сосед не посещен,
 ломаем стену, и удаляем из ArrayList, проверяем, ячейку на наличие соседей и если соседи
  есть добавляем в walls и wayList
Если у клетки нет непосещенных соседей удаляем ячейку из walls и из wayList
     */

    private final Map<Cell, List<DIRECTION>> walls = new HashMap<>();
    private final SecureRandom random = new SecureRandom();
    private final List<Cell> wayList = new ArrayList<>();
    private Maze maze;

    public PrimsGenerator(Maze maze) {
        this.maze = maze;
    }

    public PrimsGenerator() {

    }

    @Override
    public Maze generator(int height, int width) {
        maze = new Maze(height, width);

        int x = random.nextInt(height);
        int y = random.nextInt(width);
        Cell current = maze.grid()[x][y];

        walls.put(current, findNeighbors(current));
        wayList.add(current);

        while (!wayList.isEmpty()) {

            List<DIRECTION> neighbors = walls.get(current);
            current.visited(true);

            DIRECTION direction = neighbors.get(random.nextInt(neighbors.size()));
            Cell nextCell = wallBreaker(current, direction);
            neighbors.remove(direction);

            if (nextCell != null) {
                nextCell.visited(true);
                List<DIRECTION> nextCellNeighbors = findNeighbors(nextCell);
                if (!wayList.contains(nextCell) && !nextCellNeighbors.isEmpty()) {
                    walls.put(nextCell, nextCellNeighbors);
                    wayList.add(nextCell);
                }
            }

            if (neighbors.isEmpty()) {
                wayList.remove(current);
                walls.remove(current);
            }
            if (!wayList.isEmpty()) {
                current = wayList.get(random.nextInt(wayList.size()));
            }
        }
        return maze;
    }


    public Cell wallBreaker(Cell current, DIRECTION direction) {
        Cell neighbor = null;
        switch (direction) {

            case TOP:
                Cell topNeighbor = DIRECTION.goTop(maze.grid(), current);
                if (!topNeighbor.visited()) {
                    current.top(false);
                    topNeighbor.bottom(false);
                    neighbor = topNeighbor;
                }
                break;

            case BOTTOM:
                Cell botNeighbor = DIRECTION.goBottom(maze.grid(), current);
                if (!botNeighbor.visited()) {
                    current.bottom(false);
                    botNeighbor.top(false);
                    neighbor = botNeighbor;
                }
                break;

            case LEFT:
                Cell leftNeighbor = DIRECTION.goLeft(maze.grid(), current);
                if (!leftNeighbor.visited()) {
                    current.left(false);
                    leftNeighbor.right(false);
                    neighbor = leftNeighbor;
                }
                break;

            case RIGHT:
                Cell rightNeighbor = DIRECTION.goRight(maze.grid(), current);
                if (!rightNeighbor.visited()) {
                    current.right(false);
                    rightNeighbor.left(false);
                    neighbor = rightNeighbor;
                }

                break;
            default:
        }
        return neighbor;

    }

    public List<DIRECTION> findNeighbors(Cell current) {
        ArrayList<DIRECTION> neighbors = new ArrayList<>();

        if (current.coordinate().row() >= 1 && !DIRECTION.goTop(maze.grid(), current).visited()) {
            neighbors.add(DIRECTION.TOP);
        }
        if (current.coordinate().col() >= 1 && !DIRECTION.goLeft(maze.grid(), current).visited()) {
            neighbors.add(DIRECTION.LEFT);
        }
        if (current.coordinate().row() < maze.height() - 1 && !DIRECTION.goBottom(maze.grid(), current).visited()) {
            neighbors.add(DIRECTION.BOTTOM);
        }
        if (current.coordinate().col() < maze.width() - 1 && !DIRECTION.goRight(maze.grid(), current).visited()) {
            neighbors.add(DIRECTION.RIGHT);
        }
        return neighbors;
    }
}
