package io.github.anpep.MazeTool.model;

public enum Move {
    NORTH, EAST, SOUTH, WEST;

    public int toOrientationIndex() {
        return switch (this) {
            case NORTH -> 0;
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "O";
        };
    }
}
