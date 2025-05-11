package Models;

import Models.Place.Place;
import Models.Planets.Seed;

public class Tile {

    private Person person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getTile() {
        if (this.person != null) {
            return this.person.getSymbol();
        } else if(this.item != null) {
            return (item.getSymbol());
        } else if (this.getTileType() == TileType.Wall) {
            return "X";
        } else if (this.place != null) {
            return place.getSymbol();
        } else {
            return " ";
        }
    }

}

