package agh.iet.cs.player;

import javafx.scene.paint.Color;

public enum Team {
    RED,
    BLUE;

    public Color getColor() {
        if (this == RED)
            return Color.DARKRED;
        else
            return Color.DARKBLUE;
    }

    public String toString() {
        if (this == RED)
            return "red";
        else
            return "blue";
    }
}
