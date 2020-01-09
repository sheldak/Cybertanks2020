package agh.iet.cs.view;

import agh.iet.cs.fields.*;
import agh.iet.cs.map.GameMap;
import agh.iet.cs.utilities.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.stream.IntStream;

public class MapVisualizer {
    private Canvas canvas;

    private int width;
    private int height;

    private int fieldSizeX;
    private int fieldSizeY;

    public MapVisualizer(int width, int height, Canvas canvas) {
        this.canvas = canvas;

        this.width = width;
        this.height = height;
    }

    public void setFieldSize(int fieldNumberX, int fieldNumberY) {
        this.fieldSizeX = this.width / fieldNumberX;
        this.fieldSizeY = this.height / fieldNumberY;
    }

    void nextFrame(GameMap gameMap) {
        IntStream.range(0, gameMap.getSizeX())
                .forEach(column -> IntStream.range(0, gameMap.getSizeY())
                    .mapToObj(row -> new Position(column, row))
                    .forEach(position -> this.drawField(gameMap.getBattlefield().fieldAt(position))));
    }

    private void drawField(Field field) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        Position position = field.getPosition();
        int layoutX = position.getX() * this.fieldSizeX;
        int layoutY = this.height - (position.getY() + 1) * this.fieldSizeY;

        if (field instanceof Plain)
            gc.setFill(Color.GREEN);
        else if (field instanceof River)
            gc.setFill(Color.BLUE);
        else if (field instanceof DestructibleWall)
            gc.setFill(Color.BROWN);
        else if (field instanceof Wall)
            gc.setFill(Color.GREY);

        gc.fillRect(layoutX, layoutY, this.fieldSizeX, this.fieldSizeY);
    }
}
