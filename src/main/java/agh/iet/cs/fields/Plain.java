package agh.iet.cs.fields;

import agh.iet.cs.player.Tank;
import agh.iet.cs.utilities.Position;

public class Plain extends Field {

    private Tank tank;

    public Plain(Position position) {
        super(position);

        this.tank = null; // initially that field is not occupied
    }

    public void addTank(Tank tank) {
        this.tank = tank;
    }

    public void removeTank() {
        this.tank = null;
    }

    public Tank getTank() {
        return this.tank;
    }
}
