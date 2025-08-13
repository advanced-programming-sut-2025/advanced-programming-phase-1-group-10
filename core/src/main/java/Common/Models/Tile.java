package Common.Models;

import Common.Models.Animal.Animal;
import Common.Models.Place.GreenHouse;
import Common.Models.Place.Place;
import Common.Models.Planets.Crop.Crop;
import Common.Models.Planets.Seed;
import Common.Models.Planets.Tree;
import Common.Models.PlayerStuff.Player;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

public class Tile {

    public Tile() {

    }

    private Person person;
    private Farm farm;
    private Place place;
    private Animal animal;
    private Crop crop;
    private Tree tree;

    private Position position;
    private TileType tileType;
    private Item item;

    private boolean plow = false;
    private boolean watered = false;
    private boolean fertilizer = false;

    private TextureRegion assetRegionOutside;
    private TextureRegion assetRegionInside;
    private TextureRegion brokenOutsideForGreenhouse;
    private boolean renderInside = false; // default to false

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

    public Seed getPlantedSeed() {
        return plantedSeed;
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

    public void setAssetRegionOutside(TextureRegion region) {
        this.assetRegionOutside = region;
    }

    public TextureRegion getAssetRegionOutside() {
        return assetRegionOutside;
    }

    public boolean isWalkable() {
        Player current = App.getInstance().getCurrentGame().getCurrentPlayer();

        // Check farm access
        if(farm != null && farm != current.getFarm()) {
            if(current.getCouple() == null || farm != current.getCouple().getFarm()) {
                return false; // not your farm or your couple's farm
            }
        }

        // Energy check
        if(current.getEnergy().getEnergyAmount() <= 0){
            return false;
        }

        // Greenhouse check
        if(place != null && place instanceof GreenHouse){
            if(!((GreenHouse) place).isFixed()){
                return false;
            }
        }

        // Tile occupancy check
        return tileType.isWalkable() && item == null && animal == null;
    }


    public void setAssetRegionInside(TextureRegion region) {
        this.assetRegionInside = region;
    }

    public TextureRegion getAssetRegionInside() {
        return assetRegionInside;
    }

    public void setRenderInside(boolean inside) {
        this.renderInside = inside;
    }

    public boolean isRenderInside() {
        return renderInside;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public boolean isPlow() {
        return plow;
    }

    public TextureRegion getBrokenOutsideForGreenhouse() {
        return brokenOutsideForGreenhouse;
    }

    public void setBrokenOutsideForGreenhouse(TextureRegion brokenOutsideForGreenhouse) {
        this.brokenOutsideForGreenhouse = brokenOutsideForGreenhouse;
    }
}
