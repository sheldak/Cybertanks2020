package map;

import agh.iet.cs.fields.DestructibleWall;
import agh.iet.cs.fields.Plain;
import agh.iet.cs.fields.River;
import agh.iet.cs.fields.Wall;
import agh.iet.cs.map.Battlefield;
import agh.iet.cs.map.GameMap;
import agh.iet.cs.player.Team;
import agh.iet.cs.utilities.JSONReader;
import agh.iet.cs.utilities.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BattlefieldTest {
    JSONReader jsonReader = new JSONReader("src/main/resources/parameters/mapTest.json");
    GameMap map = new GameMap(jsonReader.getWidth(), jsonReader.getHeight());

    @Test
    public void testAddFieldsAndFieldsAt() {
        this.map.getBattlefield().addFields(this.jsonReader);

        Battlefield battlefield = this.map.getBattlefield();

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                if (x == 2 && (y == 4 || y == 5))
                    assertTrue(battlefield.fieldAt(new Position(x, y)) instanceof River);
                else if (x == 1 && (y == 10 || y == 11))
                    assertTrue(battlefield.fieldAt(new Position(x, y)) instanceof Wall
                            && !(battlefield.fieldAt(new Position(x, y)) instanceof DestructibleWall));
                else if (x == 6 && (y == 3 || y == 4))
                    assertTrue(battlefield.fieldAt(new Position(x, y)) instanceof DestructibleWall);
                else
                    assertTrue(battlefield.fieldAt(new Position(x, y)) instanceof Plain);
            }
        }
    }

    @Test
    public void testAddTanksAndTanksAt() {
        this.map.getBattlefield().addFields(this.jsonReader);
        this.map.getBattlefield().addTanks(this.jsonReader);

        Battlefield battlefield = this.map.getBattlefield();

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                if (x == 2 && y == 13) {
                    assertNotNull(battlefield.getTank(new Position(x, y)));
                    assertEquals(Team.RED, battlefield.getTank(new Position(x, y)).getTeam());
                }
                else if (x == 7 && y == 13) {
                    assertNotNull(battlefield.getTank(new Position(x, y)));
                    assertEquals(Team.RED, battlefield.getTank(new Position(x, y)).getTeam());
                }
                else if (x == 12 && y == 13) {
                    assertNotNull(battlefield.getTank(new Position(x, y)));
                    assertEquals(Team.RED, battlefield.getTank(new Position(x, y)).getTeam());
                }
                else if (x == 2 && y == 1) {
                    assertNotNull(battlefield.getTank(new Position(x, y)));
                    assertEquals(Team.BLUE, battlefield.getTank(new Position(x, y)).getTeam());
                }
                else if (x == 7 && y == 1) {
                    assertNotNull(battlefield.getTank(new Position(x, y)));
                    assertEquals(Team.BLUE, battlefield.getTank(new Position(x, y)).getTeam());
                }
                else if (x == 12 && y == 1) {
                    assertNotNull(battlefield.getTank(new Position(x, y)));
                    assertEquals(Team.BLUE, battlefield.getTank(new Position(x, y)).getTeam());
                }
                else
                    assertNull(battlefield.getTank(new Position(x, y)));
            }
        }
    }

    @Test
    void testGetPossibleTargets() {
        this.map.getBattlefield().addFields(this.jsonReader);

        Battlefield battlefield = this.map.getBattlefield();

        Position startPoint = new Position(6, 10);
        List<Position> possibleTargets = battlefield.getPossibleTargets(startPoint);

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                if (x == 6 && (y >= 4 && y != 10)) {
                    assertTrue(possibleTargets.contains(new Position(x, y)));
                }
                else if (y == 10 && (x >= 1 && x != 6)) {
                    assertTrue(possibleTargets.contains(new Position(x, y)));
                }
                else  {
                    assertFalse(possibleTargets.contains(new Position(x, y)));
                }

            }
        }
    }

}
