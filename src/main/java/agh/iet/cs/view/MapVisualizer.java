package agh.iet.cs.view;

import agh.iet.cs.fields.*;
import agh.iet.cs.map.GameMap;
import agh.iet.cs.player.Tank;
import agh.iet.cs.player.Team;
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

    void updateView(GameMap gameMap) {
        IntStream.range(0, gameMap.getSizeX())
                .forEach(column -> IntStream.range(0, gameMap.getSizeY())
                    .mapToObj(row -> new Position(column, row))
                    .forEach(position -> this.drawField(gameMap.getBattlefield().fieldAt(position))));

        gameMap.getBattlefield()
                .getTanks()
                .forEach(this::drawTank);

        gameMap.getBattlefield()
                .getTanks()
                .stream()
                .filter(tank -> tank.getTarget() != null)
                .forEach(this::drawTarget);
    }

    public int getFieldSizeX() {
        return this.fieldSizeX;
    }

    public int getFieldSizeY() {
        return this.fieldSizeY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private void drawField(Field field) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        Position position = field.getPosition();
        int layoutX = position.getX() * this.fieldSizeX;
        int layoutY = this.height - (position.getY() + 1) * this.fieldSizeY;

        if (field instanceof Plain)
            gc.setFill(Color.GREEN);
        else if (field instanceof River)
            gc.setFill(Color.rgb(0, 0, 175));
        else if (field instanceof DestructibleWall)
            gc.setFill(Color.rgb(80, 80, 80));
        else if (field instanceof Wall)
            gc.setFill(Color.GREY);

        gc.fillRect(layoutX, layoutY, this.fieldSizeX, this.fieldSizeY);
    }

    private void drawTank(Tank tank) {
        drawCircle(tank.getPosition(), 0, 0, this.fieldSizeX, this.fieldSizeY, tank.getTeam().getColor());
    }

    private void drawTarget(Tank tank) {
        drawCircle(tank.getTarget(), this.fieldSizeX / 4, this.fieldSizeX / 4,
                this.fieldSizeX / 2, this.fieldSizeY / 2, tank.getTeam().getColor());
    }

    private void drawCircle(Position position, int offsetX, int offsetY, int axisX, int axisY, Color color) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        int layoutX = position.getX() * this.fieldSizeX + offsetX;
        int layoutY = this.height - (position.getY() + 1) * this.fieldSizeY + offsetY;

        gc.setFill(color);
        gc.fillOval(layoutX, layoutY, axisX, axisY);
    }
}
