package backend.academy.mazegenerator;

import backend.academy.DIRECTION;
import backend.academy.utilities.Coordinate;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KruskalGenerator implements Generator {

    /*
    Class KruskalGenerator
Это генератор лабиринта, основанный на алгоритме Красскала
Генерация лабиринта происходит следующим образом  изначально все ячейки имеют поле visited = false
Заполняется arraylist availableCell всеми ячейками
Реализация представляет собой прохождение по всем ячейкам лабиринта и их объединением между собой
Создается HashMap unions ключом являются ячейки родители (текущая ячейка для которой был вызван метод createNewUnion),
а значением является ArrayList  с ячейками у которых в поле parent  лежит ячейка родитель
При объединении нескольких юнионов значения ArrayList одного из ключей переходят другому ключу и ключ удаляется
В конце в unions остается 1 ключ, с с ArrayList  в котором находятся все клетки
Проход ячеек происходит следующим образом:
рандомно достается одна ячейка и вызывается для нее метод makeUnion
В нем создается лист соседей availableNeighbors в который передается метод getAvailableNeighbors
В этом методе проверяются соседи сверху справа, снизу и
слева, если их не посещали, то выбирается 1 из них и возвращается
Если их посещали, то вызывается метод findNeighborsUniqueParents для посещенных соседей
В котором заполняется HashMap ключ уникальный родитель, а значение ArrayList  с представителями
Возвращается Arraylist с одним представителем от каждого родителя
Далее идет объединение текущей ячейки с ячейками из availableNeighbors
Объединение происходит следующим образом :
В метод передаются параметрами текущая ячейка и ячейка с которой она должна объединиться,
 а также направление от первой ко второй
Сначала происходит проверка существует ли родители у этих ячеек или нет, если нет, то создается новое объединение,
 с родителем текущей ячейкой
Если у соседней клетки есть родитель, то текущая ячейка присоединяется к объединению соседней
 и ее родителем становится родитель соседней
Если у обоих клеток есть родители, то родителем у после объединения станет тот родителей,
 у которого больше всего детей(ячеек), также все дети(ячейки) бывшего родители у которого меньше детей
  переходят к родителю с большим количеством детей
 На последнем этапом разрушаются стенки между текущей и соседней клеткой
     */

    private HashMap<Cell, ArrayList<Coordinate>> unions;
    private List<Cell> availableCell;
    private Maze maze;
    private final SecureRandom random = new SecureRandom();

    public KruskalGenerator(Maze maze) {
        this.maze = maze;
    }

    public KruskalGenerator() {

    }

    public Maze generator(int height, int width) {

        maze = new Maze(height, width);
        availableCell = fillAVAILABLECELL(maze);
        unions = new HashMap<>();
        while (!availableCell.isEmpty()) {
            Cell current = availableCell.get(random.nextInt(availableCell.size()));
            makeUnion(current);
        }
        return maze;
    }


    private void makeUnion(Cell current) {

        List<Cell> availableNeighbors = getAvailableNeighbors(current);

        for (Cell availableNeighbor : availableNeighbors) {

            union(current, availableNeighbor, getDirection(current.coordinate(), availableNeighbor.coordinate()));
        }
    }

    private DIRECTION getDirection(Coordinate current, Coordinate neighbor) {
        DIRECTION direction = DIRECTION.NODIRECTION;
        if (current.row() > neighbor.row()) {
            direction = DIRECTION.TOP;
        } else if (current.row() < neighbor.row()) {
            direction = DIRECTION.BOTTOM;
        } else if (current.col() > neighbor.col()) {
            direction = DIRECTION.LEFT;
        } else if (current.col() < neighbor.col()) {
            direction = DIRECTION.RIGHT;
        }
        return direction;
    }


    private List<Cell> getAvailableNeighbors(Cell current) {

        ArrayList<Cell> unvisitedNeighbors = new ArrayList<>();
        ArrayList<Cell> visitedNeighbors = new ArrayList<>();

        if (current.coordinate().row() >= 1) {
            Cell topNeighbor = DIRECTION.goTop(maze.grid(), current);
            if (topNeighbor.visited()) {

                visitedNeighbors.add(topNeighbor);
            } else {
                unvisitedNeighbors.add(topNeighbor);
            }
        }

        if (current.coordinate().row() < maze.height() - 1) {
            Cell bottomNeighbor = DIRECTION.goBottom(maze.grid(), current);
            if (bottomNeighbor.visited()) {

                visitedNeighbors.add(bottomNeighbor);
            } else {
                unvisitedNeighbors.add(bottomNeighbor);
            }
        }

        if (current.coordinate().col() >= 1) {
            Cell leftNeighbor = DIRECTION.goLeft(maze.grid(), current);
            if (leftNeighbor.visited()) {

                visitedNeighbors.add(leftNeighbor);
            } else {
                unvisitedNeighbors.add(leftNeighbor);
            }
        }

        if (current.coordinate().col() < maze.width() - 1) {
            Cell rightNeighbor = DIRECTION.goRight(maze.grid(), current);
            if (rightNeighbor.visited()) {

                visitedNeighbors.add(rightNeighbor);
            } else {
                unvisitedNeighbors.add(rightNeighbor);
            }

        }


        if (!visitedNeighbors.isEmpty()) {

            if (visitedNeighbors.size() == 1) {
                return visitedNeighbors;
            } else {
                return findNeighborsUniqueParents(visitedNeighbors);
            }

        } else {
            int indexOfNeighbor = random.nextInt(0, unvisitedNeighbors.size());
            if (indexOfNeighbor == 0) {
                return unvisitedNeighbors.subList(0, 1);
            }
            return unvisitedNeighbors.subList(indexOfNeighbor - 1, indexOfNeighbor);

        }

    }

    private ArrayList<Cell> findNeighborsUniqueParents(ArrayList<Cell> neighborsWithParents) {
        HashMap<Cell, ArrayList<Cell>> parents = new HashMap<>();

        for (Cell visitedNeighbor : neighborsWithParents) {
            parents.putIfAbsent(visitedNeighbor.parent(), new ArrayList<>());
            parents.get(visitedNeighbor.parent()).add(visitedNeighbor);
        }


        ArrayList<Cell> neighborsToUnion = new ArrayList<>();
        for (ArrayList<Cell> neighborsUnion : parents.values()) {
            neighborsToUnion.add(neighborsUnion.get(random.nextInt(neighborsUnion.size())));
        }
        return neighborsToUnion;

    }


    public void breakWall(Cell current, DIRECTION direction) {

        switch (direction) {
            case TOP:
                Cell topNeighbor = DIRECTION.goTop(maze.grid(), current);
                current.top(false);
                topNeighbor.bottom(false);
                break;

            case BOTTOM:
                Cell bottomNeighbor = DIRECTION.goBottom(maze.grid(), current);
                current.bottom(false);
                bottomNeighbor.top(false);
                break;
            case LEFT:
                Cell leftNeighbor = DIRECTION.goLeft(maze.grid(), current);
                current.left(false);
                leftNeighbor.right(false);
                break;

            case RIGHT:
                Cell rightNeighbor = DIRECTION.goRight(maze.grid(), current);
                current.right(false);
                rightNeighbor.left(false);
                break;
            default:
                break;
        }
    }

    private void createNewUnion(Cell current, Cell neighbor) {
        current.parent(current);
        unions.put(current.parent(), new ArrayList<>());
        unions.get(current.parent()).add(current.coordinate());
        unions.get(current.parent()).add(neighbor.coordinate());
        neighbor.parent(current.parent());
        current.visited(true);
        neighbor.visited(true);
    }

    private void union(Cell current, Cell neighbor, DIRECTION direction) {

        if (current.parent() == null && neighbor.parent() == null) {
            createNewUnion(current, neighbor);

        } else if (current.parent() == null) {
            unions.get(neighbor.parent()).add(current.coordinate());
            current.parent(neighbor.parent());
            current.visited(true);
        } else {

            ArrayList<Coordinate> currentUnion = unions.get(current.parent());
            ArrayList<Coordinate> neighborUnion = unions.get(neighbor.parent());

            Cell parentForDelete;
            if (currentUnion.size() > neighborUnion.size()) {
                parentForDelete = neighbor.parent();
                for (Coordinate coordinate : neighborUnion) {
                    currentUnion.add(coordinate);
                    maze.grid()[coordinate.row()][coordinate.col()].parent(current.parent());
                }


            } else {
                parentForDelete = current.parent();
                for (Coordinate coordinate : currentUnion) {
                    neighborUnion.add(coordinate);
                    maze.grid()[coordinate.row()][coordinate.col()].parent(neighbor.parent());
                }

            }
            unions.remove(parentForDelete);
        }

        availableCell.remove(current);
        availableCell.remove(neighbor);
        breakWall(current, direction);
    }

    private List<Cell> fillAVAILABLECELL(Maze maze) {
        ArrayList<Cell> availableCells = new ArrayList<>();
        for (int i = 0; i < maze.height(); i++) {
            availableCells.addAll(
                    Arrays.asList(maze.grid()[i]).subList(0, maze.width()));

        }
        return availableCells;
    }

}
