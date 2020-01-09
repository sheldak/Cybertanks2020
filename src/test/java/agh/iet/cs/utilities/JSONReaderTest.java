package agh.iet.cs.utilities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JSONReaderTest {

    @Test
    public void testJSONReader() {
        JSONReader jsonReader = new JSONReader("src/main/resources/parameters/mapTest.json");
        assertEquals(15, jsonReader.getWidth());
        assertEquals(15, jsonReader.getHeight());

        List<Position> rivers = jsonReader.getRivers();
        assertTrue(rivers.get(0).equals(new Position(2, 4)));
        assertTrue(rivers.get(1).equals(new Position(2, 5)));

        List<Position> solidWalls = jsonReader.getSolidWalls();
        assertTrue(solidWalls.get(0).equals(new Position(1, 10)));
        assertTrue(solidWalls.get(1).equals(new Position(1, 11)));

        List<Position> destructibleWalls = jsonReader.getDestructibleWalls();
        assertTrue(destructibleWalls.get(0).equals(new Position(6, 3)));
        assertTrue(destructibleWalls.get(1).equals(new Position(6, 4)));

        List<Position> redTanks = jsonReader.getRedTanks();
        assertTrue(redTanks.get(0).equals(new Position(2, 13)));
        assertTrue(redTanks.get(1).equals(new Position(7, 13)));
        assertTrue(redTanks.get(2).equals(new Position(12, 13)));

        List<Position> blueTanks = jsonReader.getBlueTanks();
        assertTrue(blueTanks.get(0).equals(new Position(2, 1)));
        assertTrue(blueTanks.get(1).equals(new Position(7, 1)));
        assertTrue(blueTanks.get(2).equals(new Position(12, 1)));
    }
}
