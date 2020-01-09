package agh.iet.cs.fields;

import agh.iet.cs.utilities.Position;

public abstract class Field {
    private Position position;

    public Field(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }
}
