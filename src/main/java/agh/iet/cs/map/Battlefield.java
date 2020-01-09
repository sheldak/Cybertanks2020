package agh.iet.cs.map;

import agh.iet.cs.fields.*;
import agh.iet.cs.utilities.JSONReader;
import agh.iet.cs.utilities.Position;

import java.util.stream.IntStream;

public class Battlefield {
    private int sizeX, sizeY;

    private Field[][] fields;

    public Battlefield(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.fields = new Field[sizeX][sizeY];
    }

    public void addFields(JSONReader jsonReader) {
        IntStream.range(0, this.sizeX)
                .forEach(column -> IntStream.range(0, this.sizeY)
                        .forEach(row -> this.fields[column][row] = new Plain(new Position(column, row))));

        jsonReader.getRivers()
                .forEach(position -> this.fields[position.getX()][position.getY()] = new River(position));

        jsonReader.getSolidWalls()
                .forEach(position -> this.fields[position.getX()][position.getY()] = new Wall(position));

        jsonReader.getDestructibleWalls()
                .forEach(position -> this.fields[position.getX()][position.getY()] = new DestructibleWall(position));
    }

    public Field fieldAt(Position position) {
        return this.fields[position.getX()][position.getY()];
    }
}
