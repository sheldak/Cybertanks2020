package agh.iet.cs.utilities;

public class Position {
    private final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean equals(Position other){
        return this.x == other.x && this.y == other.y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
