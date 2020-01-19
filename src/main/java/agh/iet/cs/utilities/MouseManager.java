package agh.iet.cs.utilities;

import agh.iet.cs.game.GameState;
import agh.iet.cs.map.GameMap;
import agh.iet.cs.player.Tank;
import agh.iet.cs.view.GameView;
import agh.iet.cs.view.MapVisualizer;

import java.util.List;
import java.util.Map;

public class MouseManager {
    private int fieldSizeX;
    private int fieldSizeY;

    private int mapWidth;
    private int mapHeight;

    private GameState gameState;

    public MouseManager(GameState gameState) {
        this.gameState = gameState;
    }

    public void passViewParameters(GameView gameView) {
        MapVisualizer mapVisualizer = gameView.getMapVisualizer();
        this.fieldSizeX = mapVisualizer.getFieldSizeX();
        this.fieldSizeY = mapVisualizer.getFieldSizeY();

        this.mapWidth = mapVisualizer.getWidth();
        this.mapHeight = mapVisualizer.getHeight();
    }

    public void handleLeftClick(int clickX, int clickY, GameMap map) {
        if (clickX < this.mapWidth && clickY < mapHeight) {
            Tank tankAt = map.getBattlefield().getTank(new Position(
                    clickX / this.fieldSizeX, (this.mapHeight - clickY) / this.fieldSizeY));

            if (tankAt != null && tankAt.getTeam() == this.gameState.getCurrentPlayer().getTeam())
                this.gameState.selectTank(tankAt);

            else if (this.gameState.getSelectedTank() != null &&
                    this.gameState.getSelectedTank().getTeam() == this.gameState.getCurrentPlayer().getTeam()) {
                List<Position> possibleTargets = map.getBattlefield()
                        .getPossibleTargets(this.gameState.getSelectedTank().getPosition());
                Position target = new Position(clickX / this.fieldSizeX, (this.mapHeight - clickY) / this.fieldSizeY);

                if (possibleTargets.contains(target) && this.gameState.getCurrentPlayer().canShoot()) {
                    this.gameState.getSelectedTank().setTarget(target);
                    this.gameState.getCurrentPlayer().shoot();

                    this.gameState.newChangesToDraw();

                    if (this.gameState.getCurrentPlayer().hasNoActionPoints()) {
                        this.gameState.changePlayer();
                        map.getBattlefield().checkShot(this.gameState);
                    }
                }
            }
        }
    }

    public void handleRightClick(int clickX, int clickY, GameMap map) {
        if (clickX < this.mapWidth && clickY < mapHeight) {
            if (this.gameState.getSelectedTank() != null &&
                    this.gameState.getSelectedTank().getTeam() == this.gameState.getCurrentPlayer().getTeam()) {
                Map<Position, Integer> possibleMoves = map.getBattlefield().getPossibleMoves(
                        this.gameState.getSelectedTank().getPosition(), this.gameState.getCurrentPlayer().getActionPoints());

                Position moveDirection = new Position(
                        clickX / this.fieldSizeX, (this.mapHeight - clickY) / this.fieldSizeY);

                if (possibleMoves.containsKey(moveDirection)) {
                    map.getBattlefield().moveTank(this.gameState.getSelectedTank(), moveDirection);
                    this.gameState.getCurrentPlayer().useActionPoints(possibleMoves.get(moveDirection));

                    this.gameState.newChangesToDraw();
                    if (this.gameState.getCurrentPlayer().hasNoActionPoints()) {
                        this.gameState.changePlayer();
                        map.getBattlefield().checkShot(this.gameState);
                    }
                }
            }
        }
    }
}
