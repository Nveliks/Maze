package backend.academy.pathfinder;

import backend.academy.utilities.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CellAstar {

    private final Coordinate coordinate;
    @Setter
    private Coordinate previous;
    @Setter
    private Weights weight;
    @Setter
    private int lengthtoEnd;
    @Setter
    private int lengthtoStart;
    @Setter
    private int sumWeight = 0;
    @Setter
    private boolean top;
    @Setter
    private boolean bottom;
    @Setter
    private boolean left;
    @Setter
    private boolean right;


    public CellAstar(int row, int col) {
        this.coordinate = new Coordinate(row, col);
    }

    public void fillSumWeight() {
        this.sumWeight(lengthtoEnd + lengthtoStart);
    }

}
