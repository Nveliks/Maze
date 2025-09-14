package backend.academy.mazegenerator;

import backend.academy.pathfinder.Weights;
import backend.academy.utilities.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {
    private final Coordinate coordinate;
    private Weights weight = Weights.AVERAGE;
    protected boolean left;
    protected boolean right;
    protected boolean top;
    protected boolean bottom;
    protected boolean visited;
    protected boolean partOfPath = false;
    private Cell parent = null;

    public Cell(int row, int col) {
        this.coordinate = new Coordinate(row, col);
        this.top = true;
        this.bottom = true;
        this.right = true;
        this.left = true;
        this.visited = false;
    }
}


