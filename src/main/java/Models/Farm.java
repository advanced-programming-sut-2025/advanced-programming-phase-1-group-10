package Models;

import Models.Place.Place;
import Models.PlayerStuff.Player;
import java.util.ArrayList;


public class Farm {

    private Player player;
    private Position position;

    public static final int farmHeight = 24;
    public static final int farmWidth = 32;


    private final ArrayList<Tile> tiles = new ArrayList<>();
    private final ArrayList<Place> places = new ArrayList<>();


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }


}
