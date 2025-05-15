package Models.Place;

import Models.Position;
import Models.Tile;


public class Place {

    protected Position position;
    protected int height;
    protected int width;
    protected Tile[][] placeTiles;

    public Place(Position position,int height,int width) {
        this.position = position;
        this.height = height;
        this.width = width;
        this.placeTiles = new Tile[height][width];
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
        return "!!";
    }

}
