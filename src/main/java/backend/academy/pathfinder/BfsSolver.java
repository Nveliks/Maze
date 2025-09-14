package backend.academy.pathfinder;

import backend.academy.mazegenerator.Maze;
import backend.academy.utilities.Coordinate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class BfsSolver implements Solver {

    /*
    Class BfsSolver
Находит путь из точки start в точку end, используя алгоритм bfs
Назначаем текущей ячейкой - старт, добавляем ее в очередь и пока очередь не пуста будет попать из нее ячейку,
проверять ее соседей и добавлять их если они не посещены
     */

    private Coordinate end;
    private Coordinate start;
    private int height;
    private int width;
    private ArrayList<Coordinate> path;
    private CellBFS[][] grid;
    private Deque<CellBFS> deque = new ArrayDeque<>();

    public BfsSolver() {
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        this.width = maze.width();
        this.height = maze.height();
        this.end = end;
        this.start = start;
        this.grid = fillGrid((maze));
        CellBFS cell = grid()[this.start.row()][this.start.col()];
        cell.visited(true);
        return makePath(bfs(cell));
    }

    private CellBFS bfs(CellBFS cell) {
        CellBFS current = cell;
        deque.add(current);
        while (!deque.isEmpty()) {
            current = deque.pop();
            if (current.coordinate().equals(end)) {
                return current;
            }
            fillDeque(current);
        }
        return null;
    }

    private void fillDeque(CellBFS currentCell) {
        if ((currentCell.coordinate().row() >= 1) && !currentCell.top()) {
            if (!grid[currentCell.coordinate().row() - 1][currentCell.coordinate().col()].visited()) {
                CellBFS topNeighbor = currentCell.goTop(grid);
                deque.add(topNeighbor);
            }
        }
        if ((currentCell.coordinate().row() < height() - 1) && !currentCell.bottom()) {
            if (!grid[currentCell.coordinate().row() + 1][currentCell.coordinate().col()].visited()) {
                CellBFS bottomNeighbor = currentCell.goBottom(grid);
                deque.add(bottomNeighbor);
            }
        }

        if ((currentCell.coordinate().col() >= 1) && !currentCell.left()) {
            if (!grid[currentCell.coordinate().row()][currentCell.coordinate().col() - 1].visited()) {
                CellBFS leftNeighbor = currentCell.goLeft(grid);
                deque.add(leftNeighbor);
            }
        }
        if ((currentCell.coordinate().col() < width() - 1) && !currentCell.right()) {
            if (!grid[currentCell.coordinate().row()][currentCell.coordinate().col() + 1].visited()) {
                CellBFS rightNeighbor = currentCell.goRight(grid);
                deque.add(rightNeighbor);
            }
        }
    }

    private List<Coordinate> makePath(CellBFS cellBFS) {
        CellBFS current = cellBFS;
        path = new ArrayList<>();
        while (!current.coordinate().equals(start)) {
            path.add(current.coordinate());
            current = current.previous();
        }
        path.add(start);
        return path.reversed();
    }


    private CellBFS[][] fillGrid(Maze maze) {
        grid = new CellBFS[maze.height()][maze.width()];
        for (int i = 0; i < maze.height(); i++) {
            for (int j = 0; j < maze.width(); j++) {
                grid[i][j] = new CellBFS(i, j);
                grid[i][j].top(maze.grid()[i][j].top());
                grid[i][j].bottom(maze.grid()[i][j].bottom());
                grid[i][j].right(maze.grid()[i][j].right());
                grid[i][j].left(maze.grid()[i][j].left());
            }
        }
        return grid;
    }
}
