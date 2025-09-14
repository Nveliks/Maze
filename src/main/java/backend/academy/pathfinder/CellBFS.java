package backend.academy.pathfinder;

import backend.academy.utilities.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter

public class CellBFS {

    private final Coordinate coordinate;
    @Setter
    private boolean top;
    @Setter
    private boolean bottom;
    @Setter
    private boolean left;
    @Setter
    private boolean right;
    @Setter
    private boolean visited = false;
    @Setter
    private CellBFS previous = null;

    public CellBFS(int row, int col) {
        coordinate = new Coordinate(row, col);
    }

    public CellBFS goTop(CellBFS[][] maze) {
        CellBFS topNeighbor = maze[coordinate.row() - 1][coordinate().col()];
        topNeighbor.previous(this);
        topNeighbor.visited(true);
        return topNeighbor;
    }

    public CellBFS goBottom(CellBFS[][] maze) {
        CellBFS bottomNeighbor = maze[coordinate.row() + 1][coordinate.col()];
        bottomNeighbor.previous(this);
        bottomNeighbor.visited(true);
        return bottomNeighbor;
    }

    public CellBFS goLeft(CellBFS[][] maze) {
        CellBFS leftNeighbor = maze[coordinate.row()][coordinate.col() - 1];
        leftNeighbor.previous(this);
        leftNeighbor.visited(true);
        return leftNeighbor;
    }

    public CellBFS goRight(CellBFS[][] maze) {
        CellBFS rightNeighbor = maze[coordinate.row()][coordinate.col() + 1];
        rightNeighbor.previous(this);
        rightNeighbor.visited(true);
        return rightNeighbor;
    }
}
