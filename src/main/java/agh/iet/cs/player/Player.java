package agh.iet.cs.player;

public class Player {
    Team team;

    int actionPoints;

    public Player(Team team) {
        this.team = team;

        this.actionPoints = 5;
    }

    public void useActionPoints(int amount) {
        this.actionPoints -= amount;
    }

    public void shoot() {
        this.actionPoints -= 3;
    }

    public boolean canShoot() {
        return this.actionPoints >= 3;
    }

    public boolean hasNoActionPoints() {
        return this.actionPoints == 0;
    }

    public void nextTurn() {
        this.actionPoints = 5;
    }

    public Team getTeam() {
        return this.team;
    }

    public int getActionPoints() {
        return this.actionPoints;
    }
}
