package Models;


import Models.DateTime.DateTime;
import Models.Weather.Weather;

public class Map {

    public static final int mapWidth = 400;
    public static final int mapHeight = 300;

    private final Tile[][] map = new Tile[mapHeight][mapWidth];

    public Map(){

    }

    public Tile[][] getMap() {
        return map;
    }



}