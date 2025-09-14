package backend.academy.pathfinder;


import backend.academy.mazegenerator.Maze;
import backend.academy.utilities.Coordinate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import static java.lang.Math.abs;


@Log4j2
public class AstarSolver implements Solver {

    /*
    Class AsterSolver
Находит путь из точки start в точку end в лабиринте, реализуя алгоритм A*
Выбираем текущей точкой начало пути и начинаем цикл пока текущей ячейкой не станет конечная точка
Вызывается метод processCell, который обрабатывает ячейку, подсчитывает ее вес,
а также проверяет на наличие соседей и добавляет их в activeList
Ячейку с минимальным весом добавляем в closedList и удаляем из activeList
Текущей присваиваем ячейку с минимальным весом из activeList
     */

    private final ArrayList<CellAstar> closedList;
    private final ArrayList<CellAstar> activeList;
    private ArrayList<Coordinate> path;
    private CellAstar[][] aStarGrid;
    private Coordinate end;
    private Coordinate start;

    public AstarSolver() {
        this.closedList = new ArrayList<>();
        this.activeList = new ArrayList<>();
        this.path = new ArrayList<>();
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        this.end = end;
        this.start = start;
        generateAstarGrid(maze);

        CellAstar current = aStarGrid[this.start.row()][this.start.col()];
        while (!current.coordinate().equals(this.end)) {

            processCell(current);
            closedList.add(current);
            activeList.remove(current);
            if (!activeList.isEmpty()) {
                current = getMinimum(activeList);
            }
        }
        return createPath(current);
    }


    private List<Coordinate> createPath(CellAstar currentAstar) {
        path = new ArrayList<>();
        CellAstar cellAstar = currentAstar;
        while (cellAstar.coordinate() != start) {
            path.add(cellAstar.coordinate());
            if (cellAstar.previous() == null) {
                break;
            }
            cellAstar = aStarGrid[cellAstar.previous().row()][cellAstar.previous().col()];
        }
        return path.reversed();
    }

    private CellAstar getMinimum(ArrayList<CellAstar> activeList) {
        CellAstar minWeightCell = null;
        for (CellAstar cellAstar : activeList) {
            if (minWeightCell == null) {
                minWeightCell = cellAstar;
            }
            if (minWeightCell.sumWeight() > cellAstar.sumWeight()) {
                minWeightCell = cellAstar;
            }
        }
        return minWeightCell;
    }


    private void generateAstarGrid(Maze maze) {
        aStarGrid = new CellAstar[maze.height()][maze.width()];

        for (int i = 0; i < maze.height(); i++) {
            for (int j = 0; j < maze.width(); j++) {
                aStarGrid[i][j] = new CellAstar(i, j);
                aStarGrid[i][j].top(maze.grid()[i][j].top());
                aStarGrid[i][j].bottom(maze.grid()[i][j].bottom());
                aStarGrid[i][j].right(maze.grid()[i][j].right());
                aStarGrid[i][j].left(maze.grid()[i][j].left());
                aStarGrid[i][j].weight(maze.grid()[i][j].weight());
            }
        }
    }

    private int countDistanceEnd(CellAstar current, Coordinate destination) {
        return (abs(current.coordinate().row() - destination.row())
                + abs(current.coordinate().col() - destination.col())) * Weights.AVERAGE.weight();
    }

    private int countDistanceStart(CellAstar current) {

            return abs(aStarGrid[current.previous().row()][current.previous().col()].lengthtoStart()
                    + current.weight().weight());
    }

    private void updatePrevious(CellAstar current, CellAstar previous) {
        if (current.previous() == null) {
            current.previous(previous.coordinate());
            current.lengthtoStart(countDistanceStart(current));
        } else {
            Coordinate oldPrevious = current.previous();
            current.previous(previous.coordinate());
            if (countDistanceStart(current) < current.lengthtoStart()) {
                current.lengthtoStart(countDistanceStart(current));
            } else {
                current.previous(oldPrevious);
            }
        }
    }

    private void processCell(CellAstar current) {
        if (!current.top()
                && !closedList.contains(aStarGrid[current.coordinate().row() - 1][current.coordinate().col()])) {
            CellAstar topNeighbor = aStarGrid[current.coordinate().row() - 1][current.coordinate().col()];
            topNeighbor.lengthtoEnd(countDistanceEnd(topNeighbor, end));
            updatePrevious(topNeighbor, current);
            topNeighbor.fillSumWeight();

            if (!activeList.contains(topNeighbor)) {
                activeList.add(topNeighbor);
            }
        }
        if (!current.bottom()
                && !closedList.contains(aStarGrid[current.coordinate().row() + 1][current.coordinate().col()])) {
            CellAstar bottomNeighbor = aStarGrid[current.coordinate().row() + 1][current.coordinate().col()];
            bottomNeighbor.lengthtoEnd(countDistanceEnd(bottomNeighbor, end));
            updatePrevious(bottomNeighbor, current);
            bottomNeighbor.fillSumWeight();
            if (!activeList.contains(bottomNeighbor)) {
                activeList.add(bottomNeighbor);
            }
        }
        if (!current.left()
                && !closedList.contains(aStarGrid[current.coordinate().row()][current.coordinate().col() - 1])) {
            CellAstar leftNeighbor = aStarGrid[current.coordinate().row()][current.coordinate().col() - 1];
            leftNeighbor.lengthtoEnd(countDistanceEnd(leftNeighbor, end));
            updatePrevious(leftNeighbor, current);
            leftNeighbor.fillSumWeight();
            if (!activeList.contains(leftNeighbor)) {
                activeList.add(leftNeighbor);
            }
        }
        if (!current.right()
                && !closedList.contains(aStarGrid[current.coordinate().row()][current.coordinate().col() + 1])) {
            CellAstar rightNeighbor = aStarGrid[current.coordinate().row()][current.coordinate().col() + 1];
            rightNeighbor.lengthtoEnd(countDistanceEnd(rightNeighbor, end));
            updatePrevious(rightNeighbor, current);
            rightNeighbor.fillSumWeight();
            if (!activeList.contains(rightNeighbor)) {
                activeList.add(rightNeighbor);
            }
        }
    }
}
