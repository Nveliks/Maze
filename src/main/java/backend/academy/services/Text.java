package backend.academy.services;

import lombok.Getter;

@Getter
public enum Text {
    HELLO("     Hello, let's create a maze and find a way"),
    HEIGHTWIDTH("     Enter the height and width of the maze separated by a whitespace,"
            +
            " the numbers must be greater than 1\n     The first number is height, the second is width"),
    GENERATIONALGORITHM("     There're 2 algorithms for generate a maze, enter a number\n"
            +
            "     1 - to choose the Prim's algorithm\n     2 - to choose the Kruskal's algorithm"),
    STARTEND("     Enter the starting and ending point of the path"),
    START("     Enter coordinates of starting point\n     The first number is height, the second is width"),
    END("     Enter coordinates of ending point\n     The first number is height, the second is width"),
    WRONGNUMBER("     You enter the wrong number"),
    SOLVERALGORITHM("     There're 2 algorithms for find path from start to end point, enter a number"
            +
            "\n"
            +
            "     1 - to choose the A* (surfaces)\n     2 - to choose the BfsSolver"),
    SURFACEDESCRIPTION("You choose A* algorithm, it can find a path with different surfaces (coins or swamp)"
            +
            "\n Let's break one wall and fill the maze with different surfaces"
            +
            "\n Coins is marked with '$'"
            +
            "\n Swamp is marked with '%'"),
    INTRODUCINGPATH("\n Introducing the maze with a path"),
    PATHDESCRIPTION("     The path is marked with '*' symbols\n"
            +
            "     The start point is marked with '@' symbols\n"
            +
            "     The end point is marked with '#' symbols\n"
            +
            "     It's end! Congratulations!\n"
            +
            "     Have a nice day!"),
    ENTERWRONGNUMBERALGORITHM("     You enter the wrong number of algorithm");

    final String message;

    Text(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
