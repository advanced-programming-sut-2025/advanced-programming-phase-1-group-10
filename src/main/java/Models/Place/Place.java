package Models.Place;

import Models.Position;
import Models.Tile;

import java.util.ArrayList;

public class Place {

    protected Position position;
    protected int height;
    protected int width;
    private final ArrayList<Tile> placeTiles = new ArrayList<>();

    public Place(Position position,int height,int width) {
        this.position = position;
        this.height = height;
        this.width = width;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ArrayList<Tile> getPlaceTiles() {
        return placeTiles;
    }

}
