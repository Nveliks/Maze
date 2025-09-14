package backend.academy;


import backend.academy.mazegenerator.Cell;

public enum DIRECTION {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    NODIRECTION;

    public static Cell goTop(Cell[][] grid, Cell current) {
        return grid[current.coordinate().row() - 1][current.coordinate().col()];
    }

    public static Cell goBottom(Cell[][] grid, Cell current) {
        return grid[current.coordinate().row() + 1][current.coordinate().col()];
    }

    public static Cell goRight(Cell[][] grid, Cell current) {
        return grid[current.coordinate().row()][current.coordinate().col() + 1];
    }

    public static Cell goLeft(Cell[][] grid, Cell current) {
        return grid[current.coordinate().row()][current.coordinate().col() - 1];
    }

}

