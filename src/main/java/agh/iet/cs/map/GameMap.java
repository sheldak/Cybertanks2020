package agh.iet.cs.map;

public class GameMap {
    private final int sizeX, sizeY;

    private Battlefield battlefield;

    public GameMap(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.battlefield = new Battlefield(this.sizeX, this.sizeY);
    }

    public Battlefield getBattlefield() {
        return this.battlefield;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }
}
