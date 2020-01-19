package agh.iet.cs.view;

import agh.iet.cs.game.GameState;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Menu extends VBox {
    private GameState gameState;

    private int width;
    private int height;

    private Label label;
    private String labelText;

    private Font labelFont = new Font("Arial", 25);

    public Menu(int width, int height, GameState gameState) {
        this.gameState = gameState;

        this.width = width;
        this.height = height;

        this.setMinSize(width, height);

        this.configureLabel();

        getChildren().add(this.label);
    }

    public void updateLabel() {
        if (this.gameState.getWinner() == null) {
            String currPlayerString = this.gameState.getCurrentPlayer().getTeam().toString();

            int actionPoints = this.gameState.getCurrentPlayer().getActionPoints();
            this.labelText = String.format("       " + currPlayerString + " player turn \n \n" +
                    "   %d action points left", actionPoints);
        }
        else {
            String winnerString = this.gameState.getWinner().toString();

            this.labelText = "  " + winnerString + " player has won";
        }

        this.label.setText(this.labelText);
    }

    private void configureLabel() {
        this.label = new Label();
        this.label.setFont(this.labelFont);
    }
}
