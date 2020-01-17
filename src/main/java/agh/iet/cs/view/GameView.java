package agh.iet.cs.view;

import agh.iet.cs.game.GameState;
import agh.iet.cs.map.GameMap;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {
    private Canvas canvas;
    private MapVisualizer mapVisualizer;

    private Menu menu;

    public GameView(int width, int height, int menuWidth, GameState gameState) {
        this.canvas = new Canvas(width, height);
        this.mapVisualizer = new MapVisualizer(width, height, this.canvas);

        this.menu = new Menu(menuWidth, height, gameState);

        setMargin(this.menu, new Insets(0, width, 0, height));
        setRight(this.menu);

        this.getChildren().add(this.canvas);
    }

    public void setFieldSize(int fieldNumberX, int fieldNumberY) {
        this.mapVisualizer.setFieldSize(fieldNumberX, fieldNumberY);
    }

    public void updateView(GameMap map) {
        this.mapVisualizer.updateView(map);

        this.menu.updateLabel();
    }

    public MapVisualizer getMapVisualizer() {
        return this.mapVisualizer;
    }

}
