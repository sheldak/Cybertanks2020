package agh.iet.cs.player;

import agh.iet.cs.utilities.Position;

public class Tank {
    private Team team;

    private Position position;

    private Position target;

    public Tank(Team team, Position position) {
        this.team = team;

        this.position = position;

        this.target = null;
    }

    public boolean equals(Object other) {
        if (other instanceof Tank && ((Tank) other).getPosition() == this.position)
            return true;
        return false;
    }

    public void setTarget(Position position) {
        this.target = position;
    }

    public void resetTarget() {
        this.target = null;
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public Team getTeam() {
        return this.team;
    }

    public Position getPosition() {
        return this.position;
    }

    public Position getTarget() {
        return this.target;
    }
}
