package Models.Place;

import Models.Position;
import Models.Tile;

import java.util.ArrayList;

public class Place {

    protected Position position;
    protected int height;
    protected int width;
    private final Tile[][] placeTiles;

    public Place(Position position,int height,int width) {
        this.position = position;
        this.height = height;
        this.width = width;
        placeTiles = new Tile[height][width];
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
        return "X";
    }

}
