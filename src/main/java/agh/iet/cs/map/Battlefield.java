package agh.iet.cs.map;

import agh.iet.cs.fields.*;
import agh.iet.cs.game.GameState;
import agh.iet.cs.player.Tank;
import agh.iet.cs.player.Team;
import agh.iet.cs.utilities.JSONReader;
import agh.iet.cs.utilities.Position;

import java.util.*;
import java.util.stream.IntStream;

public class Battlefield {
    private int sizeX, sizeY;

    private Field[][] fields;

    private List<Tank> redTanks;
    private List<Tank> blueTanks;

    public Battlefield(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.fields = new Field[sizeX][sizeY];
        this.redTanks = new ArrayList<>();
        this.blueTanks = new ArrayList<>();
    }

    public void addFields(JSONReader jsonReader) {
        IntStream.range(0, this.sizeX)
                .forEach(column -> IntStream.range(0, this.sizeY)
                        .forEach(row -> this.fields[column][row] = new Plain(new Position(column, row))));

        jsonReader.getRivers()
                .forEach(position -> this.fields[position.getX()][position.getY()] = new River(position));

        jsonReader.getSolidWalls()
                .forEach(position -> this.fields[position.getX()][position.getY()] = new Wall(position));

        jsonReader.getDestructibleWalls()
                .forEach(position -> this.fields[position.getX()][position.getY()] = new DestructibleWall(position));
    }

    public void addTanks(JSONReader jsonReader) {
        jsonReader.getRedTanks().stream()
                .filter(position -> this.fields[position.getX()][position.getY()] instanceof Plain)
                .forEach(position -> {
                    Tank newRedTank = new Tank(Team.RED, position);
                    ((Plain) this.fields[position.getX()][position.getY()]).addTank(newRedTank);
                    this.redTanks.add(newRedTank);
                });

        jsonReader.getBlueTanks().stream()
                .filter(position -> this.fields[position.getX()][position.getY()] instanceof Plain)
                .forEach(position -> {
                    Tank newBlueTank = new Tank(Team.BLUE, position);
                    ((Plain) this.fields[position.getX()][position.getY()]).addTank(newBlueTank);
                    this.blueTanks.add(newBlueTank);
                });
    }

    public void moveTank(Tank tank, Position position) {
        if (this.fieldAt(tank.getPosition()) instanceof Plain && this.fieldAt(position) instanceof Plain) {
            ((Plain) this.fieldAt(tank.getPosition())).removeTank();
            ((Plain) this.fieldAt(position)).addTank(tank);
            tank.moveTo(position);
        }
    }

    public Field fieldAt(Position position) {
        return this.fields[position.getX()][position.getY()];
    }

    public boolean tankAt(Position position) {
        if (this.fields[position.getX()][position.getY()] instanceof Plain) {
            return ((Plain) this.fields[position.getX()][position.getY()]).getTank() != null;
        }
        return false;
    }

    public void checkShot(GameState gameState) {
        List<Tank> currPlayerTanks;

        if (gameState.getCurrentPlayer().getTeam() == Team.RED)
            currPlayerTanks = this.redTanks;
        else
            currPlayerTanks = this.blueTanks;

        Position explosionPosition = null;
        for (Tank tank : currPlayerTanks) {
            if (tank.getTarget() != null) {
                explosionPosition = tank.getTarget();

                if(currPlayerTanks.contains(tank))
                    tank.resetTarget();
            }
        }

        if (explosionPosition != null)
            this.explode(explosionPosition);
    }

    public void explode(Position position) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Position offsetPos = new Position(x, y);
                Position explosionPos = position.add(offsetPos);

                if (explosionPos.isOnTheMap(this.sizeX, this.sizeY)) {
                    Field fieldAt = this.fieldAt(explosionPos);
                    if (fieldAt instanceof DestructibleWall)
                        this.fields[explosionPos.getX()][explosionPos.getY()] = new Plain(explosionPos);
                    else if (fieldAt instanceof Plain) {
                        Tank tankAt = ((Plain) fieldAt).getTank();

                        if (tankAt != null) {
                            if (this.redTanks.contains(tankAt))
                                this.redTanks.remove(tankAt);
                            else
                                this.blueTanks.remove(tankAt);

                            ((Plain) fieldAt).removeTank();
                        }
                    }
                }
            }
        }
    }

    public void checkWinner(GameState gameState) {
        if (this.blueTanks.isEmpty())
            gameState.setWinner(Team.RED);
        else if (this.redTanks.isEmpty())
            gameState.setWinner(Team.BLUE);
    }

    public Map<Position, Integer> getPossibleMoves(Position position, int actionPoints) {
        Map<Position, Integer> possibleMoves = new HashMap<>();

        List<Position> queue = new ArrayList<>();
        queue.add(position);

        while (!queue.isEmpty()) {
            Position currPosition = queue.get(0);
            queue.remove(0);

            int actionCost = 1;
            if (possibleMoves.containsKey(currPosition))
                actionCost = possibleMoves.get(currPosition) + 1;

            if (actionCost <= actionPoints) {
                for (Position neighbor :
                        (new Position[] {new Position(1, 0), new Position(0, -1),
                                new Position(-1, 0), new Position(0, 1)})) {

                    Position neighPosition = currPosition.add(neighbor);

                    if (neighPosition.isOnTheMap(this.sizeX, this.sizeY)) {

                        if (this.fieldAt(neighPosition) instanceof Plain
                                && ((Plain) this.fieldAt(neighPosition)).getTank() == null
                                && !possibleMoves.containsKey(neighPosition)) {
                            possibleMoves.put(neighPosition, actionCost);
                            queue.add(neighPosition);
                        }
                    }
                }
            }
        }

        return possibleMoves;
    }

    public List<Position> getPossibleTargets(Position position) {
        List <Position> possibleTargets = new ArrayList<>();
        int posX = position.getX();
        int posY = position.getY();

        boolean stopSearchingLeft = false;
        boolean stopSearchingRight = false;
        boolean stopSearchingUp = false;
        boolean stopSearchingDown = false;

        // left side of the tank
        for (int x = posX - 1; x >= 0 && !stopSearchingLeft; x--) {
            if (this.fields[x][posY] instanceof Wall)
                stopSearchingLeft = true;
            else if (!(this.fields[x][posY] instanceof River))
                possibleTargets.add(new Position(x, posY));
        }

        // right side of the tank
        for (int x = posX + 1; x < this.sizeX && !stopSearchingRight; x++) {
            if (this.fields[x][posY] instanceof Wall)
                stopSearchingRight = true;
            else if (!(this.fields[x][posY] instanceof River))
                possibleTargets.add(new Position(x, posY));
        }

        // above the tank
        for (int y = posY + 1; y < sizeY && !stopSearchingUp; y++) {
            if (this.fields[posX][y] instanceof Wall)
                stopSearchingUp = true;
            else if (!(this.fields[posX][y] instanceof River))
                possibleTargets.add(new Position(posX, y));
        }

        // below the tank
        for (int y = posY - 1; y >= 0 && !stopSearchingDown; y--) {
            if (this.fields[posX][y] instanceof Wall)
                stopSearchingDown = true;
            else if (!(this.fields[posX][y] instanceof River))
                possibleTargets.add(new Position(posX, y));
        }

        return possibleTargets;
    }

    public Tank getTank(Position position) {
        if (tankAt(position)) {
            return ((Plain) this.fields[position.getX()][position.getY()]).getTank();
        }
        return null;
    }

    public List<Tank> getTanks() {
        List<Tank> allTanks = new ArrayList<>();
        allTanks.addAll(this.redTanks);
        allTanks.addAll(this.blueTanks);
        return allTanks;
    }
}
