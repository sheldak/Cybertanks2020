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

    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public boolean equals(Object other){
        if (other instanceof Position)
            return this.x == ((Position) other).x && this.y == ((Position) other).y;

        return false;
    }

    public Position add(Position other) {
        return new Position(this.x + other.x, this.y + other.y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
