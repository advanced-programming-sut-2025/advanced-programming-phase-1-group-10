package Models;

import Models.Animal.Animal;
import Models.Place.Lake;
import Models.Place.Place;
import Models.Planets.Seed;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {

    private Person person;
    private Farm farm;
    private Place place;
    private Animal animal;

    private Position position;
    private TileType tileType;
    private Item item;

    private boolean plow = false;
    private boolean watered = false;
    private boolean fertilizer = false;

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

    public boolean getisPlow() {
        return plow;
    }

    public void setPlow(boolean plow) {
        this.plow = plow;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Position getPosition() {
        return position;
    }

    public void setPlantedSeed(Seed plantedSeed) {
        this.plantedSeed = plantedSeed;
    }

    public boolean isFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(boolean fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getTile() {
        return " ";
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public void render(SpriteBatch batch, float x, float y) {
        batch.draw(tileType.getSprite(), x, y);
    }
}



