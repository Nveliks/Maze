package backend.academy.pathfinder;

import lombok.Getter;

@Getter
public enum Weights {
    AVERAGE(10),
    COINS(1),
    SWAMP(50);
    private final int weight;

    Weights(int weight) {
        this.weight = weight;
    }
}

