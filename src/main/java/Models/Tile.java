package Models;

import Models.Place.Place;
import Models.Planets.Seed;

public class Tile {

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

    public void printTile(){
        if(this.getTileType() == TileType.Wall){
            System.out.print("X");
        } else if(this.place == null){
            System.out.print(" ");
        } else {
            System.out.print(this.place.getSymbol());
        }
    }

}
