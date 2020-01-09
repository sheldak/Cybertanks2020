package agh.iet.cs.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class JSONReader {
    // class responsible for extracting world parameters from json file

    // maps' parameters
    private int width;
    private int height;

    private List<Position> rivers = new ArrayList<>();
    private List<Position> solidWalls = new ArrayList<>();
    private List<Position> destructibleWalls = new ArrayList<>();

    private List<Position> redTanks = new ArrayList<>();
    private List<Position> blueTanks = new ArrayList<>();

    public JSONReader(String path) {
        // reading parameters
        try {
            Object object = new JSONParser().parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;

            this.width = Math.toIntExact((Long) jsonObject.get("width"));
            this.height = Math.toIntExact((Long) jsonObject.get("height"));

            JSONArray riversPositions = (JSONArray) jsonObject.get("rivers");
            for (Object riverPosition : riversPositions) {
                JSONArray riverPosAsArray = (JSONArray) riverPosition;
                int posX = Math.toIntExact((Long) riverPosAsArray.get(0));
                int posY = Math.toIntExact((Long) riverPosAsArray.get(1));
                this.rivers.add(new Position(posX, posY));
            }

            JSONArray solidWallsPositions = (JSONArray) jsonObject.get("solidWalls");
            for (Object solidWallPosition : solidWallsPositions) {
                JSONArray solidWallPosAsArray = (JSONArray) solidWallPosition;
                int posX = Math.toIntExact((Long) solidWallPosAsArray.get(0));
                int posY = Math.toIntExact((Long) solidWallPosAsArray.get(1));
                this.solidWalls.add(new Position(posX, posY));
            }

            JSONArray destructibleWallsPositions = (JSONArray) jsonObject.get("destructibleWalls");
            for (Object destructibleWallPosition : destructibleWallsPositions) {
                JSONArray destructibleWallPosAsArray = (JSONArray) destructibleWallPosition;
                int posX = Math.toIntExact((Long) destructibleWallPosAsArray.get(0));
                int posY = Math.toIntExact((Long) destructibleWallPosAsArray.get(1));
                this.destructibleWalls.add(new Position(posX, posY));
            }

            JSONArray redTanksPositions = (JSONArray) jsonObject.get("redTanks");
            for (Object redTankPosition : redTanksPositions) {
                JSONArray redTankPosAsArray = (JSONArray) redTankPosition;
                int posX = Math.toIntExact((Long) redTankPosAsArray.get(0));
                int posY = Math.toIntExact((Long) redTankPosAsArray.get(1));
                this.redTanks.add(new Position(posX, posY));
            }

            JSONArray blueTanksPositions = (JSONArray) jsonObject.get("blueTanks");
            for (Object blueTankPosition : blueTanksPositions) {
                JSONArray blueTankPosAsArray = (JSONArray) blueTankPosition;
                int posX = Math.toIntExact((Long) blueTankPosAsArray.get(0));
                int posY = Math.toIntExact((Long) blueTankPosAsArray.get(1));
                this.blueTanks.add(new Position(posX, posY));
            }


        } catch (Exception ex) {
            out.println("loading parameters has failed");
        }
    }

    // getters
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public List<Position> getRivers() {
        return this.rivers;
    }

    public List<Position> getSolidWalls() {
        return this.solidWalls;
    }

    public List<Position> getDestructibleWalls() {
        return this.destructibleWalls;
    }

    public List<Position> getRedTanks() {
        return this.redTanks;
    }

    public List<Position> getBlueTanks() {
        return this.blueTanks;
    }

}
