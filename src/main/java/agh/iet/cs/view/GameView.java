package agh.iet.cs.view;

import agh.iet.cs.map.GameMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {
    private Canvas canvas;
    private MapVisualizer mapVisualizer;

    private Menu menu;

    public GameView(int width, int height, int menuWidth) {
        this.canvas = new Canvas(width, height);
        this.mapVisualizer = new MapVisualizer(width, height, this.canvas);

        this.menu = new Menu(menuWidth, height);

        this.getChildren().add(this.canvas);
    }

    public void setFieldSize(int fieldNumberX, int fieldNumberY) {
        this.mapVisualizer.setFieldSize(fieldNumberX, fieldNumberY);
    }

    public void nextFrame(GameMap map) {
        this.mapVisualizer.nextFrame(map);
    }

}
