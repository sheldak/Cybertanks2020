package agh.iet.cs.game;

import agh.iet.cs.player.Player;
import agh.iet.cs.player.Tank;
import agh.iet.cs.player.Team;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<Player> players;

    private int currentPlayer;

    private boolean changesToDraw;

    private Tank selectedTank;

    private Team winner;

    public GameState() {
        this.changesToDraw = true;

        this.selectedTank = null;

        this.winner = null;
    }

    public void setPlayers(Player redPlayer, Player bluePlayer) {
        this.players = new ArrayList<>();

        this.players.add(redPlayer);
        this.players.add(bluePlayer);

        this.currentPlayer = 0;
    }

    public void changePlayer() {
        this.getCurrentPlayer().nextTurn();
        this.currentPlayer = (this.currentPlayer + 1) % this.players.size();
    }

    public void changesDrawn() {
        this.changesToDraw = false;
    }

    public void newChangesToDraw() {
        this.changesToDraw = true;
    }

    public void selectTank(Tank tank) {
        this.selectedTank = tank;
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.currentPlayer);
    }

    public boolean areChangesToDraw() {
        return this.changesToDraw;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public Tank getSelectedTank() {
        return this.selectedTank;
    }

    public Team getWinner() {
        return this.winner;
    }
}
