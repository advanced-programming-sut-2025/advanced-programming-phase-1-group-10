package Models;

import Models.Place.Place;

import java.util.ArrayList;


public class Farm {

    private Position position;

    public static final int farmHeight = 48;
    public static final int farmWidth = 64;


    private final Tile[][] tiles = new Tile[farmHeight][farmWidth];
    private final ArrayList<Place> places = new ArrayList<>();



    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }


}
