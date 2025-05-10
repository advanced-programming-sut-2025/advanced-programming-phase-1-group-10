package Models.Place;

import Models.Position;
import Models.Tile;

import java.util.ArrayList;

public class Place {

    protected Position position;
    protected int height;
    protected int width;
    protected Tile[][] placeTiles = new Tile[height][width];

    public Place(Position position,int height,int width) {
        this.position = position;
        this.height = height;
        this.width = width;
        this.placeTiles = new Tile[height][width];
    }

    public Place() {

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Tile[][] getPlaceTiles() {
        return placeTiles;
    }

    public String getSymbol() {
        //Should never be printed
        return "!";
    }

}
