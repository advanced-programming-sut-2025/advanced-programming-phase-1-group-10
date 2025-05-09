package Models;

import Models.Place.Place;
import Models.Planets.Seed;
import Models.PlayerStuff.Player;

public class Tile {

    private Player player;
    private Farm farm;
    private Place place;

    private Position position;
    private TileType tileType;
    private Item item;

    private boolean plow;
    private boolean iswalkable;

    private Seed plantedSeed;

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void printTile() {
        if (this.player != null) {
            System.out.print("P");
        } else if(this.item != null) {
            System.out.print(item.getSymbol());
        } else if (this.getTileType() == TileType.Wall) {
            System.out.print("X");
        } else if (this.place == null) {
            System.out.print(" ");
        } else {
            System.out.print(this.place.getSymbol());
        }
    }

}

