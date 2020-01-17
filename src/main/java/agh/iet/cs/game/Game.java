package agh.iet.cs.game;

import agh.iet.cs.map.GameMap;
import agh.iet.cs.player.Player;
import agh.iet.cs.player.Team;
import agh.iet.cs.utilities.JSONReader;
import agh.iet.cs.utilities.MouseManager;
import agh.iet.cs.view.GameView;

public class Game {
    private GameMap map;

    private GameView gameView;
    private GameState gameState;

    private MouseManager mouseManager;

    private Player playerRed;
    private Player playerBlue;

    public Game(GameView gameView, GameState gameState) {
        this.gameView = gameView;
        this.gameState = gameState;

        this.mouseManager = new MouseManager(gameState);

        this.playerRed = new Player(Team.RED);
        this.playerBlue = new Player(Team.BLUE);

        this.gameState.setPlayers(this.playerRed, this.playerBlue);

        this.setParameters();
    }

    public void setParameters() {
        JSONReader jsonReader = new JSONReader("src/main/resources/parameters/map1.json");

        this.map = new GameMap(jsonReader.getWidth(), jsonReader.getHeight());
        this.gameView.setFieldSize(jsonReader.getWidth(), jsonReader.getHeight());
        this.mouseManager.passViewParameters(this.gameView);

        this.map.getBattlefield().addFields(jsonReader);
        this.map.getBattlefield().addTanks(jsonReader);
    }

    public void changesListener() {
        if (this.gameState.areChangesToDraw()) {
            this.gameView.updateView(this.map);
            this.gameState.changesDrawn();
        }
    }

    public void handleLeftClick(int clickX, int clickY) {
        this.mouseManager.handleLeftClick(clickX, clickY, this.map);
    }

    public void handleRightClick(int clickX, int clickY) {
        this.mouseManager.handleRightClick(clickX, clickY, this.map);
    }
}
