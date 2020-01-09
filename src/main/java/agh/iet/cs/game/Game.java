package agh.iet.cs.game;

import agh.iet.cs.map.GameMap;
import agh.iet.cs.utilities.JSONReader;
import agh.iet.cs.view.GameView;

public class Game {
    private GameMap map;

    private GameView gameView;

    public Game(GameView gameView) {
        this.gameView = gameView;

        this.setParameters();
    }

    public void setParameters() {
        JSONReader jsonReader = new JSONReader("src/main/resources/parameters/map1.json");

        this.map = new GameMap(jsonReader.getWidth(), jsonReader.getHeight());
        this.gameView.setFieldSize(jsonReader.getWidth(), jsonReader.getHeight());

        this.map.getBattlefield().addFields(jsonReader);
    }

    public void nextFrame() {
        gameView.nextFrame(map);
    }

}
